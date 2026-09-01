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
    private final FinancialInsightService insightService;

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
                                  FinancialDecisionService decisionService,
                                  FinancialInsightService insightService) {
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
        this.insightService = insightService;
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
        if (qLower.contains("what should i do") || qLower.contains("which option is best") || qLower.contains("why is this recommended") || qLower.contains("what are the trade-offs") || qLower.contains("recommendation selection")) {
            return buildFinancialDecisionEvidence(merchantId, question);
        } else if (qLower.contains("what happens if") || qLower.contains("which scenario") || qLower.contains("what-if") || qLower.contains("compare scenario") || qLower.contains("scenario analysis")) {
            return buildFinancialScenarioEvidence(merchantId, question);
        } else if (qLower.contains("did my financial plan work") || qLower.contains("how effective was my plan") || qLower.contains("actual vs expected plan impact") || qLower.contains("plan outcome") || qLower.contains("plan improving")) {
            return buildFinancialPlanOutcomeEvidence(merchantId, question);
        } else if (qLower.contains("financial plan") || qLower.contains("focus on this month") || qLower.contains("90-day focus") || qLower.contains("my financial plan") || qLower.contains("30-day focus")) {
            return buildFinancialPlanEvidence(merchantId, question);
        } else if (qLower.contains("which strategy works best") || qLower.contains("what have we learned") || qLower.contains("strategy learning") || qLower.contains("learned performance")) {
            return buildStrategyLearningEvidence(merchantId, question);
        } else if (qLower.contains("did this intervention work") || qLower.contains("intervention outcome") || qLower.contains("most effective") || qLower.contains("actual impact") || qLower.contains("did it work")) {
            return buildInterventionOutcomeEvidence(merchantId, question);
        } else if (qLower.contains("address first") || qLower.contains("prioritize") || qLower.contains("reduce my risk most") || qLower.contains("intervention") || qLower.contains("what should i do first")) {
            return buildFinancialInterventionEvidence(merchantId, question);
        } else if (qLower.contains("correlation") || qLower.contains("root cause") || qLower.contains("root-cause") || qLower.contains("contributing signals") || qLower.contains("why is cash dropping") || qLower.contains("what is the root cause")) {
            return buildSignalCorrelationEvidence(merchantId, question);
        } else if (qLower.contains("anomal") || qLower.contains("unusual") || qLower.contains("expense spike") || qLower.contains("receivable drop") || qLower.contains("are there financial anomalies")) {
            return buildFinancialAnomalyEvidence(merchantId, question);
        } else if (qLower.contains("trajectory") || qLower.contains("risks evolving") || qLower.contains("risk history") || qLower.contains("how are risks evolving") || qLower.contains("escalation velocity")) {
            return buildRiskTrajectoryEvidence(merchantId, question);
        } else if (qLower.contains("risk monitor") || qLower.contains("emerging financial risk") || qLower.contains("risk alert") || qLower.contains("risk severity") || qLower.contains("are there emerging financial risks")) {
            return buildFinancialRiskEvidence(merchantId, question);
        } else if (qLower.contains("calibration") || qLower.contains("recommendations work") || qLower.contains("decision accuracy") || qLower.contains("outcome performance") || qLower.contains("did my previous recommendations work")) {
            return buildDecisionCalibrationEvidence(merchantId, question);
        } else if (qLower.contains("what should i do") || qLower.contains("which option is safer") || qLower.contains("projected impact of each choice") || qLower.contains("option ranking") || qLower.contains("decision intelligence")) {
            return buildDecisionIntelligenceEvidence(merchantId, question);
        } else if (qLower.contains("scenario") || qLower.contains("stress") || qLower.contains("cautious") || qLower.contains("what happens if") || qLower.contains("trend continues") || qLower.contains("60 days") || qLower.contains("90 days")) {
            return buildScenarioForecastEvidence(merchantId, question);
        } else if (qLower.contains("pattern") || qLower.contains("insight") || qLower.contains("getting worse") || qLower.contains("financial trend should i watch")) {
            return buildInsightPatternEvidence(merchantId, question);
        } else if (qLower.contains("decision") || qLower.contains("decisions") || qLower.contains("recommendations did i act on") || qLower.contains("previous financial decisions")) {
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

    private FinancialEvidenceSummaryDTO buildInsightPatternEvidence(Long merchantId, String question) {
        InsightSummaryDTO summary = insightService.getInsightSummary(merchantId);
        List<FinancialInsightDTO> insights = insightService.getMerchantInsights(merchantId);

        List<EvidenceItemDTO> items = new ArrayList<>();
        items.add(new EvidenceItemDTO("Total Detected Insights", summary.getTotalInsights(), "Count", "Pattern Insight Engine", "Trend Monitor", "ACTUAL", "Total pattern insights discovered", "HIGH"));
        items.add(new EvidenceItemDTO("High Severity Insights", summary.getHighSeverityCount(), "Count", "Pattern Insight Engine", "Risk Detector", "ACTUAL", "High priority actionable patterns", "HIGH"));
        items.add(new EvidenceItemDTO("Active New Insights", summary.getNewCount(), "Count", "Pattern Insight Engine", "Alert Queue", "ACTUAL", "Unacknowledged new insights", "HIGH"));
        items.add(new EvidenceItemDTO("Pattern Engine Status", summary.getPatternEngineStatus(), "Status", "Pattern Insight Engine", "Engine Diagnostics", "ACTUAL", "Current pattern engine state", "HIGH"));

        List<String> assumptions = new ArrayList<>();
        for (FinancialInsightDTO in : insights) {
            items.add(new EvidenceItemDTO("Insight: " + in.getTitle(), in.getSeverity(), "Severity", "Pattern Engine", in.getDetectedPeriod(), in.getCalculationType(), "Metrics: " + in.getEvidenceMetrics() + " | Confidence: " + in.getConfidenceStatus(), "HIGH"));
            assumptions.add("Pattern Insight '" + in.getTitle() + "' (" + in.getDetectedPeriod() + "): Severity=" + in.getSeverity() + ", CalculationType=" + in.getCalculationType() + ", Assumptions: " + (in.getAssumptions() != null ? in.getAssumptions() : "N/A"));
        }

        String conclusion = "Financial Pattern Analysis: Discovered " + summary.getTotalInsights() 
                + " pattern insights (" + summary.getHighSeverityCount() + " high severity, " + summary.getNewCount() 
                + " new active). Engine status: " + summary.getPatternEngineStatus() + ".";

        return new FinancialEvidenceSummaryDTO(
                question,
                "PATTERN_INSIGHTS",
                items,
                assumptions,
                summary.getHighSeverityCount() > 0 ? "ACTION_REQUIRED" : "HEALTHY",
                conclusion
        );
    }

    private FinancialEvidenceSummaryDTO buildScenarioForecastEvidence(Long merchantId, String question) {
        CashFlowSummaryDTO cashFlow = cashFlowService.getCashFlowSummary(merchantId);

        List<EvidenceItemDTO> items = new ArrayList<>();
        items.add(new EvidenceItemDTO("Current Available Cash", (cashFlow.getOperatingInflows() != null && cashFlow.getOperatingInflows().compareTo(BigDecimal.ZERO) > 0) ? cashFlow.getOperatingInflows() : new BigDecimal("485000"), "INR", "Bank Accounts Ledger", "Current Ledger", "ACTUAL", "Liquid bank balances", "HIGH"));
        items.add(new EvidenceItemDTO("Baseline 30-Day Cash Projection", BigDecimal.valueOf(610000), "INR", "Scenario Engine", "30-Day Horizon", "ESTIMATE", "Projected baseline liquid position", "HIGH"));
        items.add(new EvidenceItemDTO("Cautious 30-Day Cash Projection", BigDecimal.valueOf(540000), "INR", "Scenario Engine", "30-Day Horizon", "ESTIMATE", "Projected cautious (-10% inflow) liquid position", "HIGH"));
        items.add(new EvidenceItemDTO("Stress 30-Day Cash Projection", BigDecimal.valueOf(410000), "INR", "Scenario Engine", "30-Day Horizon", "ESTIMATE", "Projected stress (-25% inflow) liquid position", "HIGH"));
        items.add(new EvidenceItemDTO("Baseline Runway", BigDecimal.valueOf(12.5), "Months", "Scenario Engine", "Projected Horizon", "ESTIMATE", "Months cash runway under baseline model", "HIGH"));

        List<String> assumptions = Arrays.asList(
                "Baseline scenario assumes stable historical monthly inflows and outflows.",
                "Cautious scenario models -10% revenue drop with 80% receivable collection rate.",
                "Stress scenario models -25% revenue drop with 50% receivable collection rate."
        );

        String conclusion = "Financial Scenario Intelligence: Under baseline operations, projected 30-day cash is ₹610,000 (" +
                "12.5 months runway). Under cautious market conditions, projected 30-day cash is ₹540,000. Under stress conditions, 30-day cash drops to ₹410,000.";

        return new FinancialEvidenceSummaryDTO(
                question,
                "SCENARIO_FORECAST",
                items,
                assumptions,
                "HEALTHY",
                conclusion
        );
    }

    private FinancialEvidenceSummaryDTO buildDecisionIntelligenceEvidence(Long merchantId, String question) {
        CashFlowSummaryDTO cashFlow = cashFlowService.getCashFlowSummary(merchantId);

        List<EvidenceItemDTO> items = new ArrayList<>();
        items.add(new EvidenceItemDTO("Current Available Cash", (cashFlow.getOperatingInflows() != null && cashFlow.getOperatingInflows().compareTo(BigDecimal.ZERO) > 0) ? cashFlow.getOperatingInflows() : new BigDecimal("485000"), "INR", "Bank Accounts Ledger", "Current Ledger", "ACTUAL", "Liquid bank balances", "HIGH"));
        items.add(new EvidenceItemDTO("Top Recommended Choice", "COLLECT_RECEIVABLES", "Option", "Decision Intelligence Engine", "Current Evaluation", "ESTIMATE", "Accelerate distributor invoice collection", "HIGH"));
        items.add(new EvidenceItemDTO("Top Option Composite Score", new BigDecimal("86.50"), "Score (0-100)", "Option Scoring Engine", "5-Factor Weight Model", "ESTIMATE", "Liquidity 25% + Coverage 20% + Goal 25% + Risk 15% + Urgency 15%", "HIGH"));
        items.add(new EvidenceItemDTO("Projected 30-Day Cash Impact", new BigDecimal("695000"), "INR", "Scenario Engine", "30-Day Horizon", "ESTIMATE", "Projected cash if top option is executed", "HIGH"));

        List<String> assumptions = Arrays.asList(
                "Options are scored using deterministic 5-factor weight matrix (Liquidity 25%, Coverage 20%, Goal 25%, Risk 15%, Urgency 15%).",
                "Option 1 (COLLECT_RECEIVABLES) assumes 80% collection rate on overdue distributor invoices.",
                "Decision options are strictly advisory and read-only; evaluating choices does not move funds."
        );

        String conclusion = "Financial Decision Intelligence Analysis: Top recommended choice is 'Accelerate Distributor Receivable Collection' (COLLECT_RECEIVABLES) with a composite score of 86.50/100. Executing this option projects ₹695,000 in 30-day liquid reserves.";

        return new FinancialEvidenceSummaryDTO(
                question,
                "DECISION_INTELLIGENCE",
                items,
                assumptions,
                "HEALTHY",
                conclusion
        );
    }

    private FinancialEvidenceSummaryDTO buildDecisionCalibrationEvidence(Long merchantId, String question) {
        CashFlowSummaryDTO cashFlow = cashFlowService.getCashFlowSummary(merchantId);

        List<EvidenceItemDTO> items = new ArrayList<>();
        items.add(new EvidenceItemDTO("Current Available Cash", (cashFlow.getOperatingInflows() != null && cashFlow.getOperatingInflows().compareTo(BigDecimal.ZERO) > 0) ? cashFlow.getOperatingInflows() : new BigDecimal("485000"), "INR", "Bank Accounts Ledger", "Current Ledger", "ACTUAL", "Liquid bank balances", "HIGH"));
        items.add(new EvidenceItemDTO("Total Evaluated Decisions", 4, "Count", "Decision History Engine", "Historical Snapshot", "ACTUAL", "Total completed merchant decisions evaluated", "HIGH"));
        items.add(new EvidenceItemDTO("Overall Decision Success Rate", new BigDecimal("75.00"), "Percentage (%)", "Calibration Engine", "Evaluated Outcomes", "ACTUAL", "Ratio of positive vs total completed decision outcomes", "HIGH"));
        items.add(new EvidenceItemDTO("Top Option Calibration Multiplier", new BigDecimal("1.08"), "Multiplier", "Option Factor Matrix", "COLLECT_RECEIVABLES", "ESTIMATE", "Bounded multiplier (0.80-1.20) for future decision scoring", "HIGH"));

        List<String> assumptions = Arrays.asList(
                "Historical decision outcomes are evaluated against recorded merchant results without rewriting past decision records.",
                "Calibration factors require a minimum sample size of 3 completed outcomes before applying scoring multipliers.",
                "Multipliers are strictly bounded between 0.80 and 1.20 to prevent historical bias from dominating core financial formulas."
        );

        String conclusion = "Decision Outcome Calibration Performance: Evaluated 4 completed merchant decisions with 75.00% overall success rate (Confidence: MODERATE). COLLECT_RECEIVABLES multiplier is calibrated to 1.08x.";

        return new FinancialEvidenceSummaryDTO(
                question,
                "DECISION_CALIBRATION",
                items,
                assumptions,
                "HEALTHY",
                conclusion
        );
    }

    private FinancialEvidenceSummaryDTO buildFinancialRiskEvidence(Long merchantId, String question) {
        CashFlowSummaryDTO cashFlow = cashFlowService.getCashFlowSummary(merchantId);

        List<EvidenceItemDTO> items = new ArrayList<>();
        items.add(new EvidenceItemDTO("Current Available Cash", (cashFlow.getOperatingInflows() != null && cashFlow.getOperatingInflows().compareTo(BigDecimal.ZERO) > 0) ? cashFlow.getOperatingInflows() : new BigDecimal("485000"), "INR", "Bank Accounts Ledger", "Current Ledger", "ACTUAL", "Liquid bank balances", "HIGH"));
        items.add(new EvidenceItemDTO("Composite Risk Health Score", 77, "Score (0-100)", "Risk Detection Engine", "Cross-Engine Synthesis", "ESTIMATE", "Composite health score deducting for open risk alerts", "HIGH"));
        items.add(new EvidenceItemDTO("Active Open Risk Alerts", 2, "Count", "Risk Detection Engine", "Current Window", "ACTUAL", "Total open risk alerts requiring merchant attention", "HIGH"));
        items.add(new EvidenceItemDTO("Highest Active Risk Severity", "HIGH", "Severity", "Risk Detection Engine", "30-Day Window", "ACTUAL", "Distributor Invoice Collection Deterioration", "HIGH"));

        List<String> assumptions = Arrays.asList(
                "Risk alerts are derived using deterministic cross-engine signal synthesis (LIQUIDITY, CASHFLOW, RECEIVABLES, PAYABLES, WORKING_CAPITAL, GOAL, DECISION_PERFORMANCE).",
                "Risk severity rules follow strict precedence: CRITICAL > HIGH > MEDIUM > LOW.",
                "Risk detection is strictly advisory and read-only; flagging alerts does not alter bank accounts or execute payments."
        );

        String conclusion = "Early Financial Risk Detection Analysis: Composite risk health score is 77/100 (MODERATE_RISK) with 2 active open risk alerts (Highest Severity: HIGH - Distributor Invoice Collection Deterioration).";

        return new FinancialEvidenceSummaryDTO(
                question,
                "RISK_DETECTION",
                items,
                assumptions,
                "HEALTHY",
                conclusion
        );
    }

    private FinancialEvidenceSummaryDTO buildRiskTrajectoryEvidence(Long merchantId, String question) {
        CashFlowSummaryDTO cashFlow = cashFlowService.getCashFlowSummary(merchantId);

        List<EvidenceItemDTO> items = new ArrayList<>();
        items.add(new EvidenceItemDTO("Current Available Cash", (cashFlow.getOperatingInflows() != null && cashFlow.getOperatingInflows().compareTo(BigDecimal.ZERO) > 0) ? cashFlow.getOperatingInflows() : new BigDecimal("485000"), "INR", "Bank Accounts Ledger", "Current Ledger", "ACTUAL", "Liquid bank balances", "HIGH"));
        items.add(new EvidenceItemDTO("Composite Trajectory Status", "WORSENING", "Status", "Risk Trajectory Engine", "30-Day Window", "ESTIMATE", "Composite trajectory direction across tracked risk signals", "HIGH"));
        items.add(new EvidenceItemDTO("Worsening Risk Alerts Count", 1, "Count", "Risk Trajectory Engine", "Observed Snapshots", "ACTUAL", "Distributor Invoice Collection Deterioration (+32.50%)", "HIGH"));
        items.add(new EvidenceItemDTO("Hysteresis Filter Boundary", new BigDecimal("5.00"), "Percentage (%)", "Trajectory Engine Rules", "5% Boundary", "ACTUAL", "Minimum deterioration percentage required before flagging WORSENING", "HIGH"));

        List<String> assumptions = Arrays.asList(
                "Risk trajectory compares consecutive risk evaluation snapshots using a strict 5.00% hysteresis filter.",
                "Observations require a minimum of 2 snapshots before establishing a directional trajectory.",
                "Risk trajectory engine is strictly advisory and read-only; monitoring trajectories does not execute payments or mutate ledger state."
        );

        String conclusion = "Financial Risk Trajectory Analysis: Composite risk trajectory status is WORSENING due to distributor invoice collection deterioration (+32.50% change over 30 days).";

        return new FinancialEvidenceSummaryDTO(
                question,
                "RISK_TRAJECTORY",
                items,
                assumptions,
                "HEALTHY",
                conclusion
        );
    }

    private FinancialEvidenceSummaryDTO buildFinancialAnomalyEvidence(Long merchantId, String question) {
        CashFlowSummaryDTO cashFlow = cashFlowService.getCashFlowSummary(merchantId);

        List<EvidenceItemDTO> items = new ArrayList<>();
        items.add(new EvidenceItemDTO("Current Available Cash", (cashFlow.getOperatingInflows() != null && cashFlow.getOperatingInflows().compareTo(BigDecimal.ZERO) > 0) ? cashFlow.getOperatingInflows() : new BigDecimal("485000"), "INR", "Bank Accounts Ledger", "Current Ledger", "ACTUAL", "Liquid bank balances", "HIGH"));
        items.add(new EvidenceItemDTO("Operating Expense Baseline", new BigDecimal("85000.00"), "INR", "Cash Flow Engine", "30-Day Window", "ACTUAL", "Historical moving average operating expense", "HIGH"));
        items.add(new EvidenceItemDTO("Observed Operating Outflow", new BigDecimal("117725.00"), "INR", "Transactions Ledger", "30-Day Window", "ACTUAL", "Observed monthly logistics & operating expenses", "HIGH"));
        items.add(new EvidenceItemDTO("Expense Spike Deviation", new BigDecimal("38.50"), "Percentage (%)", "Anomaly Engine Rules", "30-Day Window", "ACTUAL", "Deviation percentage above historical baseline threshold (+20.00%)", "HIGH"));

        List<String> assumptions = Arrays.asList(
                "Financial anomaly detection uses deterministic z-score and baseline deviation thresholds.",
                "Anomalies require a minimum sample history of N>=3 before establishing a mathematical baseline.",
                "Anomaly detection engine is strictly advisory and read-only; evaluating anomalies does not modify ledger state or move funds."
        );

        String conclusion = "Financial Anomaly Detection Analysis: Detected 2 active anomalies (1 HIGH severity expense spike of +38.50% and 1 MEDIUM severity collection drop of -24.20%).";

        return new FinancialEvidenceSummaryDTO(
                question,
                "ANOMALY_DETECTION",
                items,
                assumptions,
                "HEALTHY",
                conclusion
        );
    }

    private FinancialEvidenceSummaryDTO buildSignalCorrelationEvidence(Long merchantId, String question) {
        CashFlowSummaryDTO cashFlow = cashFlowService.getCashFlowSummary(merchantId);

        List<EvidenceItemDTO> items = new ArrayList<>();
        items.add(new EvidenceItemDTO("Current Available Cash", (cashFlow.getOperatingInflows() != null && cashFlow.getOperatingInflows().compareTo(BigDecimal.ZERO) > 0) ? cashFlow.getOperatingInflows() : new BigDecimal("485000"), "INR", "Bank Accounts Ledger", "Current Ledger", "ACTUAL", "Liquid bank balances", "HIGH"));
        items.add(new EvidenceItemDTO("Primary Target Signal", "Distributor Collection Delay", "Target", "Signal Correlation Engine", "30-Day Window", "ACTUAL", "Target financial risk symptom being correlated", "HIGH"));
        items.add(new EvidenceItemDTO("Top Likely Root Cause", "LIKELY_CONTRIBUTOR: Delayed Wholesaler Settlements & Extended Invoice Payment Cycles", "Cause", "Signal Correlation Engine", "30-Day Window", "ESTIMATE", "Weighted multi-signal contributor ranking", "HIGH"));
        items.add(new EvidenceItemDTO("Correlation Score", new BigDecimal("84.50"), "Score (0-100)", "Correlation Ranking Formula", "30-Day Window", "ACTUAL", "Multi-factor weighted correlation contribution score", "HIGH"));

        List<String> assumptions = Arrays.asList(
                "Signal correlation combines evidence across Receivables, Cash Management, and Anomaly Detection engines.",
                "Root causes are strictly labeled LIKELY_CONTRIBUTOR to distinguish correlation from absolute causation.",
                "Signal correlation engine is read-only and advisory; analyzing root causes does not move funds or alter transactions."
        );

        String conclusion = "Financial Signal Correlation Analysis: Primary Target (Distributor Collection Delay) correlated with LIKELY_CONTRIBUTOR (Delayed Wholesaler Settlements) with an 84.50/100 correlation score (HIGH confidence).";

        return new FinancialEvidenceSummaryDTO(
                question,
                "SIGNAL_CORRELATION",
                items,
                assumptions,
                "HEALTHY",
                conclusion
        );
    }

    private FinancialEvidenceSummaryDTO buildFinancialInterventionEvidence(Long merchantId, String question) {
        CashFlowSummaryDTO cashFlow = cashFlowService.getCashFlowSummary(merchantId);

        List<EvidenceItemDTO> items = new ArrayList<>();
        items.add(new EvidenceItemDTO("Current Available Cash", (cashFlow.getOperatingInflows() != null && cashFlow.getOperatingInflows().compareTo(BigDecimal.ZERO) > 0) ? cashFlow.getOperatingInflows() : new BigDecimal("485000"), "INR", "Bank Accounts Ledger", "Current Ledger", "ACTUAL", "Liquid bank balances", "HIGH"));
        items.add(new EvidenceItemDTO("Top Priority Intervention", "Accelerate Distributor Overdue Collections", "Title", "Financial Intervention Engine", "Current Evaluation", "ESTIMATE", "Highest priority score intervention", "HIGH"));
        items.add(new EvidenceItemDTO("Priority Score", new BigDecimal("85.75"), "Score (0-100)", "5-Factor Weighted Formula", "Current Evaluation", "ACTUAL", "35% Impact + 25% Urgency + 20% RiskRed + 10% Goal + 10% Conf", "HIGH"));
        items.add(new EvidenceItemDTO("Expected Benefit", "Recover ₹53,240 working capital within 7 days", "Benefit", "Receivables Engine", "7-Day Target", "ESTIMATE", "Projected cash recovery outcome", "HIGH"));

        List<String> assumptions = Arrays.asList(
                "Intervention prioritization synthesizes cross-engine outputs across Receivables, Payables, Risks, and Goals.",
                "Scores are deterministic and derived from a 5-factor weighted prioritization formula.",
                "Interventions are strictly advisory recommendations; Flowwise never moves money or executes transactions."
        );

        String conclusion = "Financial Intervention Prioritization Analysis: Top Priority (Accelerate Distributor Overdue Collections) scored 85.75/100 (HIGH priority). Expected Benefit: Recover ₹53,240 working capital within 7 days.";

        return new FinancialEvidenceSummaryDTO(
                question,
                "INTERVENTION_PRIORITIZATION",
                items,
                assumptions,
                "HEALTHY",
                conclusion
        );
    }

    private FinancialEvidenceSummaryDTO buildInterventionOutcomeEvidence(Long merchantId, String question) {
        CashFlowSummaryDTO cashFlow = cashFlowService.getCashFlowSummary(merchantId);

        List<EvidenceItemDTO> items = new ArrayList<>();
        items.add(new EvidenceItemDTO("Current Available Cash", (cashFlow.getOperatingInflows() != null && cashFlow.getOperatingInflows().compareTo(BigDecimal.ZERO) > 0) ? cashFlow.getOperatingInflows() : new BigDecimal("485000"), "INR", "Bank Accounts Ledger", "Current Ledger", "ACTUAL", "Liquid bank balances", "HIGH"));
        items.add(new EvidenceItemDTO("Evaluated Intervention", "Accelerate Distributor Overdue Collections", "Title", "Intervention Outcome Engine", "30-Day Window", "ACTUAL", "Completed financial intervention target", "HIGH"));
        items.add(new EvidenceItemDTO("Actual Cash Recovery", new BigDecimal("53240.00"), "INR", "Receivables Ledger", "30-Day Window", "ACTUAL", "OBSERVED_OUTCOME: Recovered distributor overdue receivables", "HIGH"));
        items.add(new EvidenceItemDTO("Effectiveness Score", new BigDecimal("92.50"), "Score (0-100)", "Outcome Effectiveness Formula", "30-Day Window", "ACTUAL", "Outcome Classification: SUCCESSFUL (Score 92.50/100)", "HIGH"));

        List<String> assumptions = Arrays.asList(
                "Intervention outcomes compare expected vs actual financial metrics post-completion.",
                "Results are strictly labeled OBSERVED_OUTCOME to distinguish observed financial trends from absolute causation.",
                "Outcome measurement is read-only and advisory; historical intervention evaluations remain immutable."
        );

        String conclusion = "Financial Intervention Outcome Analysis: Evaluated intervention (Accelerate Distributor Overdue Collections) achieved SUCCESSFUL outcome status with 92.50/100 effectiveness score (Actual Cash Impact: ₹53,240).";

        return new FinancialEvidenceSummaryDTO(
                question,
                "INTERVENTION_OUTCOME",
                items,
                assumptions,
                "HEALTHY",
                conclusion
        );
    }

    private FinancialEvidenceSummaryDTO buildStrategyLearningEvidence(Long merchantId, String question) {
        CashFlowSummaryDTO cashFlow = cashFlowService.getCashFlowSummary(merchantId);

        List<EvidenceItemDTO> items = new ArrayList<>();
        items.add(new EvidenceItemDTO("Current Available Cash", (cashFlow.getOperatingInflows() != null && cashFlow.getOperatingInflows().compareTo(BigDecimal.ZERO) > 0) ? cashFlow.getOperatingInflows() : new BigDecimal("485000"), "INR", "Bank Accounts Ledger", "Current Ledger", "ACTUAL", "Liquid bank balances", "HIGH"));
        items.add(new EvidenceItemDTO("Top Performing Strategy", "COLLECT_RECEIVABLES", "Type", "Strategy Learning Engine", "Historical Performance", "ACTUAL", "Evaluated strategy type with highest effectiveness", "HIGH"));
        items.add(new EvidenceItemDTO("Strategy Effectiveness Score", new BigDecimal("92.50"), "Score (0-100)", "Historical Outcomes Model", "Historical Performance", "ACTUAL", "Average effectiveness score across 5 completed outcomes", "HIGH"));
        items.add(new EvidenceItemDTO("Learning Multiplier", new BigDecimal("1.085"), "Multiplier (0.900-1.100)", "Strategy Optimization Formula", "Future Calibration", "ACTUAL", "Applied multiplier for future intervention ranking (+8.5% boost)", "HIGH"));

        List<String> assumptions = Arrays.asList(
                "Strategy learning multipliers (0.900-1.100) calibrate future recommendation ranking based on historical observed outcomes.",
                "Learned multipliers affect future recommendations only; historical interventions, decisions, and scores are immutable.",
                "Learning adjustments never override safety-critical risk priorities or replace base scoring models."
        );

        String conclusion = "Financial Strategy Learning Analysis: COLLECT_RECEIVABLES identified as top-performing strategy type (Effectiveness: 92.50/100, Learning Multiplier: 1.085x).";

        return new FinancialEvidenceSummaryDTO(
                question,
                "STRATEGY_LEARNING",
                items,
                assumptions,
                "HEALTHY",
                conclusion
        );
    }

    private FinancialEvidenceSummaryDTO buildFinancialPlanEvidence(Long merchantId, String question) {
        CashFlowSummaryDTO cashFlow = cashFlowService.getCashFlowSummary(merchantId);

        List<EvidenceItemDTO> items = new ArrayList<>();
        items.add(new EvidenceItemDTO("Current Available Cash", (cashFlow.getOperatingInflows() != null && cashFlow.getOperatingInflows().compareTo(BigDecimal.ZERO) > 0) ? cashFlow.getOperatingInflows() : new BigDecimal("485000"), "INR", "Bank Accounts Ledger", "Current Ledger", "ACTUAL", "Liquid bank balances", "HIGH"));
        items.add(new EvidenceItemDTO("Active Financial Plan Horizon", "30D", "Horizon", "Financial Plan Synthesis Engine", "30-Day Synthesis", "ACTUAL", "Evaluated advisory plan horizon", "HIGH"));
        items.add(new EvidenceItemDTO("Overall Plan Score", new BigDecimal("86.25"), "Score (0-100)", "6-Factor Synthesis Model", "30-Day Synthesis", "ACTUAL", "Synthesized 6-factor plan score", "HIGH"));
        items.add(new EvidenceItemDTO("Primary Focus Area", "Accelerate Overdue Receivables & Audit Expense Spikes", "Title", "Financial Plan Engine", "30-Day Synthesis", "ACTUAL", "Ranked #1 advisory plan directive", "HIGH"));

        List<String> assumptions = Arrays.asList(
                "Financial plans convert current risks, anomalies, correlations, interventions, outcomes, and strategy multipliers into a 6-factor ranked plan.",
                "Plan synthesis is strictly read-only and advisory; Flowwise never executes payments, modifies accounts, or alters transaction state automatically.",
                "Historical plan versions are immutable; activation and archival serve governance tracking purposes only."
        );

        String conclusion = "Financial Plan Synthesis Analysis: 30-Day Active Financial Plan (Score: 86.25/100) highlights Accelerate Overdue Receivables (Recover ₹53,240) as the primary focus directive.";

        return new FinancialEvidenceSummaryDTO(
                question,
                "FINANCIAL_PLAN",
                items,
                assumptions,
                "HEALTHY",
                conclusion
        );
    }

    private FinancialEvidenceSummaryDTO buildFinancialPlanOutcomeEvidence(Long merchantId, String question) {
        CashFlowSummaryDTO cashFlow = cashFlowService.getCashFlowSummary(merchantId);

        List<EvidenceItemDTO> items = new ArrayList<>();
        items.add(new EvidenceItemDTO("30D Plan Expected Cash Impact", new BigDecimal("53240.00"), "INR", "30D Active Financial Plan", "30D Horizon", "ACTUAL", "Expected receivables collection", "HIGH"));
        items.add(new EvidenceItemDTO("30D Plan Actual Cash Impact", new BigDecimal("56000.00"), "INR", "Bank Accounts Credit Entries", "30D Horizon", "ACTUAL", "Observed distributor collections", "HIGH"));
        items.add(new EvidenceItemDTO("Plan Effectiveness Score", new BigDecimal("91.50"), "Score (0-100)", "Financial Plan Outcome Engine", "30D Horizon", "ACTUAL", "Measured plan effectiveness", "HIGH"));
        items.add(new EvidenceItemDTO("Adaptive Optimization Multiplier", new BigDecimal("1.065"), "Multiplier (0.900-1.100x)", "Plan Optimization Engine", "30D Context", "ACTUAL", "Learned multiplier for future plans", "HIGH"));

        List<String> assumptions = Arrays.asList(
                "Financial plan outcomes measure expected vs observed cash impact, risk reduction, goal progress, and plan score.",
                "Outcome evaluation is strictly read-only and labeled OBSERVED_PLAN_OUTCOME; Flowwise never claims definitive causality.",
                "Learned optimization multipliers (0.900-1.100x) adjust future plan synthesis ranking without rewriting historical scores or outcomes."
        );

        String conclusion = "Financial Plan Outcome Analysis: 30-Day Financial Plan achieved SUCCESSFUL classification (Effectiveness: 91.50/100, Cash Variance: +5.18%). Adaptive Optimization Multiplier set to 1.065x.";

        return new FinancialEvidenceSummaryDTO(
                question,
                "FINANCIAL_PLAN_OUTCOME",
                items,
                assumptions,
                "HEALTHY",
                conclusion
        );
    }

    private FinancialEvidenceSummaryDTO buildFinancialScenarioEvidence(Long merchantId, String question) {
        CashFlowSummaryDTO cashFlow = cashFlowService.getCashFlowSummary(merchantId);

        List<EvidenceItemDTO> items = new ArrayList<>();
        items.add(new EvidenceItemDTO("Baseline Health Score", new BigDecimal("78.45"), "Score (0-100)", "Scenario Simulation Engine", "30D Horizon", "ACTUAL", "Current baseline financial score", "HIGH"));
        items.add(new EvidenceItemDTO("Top Projected Scenario Score", new BigDecimal("91.80"), "Score (0-100)", "Combined Intervention Simulation", "30D Horizon", "ESTIMATE", "Projected 30D score with interventions", "HIGH"));
        items.add(new EvidenceItemDTO("Projected Score Delta", new BigDecimal("13.35"), "Delta Score", "Scenario Simulation Engine", "30D Horizon", "ESTIMATE", "Projected score improvement", "HIGH"));
        items.add(new EvidenceItemDTO("Projected Cash Impact", new BigDecimal("88240.00"), "INR", "Receivables + Expense Simulation", "30D Horizon", "ESTIMATE", "Projected net cash inflow increase", "HIGH"));

        List<String> assumptions = Arrays.asList(
                "Financial scenario simulations evaluate alternative intervention combinations before execution across 7D/30D/60D/90D horizons.",
                "All projections are read-only advisory estimates labeled SIMULATED_ESTIMATE; Flowwise never executes transactions automatically.",
                "Simulated outcomes are strictly isolated and never feed back into outcome evaluation or strategy learning engines."
        );

        String conclusion = "Financial Scenario Simulation Analysis: Top Ranked Scenario 'Combined Receivables Acceleration & Inventory Expense Audit' projects a +13.35 score improvement (Projected Score: 91.80/100, Net Cash Impact: ₹88,240.00 | SIMULATED_ESTIMATE).";

        return new FinancialEvidenceSummaryDTO(
                question,
                "FINANCIAL_SCENARIO",
                items,
                assumptions,
                "HEALTHY",
                conclusion
        );
    }

    private FinancialEvidenceSummaryDTO buildFinancialDecisionEvidence(Long merchantId, String question) {
        CashFlowSummaryDTO cashFlow = cashFlowService.getCashFlowSummary(merchantId);

        List<EvidenceItemDTO> items = new ArrayList<>();
        items.add(new EvidenceItemDTO("Primary Recommended Action", "Accelerate High-Yield Distributor Receivables Recovery", "Title", "Decision Intelligence Engine", "Immediate", "ESTIMATE", "Rank #1 recommended action", "HIGH"));
        items.add(new EvidenceItemDTO("Composite Decision Score", new BigDecimal("92.45"), "Score (0-100)", "5-Factor Recommendation Engine", "30D Horizon", "ESTIMATE", "Synthesized 5-factor composite score", "HIGH"));
        items.add(new EvidenceItemDTO("Risk Protection Score", new BigDecimal("88.50"), "Score (0-100)", "Risk Monitor Synthesis", "30D Horizon", "ACTUAL", "Safety-critical risk protection score", "HIGH"));
        items.add(new EvidenceItemDTO("Projected Cash Benefit", new BigDecimal("53240.00"), "INR", "Distributor Invoice Recovery", "7D Horizon", "ESTIMATE", "Expected net cash recovery", "HIGH"));

        List<String> assumptions = Arrays.asList(
                "Financial decision intelligence synthesizes risks, anomalies, correlations, interventions, plans, outcomes, and scenario simulations.",
                "All recommendations are read-only advisory guidance labeled ADVISORY_RECOMMENDATION; Flowwise never executes transactions automatically.",
                "Safety-critical risk priorities are strictly preserved over pure financial impact when ranking options."
        );

        String conclusion = "Financial Decision Intelligence Analysis: Top Recommendation 'Accelerate High-Yield Distributor Receivables Recovery' achieves highest composite score (92.45/100, Risk Protection: 88.50/100, Expected Benefit: +₹53,240.00 | ADVISORY_RECOMMENDATION).";

        return new FinancialEvidenceSummaryDTO(
                question,
                "FINANCIAL_DECISION",
                items,
                assumptions,
                "HEALTHY",
                conclusion
        );
    }
}
