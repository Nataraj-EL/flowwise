package com.flowwise.dto;

import java.math.BigDecimal;
import java.util.List;

public class DecisionCalibrationDTO {
    private Long id;
    private Long merchantId;
    private String calibrationKey;
    private int totalEvaluatedDecisions;
    private int successfulDecisions;
    private BigDecimal overallSuccessRatePct;
    private String confidenceLevel; // HIGH, MODERATE, LIMITED, INSUFFICIENT_DATA
    private BigDecimal dataCompletenessPct;
    private String summaryInsight;
    private String evaluatedAt;
    private List<OptionPerformanceDTO> optionPerformances;
    private List<FinancialDecisionDTO> recentDecisions;
    private String advisoryNotice;

    public DecisionCalibrationDTO() {}

    public DecisionCalibrationDTO(Long id, Long merchantId, String calibrationKey, 
                                  int totalEvaluatedDecisions, int successfulDecisions, 
                                  BigDecimal overallSuccessRatePct, String confidenceLevel, 
                                  BigDecimal dataCompletenessPct, String summaryInsight, 
                                  String evaluatedAt, List<OptionPerformanceDTO> optionPerformances, 
                                  List<FinancialDecisionDTO> recentDecisions, String advisoryNotice) {
        this.id = id;
        this.merchantId = merchantId;
        this.calibrationKey = calibrationKey;
        this.totalEvaluatedDecisions = totalEvaluatedDecisions;
        this.successfulDecisions = successfulDecisions;
        this.overallSuccessRatePct = overallSuccessRatePct;
        this.confidenceLevel = confidenceLevel;
        this.dataCompletenessPct = dataCompletenessPct;
        this.summaryInsight = summaryInsight;
        this.evaluatedAt = evaluatedAt;
        this.optionPerformances = optionPerformances;
        this.recentDecisions = recentDecisions;
        this.advisoryNotice = advisoryNotice;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public String getCalibrationKey() { return calibrationKey; }
    public void setCalibrationKey(String calibrationKey) { this.calibrationKey = calibrationKey; }

    public int getTotalEvaluatedDecisions() { return totalEvaluatedDecisions; }
    public void setTotalEvaluatedDecisions(int totalEvaluatedDecisions) { this.totalEvaluatedDecisions = totalEvaluatedDecisions; }

    public int getSuccessfulDecisions() { return successfulDecisions; }
    public void setSuccessfulDecisions(int successfulDecisions) { this.successfulDecisions = successfulDecisions; }

    public BigDecimal getOverallSuccessRatePct() { return overallSuccessRatePct; }
    public void setOverallSuccessRatePct(BigDecimal overallSuccessRatePct) { this.overallSuccessRatePct = overallSuccessRatePct; }

    public String getConfidenceLevel() { return confidenceLevel; }
    public void setConfidenceLevel(String confidenceLevel) { this.confidenceLevel = confidenceLevel; }

    public BigDecimal getDataCompletenessPct() { return dataCompletenessPct; }
    public void setDataCompletenessPct(BigDecimal dataCompletenessPct) { this.dataCompletenessPct = dataCompletenessPct; }

    public String getSummaryInsight() { return summaryInsight; }
    public void setSummaryInsight(String summaryInsight) { this.summaryInsight = summaryInsight; }

    public String getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(String evaluatedAt) { this.evaluatedAt = evaluatedAt; }

    public List<OptionPerformanceDTO> getOptionPerformances() { return optionPerformances; }
    public void setOptionPerformances(List<OptionPerformanceDTO> optionPerformances) { this.optionPerformances = optionPerformances; }

    public List<FinancialDecisionDTO> getRecentDecisions() { return recentDecisions; }
    public void setRecentDecisions(List<FinancialDecisionDTO> recentDecisions) { this.recentDecisions = recentDecisions; }

    public String getAdvisoryNotice() { return advisoryNotice; }
    public void setAdvisoryNotice(String advisoryNotice) { this.advisoryNotice = advisoryNotice; }
}
