package com.flowwise.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "financial_decisions")
public class FinancialDecision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_id")
    private FinancialAction action;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_id")
    private FinancialGoal goal;

    @Column(name = "decision_type", nullable = false, length = 64)
    private String decisionType = "INTERVENTION_EXECUTION";

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "recommendation", columnDefinition = "TEXT")
    private String recommendation;

    @Column(name = "decision_status", nullable = false, length = 32)
    private String decisionStatus = "PENDING"; // PENDING, ACCEPTED, DECLINED, COMPLETED

    @Column(name = "decision_notes", length = 1000)
    private String decisionNotes;

    @Column(name = "decision_date")
    private LocalDate decisionDate = LocalDate.now();

    @Column(name = "outcome_status", length = 32)
    private String outcomeStatus = "UNKNOWN"; // UNKNOWN, POSITIVE, NEGATIVE, NEUTRAL

    @Column(name = "outcome_notes", length = 1000)
    private String outcomeNotes;

    // Sprint 37 fields
    @Column(name = "decision_key", nullable = false, length = 128)
    private String decisionKey = "DECISION_KEY";

    @Column(name = "status", nullable = false, length = 32)
    private String status = "RECOMMENDED"; // DRAFT, RECOMMENDED, ACKNOWLEDGED, COMPLETED, DISMISSED, ARCHIVED

    @Column(name = "decision_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal decisionScore = BigDecimal.ZERO;

    @Column(name = "risk_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal riskScore = BigDecimal.ZERO;

    @Column(name = "impact_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal impactScore = BigDecimal.ZERO;

    @Column(name = "urgency_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal urgencyScore = BigDecimal.ZERO;

    @Column(name = "confidence_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal confidenceScore = BigDecimal.ZERO;

    @Column(name = "expected_benefit", nullable = false, columnDefinition = "TEXT")
    private String expectedBenefit = "Expected benefit";

    @Column(name = "risk_if_ignored", nullable = false, columnDefinition = "TEXT")
    private String riskIfIgnored = "Risk if ignored";

    @Column(name = "selected_scenario_id")
    private Long selectedScenarioId;

    @Column(name = "selected_plan_id")
    private Long selectedPlanId;

    @Column(name = "selected_intervention_id")
    private Long selectedInterventionId;

    @Column(name = "evidence_metrics", nullable = false, columnDefinition = "TEXT")
    private String evidenceMetrics = "ADVISORY_RECOMMENDATION";

    @Column(name = "assumptions", nullable = false, columnDefinition = "TEXT")
    private String assumptions = "Advisory assumptions";

    @Column(name = "tradeoffs", nullable = false, columnDefinition = "TEXT")
    private String tradeoffs = "Trade-off analysis";

    @Column(name = "confidence_status", nullable = false, length = 32)
    private String confidenceStatus = "HIGH"; // HIGH, MODERATE, LIMITED, INSUFFICIENT_DATA

    @OneToMany(mappedBy = "decision", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FinancialDecisionOption> options = new ArrayList<>();

    @Column(name = "evaluated_at", nullable = false)
    private Instant evaluatedAt = Instant.now();

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    public FinancialDecision() {}

    // Sprint 17 Constructor
    public FinancialDecision(Merchant merchant, FinancialAction action, FinancialGoal goal, String decisionType,
                             String title, String recommendation, String decisionNotes, LocalDate decisionDate) {
        this.merchant = merchant;
        this.action = action;
        this.goal = goal;
        this.decisionType = decisionType != null ? decisionType : "INTERVENTION_EXECUTION";
        this.title = title;
        this.recommendation = recommendation;
        this.decisionStatus = "PENDING";
        this.status = "RECOMMENDED";
        this.decisionNotes = decisionNotes;
        this.decisionDate = decisionDate != null ? decisionDate : LocalDate.now();
        this.outcomeStatus = "UNKNOWN";
        this.evaluatedAt = Instant.now();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public FinancialDecision(Merchant merchant, FinancialAction action, FinancialGoal goal, String decisionType,
                             String title, String recommendation, String decisionStatus, String decisionNotes,
                             LocalDate decisionDate) {
        this(merchant, action, goal, decisionType, title, recommendation, decisionNotes, decisionDate);
        this.decisionStatus = decisionStatus != null ? decisionStatus : "PENDING";
    }

    // Sprint 37 Constructor
    public FinancialDecision(Merchant merchant, String decisionKey, String decisionType, String title,
                             String recommendation, String status, BigDecimal decisionScore, BigDecimal riskScore,
                             BigDecimal impactScore, BigDecimal urgencyScore, BigDecimal confidenceScore,
                             String expectedBenefit, String riskIfIgnored, Long selectedScenarioId,
                             Long selectedPlanId, Long selectedInterventionId, String evidenceMetrics,
                             String assumptions, String tradeoffs, String confidenceStatus) {
        this.merchant = merchant;
        this.decisionKey = decisionKey;
        this.decisionType = decisionType != null ? decisionType : "INTERVENTION_EXECUTION";
        this.title = title;
        this.recommendation = recommendation;
        this.status = status != null ? status : "RECOMMENDED";
        this.decisionStatus = "PENDING";
        this.decisionScore = decisionScore != null ? decisionScore : BigDecimal.ZERO;
        this.riskScore = riskScore != null ? riskScore : BigDecimal.ZERO;
        this.impactScore = impactScore != null ? impactScore : BigDecimal.ZERO;
        this.urgencyScore = urgencyScore != null ? urgencyScore : BigDecimal.ZERO;
        this.confidenceScore = confidenceScore != null ? confidenceScore : BigDecimal.ZERO;
        this.expectedBenefit = expectedBenefit;
        this.riskIfIgnored = riskIfIgnored;
        this.selectedScenarioId = selectedScenarioId;
        this.selectedPlanId = selectedPlanId;
        this.selectedInterventionId = selectedInterventionId;
        this.evidenceMetrics = evidenceMetrics;
        this.assumptions = assumptions;
        this.tradeoffs = tradeoffs;
        this.confidenceStatus = confidenceStatus != null ? confidenceStatus : "HIGH";
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

    public FinancialAction getAction() { return action; }
    public void setAction(FinancialAction action) { this.action = action; }

    public FinancialGoal getGoal() { return goal; }
    public void setGoal(FinancialGoal goal) { this.goal = goal; }

    public String getDecisionType() { return decisionType; }
    public void setDecisionType(String decisionType) { this.decisionType = decisionType; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getRecommendation() { return recommendation; }
    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }

    public String getDecisionStatus() { return decisionStatus; }
    public void setDecisionStatus(String decisionStatus) { this.decisionStatus = decisionStatus; }

    public String getDecisionNotes() { return decisionNotes; }
    public void setDecisionNotes(String decisionNotes) { this.decisionNotes = decisionNotes; }

    public LocalDate getDecisionDate() { return decisionDate; }
    public void setDecisionDate(LocalDate decisionDate) { this.decisionDate = decisionDate; }

    public String getOutcomeStatus() { return outcomeStatus; }
    public void setOutcomeStatus(String outcomeStatus) { this.outcomeStatus = outcomeStatus; }

    public String getOutcomeNotes() { return outcomeNotes; }
    public void setOutcomeNotes(String outcomeNotes) { this.outcomeNotes = outcomeNotes; }

    public String getDecisionKey() { return decisionKey; }
    public void setDecisionKey(String decisionKey) { this.decisionKey = decisionKey; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getDecisionScore() { return decisionScore; }
    public void setDecisionScore(BigDecimal decisionScore) { this.decisionScore = decisionScore; }

    public BigDecimal getRiskScore() { return riskScore; }
    public void setRiskScore(BigDecimal riskScore) { this.riskScore = riskScore; }

    public BigDecimal getImpactScore() { return impactScore; }
    public void setImpactScore(BigDecimal impactScore) { this.impactScore = impactScore; }

    public BigDecimal getUrgencyScore() { return urgencyScore; }
    public void setUrgencyScore(BigDecimal urgencyScore) { this.urgencyScore = urgencyScore; }

    public BigDecimal getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(BigDecimal confidenceScore) { this.confidenceScore = confidenceScore; }

    public String getExpectedBenefit() { return expectedBenefit; }
    public void setExpectedBenefit(String expectedBenefit) { this.expectedBenefit = expectedBenefit; }

    public String getRiskIfIgnored() { return riskIfIgnored; }
    public void setRiskIfIgnored(String riskIfIgnored) { this.riskIfIgnored = riskIfIgnored; }

    public Long getSelectedScenarioId() { return selectedScenarioId; }
    public void setSelectedScenarioId(Long selectedScenarioId) { this.selectedScenarioId = selectedScenarioId; }

    public Long getSelectedPlanId() { return selectedPlanId; }
    public void setSelectedPlanId(Long selectedPlanId) { this.selectedPlanId = selectedPlanId; }

    public Long getSelectedInterventionId() { return selectedInterventionId; }
    public void setSelectedInterventionId(Long selectedInterventionId) { this.selectedInterventionId = selectedInterventionId; }

    public String getEvidenceMetrics() { return evidenceMetrics; }
    public void setEvidenceMetrics(String evidenceMetrics) { this.evidenceMetrics = evidenceMetrics; }

    public String getAssumptions() { return assumptions; }
    public void setAssumptions(String assumptions) { this.assumptions = assumptions; }

    public String getTradeoffs() { return tradeoffs; }
    public void setTradeoffs(String tradeoffs) { this.tradeoffs = tradeoffs; }

    public String getConfidenceStatus() { return confidenceStatus; }
    public void setConfidenceStatus(String confidenceStatus) { this.confidenceStatus = confidenceStatus; }

    public List<FinancialDecisionOption> getOptions() { return options; }
    public void setOptions(List<FinancialDecisionOption> options) { this.options = options; }

    public Instant getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(Instant evaluatedAt) { this.evaluatedAt = evaluatedAt; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
