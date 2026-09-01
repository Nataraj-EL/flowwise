package com.flowwise.dto;

import java.math.BigDecimal;
import java.util.List;

public class AdvisoryActionPlanDTO {
    private Long id;
    private Long merchantId;
    private String planKey;
    private String horizon;
    private String status;
    private BigDecimal overallReadinessScore;
    private Integer totalStepsCount;
    private Integer readyStepsCount;
    private Integer blockedStepsCount;
    private String primaryNextAction;
    private String expectedBenefit;
    private String riskIfDelayed;
    private String evidenceMetrics;
    private String assumptions;
    private List<AdvisoryActionPlanStepDTO> steps;
    private String evaluatedAt;

    public AdvisoryActionPlanDTO() {}

    public AdvisoryActionPlanDTO(Long id, Long merchantId, String planKey, String horizon, String status,
                                 BigDecimal overallReadinessScore, Integer totalStepsCount, Integer readyStepsCount,
                                 Integer blockedStepsCount, String primaryNextAction, String expectedBenefit,
                                 String riskIfDelayed, String evidenceMetrics, String assumptions,
                                 List<AdvisoryActionPlanStepDTO> steps, String evaluatedAt) {
        this.id = id;
        this.merchantId = merchantId;
        this.planKey = planKey;
        this.horizon = horizon;
        this.status = status;
        this.overallReadinessScore = overallReadinessScore;
        this.totalStepsCount = totalStepsCount;
        this.readyStepsCount = readyStepsCount;
        this.blockedStepsCount = blockedStepsCount;
        this.primaryNextAction = primaryNextAction;
        this.expectedBenefit = expectedBenefit;
        this.riskIfDelayed = riskIfDelayed;
        this.evidenceMetrics = evidenceMetrics;
        this.assumptions = assumptions;
        this.steps = steps;
        this.evaluatedAt = evaluatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public String getPlanKey() { return planKey; }
    public void setPlanKey(String planKey) { this.planKey = planKey; }

    public String getHorizon() { return horizon; }
    public void setHorizon(String horizon) { this.horizon = horizon; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getOverallReadinessScore() { return overallReadinessScore; }
    public void setOverallReadinessScore(BigDecimal overallReadinessScore) { this.overallReadinessScore = overallReadinessScore; }

    public Integer getTotalStepsCount() { return totalStepsCount; }
    public void setTotalStepsCount(Integer totalStepsCount) { this.totalStepsCount = totalStepsCount; }

    public Integer getReadyStepsCount() { return readyStepsCount; }
    public void setReadyStepsCount(Integer readyStepsCount) { this.readyStepsCount = readyStepsCount; }

    public Integer getBlockedStepsCount() { return blockedStepsCount; }
    public void setBlockedStepsCount(Integer blockedStepsCount) { this.blockedStepsCount = blockedStepsCount; }

    public String getPrimaryNextAction() { return primaryNextAction; }
    public void setPrimaryNextAction(String primaryNextAction) { this.primaryNextAction = primaryNextAction; }

    public String getExpectedBenefit() { return expectedBenefit; }
    public void setExpectedBenefit(String expectedBenefit) { this.expectedBenefit = expectedBenefit; }

    public String getRiskIfDelayed() { return riskIfDelayed; }
    public void setRiskIfDelayed(String riskIfDelayed) { this.riskIfDelayed = riskIfDelayed; }

    public String getEvidenceMetrics() { return evidenceMetrics; }
    public void setEvidenceMetrics(String evidenceMetrics) { this.evidenceMetrics = evidenceMetrics; }

    public String getAssumptions() { return assumptions; }
    public void setAssumptions(String assumptions) { this.assumptions = assumptions; }

    public List<AdvisoryActionPlanStepDTO> getSteps() { return steps; }
    public void setSteps(List<AdvisoryActionPlanStepDTO> steps) { this.steps = steps; }

    public String getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(String evaluatedAt) { this.evaluatedAt = evaluatedAt; }
}
