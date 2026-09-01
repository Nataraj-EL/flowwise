package com.flowwise.dto;

import java.math.BigDecimal;
import java.util.List;

public class CommandCenterSnapshotDTO {
    private String overallFinancialStatus; // HEALTHY, WATCH, AT_RISK
    private int overallHealthScore;
    private BigDecimal availableCash;
    private BigDecimal netCashFlow;
    private BigDecimal workingCapitalCoverage;
    private BigDecimal receivablesPressure;
    private BigDecimal payablesPressure;
    private String forecastRisk; // FEASIBLE, CAUTION, HIGH_RISK
    private List<FinancialActionDTO> top3Priorities;
    private String keyPositiveSignal;
    private String keyRiskSignal;
    private String whatChangedSummary;
    private String generatedAt;

    public CommandCenterSnapshotDTO() {}

    public CommandCenterSnapshotDTO(String overallFinancialStatus, int overallHealthScore, 
                                    BigDecimal availableCash, BigDecimal netCashFlow, 
                                    BigDecimal workingCapitalCoverage, BigDecimal receivablesPressure, 
                                    BigDecimal payablesPressure, String forecastRisk, 
                                    List<FinancialActionDTO> top3Priorities, String keyPositiveSignal, 
                                    String keyRiskSignal, String whatChangedSummary, String generatedAt) {
        this.overallFinancialStatus = overallFinancialStatus;
        this.overallHealthScore = overallHealthScore;
        this.availableCash = availableCash;
        this.netCashFlow = netCashFlow;
        this.workingCapitalCoverage = workingCapitalCoverage;
        this.receivablesPressure = receivablesPressure;
        this.payablesPressure = payablesPressure;
        this.forecastRisk = forecastRisk;
        this.top3Priorities = top3Priorities;
        this.keyPositiveSignal = keyPositiveSignal;
        this.keyRiskSignal = keyRiskSignal;
        this.whatChangedSummary = whatChangedSummary;
        this.generatedAt = generatedAt;
    }

    public String getOverallFinancialStatus() { return overallFinancialStatus; }
    public void setOverallFinancialStatus(String overallFinancialStatus) { this.overallFinancialStatus = overallFinancialStatus; }

    public int getOverallHealthScore() { return overallHealthScore; }
    public void setOverallHealthScore(int overallHealthScore) { this.overallHealthScore = overallHealthScore; }

    public BigDecimal getAvailableCash() { return availableCash; }
    public void setAvailableCash(BigDecimal availableCash) { this.availableCash = availableCash; }

    public BigDecimal getNetCashFlow() { return netCashFlow; }
    public void setNetCashFlow(BigDecimal netCashFlow) { this.netCashFlow = netCashFlow; }

    public BigDecimal getWorkingCapitalCoverage() { return workingCapitalCoverage; }
    public void setWorkingCapitalCoverage(BigDecimal workingCapitalCoverage) { this.workingCapitalCoverage = workingCapitalCoverage; }

    public BigDecimal getReceivablesPressure() { return receivablesPressure; }
    public void setReceivablesPressure(BigDecimal receivablesPressure) { this.receivablesPressure = receivablesPressure; }

    public BigDecimal getPayablesPressure() { return payablesPressure; }
    public void setPayablesPressure(BigDecimal payablesPressure) { this.payablesPressure = payablesPressure; }

    public String getForecastRisk() { return forecastRisk; }
    public void setForecastRisk(String forecastRisk) { this.forecastRisk = forecastRisk; }

    public List<FinancialActionDTO> getTop3Priorities() { return top3Priorities; }
    public void setTop3Priorities(List<FinancialActionDTO> top3Priorities) { this.top3Priorities = top3Priorities; }

    public String getKeyPositiveSignal() { return keyPositiveSignal; }
    public void setKeyPositiveSignal(String keyPositiveSignal) { this.keyPositiveSignal = keyPositiveSignal; }

    public String getKeyRiskSignal() { return keyRiskSignal; }
    public void setKeyRiskSignal(String keyRiskSignal) { this.keyRiskSignal = keyRiskSignal; }

    public String getWhatChangedSummary() { return whatChangedSummary; }
    public void setWhatChangedSummary(String whatChangedSummary) { this.whatChangedSummary = whatChangedSummary; }

    public String getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(String generatedAt) { this.generatedAt = generatedAt; }
}
