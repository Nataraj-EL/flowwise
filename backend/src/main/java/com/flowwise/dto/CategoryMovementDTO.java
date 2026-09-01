package com.flowwise.dto;

import java.math.BigDecimal;

public class CategoryMovementDTO {
    private String category;
    private BigDecimal currentAmount;
    private BigDecimal previousAmount;
    private BigDecimal changeAmount;
    private BigDecimal changePct;
    private String direction; // INCREASED, DECREASED, STABLE

    public CategoryMovementDTO() {}

    public CategoryMovementDTO(String category, BigDecimal currentAmount, BigDecimal previousAmount, 
                               BigDecimal changeAmount, BigDecimal changePct, String direction) {
        this.category = category;
        this.currentAmount = currentAmount;
        this.previousAmount = previousAmount;
        this.changeAmount = changeAmount;
        this.changePct = changePct;
        this.direction = direction;
    }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public BigDecimal getCurrentAmount() { return currentAmount; }
    public void setCurrentAmount(BigDecimal currentAmount) { this.currentAmount = currentAmount; }

    public BigDecimal getPreviousAmount() { return previousAmount; }
    public void setPreviousAmount(BigDecimal previousAmount) { this.previousAmount = previousAmount; }

    public BigDecimal getChangeAmount() { return changeAmount; }
    public void setChangeAmount(BigDecimal changeAmount) { this.changeAmount = changeAmount; }

    public BigDecimal getChangePct() { return changePct; }
    public void setChangePct(BigDecimal changePct) { this.changePct = changePct; }

    public String getDirection() { return direction; }
    public void setDirection(String direction) { this.direction = direction; }
}
