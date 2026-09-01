package com.flowwise.dto;

import java.util.List;

public class AnomalySummaryDTO {
    private Long merchantId;
    private int totalAnomaliesCount;
    private int criticalCount;
    private int highCount;
    private int mediumCount;
    private int lowCount;
    private int openCount;
    private List<FinancialAnomalyDTO> anomalies;
    private List<FinancialActionDTO> recommendedAnomalyActions;
    private String summaryExplanation;
    private String advisoryNotice;

    public AnomalySummaryDTO() {}

    public AnomalySummaryDTO(Long merchantId, int totalAnomaliesCount, int criticalCount, int highCount,
                             int mediumCount, int lowCount, int openCount, List<FinancialAnomalyDTO> anomalies,
                             List<FinancialActionDTO> recommendedAnomalyActions, String summaryExplanation,
                             String advisoryNotice) {
        this.merchantId = merchantId;
        this.totalAnomaliesCount = totalAnomaliesCount;
        this.criticalCount = criticalCount;
        this.highCount = highCount;
        this.mediumCount = mediumCount;
        this.lowCount = lowCount;
        this.openCount = openCount;
        this.anomalies = anomalies;
        this.recommendedAnomalyActions = recommendedAnomalyActions;
        this.summaryExplanation = summaryExplanation;
        this.advisoryNotice = advisoryNotice;
    }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public int getTotalAnomaliesCount() { return totalAnomaliesCount; }
    public void setTotalAnomaliesCount(int totalAnomaliesCount) { this.totalAnomaliesCount = totalAnomaliesCount; }

    public int getCriticalCount() { return criticalCount; }
    public void setCriticalCount(int criticalCount) { this.criticalCount = criticalCount; }

    public int getHighCount() { return highCount; }
    public void setHighCount(int highCount) { this.highCount = highCount; }

    public int getMediumCount() { return mediumCount; }
    public void setMediumCount(int mediumCount) { this.mediumCount = mediumCount; }

    public int getLowCount() { return lowCount; }
    public void setLowCount(int lowCount) { this.lowCount = lowCount; }

    public int getOpenCount() { return openCount; }
    public void setOpenCount(int openCount) { this.openCount = openCount; }

    public List<FinancialAnomalyDTO> getAnomalies() { return anomalies; }
    public void setAnomalies(List<FinancialAnomalyDTO> anomalies) { this.anomalies = anomalies; }

    public List<FinancialActionDTO> getRecommendedAnomalyActions() { return recommendedAnomalyActions; }
    public void setRecommendedAnomalyActions(List<FinancialActionDTO> recommendedAnomalyActions) { this.recommendedAnomalyActions = recommendedAnomalyActions; }

    public String getSummaryExplanation() { return summaryExplanation; }
    public void setSummaryExplanation(String summaryExplanation) { this.summaryExplanation = summaryExplanation; }

    public String getAdvisoryNotice() { return advisoryNotice; }
    public void setAdvisoryNotice(String advisoryNotice) { this.advisoryNotice = advisoryNotice; }
}
