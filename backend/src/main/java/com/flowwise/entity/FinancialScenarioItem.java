package com.flowwise.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "financial_scenario_items")
public class FinancialScenarioItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scenario_id", nullable = false)
    private FinancialScenario scenario;

    @Column(name = "intervention_type", nullable = false, length = 64)
    private String interventionType;

    @Column(name = "intervention_id")
    private Long interventionId;

    @Column(name = "rank_order", nullable = false)
    private Integer rankOrder = 1;

    @Column(name = "projected_impact", nullable = false, precision = 15, scale = 2)
    private BigDecimal projectedImpact = BigDecimal.ZERO;

    @Column(name = "projected_risk_reduction", nullable = false, precision = 5, scale = 2)
    private BigDecimal projectedRiskReduction = BigDecimal.ZERO;

    @Column(name = "projected_goal_impact", nullable = false, precision = 5, scale = 2)
    private BigDecimal projectedGoalImpact = BigDecimal.ZERO;

    @Column(name = "evidence_metrics", nullable = false, columnDefinition = "TEXT")
    private String evidenceMetrics;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    public FinancialScenarioItem() {}

    public FinancialScenarioItem(FinancialScenario scenario, String interventionType, Long interventionId,
                                 Integer rankOrder, BigDecimal projectedImpact, BigDecimal projectedRiskReduction,
                                 BigDecimal projectedGoalImpact, String evidenceMetrics) {
        this.scenario = scenario;
        this.interventionType = interventionType;
        this.interventionId = interventionId;
        this.rankOrder = rankOrder != null ? rankOrder : 1;
        this.projectedImpact = projectedImpact != null ? projectedImpact : BigDecimal.ZERO;
        this.projectedRiskReduction = projectedRiskReduction != null ? projectedRiskReduction : BigDecimal.ZERO;
        this.projectedGoalImpact = projectedGoalImpact != null ? projectedGoalImpact : BigDecimal.ZERO;
        this.evidenceMetrics = evidenceMetrics;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public FinancialScenario getScenario() { return scenario; }
    public void setScenario(FinancialScenario scenario) { this.scenario = scenario; }

    public String getInterventionType() { return interventionType; }
    public void setInterventionType(String interventionType) { this.interventionType = interventionType; }

    public Long getInterventionId() { return interventionId; }
    public void setInterventionId(Long interventionId) { this.interventionId = interventionId; }

    public Integer getRankOrder() { return rankOrder; }
    public void setRankOrder(Integer rankOrder) { this.rankOrder = rankOrder; }

    public BigDecimal getProjectedImpact() { return projectedImpact; }
    public void setProjectedImpact(BigDecimal projectedImpact) { this.projectedImpact = projectedImpact; }

    public BigDecimal getProjectedRiskReduction() { return projectedRiskReduction; }
    public void setProjectedRiskReduction(BigDecimal projectedRiskReduction) { this.projectedRiskReduction = projectedRiskReduction; }

    public BigDecimal getProjectedGoalImpact() { return projectedGoalImpact; }
    public void setProjectedGoalImpact(BigDecimal projectedGoalImpact) { this.projectedGoalImpact = projectedGoalImpact; }

    public String getEvidenceMetrics() { return evidenceMetrics; }
    public void setEvidenceMetrics(String evidenceMetrics) { this.evidenceMetrics = evidenceMetrics; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
