package com.flowwise.dto;

import java.math.BigDecimal;

public class FinancialExecutionScheduleItemDTO {
    private Long id;
    private Long scheduleId;
    private Long actionPlanId;
    private Long stepId;
    private String actionType;
    private String title;
    private String scheduledPeriod;
    private Integer sequenceOrder;
    private String readinessStatus;
    private BigDecimal priorityScore;
    private BigDecimal riskProtectionScore;
    private BigDecimal urgencyScore;
    private BigDecimal dependencyScore;
    private BigDecimal effectivenessScore;
    private BigDecimal capacityCost;
    private BigDecimal deferralScore;
    private String confidenceStatus;
    private String expectedOutcome;
    private String deferralRisk;
    private String evidenceMetrics;

    public FinancialExecutionScheduleItemDTO() {}

    public FinancialExecutionScheduleItemDTO(Long id, Long scheduleId, Long actionPlanId, Long stepId,
                                            String actionType, String title, String scheduledPeriod,
                                            Integer sequenceOrder, String readinessStatus, BigDecimal priorityScore,
                                            BigDecimal riskProtectionScore, BigDecimal urgencyScore,
                                            BigDecimal dependencyScore, BigDecimal effectivenessScore,
                                            BigDecimal capacityCost, BigDecimal deferralScore,
                                            String confidenceStatus, String expectedOutcome, String deferralRisk,
                                            String evidenceMetrics) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.actionPlanId = actionPlanId;
        this.stepId = stepId;
        this.actionType = actionType;
        this.title = title;
        this.scheduledPeriod = scheduledPeriod;
        this.sequenceOrder = sequenceOrder;
        this.readinessStatus = readinessStatus;
        this.priorityScore = priorityScore;
        this.riskProtectionScore = riskProtectionScore;
        this.urgencyScore = urgencyScore;
        this.dependencyScore = dependencyScore;
        this.effectivenessScore = effectivenessScore;
        this.capacityCost = capacityCost;
        this.deferralScore = deferralScore;
        this.confidenceStatus = confidenceStatus;
        this.expectedOutcome = expectedOutcome;
        this.deferralRisk = deferralRisk;
        this.evidenceMetrics = evidenceMetrics;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getScheduleId() { return scheduleId; }
    public void setScheduleId(Long scheduleId) { this.scheduleId = scheduleId; }

    public Long getActionPlanId() { return actionPlanId; }
    public void setActionPlanId(Long actionPlanId) { this.actionPlanId = actionPlanId; }

    public Long getStepId() { return stepId; }
    public void setStepId(Long stepId) { this.stepId = stepId; }

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
}
