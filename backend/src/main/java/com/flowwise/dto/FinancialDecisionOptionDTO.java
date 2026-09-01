package com.flowwise.dto;

import java.math.BigDecimal;

public class FinancialDecisionOptionDTO {
    private Long id;
    private Long decisionId;
    private String optionKey;
    private String optionType;
    private Long sourceId;
    private BigDecimal optionScore;
    private BigDecimal riskScore;
    private BigDecimal impactScore;
    private BigDecimal urgencyScore;
    private String confidenceStatus;
    private String expectedBenefit;
    private String riskIfIgnored;
    private Integer rankOrder;
    private String evidenceMetrics;

    public FinancialDecisionOptionDTO() {}

    public FinancialDecisionOptionDTO(Long id, Long decisionId, String optionKey, String optionType, Long sourceId,
                                      BigDecimal optionScore, BigDecimal riskScore, BigDecimal impactScore,
                                      BigDecimal urgencyScore, String confidenceStatus, String expectedBenefit,
                                      String riskIfIgnored, Integer rankOrder, String evidenceMetrics) {
        this.id = id;
        this.decisionId = decisionId;
        this.optionKey = optionKey;
        this.optionType = optionType;
        this.sourceId = sourceId;
        this.optionScore = optionScore;
        this.riskScore = riskScore;
        this.impactScore = impactScore;
        this.urgencyScore = urgencyScore;
        this.confidenceStatus = confidenceStatus;
        this.expectedBenefit = expectedBenefit;
        this.riskIfIgnored = riskIfIgnored;
        this.rankOrder = rankOrder;
        this.evidenceMetrics = evidenceMetrics;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getDecisionId() { return decisionId; }
    public void setDecisionId(Long decisionId) { this.decisionId = decisionId; }

    public String getOptionKey() { return optionKey; }
    public void setOptionKey(String optionKey) { this.optionKey = optionKey; }

    public String getOptionType() { return optionType; }
    public void setOptionType(String optionType) { this.optionType = optionType; }

    public Long getSourceId() { return sourceId; }
    public void setSourceId(Long sourceId) { this.sourceId = sourceId; }

    public BigDecimal getOptionScore() { return optionScore; }
    public void setOptionScore(BigDecimal optionScore) { this.optionScore = optionScore; }

    public BigDecimal getRiskScore() { return riskScore; }
    public void setRiskScore(BigDecimal riskScore) { this.riskScore = riskScore; }

    public BigDecimal getImpactScore() { return impactScore; }
    public void setImpactScore(BigDecimal impactScore) { this.impactScore = impactScore; }

    public BigDecimal getUrgencyScore() { return urgencyScore; }
    public void setUrgencyScore(BigDecimal urgencyScore) { this.urgencyScore = urgencyScore; }

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
