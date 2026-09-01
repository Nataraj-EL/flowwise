package com.flowwise.dto;

import java.util.List;

public class AdvisoryActionPlanSummaryDTO {
    private Long merchantId;
    private int totalPlansCount;
    private AdvisoryActionPlanDTO activePlan;
    private List<AdvisoryActionPlanDTO> plans;
    private String summaryExplanation;
    private String advisoryNotice;

    public AdvisoryActionPlanSummaryDTO() {}

    public AdvisoryActionPlanSummaryDTO(Long merchantId, int totalPlansCount, AdvisoryActionPlanDTO activePlan,
                                       List<AdvisoryActionPlanDTO> plans, String summaryExplanation,
                                       String advisoryNotice) {
        this.merchantId = merchantId;
        this.totalPlansCount = totalPlansCount;
        this.activePlan = activePlan;
        this.plans = plans;
        this.summaryExplanation = summaryExplanation;
        this.advisoryNotice = advisoryNotice;
    }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public int getTotalPlansCount() { return totalPlansCount; }
    public void setTotalPlansCount(int totalPlansCount) { this.totalPlansCount = totalPlansCount; }

    public AdvisoryActionPlanDTO getActivePlan() { return activePlan; }
    public void setActivePlan(AdvisoryActionPlanDTO activePlan) { this.activePlan = activePlan; }

    public List<AdvisoryActionPlanDTO> getPlans() { return plans; }
    public void setPlans(List<AdvisoryActionPlanDTO> plans) { this.plans = plans; }

    public String getSummaryExplanation() { return summaryExplanation; }
    public void setSummaryExplanation(String summaryExplanation) { this.summaryExplanation = summaryExplanation; }

    public String getAdvisoryNotice() { return advisoryNotice; }
    public void setAdvisoryNotice(String advisoryNotice) { this.advisoryNotice = advisoryNotice; }
}
