package com.flowwise.dto;

import java.math.BigDecimal;
import java.util.List;

public class FinancialDecisionOutcomeSummaryDTO {
    private Long merchantId;
    private int totalEvaluatedOutcomesCount;
    private int successfulCount;
    private int partialCount;
    private int ineffectiveCount;
    private int insufficientDataCount;
    private BigDecimal averageEffectivenessScore;
    private List<FinancialDecisionOutcomeDTO> outcomes;
    private List<DecisionLearningDTO> learnings;
    private String summaryExplanation;
    private String advisoryNotice;

    public FinancialDecisionOutcomeSummaryDTO() {}

    public FinancialDecisionOutcomeSummaryDTO(Long merchantId, int totalEvaluatedOutcomesCount,
                                              int successfulCount, int partialCount, int ineffectiveCount,
                                              int insufficientDataCount, BigDecimal averageEffectivenessScore,
                                              List<FinancialDecisionOutcomeDTO> outcomes, List<DecisionLearningDTO> learnings,
                                              String summaryExplanation, String advisoryNotice) {
        this.merchantId = merchantId;
        this.totalEvaluatedOutcomesCount = totalEvaluatedOutcomesCount;
        this.successfulCount = successfulCount;
        this.partialCount = partialCount;
        this.ineffectiveCount = ineffectiveCount;
        this.insufficientDataCount = insufficientDataCount;
        this.averageEffectivenessScore = averageEffectivenessScore;
        this.outcomes = outcomes;
        this.learnings = learnings;
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

    public List<FinancialDecisionOutcomeDTO> getOutcomes() { return outcomes; }
    public void setOutcomes(List<FinancialDecisionOutcomeDTO> outcomes) { this.outcomes = outcomes; }

    public List<DecisionLearningDTO> getLearnings() { return learnings; }
    public void setLearnings(List<DecisionLearningDTO> learnings) { this.learnings = learnings; }

    public String getSummaryExplanation() { return summaryExplanation; }
    public void setSummaryExplanation(String summaryExplanation) { this.summaryExplanation = summaryExplanation; }

    public String getAdvisoryNotice() { return advisoryNotice; }
    public void setAdvisoryNotice(String advisoryNotice) { this.advisoryNotice = advisoryNotice; }
}
