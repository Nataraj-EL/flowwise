package com.flowwise.dto;

import java.math.BigDecimal;

public class FinancialDecisionPortfolioItemDTO {
    private Long id;
    private Long portfolioId;
    private String itemKey;
    private String decisionType;
    private String title;
    private String description;
    private BigDecimal priorityScore;
    private BigDecimal riskProtectionScore;
    private BigDecimal financialImpactScore;
    private BigDecimal urgencyScore;
    private BigDecimal historicalEffectivenessScore;
    private BigDecimal goalAlignmentScore;
    private String confidenceStatus;
    private String expectedBenefit;
    private String riskIfIgnored;
    private Integer rankOrder;
    private String evidenceMetrics;

    public FinancialDecisionPortfolioItemDTO() {}

    public FinancialDecisionPortfolioItemDTO(Long id, Long portfolioId, String itemKey, String decisionType,
                                             String title, String description, BigDecimal priorityScore,
                                             BigDecimal riskProtectionScore, BigDecimal financialImpactScore,
                                             BigDecimal urgencyScore, BigDecimal historicalEffectivenessScore,
                                             BigDecimal goalAlignmentScore, String confidenceStatus,
                                             String expectedBenefit, String riskIfIgnored, Integer rankOrder,
                                             String evidenceMetrics) {
        this.id = id;
        this.portfolioId = portfolioId;
        this.itemKey = itemKey;
        this.decisionType = decisionType;
        this.title = title;
        this.description = description;
        this.priorityScore = priorityScore;
        this.riskProtectionScore = riskProtectionScore;
        this.financialImpactScore = financialImpactScore;
        this.urgencyScore = urgencyScore;
        this.historicalEffectivenessScore = historicalEffectivenessScore;
        this.goalAlignmentScore = goalAlignmentScore;
        this.confidenceStatus = confidenceStatus;
        this.expectedBenefit = expectedBenefit;
        this.riskIfIgnored = riskIfIgnored;
        this.rankOrder = rankOrder;
        this.evidenceMetrics = evidenceMetrics;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPortfolioId() { return portfolioId; }
    public void setPortfolioId(Long portfolioId) { this.portfolioId = portfolioId; }

    public String getItemKey() { return itemKey; }
    public void setItemKey(String itemKey) { this.itemKey = itemKey; }

    public String getDecisionType() { return decisionType; }
    public void setDecisionType(String decisionType) { this.decisionType = decisionType; }

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

    public BigDecimal getHistoricalEffectivenessScore() { return historicalEffectivenessScore; }
    public void setHistoricalEffectivenessScore(BigDecimal historicalEffectivenessScore) { this.historicalEffectivenessScore = historicalEffectivenessScore; }

    public BigDecimal getGoalAlignmentScore() { return goalAlignmentScore; }
    public void setGoalAlignmentScore(BigDecimal goalAlignmentScore) { this.goalAlignmentScore = goalAlignmentScore; }

    public String getConfidenceStatus() { return confidenceStatus; }
    public void setConfidenceStatus(String confidenceStatus) { this.confidenceStatus = confidenceStatus; }

    public String getExpectedBenefit() { return expectedBenefit; }
    public void setExpectedBenefit(String expectedBenefit) { this.expectedBenefit = expectedBenefit; }

    public String getRiskIfIgnored() { return riskIfIgnored; }
    public void setRiskIfIgnored(String riskIfIgnored) { this.riskIfIgnored = riskIfIgnored; }

    public Integer getRankOrder() { return rankOrder; }
    public void setRankOrder(Integer rankOrder) { this.rankOrder = rankOrder; }

    public String getEvidenceMetrics() { return evidenceMetrics; }
    public void setEvidenceMetrics(String evidenceMetrics) { this.evidenceMetrics = evidenceMetrics; }
}
