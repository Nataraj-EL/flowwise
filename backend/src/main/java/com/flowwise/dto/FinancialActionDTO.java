package com.flowwise.dto;

public class FinancialActionDTO {
    private Long id;
    private Long merchantId;
    private String actionKey;
    private String title;
    private String severity; // HIGH, MEDIUM, LOW
    private String category;
    private String explanation;
    private String supportingEvidence;
    private String recommendedStep;
    private String status; // OPEN, DISMISSED, RESOLVED
    private String createdAt;
    private String updatedAt;

    public FinancialActionDTO() {}

    public FinancialActionDTO(Long id, Long merchantId, String actionKey, String title, 
                              String severity, String category, String explanation, 
                              String supportingEvidence, String recommendedStep, 
                              String status, String createdAt, String updatedAt) {
        this.id = id;
        this.merchantId = merchantId;
        this.actionKey = actionKey;
        this.title = title;
        this.severity = severity;
        this.category = category;
        this.explanation = explanation;
        this.supportingEvidence = supportingEvidence;
        this.recommendedStep = recommendedStep;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public String getActionKey() { return actionKey; }
    public void setActionKey(String actionKey) { this.actionKey = actionKey; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }

    public String getSupportingEvidence() { return supportingEvidence; }
    public void setSupportingEvidence(String supportingEvidence) { this.supportingEvidence = supportingEvidence; }

    public String getRecommendedStep() { return recommendedStep; }
    public void setRecommendedStep(String recommendedStep) { this.recommendedStep = recommendedStep; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
