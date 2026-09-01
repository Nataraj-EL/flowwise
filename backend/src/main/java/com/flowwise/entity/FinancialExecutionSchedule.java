package com.flowwise.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "financial_execution_schedules", uniqueConstraints = {
    @UniqueConstraint(name = "uk_financial_execution_schedule_merchant_horizon_key", columnNames = {"merchant_id", "horizon", "schedule_key"})
})
public class FinancialExecutionSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(name = "schedule_key", nullable = false, length = 64)
    private String scheduleKey;

    @Column(name = "horizon", nullable = false, length = 16)
    private String horizon = "30D";

    @Column(name = "status", nullable = false, length = 32)
    private String status = "ACTIVE"; // DRAFT, ACTIVE, COMPLETED, ARCHIVED

    @Column(name = "overall_schedule_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal overallScheduleScore = BigDecimal.ZERO;

    @Column(name = "capacity_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal capacityScore = BigDecimal.ZERO;

    @Column(name = "risk_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal riskScore = BigDecimal.ZERO;

    @Column(name = "impact_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal impactScore = BigDecimal.ZERO;

    @Column(name = "urgency_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal urgencyScore = BigDecimal.ZERO;

    @Column(name = "total_actions", nullable = false)
    private Integer totalActions = 0;

    @Column(name = "scheduled_actions", nullable = false)
    private Integer scheduledActions = 0;

    @Column(name = "deferred_actions", nullable = false)
    private Integer deferredActions = 0;

    @Column(name = "primary_focus", nullable = false, columnDefinition = "TEXT")
    private String primaryFocus;

    @Column(name = "expected_benefit", nullable = false, columnDefinition = "TEXT")
    private String expectedBenefit;

    @Column(name = "risk_if_deferred", nullable = false, columnDefinition = "TEXT")
    private String riskIfDeferred;

    @Column(name = "evidence_metrics", nullable = false, columnDefinition = "TEXT")
    private String evidenceMetrics;

    @Column(name = "assumptions", nullable = false, columnDefinition = "TEXT")
    private String assumptions;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FinancialExecutionScheduleItem> items = new ArrayList<>();

    @Column(name = "evaluated_at", nullable = false)
    private Instant evaluatedAt = Instant.now();

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    public FinancialExecutionSchedule() {}

    public FinancialExecutionSchedule(Merchant merchant, String scheduleKey, String horizon, String status,
                                      BigDecimal overallScheduleScore, BigDecimal capacityScore, BigDecimal riskScore,
                                      BigDecimal impactScore, BigDecimal urgencyScore, Integer totalActions,
                                      Integer scheduledActions, Integer deferredActions, String primaryFocus,
                                      String expectedBenefit, String riskIfDeferred, String evidenceMetrics, String assumptions) {
        this.merchant = merchant;
        this.scheduleKey = scheduleKey;
        this.horizon = horizon != null ? horizon : "30D";
        this.status = status != null ? status : "ACTIVE";
        this.overallScheduleScore = overallScheduleScore != null ? overallScheduleScore : BigDecimal.ZERO;
        this.capacityScore = capacityScore != null ? capacityScore : BigDecimal.ZERO;
        this.riskScore = riskScore != null ? riskScore : BigDecimal.ZERO;
        this.impactScore = impactScore != null ? impactScore : BigDecimal.ZERO;
        this.urgencyScore = urgencyScore != null ? urgencyScore : BigDecimal.ZERO;
        this.totalActions = totalActions != null ? totalActions : 0;
        this.scheduledActions = scheduledActions != null ? scheduledActions : 0;
        this.deferredActions = deferredActions != null ? deferredActions : 0;
        this.primaryFocus = primaryFocus;
        this.expectedBenefit = expectedBenefit;
        this.riskIfDeferred = riskIfDeferred;
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

    public String getScheduleKey() { return scheduleKey; }
    public void setScheduleKey(String scheduleKey) { this.scheduleKey = scheduleKey; }

    public String getHorizon() { return horizon; }
    public void setHorizon(String horizon) { this.horizon = horizon; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getOverallScheduleScore() { return overallScheduleScore; }
    public void setOverallScheduleScore(BigDecimal overallScheduleScore) { this.overallScheduleScore = overallScheduleScore; }

    public BigDecimal getCapacityScore() { return capacityScore; }
    public void setCapacityScore(BigDecimal capacityScore) { this.capacityScore = capacityScore; }

    public BigDecimal getRiskScore() { return riskScore; }
    public void setRiskScore(BigDecimal riskScore) { this.riskScore = riskScore; }

    public BigDecimal getImpactScore() { return impactScore; }
    public void setImpactScore(BigDecimal impactScore) { this.impactScore = impactScore; }

    public BigDecimal getUrgencyScore() { return urgencyScore; }
    public void setUrgencyScore(BigDecimal urgencyScore) { this.urgencyScore = urgencyScore; }

    public Integer getTotalActions() { return totalActions; }
    public void setTotalActions(Integer totalActions) { this.totalActions = totalActions; }

    public Integer getScheduledActions() { return scheduledActions; }
    public void setScheduledActions(Integer scheduledActions) { this.scheduledActions = scheduledActions; }

    public Integer getDeferredActions() { return deferredActions; }
    public void setDeferredActions(Integer deferredActions) { this.deferredActions = deferredActions; }

    public String getPrimaryFocus() { return primaryFocus; }
    public void setPrimaryFocus(String primaryFocus) { this.primaryFocus = primaryFocus; }

    public String getExpectedBenefit() { return expectedBenefit; }
    public void setExpectedBenefit(String expectedBenefit) { this.expectedBenefit = expectedBenefit; }

    public String getRiskIfDeferred() { return riskIfDeferred; }
    public void setRiskIfDeferred(String riskIfDeferred) { this.riskIfDeferred = riskIfDeferred; }

    public String getEvidenceMetrics() { return evidenceMetrics; }
    public void setEvidenceMetrics(String evidenceMetrics) { this.evidenceMetrics = evidenceMetrics; }

    public String getAssumptions() { return assumptions; }
    public void setAssumptions(String assumptions) { this.assumptions = assumptions; }

    public List<FinancialExecutionScheduleItem> getItems() { return items; }
    public void setItems(List<FinancialExecutionScheduleItem> items) { this.items = items; }

    public Instant getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(Instant evaluatedAt) { this.evaluatedAt = evaluatedAt; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
