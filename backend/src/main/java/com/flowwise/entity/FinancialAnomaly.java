package com.flowwise.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "financial_anomalies")
public class FinancialAnomaly {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(name = "anomaly_key", nullable = false, length = 64)
    private String anomalyKey;

    @Column(name = "anomaly_type", nullable = false, length = 64)
    private String anomalyType;

    @Column(name = "severity", nullable = false, length = 32)
    private String severity = "MEDIUM"; // LOW, MEDIUM, HIGH, CRITICAL

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "baseline_value", nullable = false, precision = 15, scale = 2)
    private BigDecimal baselineValue = BigDecimal.ZERO;

    @Column(name = "observed_value", nullable = false, precision = 15, scale = 2)
    private BigDecimal observedValue = BigDecimal.ZERO;

    @Column(name = "deviation_pct", nullable = false, precision = 7, scale = 2)
    private BigDecimal deviationPct = BigDecimal.ZERO;

    @Column(name = "threshold_pct", nullable = false, precision = 7, scale = 2)
    private BigDecimal thresholdPct = new BigDecimal("20.00");

    @Column(name = "detection_window", nullable = false, length = 64)
    private String detectionWindow = "30-Day Window";

    @Column(name = "sample_size", nullable = false)
    private Integer sampleSize = 3;

    @Column(name = "status", nullable = false, length = 32)
    private String status = "OPEN"; // OPEN, ACKNOWLEDGED, RESOLVED

    @Column(name = "confidence_status", nullable = false, length = 32)
    private String confidenceStatus = "HIGH"; // HIGH, MODERATE, LIMITED, INSUFFICIENT_DATA

    @Column(name = "evidence_metrics", nullable = false, columnDefinition = "TEXT")
    private String evidenceMetrics;

    @Column(name = "evaluated_at", nullable = false)
    private Instant evaluatedAt = Instant.now();

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    public FinancialAnomaly() {}

    public FinancialAnomaly(Merchant merchant, String anomalyKey, String anomalyType, String severity,
                            String title, String description, BigDecimal baselineValue, BigDecimal observedValue,
                            BigDecimal deviationPct, BigDecimal thresholdPct, String detectionWindow,
                            Integer sampleSize, String status, String confidenceStatus, String evidenceMetrics) {
        this.merchant = merchant;
        this.anomalyKey = anomalyKey;
        this.anomalyType = anomalyType;
        this.severity = severity != null ? severity : "MEDIUM";
        this.title = title;
        this.description = description;
        this.baselineValue = baselineValue != null ? baselineValue : BigDecimal.ZERO;
        this.observedValue = observedValue != null ? observedValue : BigDecimal.ZERO;
        this.deviationPct = deviationPct != null ? deviationPct : BigDecimal.ZERO;
        this.thresholdPct = thresholdPct != null ? thresholdPct : new BigDecimal("20.00");
        this.detectionWindow = detectionWindow != null ? detectionWindow : "30-Day Window";
        this.sampleSize = sampleSize != null ? sampleSize : 3;
        this.status = status != null ? status : "OPEN";
        this.confidenceStatus = confidenceStatus != null ? confidenceStatus : "HIGH";
        this.evidenceMetrics = evidenceMetrics;
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

    public String getAnomalyKey() { return anomalyKey; }
    public void setAnomalyKey(String anomalyKey) { this.anomalyKey = anomalyKey; }

    public String getAnomalyType() { return anomalyType; }
    public void setAnomalyType(String anomalyType) { this.anomalyType = anomalyType; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getBaselineValue() { return baselineValue; }
    public void setBaselineValue(BigDecimal baselineValue) { this.baselineValue = baselineValue; }

    public BigDecimal getObservedValue() { return observedValue; }
    public void setObservedValue(BigDecimal observedValue) { this.observedValue = observedValue; }

    public BigDecimal getDeviationPct() { return deviationPct; }
    public void setDeviationPct(BigDecimal deviationPct) { this.deviationPct = deviationPct; }

    public BigDecimal getThresholdPct() { return thresholdPct; }
    public void setThresholdPct(BigDecimal thresholdPct) { this.thresholdPct = thresholdPct; }

    public String getDetectionWindow() { return detectionWindow; }
    public void setDetectionWindow(String detectionWindow) { this.detectionWindow = detectionWindow; }

    public Integer getSampleSize() { return sampleSize; }
    public void setSampleSize(Integer sampleSize) { this.sampleSize = sampleSize; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getConfidenceStatus() { return confidenceStatus; }
    public void setConfidenceStatus(String confidenceStatus) { this.confidenceStatus = confidenceStatus; }

    public String getEvidenceMetrics() { return evidenceMetrics; }
    public void setEvidenceMetrics(String evidenceMetrics) { this.evidenceMetrics = evidenceMetrics; }

    public Instant getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(Instant evaluatedAt) { this.evaluatedAt = evaluatedAt; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
