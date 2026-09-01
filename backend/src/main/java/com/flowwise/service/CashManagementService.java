package com.flowwise.service;

import com.flowwise.dto.*;
import com.flowwise.entity.Payable;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.MerchantRepository;
import com.flowwise.repository.PayableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class CashManagementService {

    private final MerchantRepository merchantRepository;
    private final PayableRepository payableRepository;
    private final CashFlowService cashFlowService;
    private final ReceivablesService receivablesService;
    private final PayablesService payablesService;
    private final WorkingCapitalService workingCapitalService;

    public CashManagementService(MerchantRepository merchantRepository,
                                 PayableRepository payableRepository,
                                 CashFlowService cashFlowService,
                                 ReceivablesService receivablesService,
                                 PayablesService payablesService,
                                 WorkingCapitalService workingCapitalService) {
        this.merchantRepository = merchantRepository;
        this.payableRepository = payableRepository;
        this.cashFlowService = cashFlowService;
        this.receivablesService = receivablesService;
        this.payablesService = payablesService;
        this.workingCapitalService = workingCapitalService;
    }

    public CashManagementSummaryDTO getCashManagementSummary(Long merchantId) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }

        // 1. Fetch underlying engine data
        CashFlowSummaryDTO cashFlow = cashFlowService.getCashFlowSummary(merchantId);
        ReceivablesSummaryDTO receivables = receivablesService.getReceivablesSummary(merchantId);
        PayablesSummaryDTO payables = payablesService.getPayablesSummary(merchantId);
        WorkingCapitalSummaryDTO workingCapital = workingCapitalService.getWorkingCapitalSummary(merchantId);

        BigDecimal availableCash = cashFlow.getNetCashFlow().max(BigDecimal.ZERO);
        if (workingCapital != null && workingCapital.getAvailableCash() != null) {
            availableCash = workingCapital.getAvailableCash();
        }

        // 2. Collections & Obligations Math
        BigDecimal expected7DayCollections = receivables.getEstimatedNearTermCollection() != null 
                ? receivables.getEstimatedNearTermCollection() 
                : BigDecimal.ZERO;
        BigDecimal expected30DayCollections = receivables.getTotalOutstanding() != null 
                ? receivables.getTotalOutstanding() 
                : BigDecimal.ZERO;

        BigDecimal upcoming7DayObligations = payables.getDueToday().add(payables.getDue7Days());
        BigDecimal upcoming30DayObligations = payables.getTotalOutstanding();

        // 3. Deterministic Projections Math: Projected Cash = Available Cash + Expected Collections - Obligations
        BigDecimal projected7DayCash = availableCash.add(expected7DayCollections).subtract(upcoming7DayObligations);
        BigDecimal projected30DayCash = availableCash.add(expected30DayCollections).subtract(upcoming30DayObligations);

        // 4. Reserve Buffer & Advisory Safe Payment Capacity Math
        BigDecimal safetyBuffer = new BigDecimal("10000.00");
        if (cashFlow.getBurnRate() != null && cashFlow.getBurnRate().compareTo(BigDecimal.ZERO) > 0) {
            safetyBuffer = cashFlow.getBurnRate().multiply(new BigDecimal("0.10")).max(new BigDecimal("10000.00"));
        }

        BigDecimal rawCapacity = availableCash.add(expected7DayCollections).subtract(upcoming7DayObligations).subtract(safetyBuffer);
        BigDecimal safePaymentCapacity = rawCapacity.compareTo(BigDecimal.ZERO) > 0 ? rawCapacity : BigDecimal.ZERO;

        // If available cash is zero or negative, capacity is strictly zero
        if (availableCash.compareTo(BigDecimal.ZERO) <= 0) {
            safePaymentCapacity = BigDecimal.ZERO;
        }

        // 5. Payment Risk Status Determination
        String riskStatus = "SAFE";
        if (availableCash.compareTo(BigDecimal.ZERO) <= 0 || projected7DayCash.compareTo(BigDecimal.ZERO) < 0) {
            riskStatus = "AT_RISK";
        } else if (projected30DayCash.compareTo(BigDecimal.ZERO) < 0 || safePaymentCapacity.compareTo(upcoming7DayObligations) < 0) {
            riskStatus = "CAUTION";
        }

        // 6. Payment Priority Ranking
        List<PaymentItemDTO> allRankedPayments = getPrioritizedPaymentItems(merchantId, safePaymentCapacity);
        List<PaymentItemDTO> top3Payments = allRankedPayments.stream().limit(3).toList();

        // 7. Explanations & Assumptions
        String summaryExplanation = "Current liquid cash of ₹" + availableCash + " with 7-day obligations of ₹" + upcoming7DayObligations 
                + " and expected collections of ₹" + expected7DayCollections + ". Projected 7-day cash position is ₹" + projected7DayCash + ".";

        String calcBasis = "Safe Payment Capacity = (Available Cash ₹" + availableCash + " + 7-Day Expected Collections ₹" 
                + expected7DayCollections + ") - 7-Day Due Obligations ₹" + upcoming7DayObligations + " - Minimum Reserve Buffer ₹" + safetyBuffer + ".";

        List<String> assumptions = new ArrayList<>();
        assumptions.add("Collections assume 100% on-time settlement of current receivables.");
        assumptions.add("Obligations include active bills due within 30 days.");
        assumptions.add("Safety Reserve Buffer set to ₹" + safetyBuffer + " based on monthly operational burn.");
        assumptions.add("Safe Payment Capacity is advisory only and does not execute bank transfers.");

        String advisoryNotice = "Advisory Liquidity Recommendation: Safe payment capacity is an estimated operational decision aid. Flowwise will never execute payments or contact vendors automatically.";

        return new CashManagementSummaryDTO(
                availableCash,
                upcoming7DayObligations,
                upcoming30DayObligations,
                expected7DayCollections,
                expected30DayCollections,
                projected7DayCash,
                projected30DayCash,
                safePaymentCapacity,
                riskStatus,
                top3Payments,
                summaryExplanation,
                calcBasis,
                assumptions,
                advisoryNotice
        );
    }

    public PaymentPlanDTO getPaymentPlan(Long merchantId) {
        CashManagementSummaryDTO summary = getCashManagementSummary(merchantId);
        List<PaymentItemDTO> allPayments = getPrioritizedPaymentItems(merchantId, summary.getSafePaymentCapacity());

        List<PaymentItemDTO> recommended = new ArrayList<>();
        List<PaymentItemDTO> deferred = new ArrayList<>();

        BigDecimal recommendedTotal = BigDecimal.ZERO;
        BigDecimal deferredTotal = BigDecimal.ZERO;
        BigDecimal runningCapacity = summary.getSafePaymentCapacity();

        for (PaymentItemDTO item : allPayments) {
            if (runningCapacity.compareTo(item.getOutstandingAmount()) >= 0) {
                item.setAdvisoryStatus("RECOMMENDED");
                recommended.add(item);
                recommendedTotal = recommendedTotal.add(item.getOutstandingAmount());
                runningCapacity = runningCapacity.subtract(item.getOutstandingAmount());
            } else {
                item.setAdvisoryStatus(runningCapacity.compareTo(BigDecimal.ZERO) > 0 ? "HOLD_NEEDS_FUNDS" : "DEFERRED");
                deferred.add(item);
                deferredTotal = deferredTotal.add(item.getOutstandingAmount());
            }
        }

        String executionAdvice;
        if ("AT_RISK".equalsIgnoreCase(summary.getPaymentRiskStatus())) {
            executionAdvice = "CRITICAL: Conserve cash immediately. Prioritize statutory taxes and critical inventory suppliers. Defer non-essential expenses.";
        } else if ("CAUTION".equalsIgnoreCase(summary.getPaymentRiskStatus())) {
            executionAdvice = "CAUTION: Payment capacity is tight. Settle P1 & P2 obligations first. Ensure expected receivable collections arrive before releasing P3/P4 payments.";
        } else {
            executionAdvice = "OPTIMAL: Available cash covers upcoming 30-day obligations comfortably. Recommended payments can be scheduled according to vendor terms.";
        }

        return new PaymentPlanDTO(
                summary.getSafePaymentCapacity(),
                summary.getUpcoming30DayObligations(),
                recommendedTotal,
                deferredTotal,
                recommended,
                deferred,
                executionAdvice
        );
    }

    private List<PaymentItemDTO> getPrioritizedPaymentItems(Long merchantId, BigDecimal capacity) {
        List<Payable> payables = payableRepository.findByMerchantIdOrderByDueDateAsc(merchantId);
        List<PaymentItemDTO> items = new ArrayList<>();

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        for (Payable p : payables) {
            if ("PAID".equalsIgnoreCase(p.getStatus())) {
                continue;
            }

            int daysUntilDue = (int) ChronoUnit.DAYS.between(today, p.getDueDate());
            String priority;
            String priorityReason;

            // Deterministic Priority Tiering
            if (daysUntilDue < 0) {
                priority = isCriticalCategory(p.getCategory()) ? "P1_CRITICAL" : "P2_HIGH";
                priorityReason = "OVERDUE by " + Math.abs(daysUntilDue) + " days. " + (isCriticalCategory(p.getCategory()) ? "Critical statutory/essential vendor." : "Standard vendor overdue.");
            } else if (daysUntilDue == 0) {
                priority = "P2_HIGH";
                priorityReason = "DUE TODAY. Immediate obligation.";
            } else if (daysUntilDue <= 7) {
                priority = isCriticalCategory(p.getCategory()) ? "P2_HIGH" : "P3_MEDIUM";
                priorityReason = "Due within 7 days (" + daysUntilDue + " days remaining).";
            } else {
                priority = "P4_DEFERRABLE";
                priorityReason = "Due in " + daysUntilDue + " days. Flexible schedule.";
            }

            items.add(new PaymentItemDTO(
                    p.getId(),
                    p.getVendor(),
                    p.getBillReference(),
                    p.getBillAmount(),
                    p.getOutstandingAmount(),
                    p.getBillDate().toString(),
                    p.getDueDate().toString(),
                    p.getCategory(),
                    priority,
                    priorityReason,
                    daysUntilDue,
                    "UNSCHEDULED"
            ));
        }

        // Sort Deterministically: Priority Tier -> Days Until Due ascending -> Outstanding Amount descending
        items.sort(Comparator.comparingInt((PaymentItemDTO item) -> getPriorityScore(item.getPriority()))
                .thenComparingInt(PaymentItemDTO::getDaysUntilDue)
                .thenComparing((PaymentItemDTO item) -> item.getOutstandingAmount().negate()));

        return items;
    }

    private boolean isCriticalCategory(String category) {
        if (category == null) return false;
        String cat = category.toUpperCase().trim();
        return cat.contains("TAX") || cat.contains("RENT") || cat.contains("UTILITIES") || cat.contains("INVENTORY") || cat.contains("STATUTORY");
    }

    private int getPriorityScore(String priority) {
        return switch (priority) {
            case "P1_CRITICAL" -> 1;
            case "P2_HIGH" -> 2;
            case "P3_MEDIUM" -> 3;
            case "P4_DEFERRABLE" -> 4;
            default -> 5;
        };
    }
}
