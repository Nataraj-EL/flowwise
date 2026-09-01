package com.flowwise.dto;

import java.math.BigDecimal;
import java.util.List;

public class TemporalSummaryDTO {
    private String currentMonth;
    private String previousMonth;
    private BigDecimal currentInflow;
    private BigDecimal previousInflow;
    private BigDecimal inflowChangePct;
    private String inflowDirection; // UP, DOWN, FLAT
    private BigDecimal currentOutflow;
    private BigDecimal previousOutflow;
    private BigDecimal outflowChangePct;
    private String outflowDirection; // UP, DOWN, FLAT
    private BigDecimal currentNetCash;
    private BigDecimal previousNetCash;
    private BigDecimal netCashChangePct;
    private String netCashDirection; // UP, DOWN, FLAT
    private List<CategoryMovementDTO> categoryMovements;
    private List<String> anomalies;
    private boolean insufficientHistory;
    private int historyMonthCount;

    public TemporalSummaryDTO() {}

    public TemporalSummaryDTO(String currentMonth, String previousMonth, BigDecimal currentInflow, 
                              BigDecimal previousInflow, BigDecimal inflowChangePct, String inflowDirection, 
                              BigDecimal currentOutflow, BigDecimal previousOutflow, BigDecimal outflowChangePct, 
                              String outflowDirection, BigDecimal currentNetCash, BigDecimal previousNetCash, 
                              BigDecimal netCashChangePct, String netCashDirection, 
                              List<CategoryMovementDTO> categoryMovements, List<String> anomalies, 
                              boolean insufficientHistory, int historyMonthCount) {
        this.currentMonth = currentMonth;
        this.previousMonth = previousMonth;
        this.currentInflow = currentInflow;
        this.previousInflow = previousInflow;
        this.inflowChangePct = inflowChangePct;
        this.inflowDirection = inflowDirection;
        this.currentOutflow = currentOutflow;
        this.previousOutflow = previousOutflow;
        this.outflowChangePct = outflowChangePct;
        this.outflowDirection = outflowDirection;
        this.currentNetCash = currentNetCash;
        this.previousNetCash = previousNetCash;
        this.netCashChangePct = netCashChangePct;
        this.netCashDirection = netCashDirection;
        this.categoryMovements = categoryMovements;
        this.anomalies = anomalies;
        this.insufficientHistory = insufficientHistory;
        this.historyMonthCount = historyMonthCount;
    }

    public String getCurrentMonth() { return currentMonth; }
    public void setCurrentMonth(String currentMonth) { this.currentMonth = currentMonth; }

    public String getPreviousMonth() { return previousMonth; }
    public void setPreviousMonth(String previousMonth) { this.previousMonth = previousMonth; }

    public BigDecimal getCurrentInflow() { return currentInflow; }
    public void setCurrentInflow(BigDecimal currentInflow) { this.currentInflow = currentInflow; }

    public BigDecimal getPreviousInflow() { return previousInflow; }
    public void setPreviousInflow(BigDecimal previousInflow) { this.previousInflow = previousInflow; }

    public BigDecimal getInflowChangePct() { return inflowChangePct; }
    public void setInflowChangePct(BigDecimal inflowChangePct) { this.inflowChangePct = inflowChangePct; }

    public String getInflowDirection() { return inflowDirection; }
    public void setInflowDirection(String inflowDirection) { this.inflowDirection = inflowDirection; }

    public BigDecimal getCurrentOutflow() { return currentOutflow; }
    public void setCurrentOutflow(BigDecimal currentOutflow) { this.currentOutflow = currentOutflow; }

    public BigDecimal getPreviousOutflow() { return previousOutflow; }
    public void setPreviousOutflow(BigDecimal previousOutflow) { this.previousOutflow = previousOutflow; }

    public BigDecimal getOutflowChangePct() { return outflowChangePct; }
    public void setOutflowChangePct(BigDecimal outflowChangePct) { this.outflowChangePct = outflowChangePct; }

    public String getOutflowDirection() { return outflowDirection; }
    public void setOutflowDirection(String outflowDirection) { this.outflowDirection = outflowDirection; }

    public BigDecimal getCurrentNetCash() { return currentNetCash; }
    public void setCurrentNetCash(BigDecimal currentNetCash) { this.currentNetCash = currentNetCash; }

    public BigDecimal getPreviousNetCash() { return previousNetCash; }
    public void setPreviousNetCash(BigDecimal previousNetCash) { this.previousNetCash = previousNetCash; }

    public BigDecimal getNetCashChangePct() { return netCashChangePct; }
    public void setNetCashChangePct(BigDecimal netCashChangePct) { this.netCashChangePct = netCashChangePct; }

    public String getNetCashDirection() { return netCashDirection; }
    public void setNetCashDirection(String netCashDirection) { this.netCashDirection = netCashDirection; }

    public List<CategoryMovementDTO> getCategoryMovements() { return categoryMovements; }
    public void setCategoryMovements(List<CategoryMovementDTO> categoryMovements) { this.categoryMovements = categoryMovements; }

    public List<String> getAnomalies() { return anomalies; }
    public void setAnomalies(List<String> anomalies) { this.anomalies = anomalies; }

    public boolean isInsufficientHistory() { return insufficientHistory; }
    public void setInsufficientHistory(boolean insufficientHistory) { this.insufficientHistory = insufficientHistory; }

    public int getHistoryMonthCount() { return historyMonthCount; }
    public void setHistoryMonthCount(int historyMonthCount) { this.historyMonthCount = historyMonthCount; }
}
