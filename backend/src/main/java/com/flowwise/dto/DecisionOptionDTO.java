package com.flowwise.dto;

import java.math.BigDecimal;

public class DecisionOptionDTO {
    private Long id;
    private String optionKey; // PAY_NOW, DEFER, COLLECT_RECEIVABLES, REDUCE_EXPENSE, BUILD_RESERVE
    private String title;
    private String description;
    private BigDecimal compositeScore; // 0 - 100
    private BigDecimal liquidityScore; // 25% weight
    private BigDecimal coverageScore; // 20% weight
    private BigDecimal goalScore; // 25% weight
    private BigDecimal riskScore; // 15% weight
    private BigDecimal urgencyScore; // 15% weight
    private BigDecimal projected7dCash;
    private BigDecimal projected30dCash;
    private BigDecimal projected90dCash;
    private String riskStatus; // FEASIBLE, CAUTION, HIGH_RISK
    private String goalImpactStatus; // POSITIVE, NEUTRAL, NEGATIVE
    private String assumptions;
    private String evidenceMetrics;
    private Integer rankOrder;
    private boolean estimate = true;

    public DecisionOptionDTO() {}

    public DecisionOptionDTO(Long id, String optionKey, String title, String description, 
                             BigDecimal compositeScore, BigDecimal liquidityScore, 
                             BigDecimal coverageScore, BigDecimal goalScore, 
                             BigDecimal riskScore, BigDecimal urgencyScore, 
                             BigDecimal projected7dCash, BigDecimal projected30dCash, 
                             BigDecimal projected90dCash, String riskStatus, 
                             String goalImpactStatus, String assumptions, 
                             String evidenceMetrics, Integer rankOrder, boolean estimate) {
        this.id = id;
        this.optionKey = optionKey;
        this.title = title;
        this.description = description;
        this.compositeScore = compositeScore;
        this.liquidityScore = liquidityScore;
        this.coverageScore = coverageScore;
        this.goalScore = goalScore;
        this.riskScore = riskScore;
        this.urgencyScore = urgencyScore;
        this.projected7dCash = projected7dCash;
        this.projected30dCash = projected30dCash;
        this.projected90dCash = projected90dCash;
        this.riskStatus = riskStatus;
        this.goalImpactStatus = goalImpactStatus;
        this.assumptions = assumptions;
        this.evidenceMetrics = evidenceMetrics;
        this.rankOrder = rankOrder;
        this.estimate = estimate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getOptionKey() { return optionKey; }
    public void setOptionKey(String optionKey) { this.optionKey = optionKey; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getCompositeScore() { return compositeScore; }
    public void setCompositeScore(BigDecimal compositeScore) { this.compositeScore = compositeScore; }

    public BigDecimal getLiquidityScore() { return liquidityScore; }
    public void setLiquidityScore(BigDecimal liquidityScore) { this.liquidityScore = liquidityScore; }

    public BigDecimal getCoverageScore() { return coverageScore; }
    public void setCoverageScore(BigDecimal coverageScore) { this.coverageScore = coverageScore; }

    public BigDecimal getGoalScore() { return goalScore; }
    public void setGoalScore(BigDecimal goalScore) { this.goalScore = goalScore; }

    public BigDecimal getRiskScore() { return riskScore; }
    public void setRiskScore(BigDecimal riskScore) { this.riskScore = riskScore; }

    public BigDecimal getUrgencyScore() { return urgencyScore; }
    public void setUrgencyScore(BigDecimal urgencyScore) { this.urgencyScore = urgencyScore; }

    public BigDecimal getProjected7dCash() { return projected7dCash; }
    public void setProjected7dCash(BigDecimal projected7dCash) { this.projected7dCash = projected7dCash; }

    public BigDecimal getProjected30dCash() { return projected30dCash; }
    public void setProjected30dCash(BigDecimal projected30dCash) { this.projected30dCash = projected30dCash; }

    public BigDecimal getProjected90dCash() { return projected90dCash; }
    public void setProjected90dCash(BigDecimal projected90dCash) { this.projected90dCash = projected90dCash; }

    public String getRiskStatus() { return riskStatus; }
    public void setRiskStatus(String riskStatus) { this.riskStatus = riskStatus; }

    public String getGoalImpactStatus() { return goalImpactStatus; }
    public void setGoalImpactStatus(String goalImpactStatus) { this.goalImpactStatus = goalImpactStatus; }

    public String getAssumptions() { return assumptions; }
    public void setAssumptions(String assumptions) { this.assumptions = assumptions; }

    public String getEvidenceMetrics() { return evidenceMetrics; }
    public void setEvidenceMetrics(String evidenceMetrics) { this.evidenceMetrics = evidenceMetrics; }

    public Integer getRankOrder() { return rankOrder; }
    public void setRankOrder(Integer rankOrder) { this.rankOrder = rankOrder; }

    public boolean isEstimate() { return estimate; }
    public void setEstimate(boolean estimate) { this.estimate = estimate; }
}
