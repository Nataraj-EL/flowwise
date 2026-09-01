package com.flowwise.dto;

import java.math.BigDecimal;

public class RiskTrajectoryDTO {
    private Long id;
    private Long merchantId;
    private String riskKey;
    private String riskType;
    private String trajectoryDirection; // IMPROVING, STABLE, WORSENING, RESOLVED, INSUFFICIENT_DATA
    private String severityTransition;
    private BigDecimal escalationVelocity;
    private int observedSnapshotsCount;
    private BigDecimal baselineValue;
    private BigDecimal currentValue;
    private BigDecimal scoreDelta;
    private BigDecimal resolutionTimeHours;
    private int recurrenceCount;
    private String evaluatedAt;

    public RiskTrajectoryDTO() {}

    public RiskTrajectoryDTO(Long id, Long merchantId, String riskKey, String riskType, String trajectoryDirection,
                             String severityTransition, BigDecimal escalationVelocity, int observedSnapshotsCount,
                             BigDecimal baselineValue, BigDecimal currentValue, BigDecimal scoreDelta,
                             BigDecimal resolutionTimeHours, int recurrenceCount, String evaluatedAt) {
        this.id = id;
        this.merchantId = merchantId;
        this.riskKey = riskKey;
        this.riskType = riskType;
        this.trajectoryDirection = trajectoryDirection;
        this.severityTransition = severityTransition;
        this.escalationVelocity = escalationVelocity;
        this.observedSnapshotsCount = observedSnapshotsCount;
        this.baselineValue = baselineValue;
        this.currentValue = currentValue;
        this.scoreDelta = scoreDelta;
        this.resolutionTimeHours = resolutionTimeHours;
        this.recurrenceCount = recurrenceCount;
        this.evaluatedAt = evaluatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public String getRiskKey() { return riskKey; }
    public void setRiskKey(String riskKey) { this.riskKey = riskKey; }

    public String getRiskType() { return riskType; }
    public void setRiskType(String riskType) { this.riskType = riskType; }

    public String getTrajectoryDirection() { return trajectoryDirection; }
    public void setTrajectoryDirection(String trajectoryDirection) { this.trajectoryDirection = trajectoryDirection; }

    public String getSeverityTransition() { return severityTransition; }
    public void setSeverityTransition(String severityTransition) { this.severityTransition = severityTransition; }

    public BigDecimal getEscalationVelocity() { return escalationVelocity; }
    public void setEscalationVelocity(BigDecimal escalationVelocity) { this.escalationVelocity = escalationVelocity; }

    public int getObservedSnapshotsCount() { return observedSnapshotsCount; }
    public void setObservedSnapshotsCount(int observedSnapshotsCount) { this.observedSnapshotsCount = observedSnapshotsCount; }

    public BigDecimal getBaselineValue() { return baselineValue; }
    public void setBaselineValue(BigDecimal baselineValue) { this.baselineValue = baselineValue; }

    public BigDecimal getCurrentValue() { return currentValue; }
    public void setCurrentValue(BigDecimal currentValue) { this.currentValue = currentValue; }

    public BigDecimal getScoreDelta() { return scoreDelta; }
    public void setScoreDelta(BigDecimal scoreDelta) { this.scoreDelta = scoreDelta; }

    public BigDecimal getResolutionTimeHours() { return resolutionTimeHours; }
    public void setResolutionTimeHours(BigDecimal resolutionTimeHours) { this.resolutionTimeHours = resolutionTimeHours; }

    public int getRecurrenceCount() { return recurrenceCount; }
    public void setRecurrenceCount(int recurrenceCount) { this.recurrenceCount = recurrenceCount; }

    public String getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(String evaluatedAt) { this.evaluatedAt = evaluatedAt; }
}
