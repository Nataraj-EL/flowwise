package com.flowwise.dto;

import java.math.BigDecimal;
import java.util.List;

public class DecisionAnalysisDTO {
    private Long id;
    private Long merchantId;
    private String analysisKey;
    private String title;
    private String recommendedOption;
    private BigDecimal baselineScore;
    private String dataQualityStatus; // SUFFICIENT, INSUFFICIENT_DATA
    private String inputFingerprint;
    private String summaryExplanation;
    private String evaluatedAt;
    private List<DecisionOptionDTO> options;
    private String advisoryNotice;

    public DecisionAnalysisDTO() {}

    public DecisionAnalysisDTO(Long id, Long merchantId, String analysisKey, String title, 
                               String recommendedOption, BigDecimal baselineScore, 
                               String dataQualityStatus, String inputFingerprint, 
                               String summaryExplanation, String evaluatedAt, 
                               List<DecisionOptionDTO> options, String advisoryNotice) {
        this.id = id;
        this.merchantId = merchantId;
        this.analysisKey = analysisKey;
        this.title = title;
        this.recommendedOption = recommendedOption;
        this.baselineScore = baselineScore;
        this.dataQualityStatus = dataQualityStatus;
        this.inputFingerprint = inputFingerprint;
        this.summaryExplanation = summaryExplanation;
        this.evaluatedAt = evaluatedAt;
        this.options = options;
        this.advisoryNotice = advisoryNotice;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public String getAnalysisKey() { return analysisKey; }
    public void setAnalysisKey(String analysisKey) { this.analysisKey = analysisKey; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getRecommendedOption() { return recommendedOption; }
    public void setRecommendedOption(String recommendedOption) { this.recommendedOption = recommendedOption; }

    public BigDecimal getBaselineScore() { return baselineScore; }
    public void setBaselineScore(BigDecimal baselineScore) { this.baselineScore = baselineScore; }

    public String getDataQualityStatus() { return dataQualityStatus; }
    public void setDataQualityStatus(String dataQualityStatus) { this.dataQualityStatus = dataQualityStatus; }

    public String getInputFingerprint() { return inputFingerprint; }
    public void setInputFingerprint(String inputFingerprint) { this.inputFingerprint = inputFingerprint; }

    public String getSummaryExplanation() { return summaryExplanation; }
    public void setSummaryExplanation(String summaryExplanation) { this.summaryExplanation = summaryExplanation; }

    public String getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(String evaluatedAt) { this.evaluatedAt = evaluatedAt; }

    public List<DecisionOptionDTO> getOptions() { return options; }
    public void setOptions(List<DecisionOptionDTO> options) { this.options = options; }

    public String getAdvisoryNotice() { return advisoryNotice; }
    public void setAdvisoryNotice(String advisoryNotice) { this.advisoryNotice = advisoryNotice; }
}
