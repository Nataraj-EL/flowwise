package com.flowwise.dto;

import java.math.BigDecimal;

public class StrategyLearningDTO {
    private Long id;
    private Long merchantId;
    private String strategyKey;
    private String interventionType;
    private String contextType;
    private Integer sampleCount;
    private BigDecimal effectivenessScore;
    private BigDecimal learningMultiplier;
    private String confidenceStatus;
    private String evidenceMetrics;
    private String assumptions;
    private String evaluatedAt;

    public StrategyLearningDTO() {}

    public StrategyLearningDTO(Long id, Long merchantId, String strategyKey, String interventionType,
                               String contextType, Integer sampleCount, BigDecimal effectivenessScore,
                               BigDecimal learningMultiplier, String confidenceStatus, String evidenceMetrics,
                               String assumptions, String evaluatedAt) {
        this.id = id;
        this.merchantId = merchantId;
        this.strategyKey = strategyKey;
        this.interventionType = interventionType;
        this.contextType = contextType;
        this.sampleCount = sampleCount;
        this.effectivenessScore = effectivenessScore;
        this.learningMultiplier = learningMultiplier;
        this.confidenceStatus = confidenceStatus;
        this.evidenceMetrics = evidenceMetrics;
        this.assumptions = assumptions;
        this.evaluatedAt = evaluatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

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

    public String getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(String evaluatedAt) { this.evaluatedAt = evaluatedAt; }
}
