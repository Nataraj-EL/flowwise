package com.flowwise.dto;

import java.math.BigDecimal;

public class FinancialPlanItemDTO {
    private Long id;
    private Long planId;
    private String itemKey;
    private String interventionType;
    private String title;
    private String description;
    private BigDecimal priorityScore;
    private BigDecimal riskProtectionScore;
    private BigDecimal financialImpactScore;
    private BigDecimal urgencyScore;
    private BigDecimal goalAlignmentScore;
    private BigDecimal historicalEffectivenessScore;
    private String confidenceStatus;
    private String expectedBenefit;
    private String riskIfIgnored;
    private String horizon;
    private Integer rankOrder;
    private String evidenceMetrics;

    public FinancialPlanItemDTO() {}

    public FinancialPlanItemDTO(Long id, Long planId, String itemKey, String interventionType, String title,
                               String description, BigDecimal priorityScore, BigDecimal riskProtectionScore,
                               BigDecimal financialImpactScore, BigDecimal urgencyScore, BigDecimal goalAlignmentScore,
                               BigDecimal historicalEffectivenessScore, String confidenceStatus, String expectedBenefit,
                               String riskIfIgnored, String horizon, Integer rankOrder, String evidenceMetrics) {
        this.id = id;
        this.planId = planId;
        this.itemKey = itemKey;
        this.interventionType = interventionType;
        this.title = title;
        this.description = description;
        this.priorityScore = priorityScore;
        this.riskProtectionScore = riskProtectionScore;
        this.financialImpactScore = financialImpactScore;
        this.urgencyScore = urgencyScore;
        this.goalAlignmentScore = goalAlignmentScore;
        this.historicalEffectivenessScore = historicalEffectivenessScore;
        this.confidenceStatus = confidenceStatus;
        this.expectedBenefit = expectedBenefit;
        this.riskIfIgnored = riskIfIgnored;
        this.horizon = horizon;
        this.rankOrder = rankOrder;
        this.evidenceMetrics = evidenceMetrics;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPlanId() { return planId; }
    public void setPlanId(Long planId) { this.planId = planId; }

    public String getItemKey() { return itemKey; }
    public void setItemKey(String itemKey) { this.itemKey = itemKey; }

    public String getInterventionType() { return interventionType; }
    public void setInterventionType(String interventionType) { this.interventionType = interventionType; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPriorityScore() { return priorityScore; }
    public void setPriorityScore(BigDecimal priorityScore) { this.priorityScore = priorityScore; }

    public BigDecimal getRiskProtectionScore() { return riskProtectionScore; }
    public void setRiskProtectionScore(BigDecimal riskProtectionScore) { this.riskProtectionScore = riskProtectionScore; }

    public BigDecimal getFinancialImpactScore() { return financialImpactScore; }
    public void setFinancialImpactScore(BigDecimal financialImpactScore) { this.financialImpactScore = financialImpactScore; }

    public BigDecimal getUrgencyScore() { return urgencyScore; }
    public void setUrgencyScore(BigDecimal urgencyScore) { this.urgencyScore = urgencyScore; }

    public BigDecimal getGoalAlignmentScore() { return goalAlignmentScore; }
    public void setGoalAlignmentScore(BigDecimal goalAlignmentScore) { this.goalAlignmentScore = goalAlignmentScore; }

    public BigDecimal getHistoricalEffectivenessScore() { return historicalEffectivenessScore; }
    public void setHistoricalEffectivenessScore(BigDecimal historicalEffectivenessScore) { this.historicalEffectivenessScore = historicalEffectivenessScore; }

    public String getConfidenceStatus() { return confidenceStatus; }
    public void setConfidenceStatus(String confidenceStatus) { this.confidenceStatus = confidenceStatus; }

    public String getExpectedBenefit() { return expectedBenefit; }
    public void setExpectedBenefit(String expectedBenefit) { this.expectedBenefit = expectedBenefit; }

    public String getRiskIfIgnored() { return riskIfIgnored; }
    public void setRiskIfIgnored(String riskIfIgnored) { this.riskIfIgnored = riskIfIgnored; }

    public String getHorizon() { return horizon; }
    public void setHorizon(String horizon) { this.horizon = horizon; }

    public Integer getRankOrder() { return rankOrder; }
    public void setRankOrder(Integer rankOrder) { this.rankOrder = rankOrder; }

    public String getEvidenceMetrics() { return evidenceMetrics; }
    public void setEvidenceMetrics(String evidenceMetrics) { this.evidenceMetrics = evidenceMetrics; }
}
