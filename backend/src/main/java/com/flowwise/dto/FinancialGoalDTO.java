package com.flowwise.dto;

import java.math.BigDecimal;

public class FinancialGoalDTO {
    private Long id;
    private Long merchantId;
    private String goalType; // CASH_RESERVE, WORKING_CAPITAL, DEBT_REDUCTION, RECEIVABLES_COLLECTION, EXPENSE_REDUCTION
    private String goalCategoryType; // ACCUMULATION vs REDUCTION
    private String name;
    private BigDecimal targetAmount;
    private BigDecimal currentAmount;
    private BigDecimal baselineAmount;
    private BigDecimal progressAmount;
    private BigDecimal progressPct;
    private BigDecimal remainingAmount;
    private String targetDate;
    private long daysRemaining;
    private BigDecimal requiredMonthlyPace;
    private BigDecimal projectedOutcome;
    private String riskStatus; // ON_TRACK, AT_RISK, ACHIEVED, EXPIRED, ARCHIVED
    private String statusExplanation;
    private String calculationSource;

    public FinancialGoalDTO() {}

    public FinancialGoalDTO(Long id, Long merchantId, String goalType, String goalCategoryType, 
                            String name, BigDecimal targetAmount, BigDecimal currentAmount, 
                            BigDecimal baselineAmount, BigDecimal progressAmount, BigDecimal progressPct, 
                            BigDecimal remainingAmount, String targetDate, long daysRemaining, 
                            BigDecimal requiredMonthlyPace, BigDecimal projectedOutcome, 
                            String riskStatus, String statusExplanation, String calculationSource) {
        this.id = id;
        this.merchantId = merchantId;
        this.goalType = goalType;
        this.goalCategoryType = goalCategoryType;
        this.name = name;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.baselineAmount = baselineAmount;
        this.progressAmount = progressAmount;
        this.progressPct = progressPct;
        this.remainingAmount = remainingAmount;
        this.targetDate = targetDate;
        this.daysRemaining = daysRemaining;
        this.requiredMonthlyPace = requiredMonthlyPace;
        this.projectedOutcome = projectedOutcome;
        this.riskStatus = riskStatus;
        this.statusExplanation = statusExplanation;
        this.calculationSource = calculationSource;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public String getGoalType() { return goalType; }
    public void setGoalType(String goalType) { this.goalType = goalType; }

    public String getGoalCategoryType() { return goalCategoryType; }
    public void setGoalCategoryType(String goalCategoryType) { this.goalCategoryType = goalCategoryType; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getTargetAmount() { return targetAmount; }
    public void setTargetAmount(BigDecimal targetAmount) { this.targetAmount = targetAmount; }

    public BigDecimal getCurrentAmount() { return currentAmount; }
    public void setCurrentAmount(BigDecimal currentAmount) { this.currentAmount = currentAmount; }

    public BigDecimal getBaselineAmount() { return baselineAmount; }
    public void setBaselineAmount(BigDecimal baselineAmount) { this.baselineAmount = baselineAmount; }

    public BigDecimal getProgressAmount() { return progressAmount; }
    public void setProgressAmount(BigDecimal progressAmount) { this.progressAmount = progressAmount; }

    public BigDecimal getProgressPct() { return progressPct; }
    public void setProgressPct(BigDecimal progressPct) { this.progressPct = progressPct; }

    public BigDecimal getRemainingAmount() { return remainingAmount; }
    public void setRemainingAmount(BigDecimal remainingAmount) { this.remainingAmount = remainingAmount; }

    public String getTargetDate() { return targetDate; }
    public void setTargetDate(String targetDate) { this.targetDate = targetDate; }

    public long getDaysRemaining() { return daysRemaining; }
    public void setDaysRemaining(long daysRemaining) { this.daysRemaining = daysRemaining; }

    public BigDecimal getRequiredMonthlyPace() { return requiredMonthlyPace; }
    public void setRequiredMonthlyPace(BigDecimal requiredMonthlyPace) { this.requiredMonthlyPace = requiredMonthlyPace; }

    public BigDecimal getProjectedOutcome() { return projectedOutcome; }
    public void setProjectedOutcome(BigDecimal projectedOutcome) { this.projectedOutcome = projectedOutcome; }

    public String getRiskStatus() { return riskStatus; }
    public void setRiskStatus(String riskStatus) { this.riskStatus = riskStatus; }

    public String getStatusExplanation() { return statusExplanation; }
    public void setStatusExplanation(String statusExplanation) { this.statusExplanation = statusExplanation; }

    public String getCalculationSource() { return calculationSource; }
    public void setCalculationSource(String calculationSource) { this.calculationSource = calculationSource; }
}
