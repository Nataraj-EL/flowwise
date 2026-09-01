package com.flowwise.dto;

import java.math.BigDecimal;

public class FinancialDecisionOutcomeDTO {
    private Long id;
    private Long merchantId;
    private Long decisionId;
    private String outcomeStatus;
    private String evaluationWindow;
    private BigDecimal expectedScore;
    private BigDecimal actualScore;
    private BigDecimal scoreVariancePct;
    private BigDecimal expectedCashImpact;
    private BigDecimal actualCashImpact;
    private BigDecimal cashVariancePct;
    private BigDecimal expectedRiskReduction;
    private BigDecimal actualRiskReduction;
    private BigDecimal expectedGoalImpact;
    private BigDecimal actualGoalImpact;
    private BigDecimal effectivenessScore;
    private String confidenceStatus;
    private String evidenceMetrics;
    private String assumptions;
    private String evaluatedAt;

    public FinancialDecisionOutcomeDTO() {}

    public FinancialDecisionOutcomeDTO(Long id, Long merchantId, Long decisionId, String outcomeStatus,
                                       String evaluationWindow, BigDecimal expectedScore, BigDecimal actualScore,
                                       BigDecimal scoreVariancePct, BigDecimal expectedCashImpact, BigDecimal actualCashImpact,
                                       BigDecimal cashVariancePct, BigDecimal expectedRiskReduction, BigDecimal actualRiskReduction,
                                       BigDecimal expectedGoalImpact, BigDecimal actualGoalImpact, BigDecimal effectivenessScore,
                                       String confidenceStatus, String evidenceMetrics, String assumptions, String evaluatedAt) {
        this.id = id;
        this.merchantId = merchantId;
        this.decisionId = decisionId;
        this.outcomeStatus = outcomeStatus;
        this.evaluationWindow = evaluationWindow;
        this.expectedScore = expectedScore;
        this.actualScore = actualScore;
        this.scoreVariancePct = scoreVariancePct;
        this.expectedCashImpact = expectedCashImpact;
        this.actualCashImpact = actualCashImpact;
        this.cashVariancePct = cashVariancePct;
        this.expectedRiskReduction = expectedRiskReduction;
        this.actualRiskReduction = actualRiskReduction;
        this.expectedGoalImpact = expectedGoalImpact;
        this.actualGoalImpact = actualGoalImpact;
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

    public Long getDecisionId() { return decisionId; }
    public void setDecisionId(Long decisionId) { this.decisionId = decisionId; }

    public String getOutcomeStatus() { return outcomeStatus; }
    public void setOutcomeStatus(String outcomeStatus) { this.outcomeStatus = outcomeStatus; }

    public String getEvaluationWindow() { return evaluationWindow; }
    public void setEvaluationWindow(String evaluationWindow) { this.evaluationWindow = evaluationWindow; }

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

    public BigDecimal getExpectedRiskReduction() { return expectedRiskReduction; }
    public void setExpectedRiskReduction(BigDecimal expectedRiskReduction) { this.expectedRiskReduction = expectedRiskReduction; }

    public BigDecimal getActualRiskReduction() { return actualRiskReduction; }
    public void setActualRiskReduction(BigDecimal actualRiskReduction) { this.actualRiskReduction = actualRiskReduction; }

    public BigDecimal getExpectedGoalImpact() { return expectedGoalImpact; }
    public void setExpectedGoalImpact(BigDecimal expectedGoalImpact) { this.expectedGoalImpact = expectedGoalImpact; }

    public BigDecimal getActualGoalImpact() { return actualGoalImpact; }
    public void setActualGoalImpact(BigDecimal actualGoalImpact) { this.actualGoalImpact = actualGoalImpact; }

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
