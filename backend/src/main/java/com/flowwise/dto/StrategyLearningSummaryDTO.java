package com.flowwise.dto;

import java.math.BigDecimal;
import java.util.List;

public class StrategyLearningSummaryDTO {
    private Long merchantId;
    private int totalEvaluatedStrategiesCount;
    private String topPerformingInterventionType;
    private int highConfidenceCount;
    private BigDecimal averageLearningMultiplier;
    private List<StrategyLearningDTO> learnings;
    private String summaryExplanation;
    private String advisoryNotice;

    public StrategyLearningSummaryDTO() {}

    public StrategyLearningSummaryDTO(Long merchantId, int totalEvaluatedStrategiesCount,
                                      String topPerformingInterventionType, int highConfidenceCount,
                                      BigDecimal averageLearningMultiplier, List<StrategyLearningDTO> learnings,
                                      String summaryExplanation, String advisoryNotice) {
        this.merchantId = merchantId;
        this.totalEvaluatedStrategiesCount = totalEvaluatedStrategiesCount;
        this.topPerformingInterventionType = topPerformingInterventionType;
        this.highConfidenceCount = highConfidenceCount;
        this.averageLearningMultiplier = averageLearningMultiplier;
        this.learnings = learnings;
        this.summaryExplanation = summaryExplanation;
        this.advisoryNotice = advisoryNotice;
    }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public int getTotalEvaluatedStrategiesCount() { return totalEvaluatedStrategiesCount; }
    public void setTotalEvaluatedStrategiesCount(int totalEvaluatedStrategiesCount) { this.totalEvaluatedStrategiesCount = totalEvaluatedStrategiesCount; }

    public String getTopPerformingInterventionType() { return topPerformingInterventionType; }
    public void setTopPerformingInterventionType(String topPerformingInterventionType) { this.topPerformingInterventionType = topPerformingInterventionType; }

    public int getHighConfidenceCount() { return highConfidenceCount; }
    public void setHighConfidenceCount(int highConfidenceCount) { this.highConfidenceCount = highConfidenceCount; }

    public BigDecimal getAverageLearningMultiplier() { return averageLearningMultiplier; }
    public void setAverageLearningMultiplier(BigDecimal averageLearningMultiplier) { this.averageLearningMultiplier = averageLearningMultiplier; }

    public List<StrategyLearningDTO> getLearnings() { return learnings; }
    public void setLearnings(List<StrategyLearningDTO> learnings) { this.learnings = learnings; }

    public String getSummaryExplanation() { return summaryExplanation; }
    public void setSummaryExplanation(String summaryExplanation) { this.summaryExplanation = summaryExplanation; }

    public String getAdvisoryNotice() { return advisoryNotice; }
    public void setAdvisoryNotice(String advisoryNotice) { this.advisoryNotice = advisoryNotice; }
}
