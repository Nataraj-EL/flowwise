package com.flowwise.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "financial_execution_schedule_items")
public class FinancialExecutionScheduleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private FinancialExecutionSchedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_plan_id", nullable = false)
    private AdvisoryActionPlan actionPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "step_id", nullable = false)
    private AdvisoryActionPlanStep step;

    @Column(name = "action_type", nullable = false, length = 64)
    private String actionType;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "scheduled_period", nullable = false, length = 32)
    private String scheduledPeriod = "WEEK_1";

    @Column(name = "sequence_order", nullable = false)
    private Integer sequenceOrder = 1;

    @Column(name = "readiness_status", nullable = false, length = 32)
    private String readinessStatus = "SCHEDULED"; // SCHEDULED, DEFERRED

    @Column(name = "priority_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal priorityScore = BigDecimal.ZERO;

    @Column(name = "risk_protection_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal riskProtectionScore = BigDecimal.ZERO;

    @Column(name = "urgency_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal urgencyScore = BigDecimal.ZERO;

    @Column(name = "dependency_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal dependencyScore = BigDecimal.ZERO;

    @Column(name = "effectiveness_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal effectivenessScore = BigDecimal.ZERO;

    @Column(name = "capacity_cost", nullable = false, precision = 5, scale = 2)
    private BigDecimal capacityCost = BigDecimal.ZERO;

    @Column(name = "deferral_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal deferralScore = BigDecimal.ZERO;

    @Column(name = "confidence_status", nullable = false, length = 32)
    private String confidenceStatus = "HIGH";

    @Column(name = "expected_outcome", nullable = false, columnDefinition = "TEXT")
    private String expectedOutcome;

    @Column(name = "deferral_risk", nullable = false, columnDefinition = "TEXT")
    private String deferralRisk;

    @Column(name = "evidence_metrics", nullable = false, columnDefinition = "TEXT")
    private String evidenceMetrics;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    public FinancialExecutionScheduleItem() {}

    public FinancialExecutionScheduleItem(FinancialExecutionSchedule schedule, AdvisoryActionPlan actionPlan,
                                         AdvisoryActionPlanStep step, String actionType, String title,
                                         String scheduledPeriod, Integer sequenceOrder, String readinessStatus,
                                         BigDecimal priorityScore, BigDecimal riskProtectionScore, BigDecimal urgencyScore,
                                         BigDecimal dependencyScore, BigDecimal effectivenessScore, BigDecimal capacityCost,
                                         BigDecimal deferralScore, String confidenceStatus, String expectedOutcome,
                                         String deferralRisk, String evidenceMetrics) {
        this.schedule = schedule;
        this.actionPlan = actionPlan;
        this.step = step;
        this.actionType = actionType;
        this.title = title;
        this.scheduledPeriod = scheduledPeriod != null ? scheduledPeriod : "WEEK_1";
        this.sequenceOrder = sequenceOrder != null ? sequenceOrder : 1;
        this.readinessStatus = readinessStatus != null ? readinessStatus : "SCHEDULED";
        this.priorityScore = priorityScore != null ? priorityScore : BigDecimal.ZERO;
        this.riskProtectionScore = riskProtectionScore != null ? riskProtectionScore : BigDecimal.ZERO;
        this.urgencyScore = urgencyScore != null ? urgencyScore : BigDecimal.ZERO;
        this.dependencyScore = dependencyScore != null ? dependencyScore : BigDecimal.ZERO;
        this.effectivenessScore = effectivenessScore != null ? effectivenessScore : BigDecimal.ZERO;
        this.capacityCost = capacityCost != null ? capacityCost : BigDecimal.ZERO;
        this.deferralScore = deferralScore != null ? deferralScore : BigDecimal.ZERO;
        this.confidenceStatus = confidenceStatus != null ? confidenceStatus : "HIGH";
        this.expectedOutcome = expectedOutcome;
        this.deferralRisk = deferralRisk;
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

    public FinancialExecutionSchedule getSchedule() { return schedule; }
    public void setSchedule(FinancialExecutionSchedule schedule) { this.schedule = schedule; }

    public AdvisoryActionPlan getActionPlan() { return actionPlan; }
    public void setActionPlan(AdvisoryActionPlan actionPlan) { this.actionPlan = actionPlan; }

    public AdvisoryActionPlanStep getStep() { return step; }
    public void setStep(AdvisoryActionPlanStep step) { this.step = step; }

    public String getActionType() { return actionType; }
    public void setActionType(String actionType) { this.actionType = actionType; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getScheduledPeriod() { return scheduledPeriod; }
    public void setScheduledPeriod(String scheduledPeriod) { this.scheduledPeriod = scheduledPeriod; }

    public Integer getSequenceOrder() { return sequenceOrder; }
    public void setSequenceOrder(Integer sequenceOrder) { this.sequenceOrder = sequenceOrder; }

    public String getReadinessStatus() { return readinessStatus; }
    public void setReadinessStatus(String readinessStatus) { this.readinessStatus = readinessStatus; }

    public BigDecimal getPriorityScore() { return priorityScore; }
    public void setPriorityScore(BigDecimal priorityScore) { this.priorityScore = priorityScore; }

    public BigDecimal getRiskProtectionScore() { return riskProtectionScore; }
    public void setRiskProtectionScore(BigDecimal riskProtectionScore) { this.riskProtectionScore = riskProtectionScore; }

    public BigDecimal getUrgencyScore() { return urgencyScore; }
    public void setUrgencyScore(BigDecimal urgencyScore) { this.urgencyScore = urgencyScore; }

    public BigDecimal getDependencyScore() { return dependencyScore; }
    public void setDependencyScore(BigDecimal dependencyScore) { this.dependencyScore = dependencyScore; }

    public BigDecimal getEffectivenessScore() { return effectivenessScore; }
    public void setEffectivenessScore(BigDecimal effectivenessScore) { this.effectivenessScore = effectivenessScore; }

    public BigDecimal getCapacityCost() { return capacityCost; }
    public void setCapacityCost(BigDecimal capacityCost) { this.capacityCost = capacityCost; }

    public BigDecimal getDeferralScore() { return deferralScore; }
    public void setDeferralScore(BigDecimal deferralScore) { this.deferralScore = deferralScore; }

    public String getConfidenceStatus() { return confidenceStatus; }
    public void setConfidenceStatus(String confidenceStatus) { this.confidenceStatus = confidenceStatus; }

    public String getExpectedOutcome() { return expectedOutcome; }
    public void setExpectedOutcome(String expectedOutcome) { this.expectedOutcome = expectedOutcome; }

    public String getDeferralRisk() { return deferralRisk; }
    public void setDeferralRisk(String deferralRisk) { this.deferralRisk = deferralRisk; }

    public String getEvidenceMetrics() { return evidenceMetrics; }
    public void setEvidenceMetrics(String evidenceMetrics) { this.evidenceMetrics = evidenceMetrics; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
