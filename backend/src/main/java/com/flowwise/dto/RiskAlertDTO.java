package com.flowwise.dto;

import java.math.BigDecimal;

public class RiskAlertDTO {
    private Long id;
    private Long merchantId;
    private String riskKey;
    private String riskType; // LIQUIDITY, CASHFLOW, RECEIVABLES, PAYABLES, WORKING_CAPITAL, GOAL, DECISION_PERFORMANCE
    private String severity; // LOW, MEDIUM, HIGH, CRITICAL
    private String title;
    private String description;
    private BigDecimal baselineValue;
    private BigDecimal currentValue;
    private BigDecimal changePct;
    private BigDecimal thresholdValue;
    private String detectionWindow;
    private String status; // OPEN, ACKNOWLEDGED, RESOLVED
    private String confidenceStatus; // HIGH, MODERATE, LIMITED, INSUFFICIENT_DATA
    private String evidenceMetrics;
    private String evaluatedAt;

    public RiskAlertDTO() {}

    public RiskAlertDTO(Long id, Long merchantId, String riskKey, String riskType, String severity,
                        String title, String description, BigDecimal baselineValue, BigDecimal currentValue,
                        BigDecimal changePct, BigDecimal thresholdValue, String detectionWindow,
                        String status, String confidenceStatus, String evidenceMetrics, String evaluatedAt) {
        this.id = id;
        this.merchantId = merchantId;
        this.riskKey = riskKey;
        this.riskType = riskType;
        this.severity = severity;
        this.title = title;
        this.description = description;
        this.baselineValue = baselineValue;
        this.currentValue = currentValue;
        this.changePct = changePct;
        this.thresholdValue = thresholdValue;
        this.detectionWindow = detectionWindow;
        this.status = status;
        this.confidenceStatus = confidenceStatus;
        this.evidenceMetrics = evidenceMetrics;
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

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getBaselineValue() { return baselineValue; }
    public void setBaselineValue(BigDecimal baselineValue) { this.baselineValue = baselineValue; }

    public BigDecimal getCurrentValue() { return currentValue; }
    public void setCurrentValue(BigDecimal currentValue) { this.currentValue = currentValue; }

    public BigDecimal getChangePct() { return changePct; }
    public void setChangePct(BigDecimal changePct) { this.changePct = changePct; }

    public BigDecimal getThresholdValue() { return thresholdValue; }
    public void setThresholdValue(BigDecimal thresholdValue) { this.thresholdValue = thresholdValue; }

    public String getDetectionWindow() { return detectionWindow; }
    public void setDetectionWindow(String detectionWindow) { this.detectionWindow = detectionWindow; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getConfidenceStatus() { return confidenceStatus; }
    public void setConfidenceStatus(String confidenceStatus) { this.confidenceStatus = confidenceStatus; }

    public String getEvidenceMetrics() { return evidenceMetrics; }
    public void setEvidenceMetrics(String evidenceMetrics) { this.evidenceMetrics = evidenceMetrics; }

    public String getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(String evaluatedAt) { this.evaluatedAt = evaluatedAt; }
}
