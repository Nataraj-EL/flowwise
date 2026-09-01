package com.flowwise.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "financial_plan_outcomes")
public class FinancialPlanOutcome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private FinancialPlan plan;

    @Column(name = "horizon", nullable = false, length = 32)
    private String horizon = "30D"; // 7D, 30D, 60D, 90D

    @Column(name = "outcome_status", nullable = false, length = 32)
    private String outcomeStatus = "SUCCESSFUL"; // SUCCESSFUL, PARTIAL, INEFFECTIVE, INSUFFICIENT_DATA

    @Column(name = "expected_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal expectedScore = BigDecimal.ZERO;

    @Column(name = "actual_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal actualScore = BigDecimal.ZERO;

    @Column(name = "score_variance_pct", nullable = false, precision = 8, scale = 2)
    private BigDecimal scoreVariancePct = BigDecimal.ZERO;

    @Column(name = "expected_cash_impact", nullable = false, precision = 15, scale = 2)
    private BigDecimal expectedCashImpact = BigDecimal.ZERO;

    @Column(name = "actual_cash_impact", nullable = false, precision = 15, scale = 2)
    private BigDecimal actualCashImpact = BigDecimal.ZERO;

    @Column(name = "cash_variance_pct", nullable = false, precision = 8, scale = 2)
    private BigDecimal cashVariancePct = BigDecimal.ZERO;

    @Column(name = "risk_reduction_expected", nullable = false, precision = 5, scale = 2)
    private BigDecimal riskReductionExpected = BigDecimal.ZERO;

    @Column(name = "risk_reduction_actual", nullable = false, precision = 5, scale = 2)
    private BigDecimal riskReductionActual = BigDecimal.ZERO;

    @Column(name = "goal_progress_expected", nullable = false, precision = 5, scale = 2)
    private BigDecimal goalProgressExpected = BigDecimal.ZERO;

    @Column(name = "goal_progress_actual", nullable = false, precision = 5, scale = 2)
    private BigDecimal goalProgressActual = BigDecimal.ZERO;

    @Column(name = "effectiveness_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal effectivenessScore = BigDecimal.ZERO;

    @Column(name = "confidence_status", nullable = false, length = 32)
    private String confidenceStatus = "HIGH"; // HIGH, MODERATE, LIMITED, INSUFFICIENT_DATA

    @Column(name = "evidence_metrics", nullable = false, columnDefinition = "TEXT")
    private String evidenceMetrics;

    @Column(name = "assumptions", nullable = false, columnDefinition = "TEXT")
    private String assumptions;

    @Column(name = "evaluated_at", nullable = false)
    private Instant evaluatedAt = Instant.now();

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    public FinancialPlanOutcome() {}

    public FinancialPlanOutcome(Merchant merchant, FinancialPlan plan, String horizon, String outcomeStatus,
                               BigDecimal expectedScore, BigDecimal actualScore, BigDecimal scoreVariancePct,
                               BigDecimal expectedCashImpact, BigDecimal actualCashImpact, BigDecimal cashVariancePct,
                               BigDecimal riskReductionExpected, BigDecimal riskReductionActual,
                               BigDecimal goalProgressExpected, BigDecimal goalProgressActual,
                               BigDecimal effectivenessScore, String confidenceStatus,
                               String evidenceMetrics, String assumptions) {
        this.merchant = merchant;
        this.plan = plan;
        this.horizon = horizon != null ? horizon : "30D";
        this.outcomeStatus = outcomeStatus != null ? outcomeStatus : "SUCCESSFUL";
        this.expectedScore = expectedScore != null ? expectedScore : BigDecimal.ZERO;
        this.actualScore = actualScore != null ? actualScore : BigDecimal.ZERO;
        this.scoreVariancePct = scoreVariancePct != null ? scoreVariancePct : BigDecimal.ZERO;
        this.expectedCashImpact = expectedCashImpact != null ? expectedCashImpact : BigDecimal.ZERO;
        this.actualCashImpact = actualCashImpact != null ? actualCashImpact : BigDecimal.ZERO;
        this.cashVariancePct = cashVariancePct != null ? cashVariancePct : BigDecimal.ZERO;
        this.riskReductionExpected = riskReductionExpected != null ? riskReductionExpected : BigDecimal.ZERO;
        this.riskReductionActual = riskReductionActual != null ? riskReductionActual : BigDecimal.ZERO;
        this.goalProgressExpected = goalProgressExpected != null ? goalProgressExpected : BigDecimal.ZERO;
        this.goalProgressActual = goalProgressActual != null ? goalProgressActual : BigDecimal.ZERO;
        this.effectivenessScore = effectivenessScore != null ? effectivenessScore : BigDecimal.ZERO;
        this.confidenceStatus = confidenceStatus != null ? confidenceStatus : "HIGH";
        this.evidenceMetrics = evidenceMetrics;
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

    public FinancialPlan getPlan() { return plan; }
    public void setPlan(FinancialPlan plan) { this.plan = plan; }

    public String getHorizon() { return horizon; }
    public void setHorizon(String horizon) { this.horizon = horizon; }

    public String getOutcomeStatus() { return outcomeStatus; }
    public void setOutcomeStatus(String outcomeStatus) { this.outcomeStatus = outcomeStatus; }

    public BigDecimal getExpectedScore() { return expectedScore; }
    public void setExpectedScore(BigDecimal expectedScore) { this.expectedScore = expectedScore; }

    public BigDecimal getActualScore() { return actualScore; }
    public void setActualScore(BigDecimal actualScore) { this.actualScore = actualScore; }

    public BigDecimal getScoreVariancePct() { return scoreVariancePct; }
    public void setScoreVariancePct(BigDecimal scoreVariancePct) { this.scoreVariancePct = scoreVariancePct; }

    public BigDecimal getExpectedCashImpact() { return expectedCashImpact; }
    public void setExpectedCashImpact(BigDecimal expectedCashImpact) { this.expectedCashImpact = expectedCashImpact; }

    public BigDecimal getActualCashImpact() { return actualCashImpact; }
    public void setActualCashImpact(BigDecimal actualCashImpact) { this.actualCashImpact = actualCashImpact; }

    public BigDecimal getCashVariancePct() { return cashVariancePct; }
    public void setCashVariancePct(BigDecimal cashVariancePct) { this.cashVariancePct = cashVariancePct; }

    public BigDecimal getRiskReductionExpected() { return riskReductionExpected; }
    public void setRiskReductionExpected(BigDecimal riskReductionExpected) { this.riskReductionExpected = riskReductionExpected; }

    public BigDecimal getRiskReductionActual() { return riskReductionActual; }
    public void setRiskReductionActual(BigDecimal riskReductionActual) { this.riskReductionActual = riskReductionActual; }

    public BigDecimal getGoalProgressExpected() { return goalProgressExpected; }
    public void setGoalProgressExpected(BigDecimal goalProgressExpected) { this.goalProgressExpected = goalProgressExpected; }

    public BigDecimal getGoalProgressActual() { return goalProgressActual; }
    public void setGoalProgressActual(BigDecimal goalProgressActual) { this.goalProgressActual = goalProgressActual; }

    public BigDecimal getEffectivenessScore() { return effectivenessScore; }
    public void setEffectivenessScore(BigDecimal effectivenessScore) { this.effectivenessScore = effectivenessScore; }

    public String getConfidenceStatus() { return confidenceStatus; }
    public void setConfidenceStatus(String confidenceStatus) { this.confidenceStatus = confidenceStatus; }

    public String getEvidenceMetrics() { return evidenceMetrics; }
    public void setEvidenceMetrics(String evidenceMetrics) { this.evidenceMetrics = evidenceMetrics; }

    public String getAssumptions() { return assumptions; }
    public void setAssumptions(String assumptions) { this.assumptions = assumptions; }

    public Instant getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(Instant evaluatedAt) { this.evaluatedAt = evaluatedAt; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
