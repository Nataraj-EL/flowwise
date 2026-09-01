package com.flowwise.dto;

import java.math.BigDecimal;

public class InterventionOutcomeDTO {
    private Long id;
    private Long merchantId;
    private Long interventionId;
    private String interventionType;
    private String outcomeStatus;
    private String evaluationWindow;
    private String expectedBenefit;
    private String actualBenefit;
    private BigDecimal benefitVariancePct;
    private BigDecimal expectedCashImpact;
    private BigDecimal actualCashImpact;
    private BigDecimal cashImpactVariancePct;
    private BigDecimal expectedRiskReduction;
    private BigDecimal actualRiskReduction;
    private BigDecimal goalImpactVariancePct;
    private BigDecimal effectivenessScore;
    private String confidenceStatus;
    private String evidenceMetrics;
    private String assumptions;
    private String evaluatedAt;

    public InterventionOutcomeDTO() {}

    public InterventionOutcomeDTO(Long id, Long merchantId, Long interventionId, String interventionType,
                                  String outcomeStatus, String evaluationWindow, String expectedBenefit,
                                  String actualBenefit, BigDecimal benefitVariancePct, BigDecimal expectedCashImpact,
                                  BigDecimal actualCashImpact, BigDecimal cashImpactVariancePct,
                                  BigDecimal expectedRiskReduction, BigDecimal actualRiskReduction,
                                  BigDecimal goalImpactVariancePct, BigDecimal effectivenessScore,
                                  String confidenceStatus, String evidenceMetrics, String assumptions,
                                  String evaluatedAt) {
        this.id = id;
        this.merchantId = merchantId;
        this.interventionId = interventionId;
        this.interventionType = interventionType;
        this.outcomeStatus = outcomeStatus;
        this.evaluationWindow = evaluationWindow;
        this.expectedBenefit = expectedBenefit;
        this.actualBenefit = actualBenefit;
        this.benefitVariancePct = benefitVariancePct;
        this.expectedCashImpact = expectedCashImpact;
        this.actualCashImpact = actualCashImpact;
        this.cashImpactVariancePct = cashImpactVariancePct;
        this.expectedRiskReduction = expectedRiskReduction;
        this.actualRiskReduction = actualRiskReduction;
        this.goalImpactVariancePct = goalImpactVariancePct;
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

    public Long getInterventionId() { return interventionId; }
    public void setInterventionId(Long interventionId) { this.interventionId = interventionId; }

    public String getInterventionType() { return interventionType; }
    public void setInterventionType(String interventionType) { this.interventionType = interventionType; }

    public String getOutcomeStatus() { return outcomeStatus; }
    public void setOutcomeStatus(String outcomeStatus) { this.outcomeStatus = outcomeStatus; }

    public String getEvaluationWindow() { return evaluationWindow; }
    public void setEvaluationWindow(String evaluationWindow) { this.evaluationWindow = evaluationWindow; }

    public String getExpectedBenefit() { return expectedBenefit; }
    public void setExpectedBenefit(String expectedBenefit) { this.expectedBenefit = expectedBenefit; }

    public String getActualBenefit() { return actualBenefit; }
    public void setActualBenefit(String actualBenefit) { this.actualBenefit = actualBenefit; }

    public BigDecimal getBenefitVariancePct() { return benefitVariancePct; }
    public void setBenefitVariancePct(BigDecimal benefitVariancePct) { this.benefitVariancePct = benefitVariancePct; }

    public BigDecimal getExpectedCashImpact() { return expectedCashImpact; }
    public void setExpectedCashImpact(BigDecimal expectedCashImpact) { this.expectedCashImpact = expectedCashImpact; }

    public BigDecimal getActualCashImpact() { return actualCashImpact; }
    public void setActualCashImpact(BigDecimal actualCashImpact) { this.actualCashImpact = actualCashImpact; }

    public BigDecimal getCashImpactVariancePct() { return cashImpactVariancePct; }
    public void setCashImpactVariancePct(BigDecimal cashImpactVariancePct) { this.cashImpactVariancePct = cashImpactVariancePct; }

    public BigDecimal getExpectedRiskReduction() { return expectedRiskReduction; }
    public void setExpectedRiskReduction(BigDecimal expectedRiskReduction) { this.expectedRiskReduction = expectedRiskReduction; }

    public BigDecimal getActualRiskReduction() { return actualRiskReduction; }
    public void setActualRiskReduction(BigDecimal actualRiskReduction) { this.actualRiskReduction = actualRiskReduction; }

    public BigDecimal getGoalImpactVariancePct() { return goalImpactVariancePct; }
    public void setGoalImpactVariancePct(BigDecimal goalImpactVariancePct) { this.goalImpactVariancePct = goalImpactVariancePct; }

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
