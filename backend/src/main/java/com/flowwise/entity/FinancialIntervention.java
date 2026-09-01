package com.flowwise.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "financial_interventions")
public class FinancialIntervention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(name = "intervention_key", nullable = false, length = 64)
    private String interventionKey;

    @Column(name = "intervention_type", nullable = false, length = 64)
    private String interventionType; // COLLECT_RECEIVABLES, REDUCE_EXPENSE, MANAGE_PAYABLES, BUILD_CASH_RESERVE, PROTECT_GOAL, MITIGATE_RISK, INVESTIGATE_ANOMALY

    @Column(name = "title", nullable = false, length = 128)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "priority_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal priorityScore = BigDecimal.ZERO;

    @Column(name = "urgency_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal urgencyScore = BigDecimal.ZERO;

    @Column(name = "impact_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal impactScore = BigDecimal.ZERO;

    @Column(name = "confidence_status", nullable = false, length = 32)
    private String confidenceStatus = "HIGH"; // HIGH, MODERATE, LIMITED, INSUFFICIENT_DATA

    @Column(name = "expected_benefit", nullable = false, length = 255)
    private String expectedBenefit;

    @Column(name = "risk_if_ignored", nullable = false, length = 255)
    private String riskIfIgnored;

    @Column(name = "effort_level", nullable = false, length = 32)
    private String effortLevel = "MEDIUM"; // LOW, MEDIUM, HIGH

    @Column(name = "linked_risk_id")
    private Long linkedRiskId;

    @Column(name = "linked_anomaly_id")
    private Long linkedAnomalyId;

    @Column(name = "linked_correlation_id")
    private Long linkedCorrelationId;

    @Column(name = "linked_goal_id")
    private Long linkedGoalId;

    @Column(name = "status", nullable = false, length = 32)
    private String status = "OPEN"; // OPEN, ACKNOWLEDGED, COMPLETED, DISMISSED

    @Column(name = "evidence_metrics", nullable = false, columnDefinition = "TEXT")
    private String evidenceMetrics;

    @Column(name = "assumptions", nullable = false, columnDefinition = "TEXT")
    private String assumptions;

    @Column(name = "evaluated_at", nullable = false)
    private Instant evaluatedAt = Instant.now();

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    public FinancialIntervention() {}

    public FinancialIntervention(Merchant merchant, String interventionKey, String interventionType,
                                 String title, String description, BigDecimal priorityScore, BigDecimal urgencyScore,
                                 BigDecimal impactScore, String confidenceStatus, String expectedBenefit,
                                 String riskIfIgnored, String effortLevel, String evidenceMetrics, String assumptions) {
        this.merchant = merchant;
        this.interventionKey = interventionKey;
        this.interventionType = interventionType;
        this.title = title;
        this.description = description;
        this.priorityScore = priorityScore != null ? priorityScore : BigDecimal.ZERO;
        this.urgencyScore = urgencyScore != null ? urgencyScore : BigDecimal.ZERO;
        this.impactScore = impactScore != null ? impactScore : BigDecimal.ZERO;
        this.confidenceStatus = confidenceStatus != null ? confidenceStatus : "HIGH";
        this.expectedBenefit = expectedBenefit;
        this.riskIfIgnored = riskIfIgnored;
        this.effortLevel = effortLevel != null ? effortLevel : "MEDIUM";
        this.status = "OPEN";
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

    public String getInterventionKey() { return interventionKey; }
    public void setInterventionKey(String interventionKey) { this.interventionKey = interventionKey; }

    public String getInterventionType() { return interventionType; }
    public void setInterventionType(String interventionType) { this.interventionType = interventionType; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPriorityScore() { return priorityScore; }
    public void setPriorityScore(BigDecimal priorityScore) { this.priorityScore = priorityScore; }

    public BigDecimal getUrgencyScore() { return urgencyScore; }
    public void setUrgencyScore(BigDecimal urgencyScore) { this.urgencyScore = urgencyScore; }

    public BigDecimal getImpactScore() { return impactScore; }
    public void setImpactScore(BigDecimal impactScore) { this.impactScore = impactScore; }

    public String getConfidenceStatus() { return confidenceStatus; }
    public void setConfidenceStatus(String confidenceStatus) { this.confidenceStatus = confidenceStatus; }

    public String getExpectedBenefit() { return expectedBenefit; }
    public void setExpectedBenefit(String expectedBenefit) { this.expectedBenefit = expectedBenefit; }

    public String getRiskIfIgnored() { return riskIfIgnored; }
    public void setRiskIfIgnored(String riskIfIgnored) { this.riskIfIgnored = riskIfIgnored; }

    public String getEffortLevel() { return effortLevel; }
    public void setEffortLevel(String effortLevel) { this.effortLevel = effortLevel; }

    public Long getLinkedRiskId() { return linkedRiskId; }
    public void setLinkedRiskId(Long linkedRiskId) { this.linkedRiskId = linkedRiskId; }

    public Long getLinkedAnomalyId() { return linkedAnomalyId; }
    public void setLinkedAnomalyId(Long linkedAnomalyId) { this.linkedAnomalyId = linkedAnomalyId; }

    public Long getLinkedCorrelationId() { return linkedCorrelationId; }
    public void setLinkedCorrelationId(Long linkedCorrelationId) { this.linkedCorrelationId = linkedCorrelationId; }

    public Long getLinkedGoalId() { return linkedGoalId; }
    public void setLinkedGoalId(Long linkedGoalId) { this.linkedGoalId = linkedGoalId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getEvidenceMetrics() { return evidenceMetrics; }
    public void setEvidenceMetrics(String evidenceMetrics) { this.evidenceMetrics = evidenceMetrics; }

    public String getAssumptions() { return assumptions; }
    public void setAssumptions(String assumptions) { this.assumptions = assumptions; }

    public Instant getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(Instant evaluatedAt) { this.evaluatedAt = evaluatedAt; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
