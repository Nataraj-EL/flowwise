package com.flowwise.dto;

public class InsightSummaryDTO {
    private int totalInsights;
    private int newCount;
    private int acknowledgedCount;
    private int dismissedCount;
    private int highSeverityCount;
    private int mediumSeverityCount;
    private int lowSeverityCount;
    private boolean sufficientHistory;
    private String patternEngineStatus;

    public InsightSummaryDTO() {}

    public InsightSummaryDTO(int totalInsights, int newCount, int acknowledgedCount, 
                             int dismissedCount, int highSeverityCount, int mediumSeverityCount, 
                             int lowSeverityCount, boolean sufficientHistory, String patternEngineStatus) {
        this.totalInsights = totalInsights;
        this.newCount = newCount;
        this.acknowledgedCount = acknowledgedCount;
        this.dismissedCount = dismissedCount;
        this.highSeverityCount = highSeverityCount;
        this.mediumSeverityCount = mediumSeverityCount;
        this.lowSeverityCount = lowSeverityCount;
        this.sufficientHistory = sufficientHistory;
        this.patternEngineStatus = patternEngineStatus;
    }

    public int getTotalInsights() { return totalInsights; }
    public void setTotalInsights(int totalInsights) { this.totalInsights = totalInsights; }

    public int getNewCount() { return newCount; }
    public void setNewCount(int newCount) { this.newCount = newCount; }

    public int getAcknowledgedCount() { return acknowledgedCount; }
    public void setAcknowledgedCount(int acknowledgedCount) { this.acknowledgedCount = acknowledgedCount; }

    public int getDismissedCount() { return dismissedCount; }
    public void setDismissedCount(int dismissedCount) { this.dismissedCount = dismissedCount; }

    public int getHighSeverityCount() { return highSeverityCount; }
    public void setHighSeverityCount(int highSeverityCount) { this.highSeverityCount = highSeverityCount; }

    public int getMediumSeverityCount() { return mediumSeverityCount; }
    public void setMediumSeverityCount(int mediumSeverityCount) { this.mediumSeverityCount = mediumSeverityCount; }

    public int getLowSeverityCount() { return lowSeverityCount; }
    public void setLowSeverityCount(int lowSeverityCount) { this.lowSeverityCount = lowSeverityCount; }

    public boolean isSufficientHistory() { return sufficientHistory; }
    public void setSufficientHistory(boolean sufficientHistory) { this.sufficientHistory = sufficientHistory; }

    public String getPatternEngineStatus() { return patternEngineStatus; }
    public void setPatternEngineStatus(String patternEngineStatus) { this.patternEngineStatus = patternEngineStatus; }
}
