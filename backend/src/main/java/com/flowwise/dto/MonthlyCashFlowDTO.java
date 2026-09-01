package com.flowwise.dto;

import java.math.BigDecimal;

public class MonthlyCashFlowDTO {
    private String month;
    private BigDecimal inflow;
    private BigDecimal outflow;
    private BigDecimal netCashFlow;

    public MonthlyCashFlowDTO() {}

    public MonthlyCashFlowDTO(String month, BigDecimal inflow, BigDecimal outflow, BigDecimal netCashFlow) {
        this.month = month;
        this.inflow = inflow;
        this.outflow = outflow;
        this.netCashFlow = netCashFlow;
    }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public BigDecimal getInflow() { return inflow; }
    public void setInflow(BigDecimal inflow) { this.inflow = inflow; }

    public BigDecimal getOutflow() { return outflow; }
    public void setOutflow(BigDecimal outflow) { this.outflow = outflow; }

    public BigDecimal getNetCashFlow() { return netCashFlow; }
    public void setNetCashFlow(BigDecimal netCashFlow) { this.netCashFlow = netCashFlow; }
}
