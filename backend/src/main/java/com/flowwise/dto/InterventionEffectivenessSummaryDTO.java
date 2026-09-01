package com.flowwise.dto;

import java.math.BigDecimal;
import java.util.List;

public class InterventionEffectivenessSummaryDTO {
    private Long merchantId;
    private int totalEvaluatedOutcomesCount;
    private int successfulCount;
    private int partialCount;
    private int ineffectiveCount;
    private int insufficientDataCount;
    private BigDecimal averageEffectivenessScore;
    private List<InterventionOutcomeDTO> outcomes;
    private String summaryExplanation;
    private String advisoryNotice;

    public InterventionEffectivenessSummaryDTO() {}

    public InterventionEffectivenessSummaryDTO(Long merchantId, int totalEvaluatedOutcomesCount,
                                                int successfulCount, int partialCount, int ineffectiveCount,
                                                int insufficientDataCount, BigDecimal averageEffectivenessScore,
                                                List<InterventionOutcomeDTO> outcomes, String summaryExplanation,
                                                String advisoryNotice) {
        this.merchantId = merchantId;
        this.totalEvaluatedOutcomesCount = totalEvaluatedOutcomesCount;
        this.successfulCount = successfulCount;
        this.partialCount = partialCount;
        this.ineffectiveCount = ineffectiveCount;
        this.insufficientDataCount = insufficientDataCount;
        this.averageEffectivenessScore = averageEffectivenessScore;
        this.outcomes = outcomes;
        this.summaryExplanation = summaryExplanation;
        this.advisoryNotice = advisoryNotice;
    }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public int getTotalEvaluatedOutcomesCount() { return totalEvaluatedOutcomesCount; }
    public void setTotalEvaluatedOutcomesCount(int totalEvaluatedOutcomesCount) { this.totalEvaluatedOutcomesCount = totalEvaluatedOutcomesCount; }

    public int getSuccessfulCount() { return successfulCount; }
    public void setSuccessfulCount(int successfulCount) { this.successfulCount = successfulCount; }

    public int getPartialCount() { return partialCount; }
    public void setPartialCount(int partialCount) { this.partialCount = partialCount; }

    public int getIneffectiveCount() { return ineffectiveCount; }
    public void setIneffectiveCount(int ineffectiveCount) { this.ineffectiveCount = ineffectiveCount; }

    public int getInsufficientDataCount() { return insufficientDataCount; }
    public void setInsufficientDataCount(int insufficientDataCount) { this.insufficientDataCount = insufficientDataCount; }

    public BigDecimal getAverageEffectivenessScore() { return averageEffectivenessScore; }
    public void setAverageEffectivenessScore(BigDecimal averageEffectivenessScore) { this.averageEffectivenessScore = averageEffectivenessScore; }

    public List<InterventionOutcomeDTO> getOutcomes() { return outcomes; }
    public void setOutcomes(List<InterventionOutcomeDTO> outcomes) { this.outcomes = outcomes; }

    public String getSummaryExplanation() { return summaryExplanation; }
    public void setSummaryExplanation(String summaryExplanation) { this.summaryExplanation = summaryExplanation; }

    public String getAdvisoryNotice() { return advisoryNotice; }
    public void setAdvisoryNotice(String advisoryNotice) { this.advisoryNotice = advisoryNotice; }
}
