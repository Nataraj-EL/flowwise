package com.flowwise.dto;

import java.math.BigDecimal;

public class CashFlowSummaryDTO {
    private BigDecimal totalInflows;
    private BigDecimal totalOutflows;
    private BigDecimal netCashFlow;
    private BigDecimal operatingInflows;
    private BigDecimal operatingOutflows;
    private BigDecimal averageMonthlyOutflow;
    private BigDecimal burnRate;
    private BigDecimal cashRunwayMonths;
    private BigDecimal recurringExpensesEstimate;
    private BigDecimal upcomingPayablePressure;
    private String liquidityStatus;

    public CashFlowSummaryDTO() {}

    public CashFlowSummaryDTO(BigDecimal totalInflows, BigDecimal totalOutflows, BigDecimal netCashFlow, 
                              BigDecimal operatingInflows, BigDecimal operatingOutflows, 
                              BigDecimal averageMonthlyOutflow, BigDecimal burnRate, 
                              BigDecimal cashRunwayMonths, BigDecimal recurringExpensesEstimate, 
                              BigDecimal upcomingPayablePressure, String liquidityStatus) {
        this.totalInflows = totalInflows;
        this.totalOutflows = totalOutflows;
        this.netCashFlow = netCashFlow;
        this.operatingInflows = operatingInflows;
        this.operatingOutflows = operatingOutflows;
        this.averageMonthlyOutflow = averageMonthlyOutflow;
        this.burnRate = burnRate;
        this.cashRunwayMonths = cashRunwayMonths;
        this.recurringExpensesEstimate = recurringExpensesEstimate;
        this.upcomingPayablePressure = upcomingPayablePressure;
        this.liquidityStatus = liquidityStatus;
    }

    public BigDecimal getTotalInflows() { return totalInflows; }
    public void setTotalInflows(BigDecimal totalInflows) { this.totalInflows = totalInflows; }

    public BigDecimal getTotalOutflows() { return totalOutflows; }
    public void setTotalOutflows(BigDecimal totalOutflows) { this.totalOutflows = totalOutflows; }

    public BigDecimal getNetCashFlow() { return netCashFlow; }
    public void setNetCashFlow(BigDecimal netCashFlow) { this.netCashFlow = netCashFlow; }

    public BigDecimal getOperatingInflows() { return operatingInflows; }
    public void setOperatingInflows(BigDecimal operatingInflows) { this.operatingInflows = operatingInflows; }

    public BigDecimal getOperatingOutflows() { return operatingOutflows; }
    public void setOperatingOutflows(BigDecimal operatingOutflows) { this.operatingOutflows = operatingOutflows; }

    public BigDecimal getAverageMonthlyOutflow() { return averageMonthlyOutflow; }
    public void setAverageMonthlyOutflow(BigDecimal averageMonthlyOutflow) { this.averageMonthlyOutflow = averageMonthlyOutflow; }

    public BigDecimal getBurnRate() { return burnRate; }
    public void setBurnRate(BigDecimal burnRate) { this.burnRate = burnRate; }

    public BigDecimal getCashRunwayMonths() { return cashRunwayMonths; }
    public void setCashRunwayMonths(BigDecimal cashRunwayMonths) { this.cashRunwayMonths = cashRunwayMonths; }

    public BigDecimal getRecurringExpensesEstimate() { return recurringExpensesEstimate; }
    public void setRecurringExpensesEstimate(BigDecimal recurringExpensesEstimate) { this.recurringExpensesEstimate = recurringExpensesEstimate; }

    public BigDecimal getUpcomingPayablePressure() { return upcomingPayablePressure; }
    public void setUpcomingPayablePressure(BigDecimal upcomingPayablePressure) { this.upcomingPayablePressure = upcomingPayablePressure; }

    public String getLiquidityStatus() { return liquidityStatus; }
    public void setLiquidityStatus(String liquidityStatus) { this.liquidityStatus = liquidityStatus; }
}
