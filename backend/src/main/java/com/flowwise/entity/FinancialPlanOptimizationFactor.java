package com.flowwise.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "financial_plan_optimization_factors")
public class FinancialPlanOptimizationFactor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(name = "plan_context", nullable = false, length = 128)
    private String planContext; // e.g., '30D', '7D', '90D'

    @Column(name = "sample_count", nullable = false)
    private Integer sampleCount = 0;

    @Column(name = "effectiveness_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal effectivenessScore = BigDecimal.ZERO;

    @Column(name = "optimization_multiplier", nullable = false, precision = 6, scale = 3)
    private BigDecimal optimizationMultiplier = BigDecimal.ONE; // Bounded 0.900 - 1.100

    @Column(name = "confidence_status", nullable = false, length = 32)
    private String confidenceStatus = "HIGH"; // HIGH, MODERATE, LIMITED, INSUFFICIENT_DATA

    @Column(name = "evaluated_at", nullable = false)
    private Instant evaluatedAt = Instant.now();

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    public FinancialPlanOptimizationFactor() {}

    public FinancialPlanOptimizationFactor(Merchant merchant, String planContext, Integer sampleCount,
                                           BigDecimal effectivenessScore, BigDecimal optimizationMultiplier,
                                           String confidenceStatus) {
        this.merchant = merchant;
        this.planContext = planContext;
        this.sampleCount = sampleCount != null ? sampleCount : 0;
        this.effectivenessScore = effectivenessScore != null ? effectivenessScore : BigDecimal.ZERO;
        this.optimizationMultiplier = optimizationMultiplier != null ? optimizationMultiplier : BigDecimal.ONE;
        this.confidenceStatus = confidenceStatus != null ? confidenceStatus : "HIGH";
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

    public String getPlanContext() { return planContext; }
    public void setPlanContext(String planContext) { this.planContext = planContext; }

    public Integer getSampleCount() { return sampleCount; }
    public void setSampleCount(Integer sampleCount) { this.sampleCount = sampleCount; }

    public BigDecimal getEffectivenessScore() { return effectivenessScore; }
    public void setEffectivenessScore(BigDecimal effectivenessScore) { this.effectivenessScore = effectivenessScore; }

    public BigDecimal getOptimizationMultiplier() { return optimizationMultiplier; }
    public void setOptimizationMultiplier(BigDecimal optimizationMultiplier) { this.optimizationMultiplier = optimizationMultiplier; }

    public String getConfidenceStatus() { return confidenceStatus; }
    public void setConfidenceStatus(String confidenceStatus) { this.confidenceStatus = confidenceStatus; }

    public Instant getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(Instant evaluatedAt) { this.evaluatedAt = evaluatedAt; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
