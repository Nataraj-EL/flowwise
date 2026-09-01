package com.flowwise.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "financial_scenarios")
public class FinancialScenario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(name = "scenario_type", nullable = false, length = 64)
    private String scenarioType; // BASELINE, CAUTIOUS, STRESS, CUSTOM

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "revenue_modifier_pct", nullable = false, precision = 5, scale = 2)
    private BigDecimal revenueModifierPct = BigDecimal.ZERO;

    @Column(name = "expense_modifier_pct", nullable = false, precision = 5, scale = 2)
    private BigDecimal expenseModifierPct = BigDecimal.ZERO;

    @Column(name = "receivable_collection_pct", nullable = false, precision = 5, scale = 2)
    private BigDecimal receivableCollectionPct = new BigDecimal("100.00");

    @Column(name = "payable_acceleration_pct", nullable = false, precision = 5, scale = 2)
    private BigDecimal payableAccelerationPct = new BigDecimal("100.00");

    @Column(name = "projected_7d_cash", nullable = false, precision = 15, scale = 2)
    private BigDecimal projected7dCash = BigDecimal.ZERO;

    @Column(name = "projected_30d_cash", nullable = false, precision = 15, scale = 2)
    private BigDecimal projected30dCash = BigDecimal.ZERO;

    @Column(name = "projected_60d_cash", nullable = false, precision = 15, scale = 2)
    private BigDecimal projected60dCash = BigDecimal.ZERO;

    @Column(name = "projected_90d_cash", nullable = false, precision = 15, scale = 2)
    private BigDecimal projected90dCash = BigDecimal.ZERO;

    @Column(name = "runway_months", nullable = false, precision = 5, scale = 2)
    private BigDecimal runwayMonths = BigDecimal.ZERO;

    @Column(name = "risk_status", nullable = false, length = 32)
    private String riskStatus = "FEASIBLE"; // FEASIBLE, CAUTION, HIGH_RISK

    @Column(name = "goal_achievable", nullable = false)
    private Boolean goalAchievable = true;

    @Column(name = "assumptions", columnDefinition = "TEXT")
    private String assumptions;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    public FinancialScenario() {}

    public FinancialScenario(Merchant merchant, String scenarioType, String name, String description,
                             BigDecimal revenueModifierPct, BigDecimal expenseModifierPct,
                             BigDecimal receivableCollectionPct, BigDecimal payableAccelerationPct,
                             BigDecimal projected7dCash, BigDecimal projected30dCash,
                             BigDecimal projected60dCash, BigDecimal projected90dCash,
                             BigDecimal runwayMonths, String riskStatus, Boolean goalAchievable,
                             String assumptions) {
        this.merchant = merchant;
        this.scenarioType = scenarioType;
        this.name = name;
        this.description = description;
        this.revenueModifierPct = revenueModifierPct != null ? revenueModifierPct : BigDecimal.ZERO;
        this.expenseModifierPct = expenseModifierPct != null ? expenseModifierPct : BigDecimal.ZERO;
        this.receivableCollectionPct = receivableCollectionPct != null ? receivableCollectionPct : new BigDecimal("100.00");
        this.payableAccelerationPct = payableAccelerationPct != null ? payableAccelerationPct : new BigDecimal("100.00");
        this.projected7dCash = projected7dCash != null ? projected7dCash : BigDecimal.ZERO;
        this.projected30dCash = projected30dCash != null ? projected30dCash : BigDecimal.ZERO;
        this.projected60dCash = projected60dCash != null ? projected60dCash : BigDecimal.ZERO;
        this.projected90dCash = projected90dCash != null ? projected90dCash : BigDecimal.ZERO;
        this.runwayMonths = runwayMonths != null ? runwayMonths : BigDecimal.ZERO;
        this.riskStatus = riskStatus != null ? riskStatus : "FEASIBLE";
        this.goalAchievable = goalAchievable != null ? goalAchievable : true;
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

    public String getScenarioType() { return scenarioType; }
    public void setScenarioType(String scenarioType) { this.scenarioType = scenarioType; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getRevenueModifierPct() { return revenueModifierPct; }
    public void setRevenueModifierPct(BigDecimal revenueModifierPct) { this.revenueModifierPct = revenueModifierPct; }

    public BigDecimal getExpenseModifierPct() { return expenseModifierPct; }
    public void setExpenseModifierPct(BigDecimal expenseModifierPct) { this.expenseModifierPct = expenseModifierPct; }

    public BigDecimal getReceivableCollectionPct() { return receivableCollectionPct; }
    public void setReceivableCollectionPct(BigDecimal receivableCollectionPct) { this.receivableCollectionPct = receivableCollectionPct; }

    public BigDecimal getPayableAccelerationPct() { return payableAccelerationPct; }
    public void setPayableAccelerationPct(BigDecimal payableAccelerationPct) { this.payableAccelerationPct = payableAccelerationPct; }

    public BigDecimal getProjected7dCash() { return projected7dCash; }
    public void setProjected7dCash(BigDecimal projected7dCash) { this.projected7dCash = projected7dCash; }

    public BigDecimal getProjected30dCash() { return projected30dCash; }
    public void setProjected30dCash(BigDecimal projected30dCash) { this.projected30dCash = projected30dCash; }

    public BigDecimal getProjected60dCash() { return projected60dCash; }
    public void setProjected60dCash(BigDecimal projected60dCash) { this.projected60dCash = projected60dCash; }

    public BigDecimal getProjected90dCash() { return projected90dCash; }
    public void setProjected90dCash(BigDecimal projected90dCash) { this.projected90dCash = projected90dCash; }

    public BigDecimal getRunwayMonths() { return runwayMonths; }
    public void setRunwayMonths(BigDecimal runwayMonths) { this.runwayMonths = runwayMonths; }

    public String getRiskStatus() { return riskStatus; }
    public void setRiskStatus(String riskStatus) { this.riskStatus = riskStatus; }

    public Boolean getGoalAchievable() { return goalAchievable; }
    public void setGoalAchievable(Boolean goalAchievable) { this.goalAchievable = goalAchievable; }

    public String getAssumptions() { return assumptions; }
    public void setAssumptions(String assumptions) { this.assumptions = assumptions; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
