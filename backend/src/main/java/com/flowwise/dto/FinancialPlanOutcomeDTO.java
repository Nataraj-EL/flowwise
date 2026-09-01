package com.flowwise.dto;

import java.math.BigDecimal;

public class FinancialPlanOutcomeDTO {
    private Long id;
    private Long merchantId;
    private Long planId;
    private String horizon;
    private String outcomeStatus;
    private BigDecimal expectedScore;
    private BigDecimal actualScore;
    private BigDecimal scoreVariancePct;
    private BigDecimal expectedCashImpact;
    private BigDecimal actualCashImpact;
    private BigDecimal cashVariancePct;
    private BigDecimal riskReductionExpected;
    private BigDecimal riskReductionActual;
    private BigDecimal goalProgressExpected;
    private BigDecimal goalProgressActual;
    private BigDecimal effectivenessScore;
    private String confidenceStatus;
    private String evidenceMetrics;
    private String assumptions;
    private String evaluatedAt;

    public FinancialPlanOutcomeDTO() {}

    public FinancialPlanOutcomeDTO(Long id, Long merchantId, Long planId, String horizon, String outcomeStatus,
                                  BigDecimal expectedScore, BigDecimal actualScore, BigDecimal scoreVariancePct,
                                  BigDecimal expectedCashImpact, BigDecimal actualCashImpact, BigDecimal cashVariancePct,
                                  BigDecimal riskReductionExpected, BigDecimal riskReductionActual,
                                  BigDecimal goalProgressExpected, BigDecimal goalProgressActual,
                                  BigDecimal effectivenessScore, String confidenceStatus, String evidenceMetrics,
                                  String assumptions, String evaluatedAt) {
        this.id = id;
        this.merchantId = merchantId;
        this.planId = planId;
        this.horizon = horizon;
        this.outcomeStatus = outcomeStatus;
        this.expectedScore = expectedScore;
        this.actualScore = actualScore;
        this.scoreVariancePct = scoreVariancePct;
        this.expectedCashImpact = expectedCashImpact;
        this.actualCashImpact = actualCashImpact;
        this.cashVariancePct = cashVariancePct;
        this.riskReductionExpected = riskReductionExpected;
        this.riskReductionActual = riskReductionActual;
        this.goalProgressExpected = goalProgressExpected;
        this.goalProgressActual = goalProgressActual;
        this.effectivenessScore = effectivenessScore;
        this.confidenceStatus = confidenceStatus;
        this.evidenceMetrics = evidenceMetrics;
        this.assumptions = assumptions;
        this.evaluatedAt = evaluatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public Long getPlanId() { return planId; }
    public void setPlanId(Long planId) { this.planId = planId; }

    public String getHorizon() { return horizon; }
    public void setHorizon(String horizon) { this.horizon = horizon; }

    public String getOutcomeStatus() { return outcomeStatus; }
    public void setOutcomeStatus(String outcomeStatus) { this.outcomeStatus = outcomeStatus; }

    public BigDecimal getExpectedScore() { return expectedScore; }
    public void setExpectedScore(BigDecimal expectedScore) { this.expectedScore = expectedScore; }

    public BigDecimal getActualScore() { return actualScore; }
    public void setActualScore(BigDecimal actualScore) { this.actualScore = actualScore; }

    public BigDecimal getScoreVariancePct() { return scoreVariancePct; }
    public void setScoreVariancePct(BigDecimal scoreVariancePct) { this.scoreVariancePct = scoreVariancePct; }

    public BigDecimal getExpectedCashImpact() { return expectedCashImpact; }
    public void setExpectedCashImpact(BigDecimal expectedCashImpact) { this.expectedCashImpact = expectedCashImpact; }

    public BigDecimal getActualCashImpact() { return actualCashImpact; }
    public void setActualCashImpact(BigDecimal actualCashImpact) { this.actualCashImpact = actualCashImpact; }

    public BigDecimal getCashVariancePct() { return cashVariancePct; }
    public void setCashVariancePct(BigDecimal cashVariancePct) { this.cashVariancePct = cashVariancePct; }

    public BigDecimal getRiskReductionExpected() { return riskReductionExpected; }
    public void setRiskReductionExpected(BigDecimal riskReductionExpected) { this.riskReductionExpected = riskReductionExpected; }

    public BigDecimal getRiskReductionActual() { return riskReductionActual; }
    public void setRiskReductionActual(BigDecimal riskReductionActual) { this.riskReductionActual = riskReductionActual; }

    public BigDecimal getGoalProgressExpected() { return goalProgressExpected; }
    public void setGoalProgressExpected(BigDecimal goalProgressExpected) { this.goalProgressExpected = goalProgressExpected; }

    public BigDecimal getGoalProgressActual() { return goalProgressActual; }
    public void setGoalProgressActual(BigDecimal goalProgressActual) { this.goalProgressActual = goalProgressActual; }

    public BigDecimal getEffectivenessScore() { return effectivenessScore; }
    public void setEffectivenessScore(BigDecimal effectivenessScore) { this.effectivenessScore = effectivenessScore; }

    public String getConfidenceStatus() { return confidenceStatus; }
    public void setConfidenceStatus(String confidenceStatus) { this.confidenceStatus = confidenceStatus; }

    public String getEvidenceMetrics() { return evidenceMetrics; }
    public void setEvidenceMetrics(String evidenceMetrics) { this.evidenceMetrics = evidenceMetrics; }

    public String getAssumptions() { return assumptions; }
    public void setAssumptions(String assumptions) { this.assumptions = assumptions; }

    public String getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(String evaluatedAt) { this.evaluatedAt = evaluatedAt; }
}
