package com.flowwise.dto;

import java.math.BigDecimal;

public class AdvisoryActionPlanStepDTO {
    private Long id;
    private Long planId;
    private String stepKey;
    private Integer stepNumber;
    private String actionType;
    private String title;
    private String description;
    private String readinessStatus;
    private BigDecimal stepScore;
    private BigDecimal priorityScore;
    private BigDecimal riskProtectionScore;
    private BigDecimal urgencyScore;
    private BigDecimal dependencyReadinessScore;
    private String confidenceStatus;
    private String effortLevel;
    private String prerequisites;
    private String expectedOutcome;
    private String evidenceMetrics;

    public AdvisoryActionPlanStepDTO() {}

    public AdvisoryActionPlanStepDTO(Long id, Long planId, String stepKey, Integer stepNumber, String actionType,
                                     String title, String description, String readinessStatus, BigDecimal stepScore,
                                     BigDecimal priorityScore, BigDecimal riskProtectionScore, BigDecimal urgencyScore,
                                     BigDecimal dependencyReadinessScore, String confidenceStatus, String effortLevel,
                                     String prerequisites, String expectedOutcome, String evidenceMetrics) {
        this.id = id;
        this.planId = planId;
        this.stepKey = stepKey;
        this.stepNumber = stepNumber;
        this.actionType = actionType;
        this.title = title;
        this.description = description;
        this.readinessStatus = readinessStatus;
        this.stepScore = stepScore;
        this.priorityScore = priorityScore;
        this.riskProtectionScore = riskProtectionScore;
        this.urgencyScore = urgencyScore;
        this.dependencyReadinessScore = dependencyReadinessScore;
        this.confidenceStatus = confidenceStatus;
        this.effortLevel = effortLevel;
        this.prerequisites = prerequisites;
        this.expectedOutcome = expectedOutcome;
        this.evidenceMetrics = evidenceMetrics;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPlanId() { return planId; }
    public void setPlanId(Long planId) { this.planId = planId; }

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
}
