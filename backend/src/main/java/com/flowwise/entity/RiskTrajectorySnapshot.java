package com.flowwise.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "risk_trajectory_snapshots")
public class RiskTrajectorySnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(name = "risk_key", nullable = false, length = 64)
    private String riskKey;

    @Column(name = "risk_type", nullable = false, length = 64)
    private String riskType;

    @Column(name = "trajectory_direction", nullable = false, length = 32)
    private String trajectoryDirection = "STABLE"; // IMPROVING, STABLE, WORSENING, RESOLVED, INSUFFICIENT_DATA

    @Column(name = "severity_transition", nullable = false, length = 64)
    private String severityTransition = "UNCHANGED";

    @Column(name = "escalation_velocity", nullable = false, precision = 7, scale = 2)
    private BigDecimal escalationVelocity = BigDecimal.ZERO;

    @Column(name = "observed_snapshots_count", nullable = false)
    private Integer observedSnapshotsCount = 1;

    @Column(name = "baseline_value", nullable = false, precision = 15, scale = 2)
    private BigDecimal baselineValue = BigDecimal.ZERO;

    @Column(name = "current_value", nullable = false, precision = 15, scale = 2)
    private BigDecimal currentValue = BigDecimal.ZERO;

    @Column(name = "score_delta", nullable = false, precision = 15, scale = 2)
    private BigDecimal scoreDelta = BigDecimal.ZERO;

    @Column(name = "resolution_time_hours", nullable = false, precision = 7, scale = 2)
    private BigDecimal resolutionTimeHours = BigDecimal.ZERO;

    @Column(name = "recurrence_count", nullable = false)
    private Integer recurrenceCount = 0;

    @Column(name = "evaluated_at", nullable = false)
    private Instant evaluatedAt = Instant.now();

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    public RiskTrajectorySnapshot() {}

    public RiskTrajectorySnapshot(Merchant merchant, String riskKey, String riskType, String trajectoryDirection,
                                  String severityTransition, BigDecimal escalationVelocity, Integer observedSnapshotsCount,
                                  BigDecimal baselineValue, BigDecimal currentValue, BigDecimal scoreDelta,
                                  BigDecimal resolutionTimeHours, Integer recurrenceCount) {
        this.merchant = merchant;
        this.riskKey = riskKey;
        this.riskType = riskType;
        this.trajectoryDirection = trajectoryDirection != null ? trajectoryDirection : "STABLE";
        this.severityTransition = severityTransition != null ? severityTransition : "UNCHANGED";
        this.escalationVelocity = escalationVelocity != null ? escalationVelocity : BigDecimal.ZERO;
        this.observedSnapshotsCount = observedSnapshotsCount != null ? observedSnapshotsCount : 1;
        this.baselineValue = baselineValue != null ? baselineValue : BigDecimal.ZERO;
        this.currentValue = currentValue != null ? currentValue : BigDecimal.ZERO;
        this.scoreDelta = scoreDelta != null ? scoreDelta : BigDecimal.ZERO;
        this.resolutionTimeHours = resolutionTimeHours != null ? resolutionTimeHours : BigDecimal.ZERO;
        this.recurrenceCount = recurrenceCount != null ? recurrenceCount : 0;
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

    public Integer getObservedSnapshotsCount() { return observedSnapshotsCount; }
    public void setObservedSnapshotsCount(Integer observedSnapshotsCount) { this.observedSnapshotsCount = observedSnapshotsCount; }

    public BigDecimal getBaselineValue() { return baselineValue; }
    public void setBaselineValue(BigDecimal baselineValue) { this.baselineValue = baselineValue; }

    public BigDecimal getCurrentValue() { return currentValue; }
    public void setCurrentValue(BigDecimal currentValue) { this.currentValue = currentValue; }

    public BigDecimal getScoreDelta() { return scoreDelta; }
    public void setScoreDelta(BigDecimal scoreDelta) { this.scoreDelta = scoreDelta; }

    public BigDecimal getResolutionTimeHours() { return resolutionTimeHours; }
    public void setResolutionTimeHours(BigDecimal resolutionTimeHours) { this.resolutionTimeHours = resolutionTimeHours; }

    public Integer getRecurrenceCount() { return recurrenceCount; }
    public void setRecurrenceCount(Integer recurrenceCount) { this.recurrenceCount = recurrenceCount; }

    public Instant getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(Instant evaluatedAt) { this.evaluatedAt = evaluatedAt; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
