package com.flowwise.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "financial_decision_outcomes", uniqueConstraints = {
    @UniqueConstraint(name = "uk_decision_outcome_merchant_decision_window", columnNames = {"merchant_id", "decision_id", "evaluation_window"})
})
public class FinancialDecisionOutcome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "decision_id", nullable = false)
    private FinancialDecision decision;

    @Column(name = "outcome_status", nullable = false, length = 32)
    private String outcomeStatus = "SUCCESSFUL"; // SUCCESSFUL, PARTIAL, INEFFECTIVE, INSUFFICIENT_DATA

    @Column(name = "evaluation_window", nullable = false, length = 16)
    private String evaluationWindow = "30D"; // 7D, 30D, 60D, 90D

    @Column(name = "expected_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal expectedScore = BigDecimal.ZERO;

    @Column(name = "actual_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal actualScore = BigDecimal.ZERO;

    @Column(name = "score_variance_pct", nullable = false, precision = 5, scale = 2)
    private BigDecimal scoreVariancePct = BigDecimal.ZERO;

    @Column(name = "expected_cash_impact", nullable = false, precision = 15, scale = 2)
    private BigDecimal expectedCashImpact = BigDecimal.ZERO;

    @Column(name = "actual_cash_impact", nullable = false, precision = 15, scale = 2)
    private BigDecimal actualCashImpact = BigDecimal.ZERO;

    @Column(name = "cash_variance_pct", nullable = false, precision = 5, scale = 2)
    private BigDecimal cashVariancePct = BigDecimal.ZERO;

    @Column(name = "expected_risk_reduction", nullable = false, precision = 5, scale = 2)
    private BigDecimal expectedRiskReduction = BigDecimal.ZERO;

    @Column(name = "actual_risk_reduction", nullable = false, precision = 5, scale = 2)
    private BigDecimal actualRiskReduction = BigDecimal.ZERO;

    @Column(name = "expected_goal_impact", nullable = false, precision = 5, scale = 2)
    private BigDecimal expectedGoalImpact = BigDecimal.ZERO;

    @Column(name = "actual_goal_impact", nullable = false, precision = 5, scale = 2)
    private BigDecimal actualGoalImpact = BigDecimal.ZERO;

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

    public FinancialDecisionOutcome() {}

    public FinancialDecisionOutcome(Merchant merchant, FinancialDecision decision, String outcomeStatus,
                                    String evaluationWindow, BigDecimal expectedScore, BigDecimal actualScore,
                                    BigDecimal scoreVariancePct, BigDecimal expectedCashImpact, BigDecimal actualCashImpact,
                                    BigDecimal cashVariancePct, BigDecimal expectedRiskReduction, BigDecimal actualRiskReduction,
                                    BigDecimal expectedGoalImpact, BigDecimal actualGoalImpact, BigDecimal effectivenessScore,
                                    String confidenceStatus, String evidenceMetrics, String assumptions) {
        this.merchant = merchant;
        this.decision = decision;
        this.outcomeStatus = outcomeStatus != null ? outcomeStatus : "SUCCESSFUL";
        this.evaluationWindow = evaluationWindow != null ? evaluationWindow : "30D";
        this.expectedScore = expectedScore != null ? expectedScore : BigDecimal.ZERO;
        this.actualScore = actualScore != null ? actualScore : BigDecimal.ZERO;
        this.scoreVariancePct = scoreVariancePct != null ? scoreVariancePct : BigDecimal.ZERO;
        this.expectedCashImpact = expectedCashImpact != null ? expectedCashImpact : BigDecimal.ZERO;
        this.actualCashImpact = actualCashImpact != null ? actualCashImpact : BigDecimal.ZERO;
        this.cashVariancePct = cashVariancePct != null ? cashVariancePct : BigDecimal.ZERO;
        this.expectedRiskReduction = expectedRiskReduction != null ? expectedRiskReduction : BigDecimal.ZERO;
        this.actualRiskReduction = actualRiskReduction != null ? actualRiskReduction : BigDecimal.ZERO;
        this.expectedGoalImpact = expectedGoalImpact != null ? expectedGoalImpact : BigDecimal.ZERO;
        this.actualGoalImpact = actualGoalImpact != null ? actualGoalImpact : BigDecimal.ZERO;
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

    public FinancialDecision getDecision() { return decision; }
    public void setDecision(FinancialDecision decision) { this.decision = decision; }

    public String getOutcomeStatus() { return outcomeStatus; }
    public void setOutcomeStatus(String outcomeStatus) { this.outcomeStatus = outcomeStatus; }

    public String getEvaluationWindow() { return evaluationWindow; }
    public void setEvaluationWindow(String evaluationWindow) { this.evaluationWindow = evaluationWindow; }

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

    public BigDecimal getExpectedRiskReduction() { return expectedRiskReduction; }
    public void setExpectedRiskReduction(BigDecimal expectedRiskReduction) { this.expectedRiskReduction = expectedRiskReduction; }

    public BigDecimal getActualRiskReduction() { return actualRiskReduction; }
    public void setActualRiskReduction(BigDecimal actualRiskReduction) { this.actualRiskReduction = actualRiskReduction; }

    public BigDecimal getExpectedGoalImpact() { return expectedGoalImpact; }
    public void setExpectedGoalImpact(BigDecimal expectedGoalImpact) { this.expectedGoalImpact = expectedGoalImpact; }

    public BigDecimal getActualGoalImpact() { return actualGoalImpact; }
    public void setActualGoalImpact(BigDecimal actualGoalImpact) { this.actualGoalImpact = actualGoalImpact; }

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
