package com.flowwise.dto;

import java.math.BigDecimal;
import java.util.List;

public class FinancialScenarioDTO {
    private Long id;
    private Long merchantId;
    private String scenarioKey;
    private String scenarioName;
    private String scenarioType;
    private String name;
    private String description;
    private BigDecimal revenueModifierPct;
    private BigDecimal expenseModifierPct;
    private BigDecimal receivableCollectionPct;
    private BigDecimal payableAccelerationPct;
    private BigDecimal projected7dCash;
    private BigDecimal projected30dCash;
    private BigDecimal projected60dCash;
    private BigDecimal projected90dCash;
    private BigDecimal runwayMonths;
    private String riskStatus;
    private Boolean goalAchievable;
    private String goalStatusDetail;
    private String horizon;
    private String status;
    private BigDecimal baselineScore;
    private BigDecimal projectedScore;
    private BigDecimal scoreDelta;
    private BigDecimal projectedCashImpact;
    private BigDecimal projectedRiskReduction;
    private BigDecimal projectedGoalImpact;
    private String confidenceStatus;
    private String assumptions;
    private String evidenceMetrics;
    private List<FinancialScenarioItemDTO> items;
    private Boolean estimate = true;
    private String createdAt;
    private String updatedAt;
    private String evaluatedAt;

    public FinancialScenarioDTO() {}

    // Sprint 18 Constructor
    public FinancialScenarioDTO(Long id, Long merchantId, String scenarioType, String name, String description,
                                BigDecimal revenueModifierPct, BigDecimal expenseModifierPct,
                                BigDecimal receivableCollectionPct, BigDecimal payableAccelerationPct,
                                BigDecimal projected7dCash, BigDecimal projected30dCash, BigDecimal projected60dCash,
                                BigDecimal projected90dCash, BigDecimal runwayMonths, String riskStatus,
                                Boolean goalAchievable, String goalStatusDetail, String assumptions,
                                Boolean estimate, String createdAt, String updatedAt) {
        this.id = id;
        this.merchantId = merchantId;
        this.scenarioType = scenarioType;
        this.name = name;
        this.scenarioName = name;
        this.scenarioKey = "SCENARIO_" + (scenarioType != null ? scenarioType : "CUSTOM");
        this.description = description;
        this.revenueModifierPct = revenueModifierPct;
        this.expenseModifierPct = expenseModifierPct;
        this.receivableCollectionPct = receivableCollectionPct;
        this.payableAccelerationPct = payableAccelerationPct;
        this.projected7dCash = projected7dCash;
        this.projected30dCash = projected30dCash;
        this.projected60dCash = projected60dCash;
        this.projected90dCash = projected90dCash;
        this.runwayMonths = runwayMonths;
        this.riskStatus = riskStatus;
        this.goalAchievable = goalAchievable;
        this.goalStatusDetail = goalStatusDetail;
        this.assumptions = assumptions;
        this.estimate = estimate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.evaluatedAt = createdAt;
    }

    // Sprint 36 Constructor
    public FinancialScenarioDTO(Long id, Long merchantId, String scenarioKey, String scenarioName, String horizon,
                                String status, BigDecimal baselineScore, BigDecimal projectedScore, BigDecimal scoreDelta,
                                BigDecimal projectedCashImpact, BigDecimal projectedRiskReduction, BigDecimal projectedGoalImpact,
                                String confidenceStatus, String assumptions, String evidenceMetrics,
                                List<FinancialScenarioItemDTO> items, String evaluatedAt) {
        this.id = id;
        this.merchantId = merchantId;
        this.scenarioKey = scenarioKey;
        this.scenarioName = scenarioName;
        this.name = scenarioName;
        this.scenarioType = "CUSTOM";
        this.horizon = horizon;
        this.status = status;
        this.baselineScore = baselineScore;
        this.projectedScore = projectedScore;
        this.scoreDelta = scoreDelta;
        this.projectedCashImpact = projectedCashImpact;
        this.projectedRiskReduction = projectedRiskReduction;
        this.projectedGoalImpact = projectedGoalImpact;
        this.confidenceStatus = confidenceStatus;
        this.assumptions = assumptions;
        this.evidenceMetrics = evidenceMetrics;
        this.items = items;
        this.evaluatedAt = evaluatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

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

    public String getGoalStatusDetail() { return goalStatusDetail; }
    public void setGoalStatusDetail(String goalStatusDetail) { this.goalStatusDetail = goalStatusDetail; }

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

    public List<FinancialScenarioItemDTO> getItems() { return items; }
    public void setItems(List<FinancialScenarioItemDTO> items) { this.items = items; }

    public Boolean getEstimate() { return estimate; }
    public Boolean isEstimate() { return estimate; }
    public void setEstimate(Boolean estimate) { this.estimate = estimate; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    public String getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(String evaluatedAt) { this.evaluatedAt = evaluatedAt; }
}
