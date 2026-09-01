package com.flowwise.dto;

import java.util.List;

public class CorrelationSummaryDTO {
    private Long merchantId;
    private int totalCorrelationsCount;
    private int highConfidenceCount;
    private String topLikelyRootCause;
    private List<SignalCorrelationDTO> correlations;
    private List<FinancialActionDTO> recommendedRootCauseActions;
    private String summaryExplanation;
    private String advisoryNotice;

    public CorrelationSummaryDTO() {}

    public CorrelationSummaryDTO(Long merchantId, int totalCorrelationsCount, int highConfidenceCount,
                                 String topLikelyRootCause, List<SignalCorrelationDTO> correlations,
                                 List<FinancialActionDTO> recommendedRootCauseActions, String summaryExplanation,
                                 String advisoryNotice) {
        this.merchantId = merchantId;
        this.totalCorrelationsCount = totalCorrelationsCount;
        this.highConfidenceCount = highConfidenceCount;
        this.topLikelyRootCause = topLikelyRootCause;
        this.correlations = correlations;
        this.recommendedRootCauseActions = recommendedRootCauseActions;
        this.summaryExplanation = summaryExplanation;
        this.advisoryNotice = advisoryNotice;
    }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public int getTotalCorrelationsCount() { return totalCorrelationsCount; }
    public void setTotalCorrelationsCount(int totalCorrelationsCount) { this.totalCorrelationsCount = totalCorrelationsCount; }

    public int getHighConfidenceCount() { return highConfidenceCount; }
    public void setHighConfidenceCount(int highConfidenceCount) { this.highConfidenceCount = highConfidenceCount; }

    public String getTopLikelyRootCause() { return topLikelyRootCause; }
    public void setTopLikelyRootCause(String topLikelyRootCause) { this.topLikelyRootCause = topLikelyRootCause; }

    public List<SignalCorrelationDTO> getCorrelations() { return correlations; }
    public void setCorrelations(List<SignalCorrelationDTO> correlations) { this.correlations = correlations; }

    public List<FinancialActionDTO> getRecommendedRootCauseActions() { return recommendedRootCauseActions; }
    public void setRecommendedRootCauseActions(List<FinancialActionDTO> recommendedRootCauseActions) { this.recommendedRootCauseActions = recommendedRootCauseActions; }

    public String getSummaryExplanation() { return summaryExplanation; }
    public void setSummaryExplanation(String summaryExplanation) { this.summaryExplanation = summaryExplanation; }

    public String getAdvisoryNotice() { return advisoryNotice; }
    public void setAdvisoryNotice(String advisoryNotice) { this.advisoryNotice = advisoryNotice; }
}
