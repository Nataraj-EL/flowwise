package com.flowwise.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "financial_scenarios")
public class FinancialScenario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(name = "scenario_key", nullable = false, length = 128)
    private String scenarioKey = "SCENARIO_KEY";

    @Column(name = "scenario_name", nullable = false, length = 255)
    private String scenarioName = "Scenario";

    // Sprint 18 field aliases
    @Column(name = "scenario_type", length = 64)
    private String scenarioType = "CUSTOM";

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "revenue_modifier_pct", precision = 5, scale = 2)
    private BigDecimal revenueModifierPct = BigDecimal.ZERO;

    @Column(name = "expense_modifier_pct", precision = 5, scale = 2)
    private BigDecimal expenseModifierPct = BigDecimal.ZERO;

    @Column(name = "receivable_collection_pct", precision = 5, scale = 2)
    private BigDecimal receivableCollectionPct = new BigDecimal("100.00");

    @Column(name = "payable_acceleration_pct", precision = 5, scale = 2)
    private BigDecimal payableAccelerationPct = new BigDecimal("100.00");

    @Column(name = "projected_7d_cash", precision = 15, scale = 2)
    private BigDecimal projected7dCash = BigDecimal.ZERO;

    @Column(name = "projected_30d_cash", precision = 15, scale = 2)
    private BigDecimal projected30dCash = BigDecimal.ZERO;

    @Column(name = "projected_60d_cash", precision = 15, scale = 2)
    private BigDecimal projected60dCash = BigDecimal.ZERO;

    @Column(name = "projected_90d_cash", precision = 15, scale = 2)
    private BigDecimal projected90dCash = BigDecimal.ZERO;

    @Column(name = "runway_months", precision = 5, scale = 2)
    private BigDecimal runwayMonths = BigDecimal.ZERO;

    @Column(name = "risk_status", length = 32)
    private String riskStatus = "FEASIBLE";

    @Column(name = "goal_achievable")
    private Boolean goalAchievable = true;

    // Sprint 36 fields
    @Column(name = "horizon", nullable = false, length = 32)
    private String horizon = "30D"; // 7D, 30D, 60D, 90D

    @Column(name = "status", nullable = false, length = 32)
    private String status = "EVALUATED"; // DRAFT, EVALUATED, ARCHIVED

    @Column(name = "baseline_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal baselineScore = BigDecimal.ZERO;

    @Column(name = "projected_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal projectedScore = BigDecimal.ZERO;

    @Column(name = "score_delta", nullable = false, precision = 5, scale = 2)
    private BigDecimal scoreDelta = BigDecimal.ZERO;

    @Column(name = "projected_cash_impact", nullable = false, precision = 15, scale = 2)
    private BigDecimal projectedCashImpact = BigDecimal.ZERO;

    @Column(name = "projected_risk_reduction", nullable = false, precision = 5, scale = 2)
    private BigDecimal projectedRiskReduction = BigDecimal.ZERO;

    @Column(name = "projected_goal_impact", nullable = false, precision = 5, scale = 2)
    private BigDecimal projectedGoalImpact = BigDecimal.ZERO;

    @Column(name = "confidence_status", nullable = false, length = 32)
    private String confidenceStatus = "HIGH"; // HIGH, MODERATE, LIMITED, INSUFFICIENT_DATA

    @Column(name = "assumptions", nullable = false, columnDefinition = "TEXT")
    private String assumptions = "Advisory simulation assumptions";

    @Column(name = "evidence_metrics", nullable = false, columnDefinition = "TEXT")
    private String evidenceMetrics = "Evidence metrics";

    @OneToMany(mappedBy = "scenario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FinancialScenarioItem> items = new ArrayList<>();

    @Column(name = "evaluated_at", nullable = false)
    private Instant evaluatedAt = Instant.now();

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    public FinancialScenario() {}

    public FinancialScenario(Merchant merchant, String scenarioKey, String scenarioName, String horizon,
                             String status, BigDecimal baselineScore, BigDecimal projectedScore,
                             BigDecimal scoreDelta, BigDecimal projectedCashImpact, BigDecimal projectedRiskReduction,
                             BigDecimal projectedGoalImpact, String confidenceStatus, String assumptions,
                             String evidenceMetrics) {
        this.merchant = merchant;
        this.scenarioKey = scenarioKey;
        this.scenarioName = scenarioName;
        this.name = scenarioName;
        this.horizon = horizon != null ? horizon : "30D";
        this.status = status != null ? status : "EVALUATED";
        this.baselineScore = baselineScore != null ? baselineScore : BigDecimal.ZERO;
        this.projectedScore = projectedScore != null ? projectedScore : BigDecimal.ZERO;
        this.scoreDelta = scoreDelta != null ? scoreDelta : BigDecimal.ZERO;
        this.projectedCashImpact = projectedCashImpact != null ? projectedCashImpact : BigDecimal.ZERO;
        this.projectedRiskReduction = projectedRiskReduction != null ? projectedRiskReduction : BigDecimal.ZERO;
        this.projectedGoalImpact = projectedGoalImpact != null ? projectedGoalImpact : BigDecimal.ZERO;
        this.confidenceStatus = confidenceStatus != null ? confidenceStatus : "HIGH";
        this.assumptions = assumptions;
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

    public String getScenarioKey() { return scenarioKey; }
    public void setScenarioKey(String scenarioKey) { this.scenarioKey = scenarioKey; }

    public String getScenarioName() { return scenarioName; }
    public void setScenarioName(String scenarioName) { this.scenarioName = scenarioName; }

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

    public String getHorizon() { return horizon; }
    public void setHorizon(String horizon) { this.horizon = horizon; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getBaselineScore() { return baselineScore; }
    public void setBaselineScore(BigDecimal baselineScore) { this.baselineScore = baselineScore; }

    public BigDecimal getProjectedScore() { return projectedScore; }
    public void setProjectedScore(BigDecimal projectedScore) { this.projectedScore = projectedScore; }

    public BigDecimal getScoreDelta() { return scoreDelta; }
    public void setScoreDelta(BigDecimal scoreDelta) { this.scoreDelta = scoreDelta; }

    public BigDecimal getProjectedCashImpact() { return projectedCashImpact; }
    public void setProjectedCashImpact(BigDecimal projectedCashImpact) { this.projectedCashImpact = projectedCashImpact; }

    public BigDecimal getProjectedRiskReduction() { return projectedRiskReduction; }
    public void setProjectedRiskReduction(BigDecimal projectedRiskReduction) { this.projectedRiskReduction = projectedRiskReduction; }

    public BigDecimal getProjectedGoalImpact() { return projectedGoalImpact; }
    public void setProjectedGoalImpact(BigDecimal projectedGoalImpact) { this.projectedGoalImpact = projectedGoalImpact; }

    public String getConfidenceStatus() { return confidenceStatus; }
    public void setConfidenceStatus(String confidenceStatus) { this.confidenceStatus = confidenceStatus; }

    public String getAssumptions() { return assumptions; }
    public void setAssumptions(String assumptions) { this.assumptions = assumptions; }

    public String getEvidenceMetrics() { return evidenceMetrics; }
    public void setEvidenceMetrics(String evidenceMetrics) { this.evidenceMetrics = evidenceMetrics; }

    public List<FinancialScenarioItem> getItems() { return items; }
    public void setItems(List<FinancialScenarioItem> items) { this.items = items; }

    public Instant getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(Instant evaluatedAt) { this.evaluatedAt = evaluatedAt; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
