package com.flowwise.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "advisory_action_plans", uniqueConstraints = {
    @UniqueConstraint(name = "uk_advisory_action_plan_merchant_horizon", columnNames = {"merchant_id", "horizon", "plan_key"})
})
public class AdvisoryActionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(name = "plan_key", nullable = false, length = 128)
    private String planKey;

    @Column(name = "horizon", nullable = false, length = 16)
    private String horizon = "30D"; // 7D, 30D, 60D, 90D

    @Column(name = "status", nullable = false, length = 32)
    private String status = "ACTIVE"; // DRAFT, ACTIVE, COMPLETED, ARCHIVED

    @Column(name = "overall_readiness_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal overallReadinessScore = BigDecimal.ZERO;

    @Column(name = "total_steps_count", nullable = false)
    private Integer totalStepsCount = 0;

    @Column(name = "ready_steps_count", nullable = false)
    private Integer readyStepsCount = 0;

    @Column(name = "blocked_steps_count", nullable = false)
    private Integer blockedStepsCount = 0;

    @Column(name = "primary_next_action", nullable = false, length = 255)
    private String primaryNextAction;

    @Column(name = "expected_benefit", nullable = false, columnDefinition = "TEXT")
    private String expectedBenefit;

    @Column(name = "risk_if_delayed", nullable = false, columnDefinition = "TEXT")
    private String riskIfDelayed;

    @Column(name = "evidence_metrics", nullable = false, columnDefinition = "TEXT")
    private String evidenceMetrics;

    @Column(name = "assumptions", nullable = false, columnDefinition = "TEXT")
    private String assumptions;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AdvisoryActionPlanStep> steps = new ArrayList<>();

    @Column(name = "evaluated_at", nullable = false)
    private Instant evaluatedAt = Instant.now();

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    public AdvisoryActionPlan() {}

    public AdvisoryActionPlan(Merchant merchant, String planKey, String horizon, String status,
                              BigDecimal overallReadinessScore, Integer totalStepsCount, Integer readyStepsCount,
                              Integer blockedStepsCount, String primaryNextAction, String expectedBenefit,
                              String riskIfDelayed, String evidenceMetrics, String assumptions) {
        this.merchant = merchant;
        this.planKey = planKey;
        this.horizon = horizon != null ? horizon : "30D";
        this.status = status != null ? status : "ACTIVE";
        this.overallReadinessScore = overallReadinessScore != null ? overallReadinessScore : BigDecimal.ZERO;
        this.totalStepsCount = totalStepsCount != null ? totalStepsCount : 0;
        this.readyStepsCount = readyStepsCount != null ? readyStepsCount : 0;
        this.blockedStepsCount = blockedStepsCount != null ? blockedStepsCount : 0;
        this.primaryNextAction = primaryNextAction;
        this.expectedBenefit = expectedBenefit;
        this.riskIfDelayed = riskIfDelayed;
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

    public String getPlanKey() { return planKey; }
    public void setPlanKey(String planKey) { this.planKey = planKey; }

    public String getHorizon() { return horizon; }
    public void setHorizon(String horizon) { this.horizon = horizon; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getOverallReadinessScore() { return overallReadinessScore; }
    public void setOverallReadinessScore(BigDecimal overallReadinessScore) { this.overallReadinessScore = overallReadinessScore; }

    public Integer getTotalStepsCount() { return totalStepsCount; }
    public void setTotalStepsCount(Integer totalStepsCount) { this.totalStepsCount = totalStepsCount; }

    public Integer getReadyStepsCount() { return readyStepsCount; }
    public void setReadyStepsCount(Integer readyStepsCount) { this.readyStepsCount = readyStepsCount; }

    public Integer getBlockedStepsCount() { return blockedStepsCount; }
    public void setBlockedStepsCount(Integer blockedStepsCount) { this.blockedStepsCount = blockedStepsCount; }

    public String getPrimaryNextAction() { return primaryNextAction; }
    public void setPrimaryNextAction(String primaryNextAction) { this.primaryNextAction = primaryNextAction; }

    public String getExpectedBenefit() { return expectedBenefit; }
    public void setExpectedBenefit(String expectedBenefit) { this.expectedBenefit = expectedBenefit; }

    public String getRiskIfDelayed() { return riskIfDelayed; }
    public void setRiskIfDelayed(String riskIfDelayed) { this.riskIfDelayed = riskIfDelayed; }

    public String getEvidenceMetrics() { return evidenceMetrics; }
    public void setEvidenceMetrics(String evidenceMetrics) { this.evidenceMetrics = evidenceMetrics; }

    public String getAssumptions() { return assumptions; }
    public void setAssumptions(String assumptions) { this.assumptions = assumptions; }

    public List<AdvisoryActionPlanStep> getSteps() { return steps; }
    public void setSteps(List<AdvisoryActionPlanStep> steps) { this.steps = steps; }

    public Instant getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(Instant evaluatedAt) { this.evaluatedAt = evaluatedAt; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
