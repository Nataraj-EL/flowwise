package com.flowwise.dto;

import java.math.BigDecimal;
import java.util.List;

public class ForecastSummaryDTO {
    private BigDecimal currentAvailableCash;
    private BigDecimal averageMonthlyInflow;
    private BigDecimal averageMonthlyOutflow;
    private List<PeriodProjectionDTO> projections;
    private List<String> assumptions;
    private boolean estimate;

    public ForecastSummaryDTO() {}

    public ForecastSummaryDTO(BigDecimal currentAvailableCash, BigDecimal averageMonthlyInflow, 
                              BigDecimal averageMonthlyOutflow, List<PeriodProjectionDTO> projections, 
                              List<String> assumptions, boolean estimate) {
        this.currentAvailableCash = currentAvailableCash;
        this.averageMonthlyInflow = averageMonthlyInflow;
        this.averageMonthlyOutflow = averageMonthlyOutflow;
        this.projections = projections;
        this.assumptions = assumptions;
        this.estimate = estimate;
    }

    public BigDecimal getCurrentAvailableCash() { return currentAvailableCash; }
    public void setCurrentAvailableCash(BigDecimal currentAvailableCash) { this.currentAvailableCash = currentAvailableCash; }

    public BigDecimal getAverageMonthlyInflow() { return averageMonthlyInflow; }
    public void setAverageMonthlyInflow(BigDecimal averageMonthlyInflow) { this.averageMonthlyInflow = averageMonthlyInflow; }

    public BigDecimal getAverageMonthlyOutflow() { return averageMonthlyOutflow; }
    public void setAverageMonthlyOutflow(BigDecimal averageMonthlyOutflow) { this.averageMonthlyOutflow = averageMonthlyOutflow; }

    public List<PeriodProjectionDTO> getProjections() { return projections; }
    public void setProjections(List<PeriodProjectionDTO> projections) { this.projections = projections; }

    public List<String> getAssumptions() { return assumptions; }
    public void setAssumptions(List<String> assumptions) { this.assumptions = assumptions; }

    public boolean isEstimate() { return estimate; }
    public void setEstimate(boolean estimate) { this.estimate = estimate; }
}
