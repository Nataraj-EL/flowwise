package com.flowwise.dto;

import java.math.BigDecimal;
import java.util.List;

public class WorkingCapitalSummaryDTO {
    private BigDecimal netWorkingCapital;
    private BigDecimal availableCash;
    private BigDecimal receivablesOutstanding;
    private BigDecimal payablesOutstanding;
    private BigDecimal workingCapitalGap;
    private BigDecimal currentCoverageRatio;
    private BigDecimal nearTermCoverageRatio;
    private String cashConversionRiskStatus; // OPTIMAL, MODERATE, HIGH_RISK
    private BigDecimal nearTermCollectionPotential;
    private BigDecimal upcomingPayablePressure;
    private List<String> topPressureDrivers;
    private String summaryExplanation;

    public WorkingCapitalSummaryDTO() {}

    public WorkingCapitalSummaryDTO(BigDecimal netWorkingCapital, BigDecimal availableCash, 
                                    BigDecimal receivablesOutstanding, BigDecimal payablesOutstanding, 
                                    BigDecimal workingCapitalGap, BigDecimal currentCoverageRatio, 
                                    BigDecimal nearTermCoverageRatio, String cashConversionRiskStatus, 
                                    BigDecimal nearTermCollectionPotential, BigDecimal upcomingPayablePressure, 
                                    List<String> topPressureDrivers, String summaryExplanation) {
        this.netWorkingCapital = netWorkingCapital;
        this.availableCash = availableCash;
        this.receivablesOutstanding = receivablesOutstanding;
        this.payablesOutstanding = payablesOutstanding;
        this.workingCapitalGap = workingCapitalGap;
        this.currentCoverageRatio = currentCoverageRatio;
        this.nearTermCoverageRatio = nearTermCoverageRatio;
        this.cashConversionRiskStatus = cashConversionRiskStatus;
        this.nearTermCollectionPotential = nearTermCollectionPotential;
        this.upcomingPayablePressure = upcomingPayablePressure;
        this.topPressureDrivers = topPressureDrivers;
        this.summaryExplanation = summaryExplanation;
    }

    public BigDecimal getNetWorkingCapital() { return netWorkingCapital; }
    public void setNetWorkingCapital(BigDecimal netWorkingCapital) { this.netWorkingCapital = netWorkingCapital; }

    public BigDecimal getAvailableCash() { return availableCash; }
    public void setAvailableCash(BigDecimal availableCash) { this.availableCash = availableCash; }

    public BigDecimal getReceivablesOutstanding() { return receivablesOutstanding; }
    public void setReceivablesOutstanding(BigDecimal receivablesOutstanding) { this.receivablesOutstanding = receivablesOutstanding; }

    public BigDecimal getPayablesOutstanding() { return payablesOutstanding; }
    public void setPayablesOutstanding(BigDecimal payablesOutstanding) { this.payablesOutstanding = payablesOutstanding; }

    public BigDecimal getWorkingCapitalGap() { return workingCapitalGap; }
    public void setWorkingCapitalGap(BigDecimal workingCapitalGap) { this.workingCapitalGap = workingCapitalGap; }

    public BigDecimal getCurrentCoverageRatio() { return currentCoverageRatio; }
    public void setCurrentCoverageRatio(BigDecimal currentCoverageRatio) { this.currentCoverageRatio = currentCoverageRatio; }

    public BigDecimal getNearTermCoverageRatio() { return nearTermCoverageRatio; }
    public void setNearTermCoverageRatio(BigDecimal nearTermCoverageRatio) { this.nearTermCoverageRatio = nearTermCoverageRatio; }

    public String getCashConversionRiskStatus() { return cashConversionRiskStatus; }
    public void setCashConversionRiskStatus(String cashConversionRiskStatus) { this.cashConversionRiskStatus = cashConversionRiskStatus; }

    public BigDecimal getNearTermCollectionPotential() { return nearTermCollectionPotential; }
    public void setNearTermCollectionPotential(BigDecimal nearTermCollectionPotential) { this.nearTermCollectionPotential = nearTermCollectionPotential; }

    public BigDecimal getUpcomingPayablePressure() { return upcomingPayablePressure; }
    public void setUpcomingPayablePressure(BigDecimal upcomingPayablePressure) { this.upcomingPayablePressure = upcomingPayablePressure; }

    public List<String> getTopPressureDrivers() { return topPressureDrivers; }
    public void setTopPressureDrivers(List<String> topPressureDrivers) { this.topPressureDrivers = topPressureDrivers; }

    public String getSummaryExplanation() { return summaryExplanation; }
    public void setSummaryExplanation(String summaryExplanation) { this.summaryExplanation = summaryExplanation; }
}
