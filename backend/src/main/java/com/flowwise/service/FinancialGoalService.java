package com.flowwise.service;

import com.flowwise.dto.*;
import com.flowwise.entity.FinancialGoal;
import com.flowwise.entity.Merchant;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.FinancialGoalRepository;
import com.flowwise.repository.MerchantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FinancialGoalService {

    private final MerchantRepository merchantRepository;
    private final FinancialGoalRepository goalRepository;
    private final CashFlowService cashFlowService;
    private final WorkingCapitalService workingCapitalService;
    private final ReceivablesService receivablesService;
    private final PayablesService payablesService;
    private final ForecastingService forecastingService;

    public FinancialGoalService(MerchantRepository merchantRepository,
                                FinancialGoalRepository goalRepository,
                                CashFlowService cashFlowService,
                                WorkingCapitalService workingCapitalService,
                                ReceivablesService receivablesService,
                                PayablesService payablesService,
                                ForecastingService forecastingService) {
        this.merchantRepository = merchantRepository;
        this.goalRepository = goalRepository;
        this.cashFlowService = cashFlowService;
        this.workingCapitalService = workingCapitalService;
        this.receivablesService = receivablesService;
        this.payablesService = payablesService;
        this.forecastingService = forecastingService;
    }

    public FinancialGoalDTO createGoal(Long merchantId, CreateGoalRequestDTO request) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found with ID: " + merchantId));

        BigDecimal initialBaseline = deriveCurrentEngineAmount(merchantId, request.getGoalType());

        FinancialGoal goal = new FinancialGoal(
                merchant,
                request.getGoalType().toUpperCase().trim(),
                request.getName(),
                request.getTargetAmount(),
                initialBaseline,
                request.getTargetDate()
        );

        FinancialGoal saved = goalRepository.save(goal);
        return evaluateGoal(saved);
    }

    @Transactional(readOnly = true)
    public List<FinancialGoalDTO> getMerchantGoals(Long merchantId) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }

        List<FinancialGoal> goals = goalRepository.findByMerchantIdOrderByTargetDateAsc(merchantId);
        return goals.stream()
                .map(this::evaluateGoal)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FinancialGoalDTO getGoalById(Long merchantId, Long goalId) {
        FinancialGoal goal = goalRepository.findByIdAndMerchantId(goalId, merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found with ID: " + goalId + " for merchant: " + merchantId));
        return evaluateGoal(goal);
    }

    public FinancialGoalDTO evaluateAndSaveGoal(Long merchantId, Long goalId) {
        FinancialGoal goal = goalRepository.findByIdAndMerchantId(goalId, merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found with ID: " + goalId + " for merchant: " + merchantId));

        FinancialGoalDTO evaluated = evaluateGoal(goal);
        if (!"ARCHIVED".equalsIgnoreCase(goal.getStatus()) && !evaluated.getRiskStatus().equalsIgnoreCase(goal.getStatus())) {
            goal.setStatus(evaluated.getRiskStatus());
            goalRepository.save(goal);
        }
        return evaluated;
    }

    public FinancialGoalDTO archiveGoal(Long merchantId, Long goalId) {
        FinancialGoal goal = goalRepository.findByIdAndMerchantId(goalId, merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found with ID: " + goalId + " for merchant: " + merchantId));

        goal.setStatus("ARCHIVED");
        FinancialGoal saved = goalRepository.save(goal);
        return evaluateGoal(saved);
    }

    public FinancialGoalDTO evaluateGoal(FinancialGoal goal) {
        Long merchantId = goal.getMerchant().getId();
        BigDecimal currentAmount = deriveCurrentEngineAmount(merchantId, goal.getGoalType());
        BigDecimal baseline = goal.getInitialBaselineAmount() != null ? goal.getInitialBaselineAmount() : BigDecimal.ZERO;
        BigDecimal target = goal.getTargetAmount();

        boolean isReduction = "DEBT_REDUCTION".equalsIgnoreCase(goal.getGoalType()) || "EXPENSE_REDUCTION".equalsIgnoreCase(goal.getGoalType());
        String categoryType = isReduction ? "REDUCTION" : "ACCUMULATION";

        LocalDate today = LocalDate.now();
        long daysRemaining = ChronoUnit.DAYS.between(today, goal.getTargetDate());

        BigDecimal progressAmount;
        BigDecimal progressPct;
        BigDecimal remainingAmount;
        boolean achieved;

        if (isReduction) {
            // Reduction Goals (e.g. debt reduction from 450,000 to 100,000)
            progressAmount = baseline.subtract(currentAmount).max(BigDecimal.ZERO);
            BigDecimal totalReductionNeeded = baseline.subtract(target).max(BigDecimal.ONE);
            
            if (baseline.compareTo(target) > 0) {
                progressPct = progressAmount.multiply(new BigDecimal("100"))
                        .divide(totalReductionNeeded, 2, RoundingMode.HALF_UP);
            } else {
                progressPct = new BigDecimal("100.00");
            }
            
            remainingAmount = currentAmount.subtract(target).max(BigDecimal.ZERO);
            achieved = currentAmount.compareTo(target) <= 0;
        } else {
            // Accumulation Goals (e.g. cash reserve build up to 500,000)
            progressAmount = currentAmount.subtract(baseline).max(BigDecimal.ZERO);
            
            if (target.compareTo(BigDecimal.ZERO) > 0) {
                progressPct = currentAmount.multiply(new BigDecimal("100"))
                        .divide(target, 2, RoundingMode.HALF_UP);
            } else {
                progressPct = new BigDecimal("100.00");
            }
            
            remainingAmount = target.subtract(currentAmount).max(BigDecimal.ZERO);
            achieved = currentAmount.compareTo(target) >= 0;
        }

        // Cap progress % bounds between 0% and 100%
        progressPct = progressPct.min(new BigDecimal("100.00")).max(BigDecimal.ZERO);

        // Required Monthly Pace Math
        BigDecimal monthsRemaining = BigDecimal.valueOf(Math.max(1.0, daysRemaining / 30.0));
        BigDecimal requiredMonthlyPace = remainingAmount.divide(monthsRemaining, 2, RoundingMode.HALF_UP);

        // Projected Outcome Math
        BigDecimal projectedOutcome = currentAmount;
        if (daysRemaining > 0 && !achieved) {
            ForecastSummaryDTO forecast = forecastingService.getForecastSummary(merchantId);
            BigDecimal monthlyFlow = forecast.getAverageMonthlyInflow().subtract(forecast.getAverageMonthlyOutflow());
            if (isReduction) {
                projectedOutcome = currentAmount.subtract(monthlyFlow.multiply(monthsRemaining)).max(BigDecimal.ZERO);
            } else {
                projectedOutcome = currentAmount.add(monthlyFlow.multiply(monthsRemaining)).max(BigDecimal.ZERO);
            }
        }

        // Deterministic Risk Status Determination
        String riskStatus;
        String explanation;

        if ("ARCHIVED".equalsIgnoreCase(goal.getStatus())) {
            riskStatus = "ARCHIVED";
            explanation = "Goal has been archived by merchant.";
        } else if (achieved) {
            riskStatus = "ACHIEVED";
            explanation = "Goal achieved! Current balance of ₹" + currentAmount + " satisfies target of ₹" + target + ".";
        } else if (daysRemaining < 0) {
            riskStatus = "EXPIRED";
            explanation = "Deadline passed on " + goal.getTargetDate() + " before achieving target.";
        } else if (requiredMonthlyPace.compareTo(new BigDecimal("150000.00")) > 0 || (daysRemaining <= 14 && progressPct.compareTo(new BigDecimal("70.00")) < 0)) {
            riskStatus = "AT_RISK";
            explanation = "Pace requirement of ₹" + requiredMonthlyPace + "/month is tight relative to deadline (" + daysRemaining + " days remaining).";
        } else {
            riskStatus = "ON_TRACK";
            explanation = "On track to reach target of ₹" + target + " by " + goal.getTargetDate() + ".";
        }

        String calcSource = switch (goal.getGoalType()) {
            case "CASH_RESERVE" -> "Working Capital & Cash Flow Engine (Liquid Bank Balances)";
            case "WORKING_CAPITAL" -> "Working Capital Engine (Net Working Capital)";
            case "DEBT_REDUCTION" -> "Payables Engine (Total Outstanding Bills)";
            case "RECEIVABLES_COLLECTION" -> "Receivables Engine (Total Outstanding Invoices)";
            case "EXPENSE_REDUCTION" -> "Cash Flow Engine (Monthly Operating Outflows)";
            default -> "Flowwise Financial Intelligence Engine";
        };

        return new FinancialGoalDTO(
                goal.getId(),
                merchantId,
                goal.getGoalType(),
                categoryType,
                goal.getName(),
                target,
                currentAmount,
                baseline,
                progressAmount,
                progressPct,
                remainingAmount,
                goal.getTargetDate().toString(),
                daysRemaining,
                requiredMonthlyPace,
                projectedOutcome,
                riskStatus,
                explanation,
                calcSource
        );
    }

    private BigDecimal deriveCurrentEngineAmount(Long merchantId, String goalType) {
        if (goalType == null) return BigDecimal.ZERO;
        String type = goalType.toUpperCase().trim();

        return switch (type) {
            case "CASH_RESERVE" -> {
                WorkingCapitalSummaryDTO wc = workingCapitalService.getWorkingCapitalSummary(merchantId);
                yield wc != null && wc.getAvailableCash() != null ? wc.getAvailableCash() : BigDecimal.ZERO;
            }
            case "WORKING_CAPITAL" -> {
                WorkingCapitalSummaryDTO wc = workingCapitalService.getWorkingCapitalSummary(merchantId);
                yield wc != null && wc.getNetWorkingCapital() != null ? wc.getNetWorkingCapital() : BigDecimal.ZERO;
            }
            case "DEBT_REDUCTION" -> {
                PayablesSummaryDTO payables = payablesService.getPayablesSummary(merchantId);
                yield payables != null && payables.getTotalOutstanding() != null ? payables.getTotalOutstanding() : BigDecimal.ZERO;
            }
            case "RECEIVABLES_COLLECTION" -> {
                ReceivablesSummaryDTO rec = receivablesService.getReceivablesSummary(merchantId);
                yield rec != null && rec.getTotalOutstanding() != null ? rec.getTotalOutstanding() : BigDecimal.ZERO;
            }
            case "EXPENSE_REDUCTION" -> {
                CashFlowSummaryDTO cf = cashFlowService.getCashFlowSummary(merchantId);
                yield cf != null && cf.getTotalOutflows() != null ? cf.getTotalOutflows() : BigDecimal.ZERO;
            }
            default -> BigDecimal.ZERO;
        };
    }
}
