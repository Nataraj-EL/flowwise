package com.flowwise.dto;

import java.math.BigDecimal;
import java.util.List;

public class FinancialDecisionDTO {
    private Long id;
    private Long merchantId;
    private Long actionId;
    private String actionTitle;
    private Long goalId;
    private String goalName;
    private String decisionType;
    private String title;
    private String recommendation;
    private String decisionStatus;
    private String decisionNotes;
    private String decisionDate;
    private String outcomeStatus;
    private String outcomeNotes;

    // Sprint 37 fields
    private String decisionKey;
    private String status;
    private BigDecimal decisionScore;
    private BigDecimal riskScore;
    private BigDecimal impactScore;
    private BigDecimal urgencyScore;
    private BigDecimal confidenceScore;
    private String expectedBenefit;
    private String riskIfIgnored;
    private Long selectedScenarioId;
    private Long selectedPlanId;
    private Long selectedInterventionId;
    private String evidenceMetrics;
    private String assumptions;
    private String tradeoffs;
    private String confidenceStatus;
    private List<FinancialDecisionOptionDTO> options;
    private String createdAt;
    private String updatedAt;
    private String evaluatedAt;

    public FinancialDecisionDTO() {}

    // Sprint 17 Constructor
    public FinancialDecisionDTO(Long id, Long merchantId, Long actionId, String actionTitle, Long goalId,
                                String goalName, String decisionType, String title, String recommendation,
                                String decisionStatus, String decisionNotes, String decisionDate,
                                String outcomeStatus, String outcomeNotes, String createdAt, String updatedAt) {
        this.id = id;
        this.merchantId = merchantId;
        this.actionId = actionId;
        this.actionTitle = actionTitle;
        this.goalId = goalId;
        this.goalName = goalName;
        this.decisionType = decisionType;
        this.title = title;
        this.recommendation = recommendation;
        this.decisionStatus = decisionStatus;
        this.status = decisionStatus;
        this.decisionNotes = decisionNotes;
        this.decisionDate = decisionDate;
        this.outcomeStatus = outcomeStatus;
        this.outcomeNotes = outcomeNotes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.evaluatedAt = createdAt;
    }

    // Sprint 37 Constructor
    public FinancialDecisionDTO(Long id, Long merchantId, String decisionKey, String decisionType, String title,
                                String recommendation, String status, BigDecimal decisionScore, BigDecimal riskScore,
                                BigDecimal impactScore, BigDecimal urgencyScore, BigDecimal confidenceScore,
                                String expectedBenefit, String riskIfIgnored, Long selectedScenarioId,
                                Long selectedPlanId, Long selectedInterventionId, String evidenceMetrics,
                                String assumptions, String tradeoffs, String confidenceStatus,
                                List<FinancialDecisionOptionDTO> options, String evaluatedAt) {
        this.id = id;
        this.merchantId = merchantId;
        this.decisionKey = decisionKey;
        this.decisionType = decisionType;
        this.title = title;
        this.recommendation = recommendation;
        this.status = status;
        this.decisionStatus = status;
        this.decisionScore = decisionScore;
        this.riskScore = riskScore;
        this.impactScore = impactScore;
        this.urgencyScore = urgencyScore;
        this.confidenceScore = confidenceScore;
        this.expectedBenefit = expectedBenefit;
        this.riskIfIgnored = riskIfIgnored;
        this.selectedScenarioId = selectedScenarioId;
        this.selectedPlanId = selectedPlanId;
        this.selectedInterventionId = selectedInterventionId;
        this.evidenceMetrics = evidenceMetrics;
        this.assumptions = assumptions;
        this.tradeoffs = tradeoffs;
        this.confidenceStatus = confidenceStatus;
        this.options = options;
        this.evaluatedAt = evaluatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public Long getActionId() { return actionId; }
    public void setActionId(Long actionId) { this.actionId = actionId; }

    public String getActionTitle() { return actionTitle; }
    public void setActionTitle(String actionTitle) { this.actionTitle = actionTitle; }

    public Long getGoalId() { return goalId; }
    public void setGoalId(Long goalId) { this.goalId = goalId; }

    public String getGoalName() { return goalName; }
    public void setGoalName(String goalName) { this.goalName = goalName; }

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

    public String getDecisionDate() { return decisionDate; }
    public void setDecisionDate(String decisionDate) { this.decisionDate = decisionDate; }

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

    public List<FinancialDecisionOptionDTO> getOptions() { return options; }
    public void setOptions(List<FinancialDecisionOptionDTO> options) { this.options = options; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    public String getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(String evaluatedAt) { this.evaluatedAt = evaluatedAt; }
}
