package com.flowwise.dto;

import java.math.BigDecimal;
import java.util.List;

public class ScenarioComparisonDTO {
    private BigDecimal currentAvailableCash;
    private BigDecimal currentMonthlyBurnRate;
    private FinancialScenarioDTO baselineScenario;
    private FinancialScenarioDTO cautiousScenario;
    private FinancialScenarioDTO stressScenario;
    private List<FinancialScenarioDTO> allScenarios;
    private String primaryRiskAlert;
    private String summaryAdvice;

    public ScenarioComparisonDTO() {}

    public ScenarioComparisonDTO(BigDecimal currentAvailableCash, BigDecimal currentMonthlyBurnRate, 
                                 FinancialScenarioDTO baselineScenario, FinancialScenarioDTO cautiousScenario, 
                                 FinancialScenarioDTO stressScenario, List<FinancialScenarioDTO> allScenarios, 
                                 String primaryRiskAlert, String summaryAdvice) {
        this.currentAvailableCash = currentAvailableCash;
        this.currentMonthlyBurnRate = currentMonthlyBurnRate;
        this.baselineScenario = baselineScenario;
        this.cautiousScenario = cautiousScenario;
        this.stressScenario = stressScenario;
        this.allScenarios = allScenarios;
        this.primaryRiskAlert = primaryRiskAlert;
        this.summaryAdvice = summaryAdvice;
    }

    public BigDecimal getCurrentAvailableCash() { return currentAvailableCash; }
    public void setCurrentAvailableCash(BigDecimal currentAvailableCash) { this.currentAvailableCash = currentAvailableCash; }

    public BigDecimal getCurrentMonthlyBurnRate() { return currentMonthlyBurnRate; }
    public void setCurrentMonthlyBurnRate(BigDecimal currentMonthlyBurnRate) { this.currentMonthlyBurnRate = currentMonthlyBurnRate; }

    public FinancialScenarioDTO getBaselineScenario() { return baselineScenario; }
    public void setBaselineScenario(FinancialScenarioDTO baselineScenario) { this.baselineScenario = baselineScenario; }

    public FinancialScenarioDTO getCautiousScenario() { return cautiousScenario; }
    public void setCautiousScenario(FinancialScenarioDTO cautiousScenario) { this.cautiousScenario = cautiousScenario; }

    public FinancialScenarioDTO getStressScenario() { return stressScenario; }
    public void setStressScenario(FinancialScenarioDTO stressScenario) { this.stressScenario = stressScenario; }

    public List<FinancialScenarioDTO> getAllScenarios() { return allScenarios; }
    public void setAllScenarios(List<FinancialScenarioDTO> allScenarios) { this.allScenarios = allScenarios; }

    public String getPrimaryRiskAlert() { return primaryRiskAlert; }
    public void setPrimaryRiskAlert(String primaryRiskAlert) { this.primaryRiskAlert = primaryRiskAlert; }

    public String getSummaryAdvice() { return summaryAdvice; }
    public void setSummaryAdvice(String summaryAdvice) { this.summaryAdvice = summaryAdvice; }
}
