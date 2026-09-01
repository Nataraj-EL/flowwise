package com.flowwise.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "financial_actions")
public class FinancialAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "merchant_id", nullable = false)
    private Long merchantId;

    @Column(name = "action_key", nullable = false)
    private String actionKey;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "severity", nullable = false)
    private String severity; // HIGH, MEDIUM, LOW

    @Column(name = "category", nullable = false)
    private String category; // PAYABLE_PRESSURE, RUNWAY_RISK, EXPENSE_SPIKE, RECEIVABLES_CONCENTRATION, OPPORTUNITY, HEALTH_MONITOR

    @Column(name = "explanation", nullable = false, columnDefinition = "TEXT")
    private String explanation;

    @Column(name = "supporting_evidence", columnDefinition = "TEXT")
    private String supportingEvidence;

    @Column(name = "recommended_step", nullable = false, columnDefinition = "TEXT")
    private String recommendedStep;

    @Column(name = "status", nullable = false)
    private String status = "OPEN"; // OPEN, DISMISSED, RESOLVED

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    public FinancialAction() {}

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) createdAt = OffsetDateTime.now();
        if (updatedAt == null) updatedAt = OffsetDateTime.now();
        if (status == null) status = "OPEN";
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
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

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
