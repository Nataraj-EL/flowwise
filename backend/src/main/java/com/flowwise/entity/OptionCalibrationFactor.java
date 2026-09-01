package com.flowwise.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "option_calibration_factors")
public class OptionCalibrationFactor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calibration_record_id", nullable = false)
    private DecisionCalibrationRecord calibrationRecord;

    @Column(name = "option_key", nullable = false, length = 64)
    private String optionKey; // PAY_NOW, DEFER, COLLECT_RECEIVABLES, REDUCE_EXPENSE, BUILD_RESERVE

    @Column(name = "total_sample_count", nullable = false)
    private Integer totalSampleCount = 0;

    @Column(name = "positive_outcome_count", nullable = false)
    private Integer positiveOutcomeCount = 0;

    @Column(name = "negative_outcome_count", nullable = false)
    private Integer negativeOutcomeCount = 0;

    @Column(name = "success_rate_pct", nullable = false, precision = 5, scale = 2)
    private BigDecimal successRatePct = BigDecimal.ZERO;

    @Column(name = "calibration_multiplier", nullable = false, precision = 4, scale = 2)
    private BigDecimal calibrationMultiplier = BigDecimal.ONE; // Bounded 0.80 - 1.20

    @Column(name = "avg_cash_impact_variance", nullable = false, precision = 15, scale = 2)
    private BigDecimal avgCashImpactVariance = BigDecimal.ZERO;

    @Column(name = "accuracy_status", nullable = false, length = 32)
    private String accuracyStatus = "ACCURATE"; // ACCURATE, OVERESTIMATED, UNDERESTIMATED, UNCALIBRATED

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    public OptionCalibrationFactor() {}

    public OptionCalibrationFactor(DecisionCalibrationRecord calibrationRecord, String optionKey,
                                   Integer totalSampleCount, Integer positiveOutcomeCount,
                                   Integer negativeOutcomeCount, BigDecimal successRatePct,
                                   BigDecimal calibrationMultiplier, BigDecimal avgCashImpactVariance,
                                   String accuracyStatus) {
        this.calibrationRecord = calibrationRecord;
        this.optionKey = optionKey;
        this.totalSampleCount = totalSampleCount != null ? totalSampleCount : 0;
        this.positiveOutcomeCount = positiveOutcomeCount != null ? positiveOutcomeCount : 0;
        this.negativeOutcomeCount = negativeOutcomeCount != null ? negativeOutcomeCount : 0;
        this.successRatePct = successRatePct != null ? successRatePct : BigDecimal.ZERO;
        this.calibrationMultiplier = calibrationMultiplier != null ? calibrationMultiplier : BigDecimal.ONE;
        this.avgCashImpactVariance = avgCashImpactVariance != null ? avgCashImpactVariance : BigDecimal.ZERO;
        this.accuracyStatus = accuracyStatus != null ? accuracyStatus : "UNCALIBRATED";
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public DecisionCalibrationRecord getCalibrationRecord() { return calibrationRecord; }
    public void setCalibrationRecord(DecisionCalibrationRecord calibrationRecord) { this.calibrationRecord = calibrationRecord; }

    public String getOptionKey() { return optionKey; }
    public void setOptionKey(String optionKey) { this.optionKey = optionKey; }

    public Integer getTotalSampleCount() { return totalSampleCount; }
    public void setTotalSampleCount(Integer totalSampleCount) { this.totalSampleCount = totalSampleCount; }

    public Integer getPositiveOutcomeCount() { return positiveOutcomeCount; }
    public void setPositiveOutcomeCount(Integer positiveOutcomeCount) { this.positiveOutcomeCount = positiveOutcomeCount; }

    public Integer getNegativeOutcomeCount() { return negativeOutcomeCount; }
    public void setNegativeOutcomeCount(Integer negativeOutcomeCount) { this.negativeOutcomeCount = negativeOutcomeCount; }

    public BigDecimal getSuccessRatePct() { return successRatePct; }
    public void setSuccessRatePct(BigDecimal successRatePct) { this.successRatePct = successRatePct; }

    public BigDecimal getCalibrationMultiplier() { return calibrationMultiplier; }
    public void setCalibrationMultiplier(BigDecimal calibrationMultiplier) { this.calibrationMultiplier = calibrationMultiplier; }

    public BigDecimal getAvgCashImpactVariance() { return avgCashImpactVariance; }
    public void setAvgCashImpactVariance(BigDecimal avgCashImpactVariance) { this.avgCashImpactVariance = avgCashImpactVariance; }

    public String getAccuracyStatus() { return accuracyStatus; }
    public void setAccuracyStatus(String accuracyStatus) { this.accuracyStatus = accuracyStatus; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
