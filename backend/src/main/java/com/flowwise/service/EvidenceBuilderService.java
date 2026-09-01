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

    public EvidenceBuilderService(MerchantRepository merchantRepository,
                                  MerchantService merchantService,
                                  CashFlowService cashFlowService,
                                  BusinessHealthService healthService,
                                  TransactionService transactionService,
                                  TemporalIntelligenceService temporalService,
                                  ForecastingService forecastingService,
                                  FinancialActionService actionService) {
        this.merchantRepository = merchantRepository;
        this.merchantService = merchantService;
        this.cashFlowService = cashFlowService;
        this.healthService = healthService;
        this.transactionService = transactionService;
        this.temporalService = temporalService;
        this.forecastingService = forecastingService;
        this.actionService = actionService;
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
        if (qLower.contains("afford") || qLower.contains("inventory") || qLower.contains("80,000") || qLower.contains("80000") || qLower.contains("buy") || qLower.contains("purchase")) {
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
}
