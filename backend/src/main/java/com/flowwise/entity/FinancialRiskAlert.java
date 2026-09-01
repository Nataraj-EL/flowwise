package com.flowwise.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "financial_risk_alerts")
public class FinancialRiskAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(name = "risk_key", nullable = false, length = 64)
    private String riskKey;

    @Column(name = "risk_type", nullable = false, length = 64)
    private String riskType; // LIQUIDITY, CASHFLOW, RECEIVABLES, PAYABLES, WORKING_CAPITAL, GOAL, DECISION_PERFORMANCE

    @Column(name = "severity", nullable = false, length = 32)
    private String severity; // LOW, MEDIUM, HIGH, CRITICAL

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "baseline_value", nullable = false, precision = 15, scale = 2)
    private BigDecimal baselineValue = BigDecimal.ZERO;

    @Column(name = "current_value", nullable = false, precision = 15, scale = 2)
    private BigDecimal currentValue = BigDecimal.ZERO;

    @Column(name = "change_pct", nullable = false, precision = 7, scale = 2)
    private BigDecimal changePct = BigDecimal.ZERO;

    @Column(name = "threshold_value", nullable = false, precision = 15, scale = 2)
    private BigDecimal thresholdValue = BigDecimal.ZERO;

    @Column(name = "detection_window", nullable = false, length = 64)
    private String detectionWindow = "30D";

    @Column(name = "status", nullable = false, length = 32)
    private String status = "OPEN"; // OPEN, ACKNOWLEDGED, RESOLVED

    @Column(name = "confidence_status", nullable = false, length = 32)
    private String confidenceStatus = "HIGH"; // HIGH, MODERATE, LIMITED, INSUFFICIENT_DATA

    @Column(name = "evidence_metrics", columnDefinition = "TEXT")
    private String evidenceMetrics;

    @Column(name = "evaluated_at", nullable = false)
    private Instant evaluatedAt = Instant.now();

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    public FinancialRiskAlert() {}

    public FinancialRiskAlert(Merchant merchant, String riskKey, String riskType, String severity,
                              String title, String description, BigDecimal baselineValue,
                              BigDecimal currentValue, BigDecimal changePct, BigDecimal thresholdValue,
                              String detectionWindow, String status, String confidenceStatus,
                              String evidenceMetrics) {
        this.merchant = merchant;
        this.riskKey = riskKey;
        this.riskType = riskType;
        this.severity = severity;
        this.title = title;
        this.description = description;
        this.baselineValue = baselineValue != null ? baselineValue : BigDecimal.ZERO;
        this.currentValue = currentValue != null ? currentValue : BigDecimal.ZERO;
        this.changePct = changePct != null ? changePct : BigDecimal.ZERO;
        this.thresholdValue = thresholdValue != null ? thresholdValue : BigDecimal.ZERO;
        this.detectionWindow = detectionWindow != null ? detectionWindow : "30D";
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

    public Instant getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(Instant evaluatedAt) { this.evaluatedAt = evaluatedAt; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
