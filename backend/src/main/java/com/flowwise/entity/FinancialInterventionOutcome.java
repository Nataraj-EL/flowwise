package com.flowwise.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "financial_intervention_outcomes")
public class FinancialInterventionOutcome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intervention_id", nullable = false)
    private FinancialIntervention intervention;

    @Column(name = "intervention_type", nullable = false, length = 64)
    private String interventionType;

    @Column(name = "outcome_status", nullable = false, length = 32)
    private String outcomeStatus = "SUCCESSFUL"; // SUCCESSFUL, PARTIAL, INEFFECTIVE, INSUFFICIENT_DATA

    @Column(name = "evaluation_window", nullable = false, length = 32)
    private String evaluationWindow = "30D"; // 7D, 30D, 60D, 90D

    @Column(name = "expected_benefit", nullable = false, length = 255)
    private String expectedBenefit;

    @Column(name = "actual_benefit", nullable = false, length = 255)
    private String actualBenefit;

    @Column(name = "benefit_variance_pct", nullable = false, precision = 5, scale = 2)
    private BigDecimal benefitVariancePct = BigDecimal.ZERO;

    @Column(name = "expected_cash_impact", nullable = false, precision = 15, scale = 2)
    private BigDecimal expectedCashImpact = BigDecimal.ZERO;

    @Column(name = "actual_cash_impact", nullable = false, precision = 15, scale = 2)
    private BigDecimal actualCashImpact = BigDecimal.ZERO;

    @Column(name = "cash_impact_variance_pct", nullable = false, precision = 5, scale = 2)
    private BigDecimal cashImpactVariancePct = BigDecimal.ZERO;

    @Column(name = "expected_risk_reduction", nullable = false, precision = 5, scale = 2)
    private BigDecimal expectedRiskReduction = BigDecimal.ZERO;

    @Column(name = "actual_risk_reduction", nullable = false, precision = 5, scale = 2)
    private BigDecimal actualRiskReduction = BigDecimal.ZERO;

    @Column(name = "goal_impact_variance_pct", nullable = false, precision = 5, scale = 2)
    private BigDecimal goalImpactVariancePct = BigDecimal.ZERO;

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

    public FinancialInterventionOutcome() {}

    public FinancialInterventionOutcome(Merchant merchant, FinancialIntervention intervention, String interventionType,
                                      String outcomeStatus, String evaluationWindow, String expectedBenefit,
                                      String actualBenefit, BigDecimal benefitVariancePct, BigDecimal expectedCashImpact,
                                      BigDecimal actualCashImpact, BigDecimal cashImpactVariancePct,
                                      BigDecimal expectedRiskReduction, BigDecimal actualRiskReduction,
                                      BigDecimal goalImpactVariancePct, BigDecimal effectivenessScore,
                                      String confidenceStatus, String evidenceMetrics, String assumptions) {
        this.merchant = merchant;
        this.intervention = intervention;
        this.interventionType = interventionType;
        this.outcomeStatus = outcomeStatus != null ? outcomeStatus : "SUCCESSFUL";
        this.evaluationWindow = evaluationWindow != null ? evaluationWindow : "30D";
        this.expectedBenefit = expectedBenefit;
        this.actualBenefit = actualBenefit;
        this.benefitVariancePct = benefitVariancePct != null ? benefitVariancePct : BigDecimal.ZERO;
        this.expectedCashImpact = expectedCashImpact != null ? expectedCashImpact : BigDecimal.ZERO;
        this.actualCashImpact = actualCashImpact != null ? actualCashImpact : BigDecimal.ZERO;
        this.cashImpactVariancePct = cashImpactVariancePct != null ? cashImpactVariancePct : BigDecimal.ZERO;
        this.expectedRiskReduction = expectedRiskReduction != null ? expectedRiskReduction : BigDecimal.ZERO;
        this.actualRiskReduction = actualRiskReduction != null ? actualRiskReduction : BigDecimal.ZERO;
        this.goalImpactVariancePct = goalImpactVariancePct != null ? goalImpactVariancePct : BigDecimal.ZERO;
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

    public FinancialIntervention getIntervention() { return intervention; }
    public void setIntervention(FinancialIntervention intervention) { this.intervention = intervention; }

    public String getInterventionType() { return interventionType; }
    public void setInterventionType(String interventionType) { this.interventionType = interventionType; }

    public String getOutcomeStatus() { return outcomeStatus; }
    public void setOutcomeStatus(String outcomeStatus) { this.outcomeStatus = outcomeStatus; }

    public String getEvaluationWindow() { return evaluationWindow; }
    public void setEvaluationWindow(String evaluationWindow) { this.evaluationWindow = evaluationWindow; }

    public String getExpectedBenefit() { return expectedBenefit; }
    public void setExpectedBenefit(String expectedBenefit) { this.expectedBenefit = expectedBenefit; }

    public String getActualBenefit() { return actualBenefit; }
    public void setActualBenefit(String actualBenefit) { this.actualBenefit = actualBenefit; }

    public BigDecimal getBenefitVariancePct() { return benefitVariancePct; }
    public void setBenefitVariancePct(BigDecimal benefitVariancePct) { this.benefitVariancePct = benefitVariancePct; }

    public BigDecimal getExpectedCashImpact() { return expectedCashImpact; }
    public void setExpectedCashImpact(BigDecimal expectedCashImpact) { this.expectedCashImpact = expectedCashImpact; }

    public BigDecimal getActualCashImpact() { return actualCashImpact; }
    public void setActualCashImpact(BigDecimal actualCashImpact) { this.actualCashImpact = actualCashImpact; }

    public BigDecimal getCashImpactVariancePct() { return cashImpactVariancePct; }
    public void setCashImpactVariancePct(BigDecimal cashImpactVariancePct) { this.cashImpactVariancePct = cashImpactVariancePct; }

    public BigDecimal getExpectedRiskReduction() { return expectedRiskReduction; }
    public void setExpectedRiskReduction(BigDecimal expectedRiskReduction) { this.expectedRiskReduction = expectedRiskReduction; }

    public BigDecimal getActualRiskReduction() { return actualRiskReduction; }
    public void setActualRiskReduction(BigDecimal actualRiskReduction) { this.actualRiskReduction = actualRiskReduction; }

    public BigDecimal getGoalImpactVariancePct() { return goalImpactVariancePct; }
    public void setGoalImpactVariancePct(BigDecimal goalImpactVariancePct) { this.goalImpactVariancePct = goalImpactVariancePct; }

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
