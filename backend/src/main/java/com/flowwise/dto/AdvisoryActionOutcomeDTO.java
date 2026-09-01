package com.flowwise.dto;

import java.math.BigDecimal;

public class AdvisoryActionOutcomeDTO {
    private Long id;
    private Long merchantId;
    private Long planId;
    private Long stepId;
    private String evaluationWindow;
    private String outcomeStatus;
    private BigDecimal expectedScore;
    private BigDecimal actualScore;
    private BigDecimal scoreVariancePct;
    private String expectedOutcome;
    private String actualOutcome;
    private BigDecimal riskReductionExpected;
    private BigDecimal riskReductionActual;
    private BigDecimal financialImpactExpected;
    private BigDecimal financialImpactActual;
    private BigDecimal effectivenessScore;
    private String confidenceStatus;
    private String evidenceMetrics;
    private String assumptions;
    private String evaluatedAt;

    public AdvisoryActionOutcomeDTO() {}

    public AdvisoryActionOutcomeDTO(Long id, Long merchantId, Long planId, Long stepId, String evaluationWindow,
                                    String outcomeStatus, BigDecimal expectedScore, BigDecimal actualScore,
                                    BigDecimal scoreVariancePct, String expectedOutcome, String actualOutcome,
                                    BigDecimal riskReductionExpected, BigDecimal riskReductionActual,
                                    BigDecimal financialImpactExpected, BigDecimal financialImpactActual,
                                    BigDecimal effectivenessScore, String confidenceStatus, String evidenceMetrics,
                                    String assumptions, String evaluatedAt) {
        this.id = id;
        this.merchantId = merchantId;
        this.planId = planId;
        this.stepId = stepId;
        this.evaluationWindow = evaluationWindow;
        this.outcomeStatus = outcomeStatus;
        this.expectedScore = expectedScore;
        this.actualScore = actualScore;
        this.scoreVariancePct = scoreVariancePct;
        this.expectedOutcome = expectedOutcome;
        this.actualOutcome = actualOutcome;
        this.riskReductionExpected = riskReductionExpected;
        this.riskReductionActual = riskReductionActual;
        this.financialImpactExpected = financialImpactExpected;
        this.financialImpactActual = financialImpactActual;
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

    public Long getStepId() { return stepId; }
    public void setStepId(Long stepId) { this.stepId = stepId; }

    public String getEvaluationWindow() { return evaluationWindow; }
    public void setEvaluationWindow(String evaluationWindow) { this.evaluationWindow = evaluationWindow; }

    public String getOutcomeStatus() { return outcomeStatus; }
    public void setOutcomeStatus(String outcomeStatus) { this.outcomeStatus = outcomeStatus; }

    public BigDecimal getExpectedScore() { return expectedScore; }
    public void setExpectedScore(BigDecimal expectedScore) { this.expectedScore = expectedScore; }

    public BigDecimal getActualScore() { return actualScore; }
    public void setActualScore(BigDecimal actualScore) { this.actualScore = actualScore; }

    public BigDecimal getScoreVariancePct() { return scoreVariancePct; }
    public void setScoreVariancePct(BigDecimal scoreVariancePct) { this.scoreVariancePct = scoreVariancePct; }

    public String getExpectedOutcome() { return expectedOutcome; }
    public void setExpectedOutcome(String expectedOutcome) { this.expectedOutcome = expectedOutcome; }

    public String getActualOutcome() { return actualOutcome; }
    public void setActualOutcome(String actualOutcome) { this.actualOutcome = actualOutcome; }

    public BigDecimal getRiskReductionExpected() { return riskReductionExpected; }
    public void setRiskReductionExpected(BigDecimal riskReductionExpected) { this.riskReductionExpected = riskReductionExpected; }

    public BigDecimal getRiskReductionActual() { return riskReductionActual; }
    public void setRiskReductionActual(BigDecimal riskReductionActual) { this.riskReductionActual = riskReductionActual; }

    public BigDecimal getFinancialImpactExpected() { return financialImpactExpected; }
    public void setFinancialImpactExpected(BigDecimal financialImpactExpected) { this.financialImpactExpected = financialImpactExpected; }

    public BigDecimal getFinancialImpactActual() { return financialImpactActual; }
    public void setFinancialImpactActual(BigDecimal financialImpactActual) { this.financialImpactActual = financialImpactActual; }

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
