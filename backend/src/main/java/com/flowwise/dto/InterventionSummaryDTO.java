package com.flowwise.dto;

import java.util.List;

public class InterventionSummaryDTO {
    private Long merchantId;
    private int totalInterventionsCount;
    private int openCount;
    private int highPriorityCount;
    private String topFocusArea;
    private List<FinancialInterventionDTO> interventions;
    private List<FinancialActionDTO> recommendedInterventionActions;
    private String summaryExplanation;
    private String advisoryNotice;

    public InterventionSummaryDTO() {}

    public InterventionSummaryDTO(Long merchantId, int totalInterventionsCount, int openCount,
                                  int highPriorityCount, String topFocusArea, List<FinancialInterventionDTO> interventions,
                                  List<FinancialActionDTO> recommendedInterventionActions, String summaryExplanation,
                                  String advisoryNotice) {
        this.merchantId = merchantId;
        this.totalInterventionsCount = totalInterventionsCount;
        this.openCount = openCount;
        this.highPriorityCount = highPriorityCount;
        this.topFocusArea = topFocusArea;
        this.interventions = interventions;
        this.recommendedInterventionActions = recommendedInterventionActions;
        this.summaryExplanation = summaryExplanation;
        this.advisoryNotice = advisoryNotice;
    }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public int getTotalInterventionsCount() { return totalInterventionsCount; }
    public void setTotalInterventionsCount(int totalInterventionsCount) { this.totalInterventionsCount = totalInterventionsCount; }

    public int getOpenCount() { return openCount; }
    public void setOpenCount(int openCount) { this.openCount = openCount; }

    public int getHighPriorityCount() { return highPriorityCount; }
    public void setHighPriorityCount(int highPriorityCount) { this.highPriorityCount = highPriorityCount; }

    public String getTopFocusArea() { return topFocusArea; }
    public void setTopFocusArea(String topFocusArea) { this.topFocusArea = topFocusArea; }

    public List<FinancialInterventionDTO> getInterventions() { return interventions; }
    public void setInterventions(List<FinancialInterventionDTO> interventions) { this.interventions = interventions; }

    public List<FinancialActionDTO> getRecommendedInterventionActions() { return recommendedInterventionActions; }
    public void setRecommendedInterventionActions(List<FinancialActionDTO> recommendedInterventionActions) { this.recommendedInterventionActions = recommendedInterventionActions; }

    public String getSummaryExplanation() { return summaryExplanation; }
    public void setSummaryExplanation(String summaryExplanation) { this.summaryExplanation = summaryExplanation; }

    public String getAdvisoryNotice() { return advisoryNotice; }
    public void setAdvisoryNotice(String advisoryNotice) { this.advisoryNotice = advisoryNotice; }
}
