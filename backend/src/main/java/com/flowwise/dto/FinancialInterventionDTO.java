package com.flowwise.dto;

import java.math.BigDecimal;

public class FinancialInterventionDTO {
    private Long id;
    private Long merchantId;
    private String interventionKey;
    private String interventionType;
    private String title;
    private String description;
    private BigDecimal priorityScore;
    private BigDecimal urgencyScore;
    private BigDecimal impactScore;
    private String confidenceStatus;
    private String expectedBenefit;
    private String riskIfIgnored;
    private String effortLevel;
    private Long linkedRiskId;
    private Long linkedAnomalyId;
    private Long linkedCorrelationId;
    private Long linkedGoalId;
    private String status;
    private String evidenceMetrics;
    private String assumptions;
    private String evaluatedAt;

    public FinancialInterventionDTO() {}

    public FinancialInterventionDTO(Long id, Long merchantId, String interventionKey, String interventionType,
                                  String title, String description, BigDecimal priorityScore, BigDecimal urgencyScore,
                                  BigDecimal impactScore, String confidenceStatus, String expectedBenefit,
                                  String riskIfIgnored, String effortLevel, Long linkedRiskId, Long linkedAnomalyId,
                                  Long linkedCorrelationId, Long linkedGoalId, String status,
                                  String evidenceMetrics, String assumptions, String evaluatedAt) {
        this.id = id;
        this.merchantId = merchantId;
        this.interventionKey = interventionKey;
        this.interventionType = interventionType;
        this.title = title;
        this.description = description;
        this.priorityScore = priorityScore;
        this.urgencyScore = urgencyScore;
        this.impactScore = impactScore;
        this.confidenceStatus = confidenceStatus;
        this.expectedBenefit = expectedBenefit;
        this.riskIfIgnored = riskIfIgnored;
        this.effortLevel = effortLevel;
        this.linkedRiskId = linkedRiskId;
        this.linkedAnomalyId = linkedAnomalyId;
        this.linkedCorrelationId = linkedCorrelationId;
        this.linkedGoalId = linkedGoalId;
        this.status = status;
        this.evidenceMetrics = evidenceMetrics;
        this.assumptions = assumptions;
        this.evaluatedAt = evaluatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

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

    public String getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(String evaluatedAt) { this.evaluatedAt = evaluatedAt; }
}
