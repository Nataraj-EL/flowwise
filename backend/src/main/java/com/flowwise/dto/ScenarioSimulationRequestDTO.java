package com.flowwise.dto;

import java.math.BigDecimal;

public class ScenarioSimulationRequestDTO {
    private String scenarioType; // BASELINE, CAUTIOUS, STRESS, CUSTOM
    private String name;
    private BigDecimal revenueModifierPct; // e.g. -10.00 for -10%
    private BigDecimal expenseModifierPct; // e.g. 5.00 for +5%
    private BigDecimal receivableCollectionPct; // 0.00 to 100.00
    private BigDecimal payableAccelerationPct; // 0.00 to 100.00
    private boolean saveScenario = false;

    public ScenarioSimulationRequestDTO() {}

    public ScenarioSimulationRequestDTO(String scenarioType, String name, BigDecimal revenueModifierPct, 
                                        BigDecimal expenseModifierPct, BigDecimal receivableCollectionPct, 
                                        BigDecimal payableAccelerationPct, boolean saveScenario) {
        this.scenarioType = scenarioType;
        this.name = name;
        this.revenueModifierPct = revenueModifierPct;
        this.expenseModifierPct = expenseModifierPct;
        this.receivableCollectionPct = receivableCollectionPct;
        this.payableAccelerationPct = payableAccelerationPct;
        this.saveScenario = saveScenario;
    }

    public String getScenarioType() { return scenarioType; }
    public void setScenarioType(String scenarioType) { this.scenarioType = scenarioType; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getRevenueModifierPct() { return revenueModifierPct; }
    public void setRevenueModifierPct(BigDecimal revenueModifierPct) { this.revenueModifierPct = revenueModifierPct; }

    public BigDecimal getExpenseModifierPct() { return expenseModifierPct; }
    public void setExpenseModifierPct(BigDecimal expenseModifierPct) { this.expenseModifierPct = expenseModifierPct; }

    public BigDecimal getReceivableCollectionPct() { return receivableCollectionPct; }
    public void setReceivableCollectionPct(BigDecimal receivableCollectionPct) { this.receivableCollectionPct = receivableCollectionPct; }

    public BigDecimal getPayableAccelerationPct() { return payableAccelerationPct; }
    public void setPayableAccelerationPct(BigDecimal payableAccelerationPct) { this.payableAccelerationPct = payableAccelerationPct; }

    public boolean isSaveScenario() { return saveScenario; }
    public void setSaveScenario(boolean saveScenario) { this.saveScenario = saveScenario; }
}
