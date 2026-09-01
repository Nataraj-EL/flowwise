package com.flowwise.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "financial_strategy_learnings")
public class FinancialStrategyLearning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(name = "strategy_key", nullable = false, length = 128)
    private String strategyKey;

    @Column(name = "intervention_type", nullable = false, length = 64)
    private String interventionType;

    @Column(name = "context_type", nullable = false, length = 64)
    private String contextType = "GENERAL_RECEIVABLES";

    @Column(name = "sample_count", nullable = false)
    private Integer sampleCount = 1;

    @Column(name = "effectiveness_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal effectivenessScore = BigDecimal.ZERO;

    @Column(name = "learning_multiplier", nullable = false, precision = 4, scale = 3)
    private BigDecimal learningMultiplier = new BigDecimal("1.000"); // 0.900 - 1.100

    @Column(name = "confidence_status", nullable = false, length = 32)
    private String confidenceStatus = "HIGH"; // HIGH, MODERATE, LIMITED, INSUFFICIENT_DATA

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

    public FinancialStrategyLearning() {}

    public FinancialStrategyLearning(Merchant merchant, String strategyKey, String interventionType,
                                     String contextType, Integer sampleCount, BigDecimal effectivenessScore,
                                     BigDecimal learningMultiplier, String confidenceStatus,
                                     String evidenceMetrics, String assumptions) {
        this.merchant = merchant;
        this.strategyKey = strategyKey;
        this.interventionType = interventionType;
        this.contextType = contextType != null ? contextType : "GENERAL_RECEIVABLES";
        this.sampleCount = sampleCount != null ? sampleCount : 1;
        this.effectivenessScore = effectivenessScore != null ? effectivenessScore : BigDecimal.ZERO;
        this.learningMultiplier = learningMultiplier != null ? learningMultiplier : new BigDecimal("1.000");
        this.confidenceStatus = confidenceStatus != null ? confidenceStatus : "HIGH";
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

    public String getStrategyKey() { return strategyKey; }
    public void setStrategyKey(String strategyKey) { this.strategyKey = strategyKey; }

    public String getInterventionType() { return interventionType; }
    public void setInterventionType(String interventionType) { this.interventionType = interventionType; }

    public String getContextType() { return contextType; }
    public void setContextType(String contextType) { this.contextType = contextType; }

    public Integer getSampleCount() { return sampleCount; }
    public void setSampleCount(Integer sampleCount) { this.sampleCount = sampleCount; }

    public BigDecimal getEffectivenessScore() { return effectivenessScore; }
    public void setEffectivenessScore(BigDecimal effectivenessScore) { this.effectivenessScore = effectivenessScore; }

    public BigDecimal getLearningMultiplier() { return learningMultiplier; }
    public void setLearningMultiplier(BigDecimal learningMultiplier) { this.learningMultiplier = learningMultiplier; }

    public String getConfidenceStatus() { return confidenceStatus; }
    public void setConfidenceStatus(String confidenceStatus) { this.confidenceStatus = confidenceStatus; }

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
