package com.flowwise.dto;

import java.math.BigDecimal;

public class ScenarioRequestDTO {
    private BigDecimal amount;
    private String category; // INVENTORY, PAYROLL, RENT, OPERATIONS

    public ScenarioRequestDTO() {}

    public ScenarioRequestDTO(BigDecimal amount, String category) {
        this.amount = amount;
        this.category = category;
    }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
