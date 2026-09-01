package com.flowwise.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "financial_decision_portfolios", uniqueConstraints = {
    @UniqueConstraint(name = "uk_decision_portfolio_merchant_horizon", columnNames = {"merchant_id", "horizon", "portfolio_key"})
})
public class FinancialDecisionPortfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(name = "portfolio_key", nullable = false, length = 128)
    private String portfolioKey;

    @Column(name = "horizon", nullable = false, length = 16)
    private String horizon = "30D"; // 7D, 30D, 60D, 90D

    @Column(name = "status", nullable = false, length = 32)
    private String status = "ACTIVE"; // DRAFT, ACTIVE, COMPLETED, ARCHIVED

    @Column(name = "overall_portfolio_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal overallPortfolioScore = BigDecimal.ZERO;

    @Column(name = "risk_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal riskScore = BigDecimal.ZERO;

    @Column(name = "impact_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal impactScore = BigDecimal.ZERO;

    @Column(name = "urgency_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal urgencyScore = BigDecimal.ZERO;

    @Column(name = "confidence_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal confidenceScore = BigDecimal.ZERO;

    @Column(name = "primary_focus_area", nullable = false, length = 255)
    private String primaryFocusArea;

    @Column(name = "expected_benefit", nullable = false, columnDefinition = "TEXT")
    private String expectedBenefit;

    @Column(name = "risk_if_ignored", nullable = false, columnDefinition = "TEXT")
    private String riskIfIgnored;

    @Column(name = "evidence_metrics", nullable = false, columnDefinition = "TEXT")
    private String evidenceMetrics;

    @Column(name = "assumptions", nullable = false, columnDefinition = "TEXT")
    private String assumptions;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FinancialDecisionPortfolioItem> items = new ArrayList<>();

    @Column(name = "evaluated_at", nullable = false)
    private Instant evaluatedAt = Instant.now();

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    public FinancialDecisionPortfolio() {}

    public FinancialDecisionPortfolio(Merchant merchant, String portfolioKey, String horizon, String status,
                                      BigDecimal overallPortfolioScore, BigDecimal riskScore, BigDecimal impactScore,
                                      BigDecimal urgencyScore, BigDecimal confidenceScore, String primaryFocusArea,
                                      String expectedBenefit, String riskIfIgnored, String evidenceMetrics, String assumptions) {
        this.merchant = merchant;
        this.portfolioKey = portfolioKey;
        this.horizon = horizon != null ? horizon : "30D";
        this.status = status != null ? status : "ACTIVE";
        this.overallPortfolioScore = overallPortfolioScore != null ? overallPortfolioScore : BigDecimal.ZERO;
        this.riskScore = riskScore != null ? riskScore : BigDecimal.ZERO;
        this.impactScore = impactScore != null ? impactScore : BigDecimal.ZERO;
        this.urgencyScore = urgencyScore != null ? urgencyScore : BigDecimal.ZERO;
        this.confidenceScore = confidenceScore != null ? confidenceScore : BigDecimal.ZERO;
        this.primaryFocusArea = primaryFocusArea;
        this.expectedBenefit = expectedBenefit;
        this.riskIfIgnored = riskIfIgnored;
        this.evidenceMetrics = evidenceMetrics;
        this.assumptions = assumptions;
        this.evaluatedAt = Instant.now();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Merchant getMerchant() { return merchant; }
    public void setMerchant(Merchant merchant) { this.merchant = merchant; }

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

    public List<FinancialDecisionPortfolioItem> getItems() { return items; }
    public void setItems(List<FinancialDecisionPortfolioItem> items) { this.items = items; }

    public Instant getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(Instant evaluatedAt) { this.evaluatedAt = evaluatedAt; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
