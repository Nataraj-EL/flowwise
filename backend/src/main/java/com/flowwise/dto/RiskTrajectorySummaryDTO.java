package com.flowwise.dto;

import java.math.BigDecimal;
import java.util.List;

public class RiskTrajectorySummaryDTO {
    private Long merchantId;
    private String compositeTrajectoryStatus; // IMPROVING, STABLE, WORSENING, INSUFFICIENT_DATA
    private int totalTrackedRisks;
    private int worseningCount;
    private int stableCount;
    private int improvingCount;
    private int resolvedCount;
    private BigDecimal avgResolutionTimeHours;
    private List<RiskTrajectoryDTO> trajectories;
    private List<FinancialActionDTO> escalationActions;
    private String summaryExplanation;
    private String advisoryNotice;

    public RiskTrajectorySummaryDTO() {}

    public RiskTrajectorySummaryDTO(Long merchantId, String compositeTrajectoryStatus, int totalTrackedRisks,
                                    int worseningCount, int stableCount, int improvingCount, int resolvedCount,
                                    BigDecimal avgResolutionTimeHours, List<RiskTrajectoryDTO> trajectories,
                                    List<FinancialActionDTO> escalationActions, String summaryExplanation,
                                    String advisoryNotice) {
        this.merchantId = merchantId;
        this.compositeTrajectoryStatus = compositeTrajectoryStatus;
        this.totalTrackedRisks = totalTrackedRisks;
        this.worseningCount = worseningCount;
        this.stableCount = stableCount;
        this.improvingCount = improvingCount;
        this.resolvedCount = resolvedCount;
        this.avgResolutionTimeHours = avgResolutionTimeHours;
        this.trajectories = trajectories;
        this.escalationActions = escalationActions;
        this.summaryExplanation = summaryExplanation;
        this.advisoryNotice = advisoryNotice;
    }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public String getCompositeTrajectoryStatus() { return compositeTrajectoryStatus; }
    public void setCompositeTrajectoryStatus(String compositeTrajectoryStatus) { this.compositeTrajectoryStatus = compositeTrajectoryStatus; }

    public int getTotalTrackedRisks() { return totalTrackedRisks; }
    public void setTotalTrackedRisks(int totalTrackedRisks) { this.totalTrackedRisks = totalTrackedRisks; }

    public int getWorseningCount() { return worseningCount; }
    public void setWorseningCount(int worseningCount) { this.worseningCount = worseningCount; }

    public int getStableCount() { return stableCount; }
    public void setStableCount(int stableCount) { this.stableCount = stableCount; }

    public int getImprovingCount() { return improvingCount; }
    public void setImprovingCount(int improvingCount) { this.improvingCount = improvingCount; }

    public int getResolvedCount() { return resolvedCount; }
    public void setResolvedCount(int resolvedCount) { this.resolvedCount = resolvedCount; }

    public BigDecimal getAvgResolutionTimeHours() { return avgResolutionTimeHours; }
    public void setAvgResolutionTimeHours(BigDecimal avgResolutionTimeHours) { this.avgResolutionTimeHours = avgResolutionTimeHours; }

    public List<RiskTrajectoryDTO> getTrajectories() { return trajectories; }
    public void setTrajectories(List<RiskTrajectoryDTO> trajectories) { this.trajectories = trajectories; }

    public List<FinancialActionDTO> getEscalationActions() { return escalationActions; }
    public void setEscalationActions(List<FinancialActionDTO> escalationActions) { this.escalationActions = escalationActions; }

    public String getSummaryExplanation() { return summaryExplanation; }
    public void setSummaryExplanation(String summaryExplanation) { this.summaryExplanation = summaryExplanation; }

    public String getAdvisoryNotice() { return advisoryNotice; }
    public void setAdvisoryNotice(String advisoryNotice) { this.advisoryNotice = advisoryNotice; }
}
