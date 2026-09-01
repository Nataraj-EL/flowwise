package com.flowwise.service;

import com.flowwise.dto.*;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.MerchantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class EvidenceBuilderService {

    private final MerchantRepository merchantRepository;
    private final MerchantService merchantService;
    private final CashFlowService cashFlowService;
    private final BusinessHealthService healthService;
    private final TransactionService transactionService;
    private final TemporalIntelligenceService temporalService;
    private final ForecastingService forecastingService;
    private final FinancialActionService actionService;
    private final ReceivablesService receivablesService;
    private final PayablesService payablesService;
    private final WorkingCapitalService workingCapitalService;
    private final CommandCenterService commandCenterService;
    private final ReconciliationService reconciliationService;
    private final CashManagementService cashManagementService;
    private final FinancialGoalService goalService;
    private final FinancialDecisionService decisionService;

    public EvidenceBuilderService(MerchantRepository merchantRepository,
                                  MerchantService merchantService,
                                  CashFlowService cashFlowService,
                                  BusinessHealthService healthService,
                                  TransactionService transactionService,
                                  TemporalIntelligenceService temporalService,
                                  ForecastingService forecastingService,
                                  FinancialActionService actionService,
                                  ReceivablesService receivablesService,
                                  PayablesService payablesService,
                                  WorkingCapitalService workingCapitalService,
                                  CommandCenterService commandCenterService,
                                  ReconciliationService reconciliationService,
                                  CashManagementService cashManagementService,
                                  FinancialGoalService goalService,
                                  FinancialDecisionService decisionService) {
        this.merchantRepository = merchantRepository;
        this.merchantService = merchantService;
        this.cashFlowService = cashFlowService;
        this.healthService = healthService;
        this.transactionService = transactionService;
        this.temporalService = temporalService;
        this.forecastingService = forecastingService;
        this.actionService = actionService;
        this.receivablesService = receivablesService;
        this.payablesService = payablesService;
        this.workingCapitalService = workingCapitalService;
        this.commandCenterService = commandCenterService;
        this.reconciliationService = reconciliationService;
        this.cashManagementService = cashManagementService;
        this.goalService = goalService;
        this.decisionService = decisionService;
    }

    public FinancialEvidenceSummaryDTO buildEvidenceSummary(Long merchantId, String question) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }

        if (question == null || question.trim().isEmpty()) {
            question = "Can I afford ₹80,000 of inventory this week?";
        }

        String qLower = question.toLowerCase(Locale.ROOT);
        
        // 1. Detect Intent Category
        if (qLower.contains("decision") || qLower.contains("decisions") || qLower.contains("recommendations did i act on") || qLower.contains("previous financial decisions")) {
            return buildDecisionHistoryEvidence(merchantId, question);
        } else if (qLower.contains("goal") || qLower.contains("target") || qLower.contains("doing against") || qLower.contains("hit my goal")) {
            return buildFinancialGoalEvidence(merchantId, question);
        } else if (qLower.contains("pay my bills") || qLower.contains("can i pay") || qLower.contains("pay first") || qLower.contains("safely spend") || qLower.contains("payment capacity") || qLower.contains("payment plan") || qLower.contains("spend")) {
            return buildCashManagementEvidence(merchantId, question);
        } else if (qLower.contains("duplicate") || qLower.contains("reconcil") || qLower.contains("unreviewed") || qLower.contains("suspicious")) {
            return buildReconciliationEvidence(merchantId, question);
        } else if (qLower.contains("today") || qLower.contains("briefing") || qLower.contains("attention") || qLower.contains("command center") || qLower.contains("executive")) {
            return buildCommandCenterEvidence(merchantId, question);
        } else if (qLower.contains("working capital") || qLower.contains("obligation") || qLower.contains("stuck") || qLower.contains("coverage") || qLower.contains("gap") || qLower.contains("biggest cash pressure")) {
            return buildWorkingCapitalEvidence(merchantId, question);
        } else if (qLower.contains("payable") || qLower.contains("bill") || qLower.contains("vendor") || qLower.contains("owe") || qLower.contains("rent") || qLower.contains("utility")) {
            return buildPayablesEvidence(merchantId, question);
        } else if (qLower.contains("owed") || qLower.contains("receivable") || qLower.contains("unpaid") || qLower.contains("debtor") || qLower.contains("concentration")) {
            return buildReceivablesEvidence(merchantId, question);
        } else if (qLower.contains("afford") || qLower.contains("inventory") || qLower.contains("80,000") || qLower.contains("80000") || qLower.contains("buy") || qLower.contains("purchase")) {
            return buildAffordabilityEvidence(merchantId, question);
        } else if (qLower.contains("focus") || qLower.contains("risk") || qLower.contains("do") || qLower.contains("action") || qLower.contains("recommend") || qLower.contains("priority") || qLower.contains("this week")) {
            return buildActionCenterEvidence(merchantId, question);
        } else if (qLower.contains("health") || qLower.contains("score") || qLower.contains("rating")) {
            return buildHealthEvidence(merchantId, question);
        } else if (qLower.contains("changed") || qLower.contains("compare") || qLower.contains("drop") || qLower.contains("shift") || qLower.contains("month")) {
            return buildTemporalEvidence(merchantId, question);
        } else if (qLower.contains("forecast") || qLower.contains("projection") || qLower.contains("30") || qLower.contains("60") || qLower.contains("90")) {
            return buildForecastEvidence(merchantId, question);
        } else {
            return buildCashFlowEvidence(merchantId, question);
        }
    }

    private FinancialEvidenceSummaryDTO buildAffordabilityEvidence(Long merchantId, String question) {
        MerchantDetailDTO merchantDetail = merchantService.getMerchantDetail(merchantId);
        CashFlowSummaryDTO cashFlow = cashFlowService.getCashFlowSummary(merchantId);
        ScenarioResultDTO scenario = forecastingService.simulateScenario(merchantId, new ScenarioRequestDTO(new BigDecimal("80000"), "INVENTORY"));

        List<EvidenceItemDTO> items = new ArrayList<>();
        items.add(new EvidenceItemDTO("Available Cash Reserves", merchantDetail.getTotalAvailableCash(), "INR", "Bank Accounts Ledger", "Current Ledger", "ACTUAL", "Aggregated across connected business accounts", "HIGH"));
        items.add(new EvidenceItemDTO("Upcoming Payable Pressure", cashFlow.getUpcomingPayablePressure(), "INR", "Vendor Invoices Engine", "7-Day Window", "ACTUAL", "Pending mandatory merchant payables due within 7 days", "HIGH"));
        items.add(new EvidenceItemDTO("Baseline Cash Runway", cashFlow.getCashRunwayMonths(), "Months", "Cash Flow Engine", "3-Month Average", "ACTUAL", "Available cash divided by average monthly burn rate", "HIGH"));
        items.add(new EvidenceItemDTO("Simulated Outlay Amount", scenario.getRequestedAmount(), "INR", "Scenario Sandbox", "Current Week", "ESTIMATE", "Hypothetical one-time inventory outlay", "HIGH"));
        items.add(new EvidenceItemDTO("Post-Purchase Ending Cash", scenario.getScenarioEndingCash(), "INR", "Forecasting Engine", "Immediate Post-Purchase", "ESTIMATE", "Available cash minus simulated outlay", "HIGH"));
        items.add(new EvidenceItemDTO("Post-Purchase Runway", scenario.getScenarioRunwayMonths(), "Months", "Forecasting Engine", "Projected Horizon", "ESTIMATE", "Ending cash divided by monthly burn rate", "HIGH"));

        List<String> assumptions = Arrays.asList(
                "Assumes immediate outlay deduction from current liquid cash reserves.",
                "Assumes no immediate revenue settlement returns within current 7-day window.",
                "Considers pending merchant payables reserved before outlay execution."
        );

        String conclusion = "Yes, " + merchantDetail.getMerchant().getBusinessName() + " holds ₹" + merchantDetail.getTotalAvailableCash() 
                + " in liquid reserves. Deducting ₹80,000 for the requested inventory purchase leaves ₹" + scenario.getScenarioEndingCash() 
                + " in post-purchase cash, supporting a " + scenario.getScenarioRunwayMonths() + "-month cash runway (Status: " + scenario.getRiskStatus() + ").";

        return new FinancialEvidenceSummaryDTO(
                question,
                "AFFORDABILITY",
                items,
                assumptions,
                scenario.getRiskStatus(),
                conclusion
        );
    }

    private FinancialEvidenceSummaryDTO buildHealthEvidence(Long merchantId, String question) {
        BusinessHealthDTO health = healthService.calculateBusinessHealth(merchantId);
        CashFlowSummaryDTO cashFlow = cashFlowService.getCashFlowSummary(merchantId);

        List<EvidenceItemDTO> items = new ArrayList<>();
        items.add(new EvidenceItemDTO("Overall Health Score", health.getOverallScore(), "Score (0-100)", "Business Health Engine", "Current Ledger", "ACTUAL", "Weighted average across 5 deterministic financial pillars", "HIGH"));
        items.add(new EvidenceItemDTO("Liquidity Status", cashFlow.getLiquidityStatus(), "Status", "Cash Flow Engine", "Current", "ACTUAL", "Evaluated against monthly burn requirements", "HIGH"));
        items.add(new EvidenceItemDTO("Monthly Burn Rate", cashFlow.getBurnRate(), "INR", "Cash Flow Engine", "Monthly Average", "ACTUAL", "Average monthly operational outflows", "HIGH"));
        items.add(new EvidenceItemDTO("Recurring Expenses", cashFlow.getRecurringExpensesEstimate(), "INR", "Transaction Classification Engine", "Monthly", "ACTUAL", "Fixed obligations (Rent, Payroll, Utilities)", "HIGH"));

        List<String> assumptions = Arrays.asList(
                "Health scoring uses deterministic non-AI algorithmic weights across liquidity and expense control.",
                "Score thresholds: HEALTHY (>=75), WATCH (50-74), AT_RISK (<50)."
        );

        return new FinancialEvidenceSummaryDTO(
                question,
                "HEALTH",
                items,
                assumptions,
                health.getHealthStatus(),
                health.getSummaryExplanation()
        );
    }

    private FinancialEvidenceSummaryDTO buildTemporalEvidence(Long merchantId, String question) {
        TemporalSummaryDTO temporal = temporalService.getTemporalSummary(merchantId);

        List<EvidenceItemDTO> items = new ArrayList<>();
        items.add(new EvidenceItemDTO("Current Month Inflows", temporal.getCurrentInflow(), "INR", "Transaction Engine", temporal.getCurrentMonth(), "ACTUAL", "Sum of settled credit transactions", "HIGH"));
        items.add(new EvidenceItemDTO("Inflow MoM Change", temporal.getInflowChangePct(), "%", "Period Comparison Engine", temporal.getCurrentMonth() + " vs " + temporal.getPreviousMonth(), "ACTUAL", "Percentage movement compared to previous month", "HIGH"));
        items.add(new EvidenceItemDTO("Outflow MoM Change", temporal.getOutflowChangePct(), "%", "Period Comparison Engine", temporal.getCurrentMonth() + " vs " + temporal.getPreviousMonth(), "ACTUAL", "Percentage movement compared to previous month", "HIGH"));
        items.add(new EvidenceItemDTO("Net Cash MoM Change", temporal.getNetCashChangePct(), "%", "Period Comparison Engine", temporal.getCurrentMonth() + " vs " + temporal.getPreviousMonth(), "ACTUAL", "Percentage movement in net cash position", "HIGH"));

        String conclusion = "Outflows shifted by " + temporal.getOutflowChangePct() + "% (" + temporal.getOutflowDirection() + ") while inflows shifted by " + temporal.getInflowChangePct() + "%.";

        return new FinancialEvidenceSummaryDTO(
                question,
                "TEMPORAL",
                items,
                temporal.getAnomalies(),
                "HEALTHY",
                conclusion
        );
    }

    private FinancialEvidenceSummaryDTO buildForecastEvidence(Long merchantId, String question) {
        ForecastSummaryDTO forecast = forecastingService.getForecastSummary(merchantId);
        PeriodProjectionDTO p30 = forecast.getProjections().get(0);
        PeriodProjectionDTO p90 = forecast.getProjections().get(2);

        List<EvidenceItemDTO> items = new ArrayList<>();
        items.add(new EvidenceItemDTO("30-Day Ending Cash", p30.getProjectedEndingCash(), "INR", "Forecasting Engine", "30-Day Horizon", "ESTIMATE", "Linear extrapolation of monthly burn rate", "HIGH"));
        items.add(new EvidenceItemDTO("30-Day Projected Runway", p30.getProjectedRunwayMonths(), "Months", "Forecasting Engine", "30-Day Horizon", "ESTIMATE", "Projected ending cash divided by monthly burn rate", "HIGH"));
        items.add(new EvidenceItemDTO("90-Day Ending Cash", p90.getProjectedEndingCash(), "INR", "Forecasting Engine", "90-Day Horizon", "ESTIMATE", "Linear extrapolation over 90 days", "HIGH"));

        return new FinancialEvidenceSummaryDTO(
                question,
                "FORECAST",
                items,
                forecast.getAssumptions(),
                "FEASIBLE",
                "Projected 30-day ending cash stands at ₹" + p30.getProjectedEndingCash() + " supporting " + p30.getProjectedRunwayMonths() + " months of runway."
        );
    }

    private FinancialEvidenceSummaryDTO buildCashFlowEvidence(Long merchantId, String question) {
        CashFlowSummaryDTO cashFlow = cashFlowService.getCashFlowSummary(merchantId);

        List<EvidenceItemDTO> items = new ArrayList<>();
        items.add(new EvidenceItemDTO("Total Inflows", cashFlow.getTotalInflows(), "INR", "Cash Flow Engine", "Current Ledger", "ACTUAL", "Sum of all settled credit transactions", "HIGH"));
        items.add(new EvidenceItemDTO("Total Outflows", cashFlow.getTotalOutflows(), "INR", "Cash Flow Engine", "Current Ledger", "ACTUAL", "Sum of all settled debit transactions", "HIGH"));
        items.add(new EvidenceItemDTO("Net Cash Surplus", cashFlow.getNetCashFlow(), "INR", "Cash Flow Engine", "Current Ledger", "ACTUAL", "Inflows minus outflows", "HIGH"));
        items.add(new EvidenceItemDTO("Cash Runway", cashFlow.getCashRunwayMonths(), "Months", "Cash Flow Engine", "Monthly Average", "ACTUAL", "Available cash divided by burn rate", "HIGH"));

        return new FinancialEvidenceSummaryDTO(
                question,
                "CASH_FLOW",
                items,
                Arrays.asList("Based on active transaction ledger records.", "Calculated using Java BigDecimal arithmetic."),
                "HEALTHY",
                "Net cash surplus stands at ₹" + cashFlow.getNetCashFlow() + " supporting a " + cashFlow.getCashRunwayMonths() + "-month cash runway."
        );
    }

    private FinancialEvidenceSummaryDTO buildActionCenterEvidence(Long merchantId, String question) {
        ActionSummaryDTO actionSummary = actionService.getMerchantActions(merchantId);
        CashFlowSummaryDTO cashFlow = cashFlowService.getCashFlowSummary(merchantId);

        List<EvidenceItemDTO> items = new ArrayList<>();
        items.add(new EvidenceItemDTO("Total Recommended Actions", actionSummary.getTotalActions(), "Actions", "Financial Action Engine", "Current Ledger", "ACTUAL", "Prioritized advisory actions generated from financial signals", "HIGH"));
        items.add(new EvidenceItemDTO("High Priority Risks", actionSummary.getHighPriorityCount(), "Alerts", "Financial Action Engine", "Current Ledger", "ACTUAL", "Critical short-term payables and runway risks", "HIGH"));
        items.add(new EvidenceItemDTO("Medium Priority Warnings", actionSummary.getMediumPriorityCount(), "Warnings", "Financial Action Engine", "Current Ledger", "ACTUAL", "Expense spikes and receivables pressure", "HIGH"));
        items.add(new EvidenceItemDTO("Cash Runway", cashFlow.getCashRunwayMonths(), "Months", "Cash Flow Engine", "Monthly Average", "ACTUAL", "Available cash divided by burn rate", "HIGH"));

        List<String> assumptions = new ArrayList<>();
        for (FinancialActionDTO action : actionSummary.getActions()) {
            if ("OPEN".equalsIgnoreCase(action.getStatus())) {
                assumptions.add("[" + action.getSeverity() + "] " + action.getTitle() + ": " + action.getRecommendedStep());
            }
        }

        String topRecommendation = actionSummary.getActions().stream()
                .filter(a -> "OPEN".equalsIgnoreCase(a.getStatus()))
                .findFirst()
                .map(a -> "[" + a.getSeverity() + "] " + a.getTitle() + " — " + a.getRecommendedStep())
                .orElse("All financial signals are healthy. Maintain current expense controls and monitor incoming settlements.");

        return new FinancialEvidenceSummaryDTO(
                question,
                "ACTION_CENTER",
                items,
                assumptions,
                actionSummary.getHighPriorityCount() > 0 ? "ACTION_REQUIRED" : "HEALTHY",
                "Primary Focus Recommendation: " + topRecommendation
        );
    }

    private FinancialEvidenceSummaryDTO buildReceivablesEvidence(Long merchantId, String question) {
        ReceivablesSummaryDTO recv = receivablesService.getReceivablesSummary(merchantId);

        List<EvidenceItemDTO> items = new ArrayList<>();
        items.add(new EvidenceItemDTO("Total Outstanding Receivables", recv.getTotalOutstanding(), "INR", "Receivables Engine", "Active Invoices Ledger", "ACTUAL", "Sum of unpaid balances owed by B2B counterparties", "HIGH"));
        items.add(new EvidenceItemDTO("Total Overdue Invoices", recv.getTotalOverdue(), "INR", "Aging Analysis Engine", "Overdue Buckets", "ACTUAL", "Sum of overdue balances (1-30, 31-60, 60+ days)", "HIGH"));
        items.add(new EvidenceItemDTO("Overdue Ratio", recv.getOverdueRatioPct(), "%", "Aging Analysis Engine", "Current Ledger", "ACTUAL", "Percentage of receivables past due date", "HIGH"));
        items.add(new EvidenceItemDTO("Counterparty Concentration Ratio", recv.getConcentrationRatioPct(), "%", "Concentration Engine", "Debtor Analysis", "ACTUAL", "Percentage of outstanding held by largest counterparty (" + recv.getLargestOutstandingCounterparty() + ")", "HIGH"));
        items.add(new EvidenceItemDTO("Near-Term Collection Potential", recv.getEstimatedNearTermCollection(), "INR", "Cash Flow Impact Engine", "30-Day Window", "ESTIMATE", "Current receivables + 1-30 day overdue collection potential", "HIGH"));

        List<String> assumptions = Arrays.asList(
                "Calculated using exact due dates and settled payment deductions.",
                "Does not assume speculative credit scores or unverified probability models."
        );

        String conclusion = "Total outstanding receivables stand at ₹" + recv.getTotalOutstanding() + ", of which ₹" + recv.getTotalOverdue() + " (" + recv.getOverdueRatioPct() + "%) is overdue across " + recv.getOverdueInvoicesCount() + " invoices. " + recv.getConcentrationRatioPct() + "% is concentrated with " + recv.getLargestOutstandingCounterparty() + ". Collecting near-term balances can yield ₹" + recv.getEstimatedNearTermCollection() + " in liquid cash.";

        return new FinancialEvidenceSummaryDTO(
                question,
                "RECEIVABLES",
                items,
                assumptions,
                recv.getOverdueRatioPct().compareTo(new BigDecimal("30.0")) > 0 ? "ATTENTION_REQUIRED" : "HEALTHY",
                conclusion
        );
    }

    private FinancialEvidenceSummaryDTO buildPayablesEvidence(Long merchantId, String question) {
        PayablesSummaryDTO pay = payablesService.getPayablesSummary(merchantId);

        List<EvidenceItemDTO> items = new ArrayList<>();
        items.add(new EvidenceItemDTO("Total Outstanding Payables", pay.getTotalOutstanding(), "INR", "Payables Engine", "Vendor Ledger", "ACTUAL", "Sum of unpaid vendor bills and statutory obligations", "HIGH"));
        items.add(new EvidenceItemDTO("Due Today", pay.getDueToday(), "INR", "Payables Engine", "Due Date Ledger", "ACTUAL", "Obligations past or due on current date", "HIGH"));
        items.add(new EvidenceItemDTO("Due Within 7 Days", pay.getDue7Days(), "INR", "Payables Engine", "Due Date Ledger", "ACTUAL", "Upcoming obligations due in next 7 days", "HIGH"));
        items.add(new EvidenceItemDTO("Total Overdue Vendor Payables", pay.getTotalOverdue(), "INR", "Payables Engine", "Overdue Ledger", "ACTUAL", "Vendor obligations past due date", "HIGH"));
        items.add(new EvidenceItemDTO("Upcoming Payment Pressure", pay.getUpcomingPayablePressure(), "INR", "Payables Engine", "Pressure Window", "ACTUAL", "Due today + Due 7-Day + Overdue obligations requiring cash", "HIGH"));
        items.add(new EvidenceItemDTO("Payment Coverage Ratio", pay.getPaymentCoverageRatioPct(), "%", "Payables Engine", "Settlement Ledger", "ACTUAL", "Percentage of total vendor bills paid to date", "HIGH"));

        List<String> assumptions = Arrays.asList(
                "Calculated using exact vendor bill due dates and settled disbursements.",
                "Does not assume speculative credit terms or unverified payment extensions."
        );

        String conclusion = "Total outstanding payables stand at ₹" + pay.getTotalOutstanding() + ", with near-term payment pressure of ₹" + pay.getUpcomingPayablePressure() + " (Due Today: ₹" + pay.getDueToday() + ", Due 7-Day: ₹" + pay.getDue7Days() + ", Overdue: ₹" + pay.getTotalOverdue() + "). Largest vendor obligation is " + pay.getLargestVendorObligation() + " (₹" + pay.getLargestVendorAmount() + ").";

        return new FinancialEvidenceSummaryDTO(
                question,
                "PAYABLES",
                items,
                assumptions,
                pay.getTotalOverdue().compareTo(BigDecimal.ZERO) > 0 ? "ACTION_REQUIRED" : "HEALTHY",
                conclusion
        );
    }

    private FinancialEvidenceSummaryDTO buildWorkingCapitalEvidence(Long merchantId, String question) {
        WorkingCapitalSummaryDTO wc = workingCapitalService.getWorkingCapitalSummary(merchantId);

        List<EvidenceItemDTO> items = new ArrayList<>();
        items.add(new EvidenceItemDTO("Net Working Capital", wc.getNetWorkingCapital(), "INR", "Working Capital Engine", "Derived Balance Sheet", "ACTUAL", "Available cash + Receivables outstanding - Payables outstanding", "HIGH"));
        items.add(new EvidenceItemDTO("Available Cash Reserves", wc.getAvailableCash(), "INR", "Cash Flow Engine", "Business Accounts", "ACTUAL", "Liquid balance across connected business accounts", "HIGH"));
        items.add(new EvidenceItemDTO("Receivables Outstanding", wc.getReceivablesOutstanding(), "INR", "Receivables Engine", "Active Ledger", "ACTUAL", "Uncollected B2B customer invoices", "HIGH"));
        items.add(new EvidenceItemDTO("Payables Outstanding", wc.getPayablesOutstanding(), "INR", "Payables Engine", "Vendor Ledger", "ACTUAL", "Unpaid vendor bills and obligations", "HIGH"));
        items.add(new EvidenceItemDTO("Working Capital Gap", wc.getWorkingCapitalGap(), "INR", "Working Capital Engine", "Gap Analysis", "ACTUAL", "Payables outstanding minus Receivables outstanding", "HIGH"));
        items.add(new EvidenceItemDTO("Current Coverage Ratio", wc.getCurrentCoverageRatio(), "Ratio (x)", "Working Capital Engine", "Liquidity Analysis", "ACTUAL", "Ratio of liquid assets to total payables outstanding", "HIGH"));
        items.add(new EvidenceItemDTO("Near-Term Coverage Ratio", wc.getNearTermCoverageRatio(), "Ratio (x)", "Working Capital Engine", "Pressure Window", "ACTUAL", "Ratio of near-term cash + 30-day collection potential to short-term pressure", "HIGH"));

        List<String> assumptions = wc.getTopPressureDrivers();

        return new FinancialEvidenceSummaryDTO(
                question,
                "WORKING_CAPITAL",
                items,
                assumptions,
                "HIGH_RISK".equalsIgnoreCase(wc.getCashConversionRiskStatus()) ? "ACTION_REQUIRED" : "HEALTHY",
                wc.getSummaryExplanation()
        );
    }

    private FinancialEvidenceSummaryDTO buildCommandCenterEvidence(Long merchantId, String question) {
        CommandCenterSnapshotDTO snapshot = commandCenterService.getCommandCenterSnapshot(merchantId);

        List<EvidenceItemDTO> items = new ArrayList<>();
        items.add(new EvidenceItemDTO("Overall Financial Status", snapshot.getOverallFinancialStatus(), "Status", "Business Health Engine", "Executive Synthesis", "ACTUAL", "Synthesized status across all 8 engines", "HIGH"));
        items.add(new EvidenceItemDTO("Available Cash Reserves", snapshot.getAvailableCash(), "INR", "Cash Flow Engine", "Business Accounts", "ACTUAL", "Liquid balance across connected accounts", "HIGH"));
        items.add(new EvidenceItemDTO("Net Cash Flow", snapshot.getNetCashFlow(), "INR", "Cash Flow Engine", "Statement Analysis", "ACTUAL", "Net cash flow for current period", "HIGH"));
        items.add(new EvidenceItemDTO("Working Capital Coverage", snapshot.getWorkingCapitalCoverage(), "Ratio (x)", "Working Capital Engine", "Liquidity Ratio", "ACTUAL", "Ratio of liquid assets to payables", "HIGH"));
        items.add(new EvidenceItemDTO("Overdue Receivables Pressure", snapshot.getReceivablesPressure(), "INR", "Receivables Engine", "Ledger Aging", "ACTUAL", "Overdue customer invoices", "HIGH"));
        items.add(new EvidenceItemDTO("Near-Term Payables Pressure", snapshot.getPayablesPressure(), "INR", "Payables Engine", "Due-Date Ledger", "ACTUAL", "Upcoming 7-day payment pressure", "HIGH"));
        items.add(new EvidenceItemDTO("Forecast Risk Outlook", snapshot.getForecastRisk(), "Status", "Forecasting Engine", "30-90 Day Projection", "ESTIMATE", "Projected cash flow and runway risk", "HIGH"));

        List<String> assumptions = new ArrayList<>();
        assumptions.add("Key Positive Signal: " + snapshot.getKeyPositiveSignal());
        assumptions.add("Key Risk Signal: " + snapshot.getKeyRiskSignal());
        assumptions.add("What Changed: " + snapshot.getWhatChangedSummary());
        if (!snapshot.getTop3Priorities().isEmpty()) {
            assumptions.add("Top Priority Action: " + snapshot.getTop3Priorities().get(0).getTitle());
        }

        String conclusion = "Financial Command Center Briefing: Status is " + snapshot.getOverallFinancialStatus() 
                + " (Score: " + snapshot.getOverallHealthScore() + "/100). Available cash: ₹" + snapshot.getAvailableCash() 
                + ", Net Cash Flow: ₹" + snapshot.getNetCashFlow() + ", Working Capital Coverage: " + snapshot.getWorkingCapitalCoverage() 
                + "x. Top priority: " + (!snapshot.getTop3Priorities().isEmpty() ? snapshot.getTop3Priorities().get(0).getTitle() : "Maintain operational balance") + ".";

        return new FinancialEvidenceSummaryDTO(
                question,
                "COMMAND_CENTER",
                items,
                assumptions,
                "AT_RISK".equalsIgnoreCase(snapshot.getOverallFinancialStatus()) ? "ACTION_REQUIRED" : "HEALTHY",
                conclusion
        );
    }

    private FinancialEvidenceSummaryDTO buildReconciliationEvidence(Long merchantId, String question) {
        ReconciliationSummaryDTO recon = reconciliationService.getReconciliationSummary(merchantId);

        List<EvidenceItemDTO> items = new ArrayList<>();
        items.add(new EvidenceItemDTO("Reconciliation Health Score", recon.getReconciliationHealthPct(), "%", "Reconciliation Engine", "Audit Ledger", "ACTUAL", "Percentage of reconciled/ignored transactions", "HIGH"));
        items.add(new EvidenceItemDTO("Total Processed Transactions", recon.getTotalTransactions(), "Count", "Transaction Engine", "Bank Feeds", "ACTUAL", "Total ingested transaction count", "HIGH"));
        items.add(new EvidenceItemDTO("Unreviewed Transactions", recon.getUnreviewedCount(), "Count", "Reconciliation Engine", "Review Queue", "ACTUAL", "Transactions awaiting merchant review", "HIGH"));
        items.add(new EvidenceItemDTO("Potential Duplicate Issues", recon.getDuplicateIssuesCount(), "Count", "Reconciliation Engine", "Duplicate Detection", "ACTUAL", "Transactions with matching amount & vendor within 3 days", "HIGH"));
        items.add(new EvidenceItemDTO("Uncategorized Transactions", recon.getUncategorizedIssuesCount(), "Count", "Reconciliation Engine", "Category Audit", "ACTUAL", "Transactions missing category mapping", "HIGH"));
        items.add(new EvidenceItemDTO("Suspicious High-Value Outflows", recon.getSuspiciousIssuesCount(), "Count", "Reconciliation Engine", "Anomaly Threshold", "ACTUAL", "Outflow debits > ₹1,00,000", "HIGH"));
        items.add(new EvidenceItemDTO("Pending Office Kit Captures", recon.getOfficeKitPendingCount(), "Count", "Office Kit Engine", "Capture Queue", "ACTUAL", "Document captures pending review", "HIGH"));

        List<String> assumptions = new ArrayList<>();
        assumptions.add("Reconciliation Health: " + recon.getReconciliationHealthPct() + "%");
        assumptions.add("Duplicate Items Detected: " + recon.getDuplicateIssuesCount());
        assumptions.add("Unreviewed Items Pending: " + recon.getUnreviewedCount());

        String conclusion = "Reconciliation Status: Health Score is " + recon.getReconciliationHealthPct() 
                + "%. Unreviewed transactions: " + recon.getUnreviewedCount() + ", Duplicate issues: " 
                + recon.getDuplicateIssuesCount() + ", Suspicious items: " + recon.getSuspiciousIssuesCount() + ".";

        return new FinancialEvidenceSummaryDTO(
                question,
                "RECONCILIATION",
                items,
                assumptions,
                recon.getDuplicateIssuesCount() > 0 || recon.getUnreviewedCount() > 5 ? "ACTION_REQUIRED" : "HEALTHY",
                conclusion
        );
    }

    private FinancialEvidenceSummaryDTO buildCashManagementEvidence(Long merchantId, String question) {
        CashManagementSummaryDTO cashMgmt = cashManagementService.getCashManagementSummary(merchantId);

        List<EvidenceItemDTO> items = new ArrayList<>();
        items.add(new EvidenceItemDTO("Current Available Cash", cashMgmt.getCurrentAvailableCash(), "INR", "Bank Accounts", "Current", "ACTUAL", "Available liquid bank cash across connected accounts", "HIGH"));
        items.add(new EvidenceItemDTO("Upcoming 7-Day Obligations", cashMgmt.getUpcoming7DayObligations(), "INR", "Payables Engine", "Next 7 Days", "ACTUAL", "Bills due today or within 7 days", "HIGH"));
        items.add(new EvidenceItemDTO("Upcoming 30-Day Obligations", cashMgmt.getUpcoming30DayObligations(), "INR", "Payables Engine", "Next 30 Days", "ACTUAL", "Total unpaid bills due within 30 days", "HIGH"));
        items.add(new EvidenceItemDTO("Expected 7-Day Collections", cashMgmt.getExpected7DayCollections(), "INR", "Receivables Engine", "Next 7 Days", "ESTIMATE", "Estimated collections from current receivables", "MODERATE"));
        items.add(new EvidenceItemDTO("Projected 7-Day Cash Position", cashMgmt.getProjected7DayCashPosition(), "INR", "Cash Management Engine", "7-Day Buffer", "ESTIMATE", "Available Cash + Collections - 7D Obligations", "HIGH"));
        items.add(new EvidenceItemDTO("Safe Payment Capacity (Advisory)", cashMgmt.getSafePaymentCapacity(), "INR", "Cash Management Engine", "Operational Limit", "ESTIMATE", "Max recommended payment amount after safety reserves", "HIGH"));
        items.add(new EvidenceItemDTO("Payment Risk Status", cashMgmt.getPaymentRiskStatus(), "Status", "Cash Management Engine", "Current", "ACTUAL", "Risk tier: SAFE, CAUTION, or AT_RISK", "HIGH"));

        List<String> assumptions = new ArrayList<>(cashMgmt.getAssumptions());

        String topPaymentTitle = !cashMgmt.getTopRecommendedPayments().isEmpty() 
                ? cashMgmt.getTopRecommendedPayments().get(0).getVendor() + " (₹" + cashMgmt.getTopRecommendedPayments().get(0).getOutstandingAmount() + ")"
                : "No pending payables";

        String conclusion = "Cash Management Advisory: Payment Risk Status is " + cashMgmt.getPaymentRiskStatus() 
                + ". Safe payment capacity is estimated at ₹" + cashMgmt.getSafePaymentCapacity() 
                + " against 7-day obligations of ₹" + cashMgmt.getUpcoming7DayObligations() 
                + ". Highest priority payment: " + topPaymentTitle + ".";

        return new FinancialEvidenceSummaryDTO(
                question,
                "CASH_MANAGEMENT",
                items,
                assumptions,
                "AT_RISK".equalsIgnoreCase(cashMgmt.getPaymentRiskStatus()) ? "ACTION_REQUIRED" : "HEALTHY",
                conclusion
        );
    }

    private FinancialEvidenceSummaryDTO buildFinancialGoalEvidence(Long merchantId, String question) {
        List<FinancialGoalDTO> goals = goalService.getMerchantGoals(merchantId);

        long activeCount = goals.stream().filter(g -> "ON_TRACK".equalsIgnoreCase(g.getRiskStatus()) || "ACTIVE".equalsIgnoreCase(g.getRiskStatus())).count();
        long atRiskCount = goals.stream().filter(g -> "AT_RISK".equalsIgnoreCase(g.getRiskStatus())).count();
        long achievedCount = goals.stream().filter(g -> "ACHIEVED".equalsIgnoreCase(g.getRiskStatus())).count();

        List<EvidenceItemDTO> items = new ArrayList<>();
        items.add(new EvidenceItemDTO("Total Configured Goals", goals.size(), "Count", "Goal Tracking Engine", "Active Portfolio", "ACTUAL", "Total merchant defined goals", "HIGH"));
        items.add(new EvidenceItemDTO("On-Track / Active Goals", activeCount, "Count", "Goal Tracking Engine", "Active Portfolio", "ACTUAL", "Goals meeting progress pace", "HIGH"));
        items.add(new EvidenceItemDTO("At-Risk Goals", atRiskCount, "Count", "Goal Tracking Engine", "Alert Queue", "ACTUAL", "Goals lagging required pace", "HIGH"));
        items.add(new EvidenceItemDTO("Achieved Goals", achievedCount, "Count", "Goal Tracking Engine", "Completed Portfolio", "ACTUAL", "Successfully completed goals", "HIGH"));

        List<String> assumptions = new ArrayList<>();
        for (FinancialGoalDTO g : goals) {
            items.add(new EvidenceItemDTO("Goal: " + g.getName(), g.getProgressPct(), "%", g.getCalculationSource(), g.getTargetDate(), "ACTUAL", "Progress: " + g.getProgressPct() + "% | Pace: ₹" + g.getRequiredMonthlyPace() + "/mo", "HIGH"));
            assumptions.add("Goal '" + g.getName() + "': Current ₹" + g.getCurrentAmount() + " vs Target ₹" + g.getTargetAmount() + " by " + g.getTargetDate() + " (" + g.getRiskStatus() + ")");
        }

        String conclusion = "Financial Goal Summary: Portfolio contains " + goals.size() + " goals (" + activeCount 
                + " active/on-track, " + atRiskCount + " at-risk, " + achievedCount + " achieved). Target evaluations are calculated dynamically from underlying financial engines.";

        return new FinancialEvidenceSummaryDTO(
                question,
                "FINANCIAL_GOALS",
                items,
                assumptions,
                atRiskCount > 0 ? "ACTION_REQUIRED" : "HEALTHY",
                conclusion
        );
    }

    private FinancialEvidenceSummaryDTO buildDecisionHistoryEvidence(Long merchantId, String question) {
        DecisionSummaryDTO summary = decisionService.getDecisionSummary(merchantId);
        List<FinancialDecisionDTO> decisions = decisionService.getMerchantDecisions(merchantId);

        List<EvidenceItemDTO> items = new ArrayList<>();
        items.add(new EvidenceItemDTO("Total Recorded Decisions", summary.getTotalDecisions(), "Count", "Decision History Engine", "Audit Memory", "ACTUAL", "Total merchant recorded decisions", "HIGH"));
        items.add(new EvidenceItemDTO("Accepted Decisions", summary.getAcceptedCount(), "Count", "Decision History Engine", "Approved Log", "ACTUAL", "Decisions accepted by merchant", "HIGH"));
        items.add(new EvidenceItemDTO("Completed Decisions", summary.getCompletedCount(), "Count", "Decision History Engine", "Execution Memory", "ACTUAL", "Decisions marked completed", "HIGH"));
        items.add(new EvidenceItemDTO("Declined Decisions", summary.getDeclinedCount(), "Count", "Decision History Engine", "Rejection Log", "ACTUAL", "Recommendations declined", "HIGH"));
        items.add(new EvidenceItemDTO("Positive Outcome Rate", summary.getSuccessRatePct(), "%", "Decision History Engine", "Outcome Performance", "ACTUAL", "Positive outcomes / Completed decisions", "HIGH"));

        List<String> assumptions = new ArrayList<>();
        for (FinancialDecisionDTO d : decisions) {
            items.add(new EvidenceItemDTO("Decision: " + d.getTitle(), d.getDecisionStatus(), "Status", "Decision Memory", d.getDecisionDate(), "ACTUAL", "Outcome: " + d.getOutcomeStatus() + " | Notes: " + (d.getDecisionNotes() != null ? d.getDecisionNotes() : "N/A"), "HIGH"));
            assumptions.add("Decision '" + d.getTitle() + "' (" + d.getDecisionDate() + "): Status=" + d.getDecisionStatus() + ", Outcome=" + d.getOutcomeStatus());
        }

        String conclusion = "Decision History Performance: Total recorded decisions: " + summary.getTotalDecisions() 
                + " (Accepted: " + summary.getAcceptedCount() + ", Completed: " + summary.getCompletedCount() 
                + ", Positive Outcomes: " + summary.getPositiveOutcomeCount() + "). Success rate: " + summary.getSuccessRatePct() + "%.";

        return new FinancialEvidenceSummaryDTO(
                question,
                "DECISION_HISTORY",
                items,
                assumptions,
                summary.getDeclinedCount() > summary.getAcceptedCount() ? "ACTION_REQUIRED" : "HEALTHY",
                conclusion
        );
    }
}
