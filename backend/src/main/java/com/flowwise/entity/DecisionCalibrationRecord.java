package com.flowwise.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "decision_calibration_records")
public class DecisionCalibrationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(name = "calibration_key", nullable = false, length = 64)
    private String calibrationKey;

    @Column(name = "total_evaluated_decisions", nullable = false)
    private Integer totalEvaluatedDecisions = 0;

    @Column(name = "successful_decisions", nullable = false)
    private Integer successfulDecisions = 0;

    @Column(name = "overall_success_rate_pct", nullable = false, precision = 5, scale = 2)
    private BigDecimal overallSuccessRatePct = BigDecimal.ZERO;

    @Column(name = "confidence_level", nullable = false, length = 32)
    private String confidenceLevel = "MODERATE"; // HIGH, MODERATE, LIMITED, INSUFFICIENT_DATA

    @Column(name = "data_completeness_pct", nullable = false, precision = 5, scale = 2)
    private BigDecimal dataCompletenessPct = new BigDecimal("100.00");

    @Column(name = "summary_insight", columnDefinition = "TEXT")
    private String summaryInsight;

    @Column(name = "evaluated_at", nullable = false)
    private Instant evaluatedAt = Instant.now();

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    @OneToMany(mappedBy = "calibrationRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OptionCalibrationFactor> optionFactors = new ArrayList<>();

    public DecisionCalibrationRecord() {}

    public DecisionCalibrationRecord(Merchant merchant, String calibrationKey, Integer totalEvaluatedDecisions,
                                     Integer successfulDecisions, BigDecimal overallSuccessRatePct,
                                     String confidenceLevel, BigDecimal dataCompletenessPct, String summaryInsight) {
        this.merchant = merchant;
        this.calibrationKey = calibrationKey;
        this.totalEvaluatedDecisions = totalEvaluatedDecisions != null ? totalEvaluatedDecisions : 0;
        this.successfulDecisions = successfulDecisions != null ? successfulDecisions : 0;
        this.overallSuccessRatePct = overallSuccessRatePct != null ? overallSuccessRatePct : BigDecimal.ZERO;
        this.confidenceLevel = confidenceLevel != null ? confidenceLevel : "MODERATE";
        this.dataCompletenessPct = dataCompletenessPct != null ? dataCompletenessPct : new BigDecimal("100.00");
        this.summaryInsight = summaryInsight;
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

    public String getCalibrationKey() { return calibrationKey; }
    public void setCalibrationKey(String calibrationKey) { this.calibrationKey = calibrationKey; }

    public Integer getTotalEvaluatedDecisions() { return totalEvaluatedDecisions; }
    public void setTotalEvaluatedDecisions(Integer totalEvaluatedDecisions) { this.totalEvaluatedDecisions = totalEvaluatedDecisions; }

    public Integer getSuccessfulDecisions() { return successfulDecisions; }
    public void setSuccessfulDecisions(Integer successfulDecisions) { this.successfulDecisions = successfulDecisions; }

    public BigDecimal getOverallSuccessRatePct() { return overallSuccessRatePct; }
    public void setOverallSuccessRatePct(BigDecimal overallSuccessRatePct) { this.overallSuccessRatePct = overallSuccessRatePct; }

    public String getConfidenceLevel() { return confidenceLevel; }
    public void setConfidenceLevel(String confidenceLevel) { this.confidenceLevel = confidenceLevel; }

    public BigDecimal getDataCompletenessPct() { return dataCompletenessPct; }
    public void setDataCompletenessPct(BigDecimal dataCompletenessPct) { this.dataCompletenessPct = dataCompletenessPct; }

    public String getSummaryInsight() { return summaryInsight; }
    public void setSummaryInsight(String summaryInsight) { this.summaryInsight = summaryInsight; }

    public Instant getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(Instant evaluatedAt) { this.evaluatedAt = evaluatedAt; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    public List<OptionCalibrationFactor> getOptionFactors() { return optionFactors; }
    public void setOptionFactors(List<OptionCalibrationFactor> optionFactors) { this.optionFactors = optionFactors; }
}
