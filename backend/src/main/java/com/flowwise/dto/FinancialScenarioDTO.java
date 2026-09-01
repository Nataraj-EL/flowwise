package com.flowwise.dto;

import java.math.BigDecimal;

public class FinancialScenarioDTO {
    private Long id;
    private Long merchantId;
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
    private String riskStatus; // FEASIBLE, CAUTION, HIGH_RISK
    private Boolean goalAchievable;
    private String goalStatusDetail;
    private String assumptions;
    private boolean estimate = true;
    private String createdAt;
    private String updatedAt;

    public FinancialScenarioDTO() {}

    public FinancialScenarioDTO(Long id, Long merchantId, String scenarioType, String name, 
                               String description, BigDecimal revenueModifierPct, 
                               BigDecimal expenseModifierPct, BigDecimal receivableCollectionPct, 
                               BigDecimal payableAccelerationPct, BigDecimal projected7dCash, 
                               BigDecimal projected30dCash, BigDecimal projected60dCash, 
                               BigDecimal projected90dCash, BigDecimal runwayMonths, 
                               String riskStatus, Boolean goalAchievable, String goalStatusDetail, 
                               String assumptions, boolean estimate, String createdAt, String updatedAt) {
        this.id = id;
        this.merchantId = merchantId;
        this.scenarioType = scenarioType;
        this.name = name;
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
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

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

    public String getAssumptions() { return assumptions; }
    public void setAssumptions(String assumptions) { this.assumptions = assumptions; }

    public boolean isEstimate() { return estimate; }
    public void setEstimate(boolean estimate) { this.estimate = estimate; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
