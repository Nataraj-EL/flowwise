package com.flowwise.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "advisory_action_outcomes", uniqueConstraints = {
    @UniqueConstraint(name = "uk_advisory_action_outcome_merchant_step_window", columnNames = {"merchant_id", "step_id", "evaluation_window"})
})
public class AdvisoryActionOutcome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private AdvisoryActionPlan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "step_id", nullable = false)
    private AdvisoryActionPlanStep step;

    @Column(name = "evaluation_window", nullable = false, length = 16)
    private String evaluationWindow = "30D"; // 7D, 30D, 60D, 90D

    @Column(name = "outcome_status", nullable = false, length = 32)
    private String outcomeStatus = "SUCCESSFUL"; // SUCCESSFUL, PARTIAL, INEFFECTIVE, INSUFFICIENT_DATA

    @Column(name = "expected_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal expectedScore = BigDecimal.ZERO;

    @Column(name = "actual_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal actualScore = BigDecimal.ZERO;

    @Column(name = "score_variance_pct", nullable = false, precision = 5, scale = 2)
    private BigDecimal scoreVariancePct = BigDecimal.ZERO;

    @Column(name = "expected_outcome", nullable = false, columnDefinition = "TEXT")
    private String expectedOutcome;

    @Column(name = "actual_outcome", nullable = false, columnDefinition = "TEXT")
    private String actualOutcome;

    @Column(name = "risk_reduction_expected", nullable = false, precision = 5, scale = 2)
    private BigDecimal riskReductionExpected = BigDecimal.ZERO;

    @Column(name = "risk_reduction_actual", nullable = false, precision = 5, scale = 2)
    private BigDecimal riskReductionActual = BigDecimal.ZERO;

    @Column(name = "financial_impact_expected", nullable = false, precision = 15, scale = 2)
    private BigDecimal financialImpactExpected = BigDecimal.ZERO;

    @Column(name = "financial_impact_actual", nullable = false, precision = 15, scale = 2)
    private BigDecimal financialImpactActual = BigDecimal.ZERO;

    @Column(name = "effectiveness_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal effectivenessScore = BigDecimal.ZERO;

    @Column(name = "confidence_status", nullable = false, length = 32)
    private String confidenceStatus = "HIGH";

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

    public AdvisoryActionOutcome() {}

    public AdvisoryActionOutcome(Merchant merchant, AdvisoryActionPlan plan, AdvisoryActionPlanStep step,
                                 String evaluationWindow, String outcomeStatus, BigDecimal expectedScore,
                                 BigDecimal actualScore, BigDecimal scoreVariancePct, String expectedOutcome,
                                 String actualOutcome, BigDecimal riskReductionExpected, BigDecimal riskReductionActual,
                                 BigDecimal financialImpactExpected, BigDecimal financialImpactActual,
                                 BigDecimal effectivenessScore, String confidenceStatus, String evidenceMetrics,
                                 String assumptions) {
        this.merchant = merchant;
        this.plan = plan;
        this.step = step;
        this.evaluationWindow = evaluationWindow != null ? evaluationWindow : "30D";
        this.outcomeStatus = outcomeStatus != null ? outcomeStatus : "SUCCESSFUL";
        this.expectedScore = expectedScore != null ? expectedScore : BigDecimal.ZERO;
        this.actualScore = actualScore != null ? actualScore : BigDecimal.ZERO;
        this.scoreVariancePct = scoreVariancePct != null ? scoreVariancePct : BigDecimal.ZERO;
        this.expectedOutcome = expectedOutcome;
        this.actualOutcome = actualOutcome;
        this.riskReductionExpected = riskReductionExpected != null ? riskReductionExpected : BigDecimal.ZERO;
        this.riskReductionActual = riskReductionActual != null ? riskReductionActual : BigDecimal.ZERO;
        this.financialImpactExpected = financialImpactExpected != null ? financialImpactExpected : BigDecimal.ZERO;
        this.financialImpactActual = financialImpactActual != null ? financialImpactActual : BigDecimal.ZERO;
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

    public AdvisoryActionPlan getPlan() { return plan; }
    public void setPlan(AdvisoryActionPlan plan) { this.plan = plan; }

    public AdvisoryActionPlanStep getStep() { return step; }
    public void setStep(AdvisoryActionPlanStep step) { this.step = step; }

    public String getEvaluationWindow() { return evaluationWindow; }
    public void setEvaluationWindow(String evaluationWindow) { this.evaluationWindow = evaluationWindow; }

    public String getOutcomeStatus() { return outcomeStatus; }
    public void setOutcomeStatus(String outcomeStatus) { this.outcomeStatus = outcomeStatus; }

    public BigDecimal getExpectedScore() { return expectedScore; }
    public void setExpectedScore(BigDecimal expectedScore) { this.expectedScore = expectedScore; }

    public BigDecimal getActualScore() { return actualScore; }
    public void setActualScore(BigDecimal actualScore) { this.actualScore = actualScore; }

    public BigDecimal getScoreVariancePct() { return scoreVariancePct; }
    public void setScoreVariancePct(BigDecimal scoreVariancePct) { this.scoreVariancePct = scoreVariancePct; }

    public String getExpectedOutcome() { return expectedOutcome; }
    public void setExpectedOutcome(String expectedOutcome) { this.expectedOutcome = expectedOutcome; }

    public String getActualOutcome() { return actualOutcome; }
    public void setActualOutcome(String actualOutcome) { this.actualOutcome = actualOutcome; }

    public BigDecimal getRiskReductionExpected() { return riskReductionExpected; }
    public void setRiskReductionExpected(BigDecimal riskReductionExpected) { this.riskReductionExpected = riskReductionExpected; }

    public BigDecimal getRiskReductionActual() { return riskReductionActual; }
    public void setRiskReductionActual(BigDecimal riskReductionActual) { this.riskReductionActual = riskReductionActual; }

    public BigDecimal getFinancialImpactExpected() { return financialImpactExpected; }
    public void setFinancialImpactExpected(BigDecimal financialImpactExpected) { this.financialImpactExpected = financialImpactExpected; }

    public BigDecimal getFinancialImpactActual() { return financialImpactActual; }
    public void setFinancialImpactActual(BigDecimal financialImpactActual) { this.financialImpactActual = financialImpactActual; }

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
