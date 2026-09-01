package com.flowwise.service;

import com.flowwise.dto.*;
import com.flowwise.entity.FinancialAction;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.FinancialActionRepository;
import com.flowwise.repository.MerchantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class FinancialActionService {

    private final MerchantRepository merchantRepository;
    private final FinancialActionRepository actionRepository;
    private final CashFlowService cashFlowService;
    private final BusinessHealthService healthService;
    private final TemporalIntelligenceService temporalService;

    public FinancialActionService(MerchantRepository merchantRepository,
                                  FinancialActionRepository actionRepository,
                                  CashFlowService cashFlowService,
                                  BusinessHealthService healthService,
                                  TemporalIntelligenceService temporalService) {
        this.merchantRepository = merchantRepository;
        this.actionRepository = actionRepository;
        this.cashFlowService = cashFlowService;
        this.healthService = healthService;
        this.temporalService = temporalService;
    }

    public ActionSummaryDTO getMerchantActions(Long merchantId) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }

        // Generate / Sync Deterministic Action Rules
        generateDeterministicActions(merchantId);

        List<FinancialAction> actions = actionRepository.findByMerchantIdOrderByCreatedAtDesc(merchantId);

        // Sort by Severity Weight: HIGH (3) -> MEDIUM (2) -> LOW (1)
        List<FinancialAction> sortedActions = actions.stream()
                .sorted(Comparator.comparingInt((FinancialAction a) -> getSeverityWeight(a.getSeverity())).reversed()
                        .thenComparing(FinancialAction::getCreatedAt, Comparator.reverseOrder()))
                .collect(Collectors.toList());

        int totalActions = sortedActions.size();
        int highCount = (int) sortedActions.stream().filter(a -> "HIGH".equalsIgnoreCase(a.getSeverity())).count();
        int medCount = (int) sortedActions.stream().filter(a -> "MEDIUM".equalsIgnoreCase(a.getSeverity())).count();
        int lowCount = (int) sortedActions.stream().filter(a -> "LOW".equalsIgnoreCase(a.getSeverity())).count();
        int openCount = (int) sortedActions.stream().filter(a -> "OPEN".equalsIgnoreCase(a.getStatus())).count();

        List<FinancialActionDTO> actionDTOs = sortedActions.stream().map(this::mapToDTO).collect(Collectors.toList());

        return new ActionSummaryDTO(totalActions, highCount, medCount, lowCount, openCount, actionDTOs);
    }

    public FinancialActionDTO dismissAction(Long actionId) {
        FinancialAction action = actionRepository.findById(actionId)
                .orElseThrow(() -> new ResourceNotFoundException("Financial action not found with ID: " + actionId));
        action.setStatus("DISMISSED");
        FinancialAction saved = actionRepository.save(action);
        return mapToDTO(saved);
    }

    public FinancialActionDTO resolveAction(Long actionId) {
        FinancialAction action = actionRepository.findById(actionId)
                .orElseThrow(() -> new ResourceNotFoundException("Financial action not found with ID: " + actionId));
        action.setStatus("RESOLVED");
        FinancialAction saved = actionRepository.save(action);
        return mapToDTO(saved);
    }

    private void generateDeterministicActions(Long merchantId) {
        try {
            CashFlowSummaryDTO cashFlow = cashFlowService.getCashFlowSummary(merchantId);
            BusinessHealthDTO health = healthService.calculateBusinessHealth(merchantId);
            TemporalSummaryDTO temporal = temporalService.getTemporalSummary(merchantId);

            // Rule 1: High Priority Payable Pressure
            if (cashFlow.getUpcomingPayablePressure() != null && cashFlow.getUpcomingPayablePressure().compareTo(BigDecimal.ZERO) > 0) {
                createOrUpdateAction(
                        merchantId,
                        "ACT-PAYABLE-PRESSURE",
                        "Upcoming Supplier & Tax Payable Pressure",
                        "HIGH",
                        "PAYABLE_PRESSURE",
                        "Upcoming short-term obligations of ₹" + cashFlow.getUpcomingPayablePressure() + " require liquidity planning to avoid late fee penalties or vendor supply disruption.",
                        "Upcoming payables: ₹" + cashFlow.getUpcomingPayablePressure() + " | Cash Runway: " + cashFlow.getCashRunwayMonths() + " months",
                        "Review cash inflow schedule and reserve ₹" + cashFlow.getUpcomingPayablePressure() + " in primary account before due date."
                );
            }

            // Rule 2: High Priority Cash Runway Deterioration
            if (cashFlow.getCashRunwayMonths() != null && cashFlow.getCashRunwayMonths().compareTo(new BigDecimal("3.0")) < 0) {
                createOrUpdateAction(
                        merchantId,
                        "ACT-RUNWAY-RISK",
                        "Cash Runway Alert (< 3 Months Coverage)",
                        "HIGH",
                        "RUNWAY_RISK",
                        "Current cash runway stands at " + cashFlow.getCashRunwayMonths() + " months based on average monthly burn rate of ₹" + cashFlow.getBurnRate() + ".",
                        "Runway: " + cashFlow.getCashRunwayMonths() + " months | Monthly Burn Rate: ₹" + cashFlow.getBurnRate(),
                        "Negotiate extended payment terms with key suppliers and defer non-essential capital expenditures."
                );
            }

            // Rule 3: Medium Priority Expense Spike Anomaly
            if (!temporal.getAnomalies().isEmpty()) {
                createOrUpdateAction(
                        merchantId,
                        "ACT-EXPENSE-SPIKE",
                        "Operating Expense Spike (> 25% Increase)",
                        "MEDIUM",
                        "EXPENSE_SPIKE",
                        "Operating expense anomalies detected: " + String.join("; ", temporal.getAnomalies()),
                        "Temporal Anomalies: " + String.join("; ", temporal.getAnomalies()),
                        "Audit discretionary operating expenses and cross-check recent invoice entries in Office Kit."
                );
            }

            // Rule 4: Low Priority Opportunity / Healthy Position
            if (health.getOverallScore() >= 70) {
                createOrUpdateAction(
                        merchantId,
                        "ACT-HEALTHY-OPPORTUNITY",
                        "Healthy Cash Position & Working Capital Opportunity",
                        "LOW",
                        "OPPORTUNITY",
                        "Overall business health score is " + health.getOverallScore() + "/100 (" + health.getHealthStatus() + ") with stable liquidity reserves.",
                        "Health Score: " + health.getOverallScore() + "/100 | Liquidity Status: " + cashFlow.getLiquidityStatus(),
                        "Consider deploying excess cash into inventory pre-purchases to secure bulk supplier discounts."
                );
            }

        } catch (Exception ignored) {}
    }

    private void createOrUpdateAction(Long merchantId, String key, String title, String severity,
                                      String category, String explanation, String evidence, String step) {
        Optional<FinancialAction> existingOpt = actionRepository.findByMerchantIdAndActionKey(merchantId, key);
        if (existingOpt.isEmpty()) {
            FinancialAction action = new FinancialAction();
            action.setMerchantId(merchantId);
            action.setActionKey(key);
            action.setTitle(title);
            action.setSeverity(severity);
            action.setCategory(category);
            action.setExplanation(explanation);
            action.setSupportingEvidence(evidence);
            action.setRecommendedStep(step);
            action.setStatus("OPEN");
            actionRepository.save(action);
        }
    }

    private int getSeverityWeight(String severity) {
        if ("HIGH".equalsIgnoreCase(severity)) return 3;
        if ("MEDIUM".equalsIgnoreCase(severity)) return 2;
        return 1;
    }

    private FinancialActionDTO mapToDTO(FinancialAction action) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        return new FinancialActionDTO(
                action.getId(),
                action.getMerchantId(),
                action.getActionKey(),
                action.getTitle(),
                action.getSeverity(),
                action.getCategory(),
                action.getExplanation(),
                action.getSupportingEvidence(),
                action.getRecommendedStep(),
                action.getStatus(),
                action.getCreatedAt() != null ? formatter.format(action.getCreatedAt()) : "",
                action.getUpdatedAt() != null ? formatter.format(action.getUpdatedAt()) : ""
        );
    }
}
