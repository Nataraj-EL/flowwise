package com.flowwise.evaluation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BenchmarkDataset {

    public static final String BENCHMARK_VERSION = "v1.0.0";

    public static List<BenchmarkTestCase> getTestCases() {
        List<BenchmarkTestCase> cases = new ArrayList<>();

        cases.add(new BenchmarkTestCase(
                "TC-01",
                "Can I afford ₹80,000 of inventory this week?",
                "Affordability/Scenario",
                Arrays.asList("80,000", "inventory", "reserves", "runway", "afford"),
                Arrays.asList("availableCash", "scenarioEndingCash", "scenarioRunwayMonths"),
                false
        ));

        cases.add(new BenchmarkTestCase(
                "TC-02",
                "How is my cash flow?",
                "Cash Flow",
                Arrays.asList("cash flow", "surplus", "inflows", "outflows", "runway"),
                Arrays.asList("totalInflows", "totalOutflows", "netCashFlow"),
                false
        ));

        cases.add(new BenchmarkTestCase(
                "TC-03",
                "Why is my business health score low?",
                "Business Health",
                Arrays.asList("health", "score", "runway", "burn"),
                Arrays.asList("healthScore", "healthStatus", "monthlyBurnRate"),
                false
        ));

        cases.add(new BenchmarkTestCase(
                "TC-04",
                "What changed compared with last month?",
                "Temporal",
                Arrays.asList("compared", "outflows", "inflows", "net cash", "shifted"),
                Arrays.asList("inflowChangePct", "outflowChangePct", "netCashChangePct"),
                false
        ));

        cases.add(new BenchmarkTestCase(
                "TC-05",
                "What is my 30-day cash projection?",
                "Forecasting",
                Arrays.asList("30-day", "cash", "projected", "runway"),
                Arrays.asList("30-Day Ending Cash", "30-Day Projected Runway"),
                false
        ));

        cases.add(new BenchmarkTestCase(
                "TC-06",
                "Can I afford a ₹1,50,000 equipment purchase?",
                "Affordability/Scenario",
                Arrays.asList("outlay", "equipment", "cash", "runway"),
                Arrays.asList("availableCash", "scenarioEndingCash"),
                false
        ));

        cases.add(new BenchmarkTestCase(
                "TC-07",
                "What is my current monthly burn rate?",
                "Cash Flow",
                Arrays.asList("burn rate", "outflows", "monthly"),
                Arrays.asList("monthlyBurnRate", "totalOutflows"),
                false
        ));

        cases.add(new BenchmarkTestCase(
                "TC-08",
                "Why did my cash drop this month?",
                "Temporal",
                Arrays.asList("outflows", "inflows", "changed", "net cash"),
                Arrays.asList("outflowChangePct", "netCashChangePct"),
                false
        ));

        cases.add(new BenchmarkTestCase(
                "TC-09",
                "What is my 90-day ending cash estimate?",
                "Forecasting",
                Arrays.asList("90-day", "ending cash", "estimate", "runway"),
                Arrays.asList("90-Day Ending Cash"),
                false
        ));

        cases.add(new BenchmarkTestCase(
                "TC-10",
                "Which expense category increased the most?",
                "Temporal",
                Arrays.asList("category", "expense", "operating", "shifted"),
                Arrays.asList("topExpenseCategory", "outflowChangePct"),
                false
        ));

        cases.add(new BenchmarkTestCase(
                "TC-11",
                "Is my cash flow getting better or worse?",
                "Temporal",
                Arrays.asList("trend", "improving", "contracting", "runway"),
                Arrays.asList("netCashChangePct", "cashRunwayMonths"),
                false
        ));

        cases.add(new BenchmarkTestCase(
                "TC-12",
                "What are my recurring monthly expenses?",
                "Cash Flow",
                Arrays.asList("recurring", "fixed", "rent", "payroll", "utilities"),
                Arrays.asList("recurringExpenses", "monthlyBurnRate"),
                false
        ));

        cases.add(new BenchmarkTestCase(
                "TC-13",
                "What is my cash runway duration?",
                "Cash Flow",
                Arrays.asList("runway", "months", "burn rate"),
                Arrays.asList("cashRunwayMonths", "availableCash"),
                false
        ));

        cases.add(new BenchmarkTestCase(
                "TC-14",
                "Can I afford ₹5,00,000 expansion outlay?",
                "Affordability/Scenario",
                Arrays.asList("outlay", "cash", "liquidity", "runway"),
                Arrays.asList("availableCash", "scenarioEndingCash"),
                false
        ));

        cases.add(new BenchmarkTestCase(
                "TC-15",
                "Can I get a ₹50,00,000 bank credit approval today?",
                "Insufficient-Data / Out-of-Scope",
                Arrays.asList("disclaimer", "informational", "credit", "approval"),
                Collections.emptyList(),
                true
        ));

        return cases;
    }
}
