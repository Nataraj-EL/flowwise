package com.flowwise.dto;

import java.math.BigDecimal;
import java.util.List;

public class RiskMonitorSummaryDTO {
    private Long merchantId;
    private BigDecimal compositeRiskHealthScore; // 0-100 (100 = Healthy, 0 = Critical Risk)
    private String overallRiskLevel; // LOW_RISK, MODERATE_RISK, HIGH_RISK, CRITICAL_RISK
    private int totalAlertsCount;
    private int criticalCount;
    private int highCount;
    private int mediumCount;
    private int lowCount;
    private int openCount;
    private List<RiskAlertDTO> alerts;
    private List<FinancialActionDTO> recommendedRiskActions;
    private String summaryExplanation;
    private String advisoryNotice;

    public RiskMonitorSummaryDTO() {}

    public RiskMonitorSummaryDTO(Long merchantId, BigDecimal compositeRiskHealthScore, String overallRiskLevel,
                                 int totalAlertsCount, int criticalCount, int highCount, int mediumCount,
                                 int lowCount, int openCount, List<RiskAlertDTO> alerts,
                                 List<FinancialActionDTO> recommendedRiskActions, String summaryExplanation,
                                 String advisoryNotice) {
        this.merchantId = merchantId;
        this.compositeRiskHealthScore = compositeRiskHealthScore;
        this.overallRiskLevel = overallRiskLevel;
        this.totalAlertsCount = totalAlertsCount;
        this.criticalCount = criticalCount;
        this.highCount = highCount;
        this.mediumCount = mediumCount;
        this.lowCount = lowCount;
        this.openCount = openCount;
        this.alerts = alerts;
        this.recommendedRiskActions = recommendedRiskActions;
        this.summaryExplanation = summaryExplanation;
        this.advisoryNotice = advisoryNotice;
    }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public BigDecimal getCompositeRiskHealthScore() { return compositeRiskHealthScore; }
    public void setCompositeRiskHealthScore(BigDecimal compositeRiskHealthScore) { this.compositeRiskHealthScore = compositeRiskHealthScore; }

    public String getOverallRiskLevel() { return overallRiskLevel; }
    public void setOverallRiskLevel(String overallRiskLevel) { this.overallRiskLevel = overallRiskLevel; }

    public int getTotalAlertsCount() { return totalAlertsCount; }
    public void setTotalAlertsCount(int totalAlertsCount) { this.totalAlertsCount = totalAlertsCount; }

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

    public List<RiskAlertDTO> getAlerts() { return alerts; }
    public void setAlerts(List<RiskAlertDTO> alerts) { this.alerts = alerts; }

    public List<FinancialActionDTO> getRecommendedRiskActions() { return recommendedRiskActions; }
    public void setRecommendedRiskActions(List<FinancialActionDTO> recommendedRiskActions) { this.recommendedRiskActions = recommendedRiskActions; }

    public String getSummaryExplanation() { return summaryExplanation; }
    public void setSummaryExplanation(String summaryExplanation) { this.summaryExplanation = summaryExplanation; }

    public String getAdvisoryNotice() { return advisoryNotice; }
    public void setAdvisoryNotice(String advisoryNotice) { this.advisoryNotice = advisoryNotice; }
}
