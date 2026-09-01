package com.flowwise.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "financial_plans")
public class FinancialPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(name = "plan_key", nullable = false, length = 128)
    private String planKey;

    @Column(name = "horizon", nullable = false, length = 32)
    private String horizon = "30D"; // 7D, 30D, 90D

    @Column(name = "status", nullable = false, length = 32)
    private String status = "DRAFT"; // DRAFT, ACTIVE, COMPLETED, ARCHIVED

    @Column(name = "overall_plan_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal overallPlanScore = BigDecimal.ZERO;

    @Column(name = "primary_focus_area", nullable = false, length = 255)
    private String primaryFocusArea;

    @Column(name = "summary_explanation", nullable = false, columnDefinition = "TEXT")
    private String summaryExplanation;

    @Column(name = "assumptions", nullable = false, columnDefinition = "TEXT")
    private String assumptions;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FinancialPlanItem> items = new ArrayList<>();

    @Column(name = "evaluated_at", nullable = false)
    private Instant evaluatedAt = Instant.now();

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    public FinancialPlan() {}

    public FinancialPlan(Merchant merchant, String planKey, String horizon, String status,
                         BigDecimal overallPlanScore, String primaryFocusArea,
                         String summaryExplanation, String assumptions) {
        this.merchant = merchant;
        this.planKey = planKey;
        this.horizon = horizon != null ? horizon : "30D";
        this.status = status != null ? status : "DRAFT";
        this.overallPlanScore = overallPlanScore != null ? overallPlanScore : BigDecimal.ZERO;
        this.primaryFocusArea = primaryFocusArea;
        this.summaryExplanation = summaryExplanation;
        this.assumptions = assumptions;
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

    public String getPlanKey() { return planKey; }
    public void setPlanKey(String planKey) { this.planKey = planKey; }

    public String getHorizon() { return horizon; }
    public void setHorizon(String horizon) { this.horizon = horizon; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getOverallPlanScore() { return overallPlanScore; }
    public void setOverallPlanScore(BigDecimal overallPlanScore) { this.overallPlanScore = overallPlanScore; }

    public String getPrimaryFocusArea() { return primaryFocusArea; }
    public void setPrimaryFocusArea(String primaryFocusArea) { this.primaryFocusArea = primaryFocusArea; }

    public String getSummaryExplanation() { return summaryExplanation; }
    public void setSummaryExplanation(String summaryExplanation) { this.summaryExplanation = summaryExplanation; }

    public String getAssumptions() { return assumptions; }
    public void setAssumptions(String assumptions) { this.assumptions = assumptions; }

    public List<FinancialPlanItem> getItems() { return items; }
    public void setItems(List<FinancialPlanItem> items) { this.items = items; }

    public Instant getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(Instant evaluatedAt) { this.evaluatedAt = evaluatedAt; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
