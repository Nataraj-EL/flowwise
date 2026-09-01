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
    private final ReceivablesService receivablesService;
    private final PayablesService payablesService;
    private final WorkingCapitalService workingCapitalService;
    private final ReconciliationService reconciliationService;
    private final CashManagementService cashManagementService;
    private final FinancialGoalService goalService;

    public FinancialActionService(MerchantRepository merchantRepository,
                                  FinancialActionRepository actionRepository,
                                  CashFlowService cashFlowService,
                                  BusinessHealthService healthService,
                                  TemporalIntelligenceService temporalService,
                                  ReceivablesService receivablesService,
                                  PayablesService payablesService,
                                  WorkingCapitalService workingCapitalService,
                                  ReconciliationService reconciliationService,
                                  CashManagementService cashManagementService,
                                  FinancialGoalService goalService) {
        this.merchantRepository = merchantRepository;
        this.actionRepository = actionRepository;
        this.cashFlowService = cashFlowService;
        this.healthService = healthService;
        this.temporalService = temporalService;
        this.receivablesService = receivablesService;
        this.payablesService = payablesService;
        this.workingCapitalService = workingCapitalService;
        this.reconciliationService = reconciliationService;
        this.cashManagementService = cashManagementService;
        this.goalService = goalService;
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

            // Rule 4: High/Medium Priority Receivables Overdue Alert
            ReceivablesSummaryDTO recv = receivablesService.getReceivablesSummary(merchantId);
            if (recv.getTotalOverdue() != null && recv.getTotalOverdue().compareTo(BigDecimal.ZERO) > 0) {
                String severity = recv.getOverdue60PlusDays() != null && recv.getOverdue60PlusDays().compareTo(new BigDecimal("50000.00")) > 0 ? "HIGH" : "MEDIUM";
                createOrUpdateAction(
                        merchantId,
                        "ACT-RECEIVABLES-OVERDUE",
                        "Overdue B2B Invoices (" + recv.getOverdueInvoicesCount() + " Overdue)",
                        severity,
                        "RECEIVABLES_CONCENTRATION",
                        "₹" + recv.getTotalOverdue() + " in B2B customer invoices are overdue across aging buckets. Collecting these funds will boost near-term liquidity by ₹" + recv.getEstimatedNearTermCollection() + ".",
                        "Total Overdue: ₹" + recv.getTotalOverdue() + " | Overdue Ratio: " + recv.getOverdueRatioPct() + "% | 60+ Days: ₹" + recv.getOverdue60PlusDays(),
                        "Issue formal payment reminders to top overdue debtors starting with " + recv.getLargestOutstandingCounterparty() + "."
                );
            }

            // Rule 5: Medium Priority Receivables Concentration Risk
            if (recv.getConcentrationRatioPct() != null && recv.getConcentrationRatioPct().compareTo(new BigDecimal("40.0")) > 0) {
                createOrUpdateAction(
                        merchantId,
                        "ACT-RECEIVABLES-CONCENTRATION",
                        "Receivables Counterparty Concentration (" + recv.getConcentrationRatioPct() + "%)",
                        "MEDIUM",
                        "RECEIVABLES_CONCENTRATION",
                        recv.getConcentrationRatioPct() + "% of total outstanding receivables (₹" + recv.getLargestCounterpartyAmount() + ") are concentrated with " + recv.getLargestOutstandingCounterparty() + ".",
                        "Largest Counterparty: " + recv.getLargestOutstandingCounterparty() + " | Amount: ₹" + recv.getLargestCounterpartyAmount() + " | Concentration Ratio: " + recv.getConcentrationRatioPct() + "%",
                        "Diversify credit terms and establish milestone payment schedules for major B2B distributors."
                );
            }

            // Rule 6: High Priority Overdue Vendor Payables
            PayablesSummaryDTO pay = payablesService.getPayablesSummary(merchantId);
            if (pay.getTotalOverdue() != null && pay.getTotalOverdue().compareTo(BigDecimal.ZERO) > 0) {
                createOrUpdateAction(
                        merchantId,
                        "ACT-PAYABLES-OVERDUE",
                        "Overdue Vendor Bills (" + pay.getOverdueBillsCount() + " Overdue)",
                        "HIGH",
                        "PAYABLE_PRESSURE",
                        "₹" + pay.getTotalOverdue() + " in vendor bills are past due date. Resolving overdue obligations prevents supply chain disruption and late fee penalties.",
                        "Total Overdue Payables: ₹" + pay.getTotalOverdue() + " | Largest Vendor Obligation: " + pay.getLargestVendorObligation(),
                        "Prioritize settlement of overdue vendor bills starting with " + pay.getLargestVendorObligation() + "."
                );
            }

            // Rule 7: High/Medium Priority Near-Term Payable Pressure
            if (pay.getUpcomingPayablePressure() != null && pay.getUpcomingPayablePressure().compareTo(new BigDecimal("50000.00")) > 0) {
                createOrUpdateAction(
                        merchantId,
                        "ACT-PAYABLE-PRESSURE",
                        "High Near-Term Payable Pressure (₹" + pay.getUpcomingPayablePressure() + ")",
                        "HIGH",
                        "PAYABLE_PRESSURE",
                        "Upcoming short-term obligations of ₹" + pay.getUpcomingPayablePressure() + " (Due Today: ₹" + pay.getDueToday() + ", 7-Day: ₹" + pay.getDue7Days() + ") require liquidity allocation.",
                        "Near-Term Pressure: ₹" + pay.getUpcomingPayablePressure() + " | Available Cash: ₹" + cashFlow.getOperatingInflows(),
                        "Reserve liquid funds in primary business account before vendor due dates."
                );
            }

            // Rule 8: High Priority Working Capital Coverage Deficit
            WorkingCapitalSummaryDTO wc = workingCapitalService.getWorkingCapitalSummary(merchantId);
            if (wc.getNearTermCoverageRatio() != null && wc.getNearTermCoverageRatio().compareTo(new BigDecimal("1.00")) < 0) {
                createOrUpdateAction(
                        merchantId,
                        "ACT-WORKING-CAPITAL-DEFICIT",
                        "Working Capital Coverage Deficit (" + wc.getNearTermCoverageRatio() + "x Ratio)",
                        "HIGH",
                        "RUNWAY_RISK",
                        "Available cash and 30-day receivables collection potential (" + wc.getNearTermCoverageRatio() + "x) are insufficient to comfortably cover near-term payment pressure of ₹" + wc.getUpcomingPayablePressure() + ".",
                        "Net Working Capital: ₹" + wc.getNetWorkingCapital() + " | Near-Term Coverage: " + wc.getNearTermCoverageRatio() + "x | Gap: ₹" + wc.getWorkingCapitalGap(),
                        "Accelerate receivables collection from " + recv.getLargestOutstandingCounterparty() + " to close the ₹" + wc.getWorkingCapitalGap() + " working capital gap."
                );
            }

            // Rule 9: High Priority Transaction Reconciliation & Duplicate Review
            ReconciliationSummaryDTO recon = reconciliationService.getReconciliationSummary(merchantId);
            if (recon.getDuplicateIssuesCount() > 0 || recon.getUnreviewedCount() > 5) {
                createOrUpdateAction(
                        merchantId,
                        "ACT-RECONCILIATION-REQUIRED",
                        "Transaction Reconciliation & Duplicate Review (" + recon.getUnreviewedCount() + " Unreviewed)",
                        recon.getDuplicateIssuesCount() > 0 ? "HIGH" : "MEDIUM",
                        "RECONCILIATION",
                        "Active ledger contains " + recon.getUnreviewedCount() + " unreviewed items and " + recon.getDuplicateIssuesCount() + " potential duplicate transactions.",
                        "Reconciliation Health: " + recon.getReconciliationHealthPct() + "% | Duplicates: " + recon.getDuplicateIssuesCount() + " | Unreviewed: " + recon.getUnreviewedCount(),
                        "Review flagged items in Reconciliation Console to preserve financial audit accuracy."
                );
            }

            // Rule 10: Cash Management Safe Payment Capacity & Advisory Notice
            CashManagementSummaryDTO cashMgmt = cashManagementService.getCashManagementSummary(merchantId);
            if ("AT_RISK".equalsIgnoreCase(cashMgmt.getPaymentRiskStatus()) || "CAUTION".equalsIgnoreCase(cashMgmt.getPaymentRiskStatus())) {
                createOrUpdateAction(
                        merchantId,
                        "ACT-PAYMENT-RISK-WARNING",
                        "Cash Obligations & Safe Payment Advisory (" + cashMgmt.getPaymentRiskStatus() + ")",
                        "AT_RISK".equalsIgnoreCase(cashMgmt.getPaymentRiskStatus()) ? "HIGH" : "MEDIUM",
                        "CASH_MANAGEMENT",
                        "7-day obligations of ₹" + cashMgmt.getUpcoming7DayObligations() + " exceed safe payment capacity of ₹" + cashMgmt.getSafePaymentCapacity() + ".",
                        "Available Cash: ₹" + cashMgmt.getCurrentAvailableCash() + " | Projected 7-Day Cash: ₹" + cashMgmt.getProjected7DayCashPosition() + " | Safe Limit: ₹" + cashMgmt.getSafePaymentCapacity(),
                        "Review Payment Plan to prioritize P1 critical obligations and defer non-essential expenses."
                );
            } else {
                createOrUpdateAction(
                        merchantId,
                        "ACT-SAFE-PAYMENT-CAPACITY",
                        "Optimal Safe Payment Capacity (₹" + cashMgmt.getSafePaymentCapacity() + ")",
                        "LOW",
                        "CASH_MANAGEMENT",
                        "Available cash and near-term collections comfortably cover 30-day obligations.",
                        "Safe Payment Capacity: ₹" + cashMgmt.getSafePaymentCapacity() + " | 30-Day Obligations: ₹" + cashMgmt.getUpcoming30DayObligations(),
                        "Proceed with prioritized vendor payment schedule."
                );
            }

            // Rule 11: Financial Goals At-Risk & Deadline Alerts
            List<FinancialGoalDTO> goals = goalService.getMerchantGoals(merchantId);
            for (FinancialGoalDTO g : goals) {
                if ("AT_RISK".equalsIgnoreCase(g.getRiskStatus())) {
                    createOrUpdateAction(
                            merchantId,
                            "ACT-GOAL-AT-RISK-" + g.getId(),
                            "Financial Goal At Risk: " + g.getName(),
                            "HIGH",
                            "GOALS",
                            "Current progress of " + g.getProgressPct() + "% (₹" + g.getCurrentAmount() + ") lags required pace of ₹" + g.getRequiredMonthlyPace() + "/month.",
                            "Target: ₹" + g.getTargetAmount() + " | Progress: " + g.getProgressPct() + "% | Deadline: " + g.getTargetDate(),
                            "Adjust operational spending or receivables collection to achieve goal target."
                    );
                } else if (g.getDaysRemaining() > 0 && g.getDaysRemaining() <= 14 && !"ACHIEVED".equalsIgnoreCase(g.getRiskStatus())) {
                    createOrUpdateAction(
                            merchantId,
                            "ACT-GOAL-DEADLINE-" + g.getId(),
                            "Goal Target Deadline Approaching: " + g.getName(),
                            "MEDIUM",
                            "GOALS",
                            "Target deadline is in " + g.getDaysRemaining() + " days. Remaining target: ₹" + g.getRemainingAmount() + ".",
                            "Target Date: " + g.getTargetDate() + " | Remaining Amount: ₹" + g.getRemainingAmount(),
                            "Execute final push to achieve financial goal target."
                    );
                }
            }

            // Rule 12: Low Priority Opportunity / Healthy Position
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
