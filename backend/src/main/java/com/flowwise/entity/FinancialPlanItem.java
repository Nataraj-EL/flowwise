package com.flowwise.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "financial_plan_items")
public class FinancialPlanItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private FinancialPlan plan;

    @Column(name = "item_key", nullable = false, length = 128)
    private String itemKey;

    @Column(name = "intervention_type", nullable = false, length = 64)
    private String interventionType;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "priority_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal priorityScore = BigDecimal.ZERO;

    @Column(name = "risk_protection_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal riskProtectionScore = BigDecimal.ZERO;

    @Column(name = "financial_impact_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal financialImpactScore = BigDecimal.ZERO;

    @Column(name = "urgency_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal urgencyScore = BigDecimal.ZERO;

    @Column(name = "goal_alignment_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal goalAlignmentScore = BigDecimal.ZERO;

    @Column(name = "historical_effectiveness_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal historicalEffectivenessScore = BigDecimal.ZERO;

    @Column(name = "confidence_status", nullable = false, length = 32)
    private String confidenceStatus = "HIGH"; // HIGH, MODERATE, LIMITED, INSUFFICIENT_DATA

    @Column(name = "expected_benefit", nullable = false, length = 255)
    private String expectedBenefit;

    @Column(name = "risk_if_ignored", nullable = false, length = 255)
    private String riskIfIgnored;

    @Column(name = "horizon", nullable = false, length = 32)
    private String horizon = "30D";

    @Column(name = "rank_order", nullable = false)
    private Integer rankOrder = 1;

    @Column(name = "evidence_metrics", nullable = false, columnDefinition = "TEXT")
    private String evidenceMetrics;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    public FinancialPlanItem() {}

    public FinancialPlanItem(FinancialPlan plan, String itemKey, String interventionType, String title,
                             String description, BigDecimal priorityScore, BigDecimal riskProtectionScore,
                             BigDecimal financialImpactScore, BigDecimal urgencyScore, BigDecimal goalAlignmentScore,
                             BigDecimal historicalEffectivenessScore, String confidenceStatus, String expectedBenefit,
                             String riskIfIgnored, String horizon, Integer rankOrder, String evidenceMetrics) {
        this.plan = plan;
        this.itemKey = itemKey;
        this.interventionType = interventionType;
        this.title = title;
        this.description = description;
        this.priorityScore = priorityScore != null ? priorityScore : BigDecimal.ZERO;
        this.riskProtectionScore = riskProtectionScore != null ? riskProtectionScore : BigDecimal.ZERO;
        this.financialImpactScore = financialImpactScore != null ? financialImpactScore : BigDecimal.ZERO;
        this.urgencyScore = urgencyScore != null ? urgencyScore : BigDecimal.ZERO;
        this.goalAlignmentScore = goalAlignmentScore != null ? goalAlignmentScore : BigDecimal.ZERO;
        this.historicalEffectivenessScore = historicalEffectivenessScore != null ? historicalEffectivenessScore : BigDecimal.ZERO;
        this.confidenceStatus = confidenceStatus != null ? confidenceStatus : "HIGH";
        this.expectedBenefit = expectedBenefit;
        this.riskIfIgnored = riskIfIgnored;
        this.horizon = horizon != null ? horizon : "30D";
        this.rankOrder = rankOrder != null ? rankOrder : 1;
        this.evidenceMetrics = evidenceMetrics;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public FinancialPlan getPlan() { return plan; }
    public void setPlan(FinancialPlan plan) { this.plan = plan; }

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

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
