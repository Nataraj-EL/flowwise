package com.flowwise.dto;

public class FinancialInsightDTO {
    private Long id;
    private Long merchantId;
    private String insightType;
    private String title;
    private String severity; // HIGH, MEDIUM, LOW
    private String description;
    private String evidenceMetrics;
    private String detectedPeriod;
    private String status; // NEW, ACKNOWLEDGED, DISMISSED
    private String confidenceStatus; // HIGH, MODERATE, LIMITED
    private String calculationType; // ACTUAL, ESTIMATE
    private String assumptions;
    private String createdAt;
    private String updatedAt;

    public FinancialInsightDTO() {}

    public FinancialInsightDTO(Long id, Long merchantId, String insightType, String title, 
                               String severity, String description, String evidenceMetrics, 
                               String detectedPeriod, String status, String confidenceStatus, 
                               String calculationType, String assumptions, String createdAt, 
                               String updatedAt) {
        this.id = id;
        this.merchantId = merchantId;
        this.insightType = insightType;
        this.title = title;
        this.severity = severity;
        this.description = description;
        this.evidenceMetrics = evidenceMetrics;
        this.detectedPeriod = detectedPeriod;
        this.status = status;
        this.confidenceStatus = confidenceStatus;
        this.calculationType = calculationType;
        this.assumptions = assumptions;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

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

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
