package com.flowwise.dto;

import java.math.BigDecimal;
import java.util.List;

public class ScenarioResultDTO {
    private BigDecimal requestedAmount;
    private String category;
    private BigDecimal baselineEndingCash;
    private BigDecimal scenarioEndingCash;
    private BigDecimal baselineRunwayMonths;
    private BigDecimal scenarioRunwayMonths;
    private BigDecimal cashImpact;
    private BigDecimal runwayImpactMonths;
    private String riskStatus; // FEASIBLE, CAUTION, HIGH_RISK
    private List<String> assumptions;
    private boolean estimate;

    public ScenarioResultDTO() {}

    public ScenarioResultDTO(BigDecimal requestedAmount, String category, BigDecimal baselineEndingCash, 
                             BigDecimal scenarioEndingCash, BigDecimal baselineRunwayMonths, 
                             BigDecimal scenarioRunwayMonths, BigDecimal cashImpact, 
                             BigDecimal runwayImpactMonths, String riskStatus, 
                             List<String> assumptions, boolean estimate) {
        this.requestedAmount = requestedAmount;
        this.category = category;
        this.baselineEndingCash = baselineEndingCash;
        this.scenarioEndingCash = scenarioEndingCash;
        this.baselineRunwayMonths = baselineRunwayMonths;
        this.scenarioRunwayMonths = scenarioRunwayMonths;
        this.cashImpact = cashImpact;
        this.runwayImpactMonths = runwayImpactMonths;
        this.riskStatus = riskStatus;
        this.assumptions = assumptions;
        this.estimate = estimate;
    }

    public BigDecimal getRequestedAmount() { return requestedAmount; }
    public void setRequestedAmount(BigDecimal requestedAmount) { this.requestedAmount = requestedAmount; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public BigDecimal getBaselineEndingCash() { return baselineEndingCash; }
    public void setBaselineEndingCash(BigDecimal baselineEndingCash) { this.baselineEndingCash = baselineEndingCash; }

    public BigDecimal getScenarioEndingCash() { return scenarioEndingCash; }
    public void setScenarioEndingCash(BigDecimal scenarioEndingCash) { this.scenarioEndingCash = scenarioEndingCash; }

    public BigDecimal getBaselineRunwayMonths() { return baselineRunwayMonths; }
    public void setBaselineRunwayMonths(BigDecimal baselineRunwayMonths) { this.baselineRunwayMonths = baselineRunwayMonths; }

    public BigDecimal getScenarioRunwayMonths() { return scenarioRunwayMonths; }
    public void setScenarioRunwayMonths(BigDecimal scenarioRunwayMonths) { this.scenarioRunwayMonths = scenarioRunwayMonths; }

    public BigDecimal getCashImpact() { return cashImpact; }
    public void setCashImpact(BigDecimal cashImpact) { this.cashImpact = cashImpact; }

    public BigDecimal getRunwayImpactMonths() { return runwayImpactMonths; }
    public void setRunwayImpactMonths(BigDecimal runwayImpactMonths) { this.runwayImpactMonths = runwayImpactMonths; }

    public String getRiskStatus() { return riskStatus; }
    public void setRiskStatus(String riskStatus) { this.riskStatus = riskStatus; }

    public List<String> getAssumptions() { return assumptions; }
    public void setAssumptions(List<String> assumptions) { this.assumptions = assumptions; }

    public boolean isEstimate() { return estimate; }
    public void setEstimate(boolean estimate) { this.estimate = estimate; }
}
