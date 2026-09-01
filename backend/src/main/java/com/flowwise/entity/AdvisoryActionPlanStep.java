package com.flowwise.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "advisory_action_plan_steps")
public class AdvisoryActionPlanStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private AdvisoryActionPlan plan;

    @Column(name = "step_key", nullable = false, length = 128)
    private String stepKey;

    @Column(name = "step_number", nullable = false)
    private Integer stepNumber = 1;

    @Column(name = "action_type", nullable = false, length = 64)
    private String actionType;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "readiness_status", nullable = false, length = 32)
    private String readinessStatus = "READY"; // READY, BLOCKED, REVIEW_REQUIRED, COMPLETED

    @Column(name = "step_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal stepScore = BigDecimal.ZERO;

    @Column(name = "priority_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal priorityScore = BigDecimal.ZERO;

    @Column(name = "risk_protection_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal riskProtectionScore = BigDecimal.ZERO;

    @Column(name = "urgency_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal urgencyScore = BigDecimal.ZERO;

    @Column(name = "dependency_readiness_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal dependencyReadinessScore = BigDecimal.ZERO;

    @Column(name = "confidence_status", nullable = false, length = 32)
    private String confidenceStatus = "HIGH";

    @Column(name = "effort_level", nullable = false, length = 32)
    private String effortLevel = "LOW";

    @Column(name = "prerequisites", nullable = false, columnDefinition = "TEXT")
    private String prerequisites;

    @Column(name = "expected_outcome", nullable = false, columnDefinition = "TEXT")
    private String expectedOutcome;

    @Column(name = "evidence_metrics", nullable = false, columnDefinition = "TEXT")
    private String evidenceMetrics;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    public AdvisoryActionPlanStep() {}

    public AdvisoryActionPlanStep(AdvisoryActionPlan plan, String stepKey, Integer stepNumber, String actionType,
                                  String title, String description, String readinessStatus, BigDecimal stepScore,
                                  BigDecimal priorityScore, BigDecimal riskProtectionScore, BigDecimal urgencyScore,
                                  BigDecimal dependencyReadinessScore, String confidenceStatus, String effortLevel,
                                  String prerequisites, String expectedOutcome, String evidenceMetrics) {
        this.plan = plan;
        this.stepKey = stepKey;
        this.stepNumber = stepNumber != null ? stepNumber : 1;
        this.actionType = actionType;
        this.title = title;
        this.description = description;
        this.readinessStatus = readinessStatus != null ? readinessStatus : "READY";
        this.stepScore = stepScore != null ? stepScore : BigDecimal.ZERO;
        this.priorityScore = priorityScore != null ? priorityScore : BigDecimal.ZERO;
        this.riskProtectionScore = riskProtectionScore != null ? riskProtectionScore : BigDecimal.ZERO;
        this.urgencyScore = urgencyScore != null ? urgencyScore : BigDecimal.ZERO;
        this.dependencyReadinessScore = dependencyReadinessScore != null ? dependencyReadinessScore : BigDecimal.ZERO;
        this.confidenceStatus = confidenceStatus != null ? confidenceStatus : "HIGH";
        this.effortLevel = effortLevel != null ? effortLevel : "LOW";
        this.prerequisites = prerequisites;
        this.expectedOutcome = expectedOutcome;
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

    public AdvisoryActionPlan getPlan() { return plan; }
    public void setPlan(AdvisoryActionPlan plan) { this.plan = plan; }

    public String getStepKey() { return stepKey; }
    public void setStepKey(String stepKey) { this.stepKey = stepKey; }

    public Integer getStepNumber() { return stepNumber; }
    public void setStepNumber(Integer stepNumber) { this.stepNumber = stepNumber; }

    public String getActionType() { return actionType; }
    public void setActionType(String actionType) { this.actionType = actionType; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getReadinessStatus() { return readinessStatus; }
    public void setReadinessStatus(String readinessStatus) { this.readinessStatus = readinessStatus; }

    public BigDecimal getStepScore() { return stepScore; }
    public void setStepScore(BigDecimal stepScore) { this.stepScore = stepScore; }

    public BigDecimal getPriorityScore() { return priorityScore; }
    public void setPriorityScore(BigDecimal priorityScore) { this.priorityScore = priorityScore; }

    public BigDecimal getRiskProtectionScore() { return riskProtectionScore; }
    public void setRiskProtectionScore(BigDecimal riskProtectionScore) { this.riskProtectionScore = riskProtectionScore; }

    public BigDecimal getUrgencyScore() { return urgencyScore; }
    public void setUrgencyScore(BigDecimal urgencyScore) { this.urgencyScore = urgencyScore; }

    public BigDecimal getDependencyReadinessScore() { return dependencyReadinessScore; }
    public void setDependencyReadinessScore(BigDecimal dependencyReadinessScore) { this.dependencyReadinessScore = dependencyReadinessScore; }

    public String getConfidenceStatus() { return confidenceStatus; }
    public void setConfidenceStatus(String confidenceStatus) { this.confidenceStatus = confidenceStatus; }

    public String getEffortLevel() { return effortLevel; }
    public void setEffortLevel(String effortLevel) { this.effortLevel = effortLevel; }

    public String getPrerequisites() { return prerequisites; }
    public void setPrerequisites(String prerequisites) { this.prerequisites = prerequisites; }

    public String getExpectedOutcome() { return expectedOutcome; }
    public void setExpectedOutcome(String expectedOutcome) { this.expectedOutcome = expectedOutcome; }

    public String getEvidenceMetrics() { return evidenceMetrics; }
    public void setEvidenceMetrics(String evidenceMetrics) { this.evidenceMetrics = evidenceMetrics; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
