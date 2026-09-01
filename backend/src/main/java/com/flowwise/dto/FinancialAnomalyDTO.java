package com.flowwise.dto;

import java.math.BigDecimal;

public class FinancialAnomalyDTO {
    private Long id;
    private Long merchantId;
    private String anomalyKey;
    private String anomalyType;
    private String severity;
    private String title;
    private String description;
    private BigDecimal baselineValue;
    private BigDecimal observedValue;
    private BigDecimal deviationPct;
    private BigDecimal thresholdPct;
    private String detectionWindow;
    private int sampleSize;
    private String status;
    private String confidenceStatus;
    private String evidenceMetrics;
    private String evaluatedAt;

    public FinancialAnomalyDTO() {}

    public FinancialAnomalyDTO(Long id, Long merchantId, String anomalyKey, String anomalyType, String severity,
                               String title, String description, BigDecimal baselineValue, BigDecimal observedValue,
                               BigDecimal deviationPct, BigDecimal thresholdPct, String detectionWindow,
                               int sampleSize, String status, String confidenceStatus, String evidenceMetrics,
                               String evaluatedAt) {
        this.id = id;
        this.merchantId = merchantId;
        this.anomalyKey = anomalyKey;
        this.anomalyType = anomalyType;
        this.severity = severity;
        this.title = title;
        this.description = description;
        this.baselineValue = baselineValue;
        this.observedValue = observedValue;
        this.deviationPct = deviationPct;
        this.thresholdPct = thresholdPct;
        this.detectionWindow = detectionWindow;
        this.sampleSize = sampleSize;
        this.status = status;
        this.confidenceStatus = confidenceStatus;
        this.evidenceMetrics = evidenceMetrics;
        this.evaluatedAt = evaluatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

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

    public int getSampleSize() { return sampleSize; }
    public void setSampleSize(int sampleSize) { this.sampleSize = sampleSize; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getConfidenceStatus() { return confidenceStatus; }
    public void setConfidenceStatus(String confidenceStatus) { this.confidenceStatus = confidenceStatus; }

    public String getEvidenceMetrics() { return evidenceMetrics; }
    public void setEvidenceMetrics(String evidenceMetrics) { this.evidenceMetrics = evidenceMetrics; }

    public String getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(String evaluatedAt) { this.evaluatedAt = evaluatedAt; }
}
