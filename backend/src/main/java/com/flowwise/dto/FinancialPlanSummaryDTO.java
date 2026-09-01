package com.flowwise.dto;

import java.math.BigDecimal;
import java.util.List;

public class FinancialPlanSummaryDTO {
    private Long merchantId;
    private int totalPlansCount;
    private String activeHorizon;
    private BigDecimal activePlanScore;
    private String primaryFocusArea;
    private FinancialPlanDTO activePlan;
    private List<FinancialPlanDTO> plans;
    private List<FinancialActionDTO> recommendedPlanActions;
    private String summaryExplanation;
    private String advisoryNotice;

    public FinancialPlanSummaryDTO() {}

    public FinancialPlanSummaryDTO(Long merchantId, int totalPlansCount, String activeHorizon,
                                  BigDecimal activePlanScore, String primaryFocusArea,
                                  FinancialPlanDTO activePlan, List<FinancialPlanDTO> plans,
                                  List<FinancialActionDTO> recommendedPlanActions,
                                  String summaryExplanation, String advisoryNotice) {
        this.merchantId = merchantId;
        this.totalPlansCount = totalPlansCount;
        this.activeHorizon = activeHorizon;
        this.activePlanScore = activePlanScore;
        this.primaryFocusArea = primaryFocusArea;
        this.activePlan = activePlan;
        this.plans = plans;
        this.recommendedPlanActions = recommendedPlanActions;
        this.summaryExplanation = summaryExplanation;
        this.advisoryNotice = advisoryNotice;
    }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public int getTotalPlansCount() { return totalPlansCount; }
    public void setTotalPlansCount(int totalPlansCount) { this.totalPlansCount = totalPlansCount; }

    public String getActiveHorizon() { return activeHorizon; }
    public void setActiveHorizon(String activeHorizon) { this.activeHorizon = activeHorizon; }

    public BigDecimal getActivePlanScore() { return activePlanScore; }
    public void setActivePlanScore(BigDecimal activePlanScore) { this.activePlanScore = activePlanScore; }

    public String getPrimaryFocusArea() { return primaryFocusArea; }
    public void setPrimaryFocusArea(String primaryFocusArea) { this.primaryFocusArea = primaryFocusArea; }

    public FinancialPlanDTO getActivePlan() { return activePlan; }
    public void setActivePlan(FinancialPlanDTO activePlan) { this.activePlan = activePlan; }

    public List<FinancialPlanDTO> getPlans() { return plans; }
    public void setPlans(List<FinancialPlanDTO> plans) { this.plans = plans; }

    public List<FinancialActionDTO> getRecommendedPlanActions() { return recommendedPlanActions; }
    public void setRecommendedPlanActions(List<FinancialActionDTO> recommendedPlanActions) { this.recommendedPlanActions = recommendedPlanActions; }

    public String getSummaryExplanation() { return summaryExplanation; }
    public void setSummaryExplanation(String summaryExplanation) { this.summaryExplanation = summaryExplanation; }

    public String getAdvisoryNotice() { return advisoryNotice; }
    public void setAdvisoryNotice(String advisoryNotice) { this.advisoryNotice = advisoryNotice; }
}
