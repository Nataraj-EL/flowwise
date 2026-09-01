package com.flowwise.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "financial_insights")
public class FinancialInsight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(name = "insight_type", nullable = false, length = 64)
    private String insightType;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "severity", nullable = false, length = 32)
    private String severity = "MEDIUM"; // HIGH, MEDIUM, LOW

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "evidence_metrics", nullable = false, columnDefinition = "TEXT")
    private String evidenceMetrics;

    @Column(name = "detected_period", nullable = false, length = 64)
    private String detectedPeriod;

    @Column(name = "status", nullable = false, length = 32)
    private String status = "NEW"; // NEW, ACKNOWLEDGED, DISMISSED

    @Column(name = "confidence_status", nullable = false, length = 32)
    private String confidenceStatus = "HIGH"; // HIGH, MODERATE, LIMITED

    @Column(name = "calculation_type", nullable = false, length = 32)
    private String calculationType = "ACTUAL"; // ACTUAL, ESTIMATE

    @Column(name = "assumptions", columnDefinition = "TEXT")
    private String assumptions;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    public FinancialInsight() {}

    public FinancialInsight(Merchant merchant, String insightType, String title, String severity,
                            String description, String evidenceMetrics, String detectedPeriod,
                            String confidenceStatus, String calculationType, String assumptions) {
        this.merchant = merchant;
        this.insightType = insightType;
        this.title = title;
        this.severity = severity != null ? severity : "MEDIUM";
        this.description = description;
        this.evidenceMetrics = evidenceMetrics;
        this.detectedPeriod = detectedPeriod;
        this.status = "NEW";
        this.confidenceStatus = confidenceStatus != null ? confidenceStatus : "HIGH";
        this.calculationType = calculationType != null ? calculationType : "ACTUAL";
        this.assumptions = assumptions;
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

    public String getInsightType() { return insightType; }
    public void setInsightType(String insightType) { this.insightType = insightType; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getEvidenceMetrics() { return evidenceMetrics; }
    public void setEvidenceMetrics(String evidenceMetrics) { this.evidenceMetrics = evidenceMetrics; }

    public String getDetectedPeriod() { return detectedPeriod; }
    public void setDetectedPeriod(String detectedPeriod) { this.detectedPeriod = detectedPeriod; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getConfidenceStatus() { return confidenceStatus; }
    public void setConfidenceStatus(String confidenceStatus) { this.confidenceStatus = confidenceStatus; }

    public String getCalculationType() { return calculationType; }
    public void setCalculationType(String calculationType) { this.calculationType = calculationType; }

    public String getAssumptions() { return assumptions; }
    public void setAssumptions(String assumptions) { this.assumptions = assumptions; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
