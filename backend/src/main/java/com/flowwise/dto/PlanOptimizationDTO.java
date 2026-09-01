package com.flowwise.dto;

import java.math.BigDecimal;

public class PlanOptimizationDTO {
    private Long id;
    private Long merchantId;
    private String planContext;
    private Integer sampleCount;
    private BigDecimal effectivenessScore;
    private BigDecimal optimizationMultiplier;
    private String confidenceStatus;
    private String evaluatedAt;

    public PlanOptimizationDTO() {}

    public PlanOptimizationDTO(Long id, Long merchantId, String planContext, Integer sampleCount,
                               BigDecimal effectivenessScore, BigDecimal optimizationMultiplier,
                               String confidenceStatus, String evaluatedAt) {
        this.id = id;
        this.merchantId = merchantId;
        this.planContext = planContext;
        this.sampleCount = sampleCount;
        this.effectivenessScore = effectivenessScore;
        this.optimizationMultiplier = optimizationMultiplier;
        this.confidenceStatus = confidenceStatus;
        this.evaluatedAt = evaluatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

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

    public String getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(String evaluatedAt) { this.evaluatedAt = evaluatedAt; }
}
