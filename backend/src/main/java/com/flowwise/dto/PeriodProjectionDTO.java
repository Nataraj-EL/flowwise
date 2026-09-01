package com.flowwise.dto;

import java.math.BigDecimal;

public class PeriodProjectionDTO {
    private int days; // 30, 60, 90
    private BigDecimal projectedInflow;
    private BigDecimal projectedOutflow;
    private BigDecimal projectedEndingCash;
    private BigDecimal projectedRunwayMonths;

    public PeriodProjectionDTO() {}

    public PeriodProjectionDTO(int days, BigDecimal projectedInflow, BigDecimal projectedOutflow, 
                               BigDecimal projectedEndingCash, BigDecimal projectedRunwayMonths) {
        this.days = days;
        this.projectedInflow = projectedInflow;
        this.projectedOutflow = projectedOutflow;
        this.projectedEndingCash = projectedEndingCash;
        this.projectedRunwayMonths = projectedRunwayMonths;
    }

    public int getDays() { return days; }
    public void setDays(int days) { this.days = days; }

    public BigDecimal getProjectedInflow() { return projectedInflow; }
    public void setProjectedInflow(BigDecimal projectedInflow) { this.projectedInflow = projectedInflow; }

    public BigDecimal getProjectedOutflow() { return projectedOutflow; }
    public void setProjectedOutflow(BigDecimal projectedOutflow) { this.projectedOutflow = projectedOutflow; }

    public BigDecimal getProjectedEndingCash() { return projectedEndingCash; }
    public void setProjectedEndingCash(BigDecimal projectedEndingCash) { this.projectedEndingCash = projectedEndingCash; }

    public BigDecimal getProjectedRunwayMonths() { return projectedRunwayMonths; }
    public void setProjectedRunwayMonths(BigDecimal projectedRunwayMonths) { this.projectedRunwayMonths = projectedRunwayMonths; }
}
