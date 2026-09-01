package com.flowwise.dto;

import java.math.BigDecimal;

public class DecisionLearningDTO {
    private Long id;
    private Long merchantId;
    private String decisionType;
    private String contextType;
    private Integer sampleCount;
    private BigDecimal effectivenessScore;
    private BigDecimal learningMultiplier;
    private String confidenceStatus;
    private String evidenceMetrics;
    private String evaluatedAt;

    public DecisionLearningDTO() {}

    public DecisionLearningDTO(Long id, Long merchantId, String decisionType, String contextType,
                               Integer sampleCount, BigDecimal effectivenessScore, BigDecimal learningMultiplier,
                               String confidenceStatus, String evidenceMetrics, String evaluatedAt) {
        this.id = id;
        this.merchantId = merchantId;
        this.decisionType = decisionType;
        this.contextType = contextType;
        this.sampleCount = sampleCount;
        this.effectivenessScore = effectivenessScore;
        this.learningMultiplier = learningMultiplier;
        this.confidenceStatus = confidenceStatus;
        this.evidenceMetrics = evidenceMetrics;
        this.evaluatedAt = evaluatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public String getDecisionType() { return decisionType; }
    public void setDecisionType(String decisionType) { this.decisionType = decisionType; }

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

    public String getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(String evaluatedAt) { this.evaluatedAt = evaluatedAt; }
}
