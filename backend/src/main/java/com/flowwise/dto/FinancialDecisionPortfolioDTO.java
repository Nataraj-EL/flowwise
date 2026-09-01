package com.flowwise.dto;

import java.math.BigDecimal;
import java.util.List;

public class FinancialDecisionPortfolioDTO {
    private Long id;
    private Long merchantId;
    private String portfolioKey;
    private String horizon;
    private String status;
    private BigDecimal overallPortfolioScore;
    private BigDecimal riskScore;
    private BigDecimal impactScore;
    private BigDecimal urgencyScore;
    private BigDecimal confidenceScore;
    private String primaryFocusArea;
    private String expectedBenefit;
    private String riskIfIgnored;
    private String evidenceMetrics;
    private String assumptions;
    private List<FinancialDecisionPortfolioItemDTO> items;
    private String evaluatedAt;

    public FinancialDecisionPortfolioDTO() {}

    public FinancialDecisionPortfolioDTO(Long id, Long merchantId, String portfolioKey, String horizon,
                                        String status, BigDecimal overallPortfolioScore, BigDecimal riskScore,
                                        BigDecimal impactScore, BigDecimal urgencyScore, BigDecimal confidenceScore,
                                        String primaryFocusArea, String expectedBenefit, String riskIfIgnored,
                                        String evidenceMetrics, String assumptions,
                                        List<FinancialDecisionPortfolioItemDTO> items, String evaluatedAt) {
        this.id = id;
        this.merchantId = merchantId;
        this.portfolioKey = portfolioKey;
        this.horizon = horizon;
        this.status = status;
        this.overallPortfolioScore = overallPortfolioScore;
        this.riskScore = riskScore;
        this.impactScore = impactScore;
        this.urgencyScore = urgencyScore;
        this.confidenceScore = confidenceScore;
        this.primaryFocusArea = primaryFocusArea;
        this.expectedBenefit = expectedBenefit;
        this.riskIfIgnored = riskIfIgnored;
        this.evidenceMetrics = evidenceMetrics;
        this.assumptions = assumptions;
        this.items = items;
        this.evaluatedAt = evaluatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public String getPortfolioKey() { return portfolioKey; }
    public void setPortfolioKey(String portfolioKey) { this.portfolioKey = portfolioKey; }

    public String getHorizon() { return horizon; }
    public void setHorizon(String horizon) { this.horizon = horizon; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getOverallPortfolioScore() { return overallPortfolioScore; }
    public void setOverallPortfolioScore(BigDecimal overallPortfolioScore) { this.overallPortfolioScore = overallPortfolioScore; }

    public BigDecimal getRiskScore() { return riskScore; }
    public void setRiskScore(BigDecimal riskScore) { this.riskScore = riskScore; }

    public BigDecimal getImpactScore() { return impactScore; }
    public void setImpactScore(BigDecimal impactScore) { this.impactScore = impactScore; }

    public BigDecimal getUrgencyScore() { return urgencyScore; }
    public void setUrgencyScore(BigDecimal urgencyScore) { this.urgencyScore = urgencyScore; }

    public BigDecimal getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(BigDecimal confidenceScore) { this.confidenceScore = confidenceScore; }

    public String getPrimaryFocusArea() { return primaryFocusArea; }
    public void setPrimaryFocusArea(String primaryFocusArea) { this.primaryFocusArea = primaryFocusArea; }

    public String getExpectedBenefit() { return expectedBenefit; }
    public void setExpectedBenefit(String expectedBenefit) { this.expectedBenefit = expectedBenefit; }

    public String getRiskIfIgnored() { return riskIfIgnored; }
    public void setRiskIfIgnored(String riskIfIgnored) { this.riskIfIgnored = riskIfIgnored; }

    public String getEvidenceMetrics() { return evidenceMetrics; }
    public void setEvidenceMetrics(String evidenceMetrics) { this.evidenceMetrics = evidenceMetrics; }

    public String getAssumptions() { return assumptions; }
    public void setAssumptions(String assumptions) { this.assumptions = assumptions; }

    public List<FinancialDecisionPortfolioItemDTO> getItems() { return items; }
    public void setItems(List<FinancialDecisionPortfolioItemDTO> items) { this.items = items; }

    public String getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(String evaluatedAt) { this.evaluatedAt = evaluatedAt; }
}
