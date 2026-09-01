package com.flowwise.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "financial_decision_options")
public class FinancialDecisionOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analysis_id", nullable = false)
    private FinancialDecisionAnalysis analysis;

    @Column(name = "option_key", nullable = false, length = 64)
    private String optionKey; // PAY_NOW, DEFER, COLLECT_RECEIVABLES, REDUCE_EXPENSE, BUILD_RESERVE

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "composite_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal compositeScore = BigDecimal.ZERO;

    @Column(name = "liquidity_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal liquidityScore = BigDecimal.ZERO;

    @Column(name = "coverage_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal coverageScore = BigDecimal.ZERO;

    @Column(name = "goal_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal goalScore = BigDecimal.ZERO;

    @Column(name = "risk_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal riskScore = BigDecimal.ZERO;

    @Column(name = "urgency_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal urgencyScore = BigDecimal.ZERO;

    @Column(name = "projected_7d_cash", nullable = false, precision = 15, scale = 2)
    private BigDecimal projected7dCash = BigDecimal.ZERO;

    @Column(name = "projected_30d_cash", nullable = false, precision = 15, scale = 2)
    private BigDecimal projected30dCash = BigDecimal.ZERO;

    @Column(name = "projected_90d_cash", nullable = false, precision = 15, scale = 2)
    private BigDecimal projected90dCash = BigDecimal.ZERO;

    @Column(name = "risk_status", nullable = false, length = 32)
    private String riskStatus = "FEASIBLE"; // FEASIBLE, CAUTION, HIGH_RISK

    @Column(name = "goal_impact_status", nullable = false, length = 32)
    private String goalImpactStatus = "NEUTRAL"; // POSITIVE, NEUTRAL, NEGATIVE

    @Column(name = "assumptions", columnDefinition = "TEXT")
    private String assumptions;

    @Column(name = "evidence_metrics", columnDefinition = "TEXT")
    private String evidenceMetrics;

    @Column(name = "rank_order", nullable = false)
    private Integer rankOrder = 1;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    public FinancialDecisionOption() {}

    public FinancialDecisionOption(FinancialDecisionAnalysis analysis, String optionKey, String title,
                                   String description, BigDecimal compositeScore, BigDecimal liquidityScore,
                                   BigDecimal coverageScore, BigDecimal goalScore, BigDecimal riskScore,
                                   BigDecimal urgencyScore, BigDecimal projected7dCash,
                                   BigDecimal projected30dCash, BigDecimal projected90dCash,
                                   String riskStatus, String goalImpactStatus, String assumptions,
                                   String evidenceMetrics, Integer rankOrder) {
        this.analysis = analysis;
        this.optionKey = optionKey;
        this.title = title;
        this.description = description;
        this.compositeScore = compositeScore != null ? compositeScore : BigDecimal.ZERO;
        this.liquidityScore = liquidityScore != null ? liquidityScore : BigDecimal.ZERO;
        this.coverageScore = coverageScore != null ? coverageScore : BigDecimal.ZERO;
        this.goalScore = goalScore != null ? goalScore : BigDecimal.ZERO;
        this.riskScore = riskScore != null ? riskScore : BigDecimal.ZERO;
        this.urgencyScore = urgencyScore != null ? urgencyScore : BigDecimal.ZERO;
        this.projected7dCash = projected7dCash != null ? projected7dCash : BigDecimal.ZERO;
        this.projected30dCash = projected30dCash != null ? projected30dCash : BigDecimal.ZERO;
        this.projected90dCash = projected90dCash != null ? projected90dCash : BigDecimal.ZERO;
        this.riskStatus = riskStatus != null ? riskStatus : "FEASIBLE";
        this.goalImpactStatus = goalImpactStatus != null ? goalImpactStatus : "NEUTRAL";
        this.assumptions = assumptions;
        this.evidenceMetrics = evidenceMetrics;
        this.rankOrder = rankOrder != null ? rankOrder : 1;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public FinancialDecisionAnalysis getAnalysis() { return analysis; }
    public void setAnalysis(FinancialDecisionAnalysis analysis) { this.analysis = analysis; }

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

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
