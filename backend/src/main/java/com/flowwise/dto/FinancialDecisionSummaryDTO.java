package com.flowwise.dto;

import java.math.BigDecimal;
import java.util.List;

public class FinancialDecisionSummaryDTO {
    private Long merchantId;
    private int totalEvaluatedDecisionsCount;
    private BigDecimal topDecisionScore;
    private String topRecommendationTitle;
    private FinancialDecisionDTO topRecommendation;
    private List<FinancialDecisionDTO> decisions;
    private String summaryExplanation;
    private String advisoryNotice;

    public FinancialDecisionSummaryDTO() {}

    public FinancialDecisionSummaryDTO(Long merchantId, int totalEvaluatedDecisionsCount,
                                       BigDecimal topDecisionScore, String topRecommendationTitle,
                                       FinancialDecisionDTO topRecommendation, List<FinancialDecisionDTO> decisions,
                                       String summaryExplanation, String advisoryNotice) {
        this.merchantId = merchantId;
        this.totalEvaluatedDecisionsCount = totalEvaluatedDecisionsCount;
        this.topDecisionScore = topDecisionScore;
        this.topRecommendationTitle = topRecommendationTitle;
        this.topRecommendation = topRecommendation;
        this.decisions = decisions;
        this.summaryExplanation = summaryExplanation;
        this.advisoryNotice = advisoryNotice;
    }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public int getTotalEvaluatedDecisionsCount() { return totalEvaluatedDecisionsCount; }
    public void setTotalEvaluatedDecisionsCount(int totalEvaluatedDecisionsCount) { this.totalEvaluatedDecisionsCount = totalEvaluatedDecisionsCount; }

    public BigDecimal getTopDecisionScore() { return topDecisionScore; }
    public void setTopDecisionScore(BigDecimal topDecisionScore) { this.topDecisionScore = topDecisionScore; }

    public String getTopRecommendationTitle() { return topRecommendationTitle; }
    public void setTopRecommendationTitle(String topRecommendationTitle) { this.topRecommendationTitle = topRecommendationTitle; }

    public FinancialDecisionDTO getTopRecommendation() { return topRecommendation; }
    public void setTopRecommendation(FinancialDecisionDTO topRecommendation) { this.topRecommendation = topRecommendation; }

    public List<FinancialDecisionDTO> getDecisions() { return decisions; }
    public void setDecisions(List<FinancialDecisionDTO> decisions) { this.decisions = decisions; }

    public String getSummaryExplanation() { return summaryExplanation; }
    public void setSummaryExplanation(String summaryExplanation) { this.summaryExplanation = summaryExplanation; }

    public String getAdvisoryNotice() { return advisoryNotice; }
    public void setAdvisoryNotice(String advisoryNotice) { this.advisoryNotice = advisoryNotice; }
}
