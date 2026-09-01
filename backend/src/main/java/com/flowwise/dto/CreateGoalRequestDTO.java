package com.flowwise.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateGoalRequestDTO {
    private String goalType; // CASH_RESERVE, WORKING_CAPITAL, DEBT_REDUCTION, RECEIVABLES_COLLECTION, EXPENSE_REDUCTION
    private String name;
    private BigDecimal targetAmount;
    private LocalDate targetDate;

    public CreateGoalRequestDTO() {}

    public CreateGoalRequestDTO(String goalType, String name, BigDecimal targetAmount, LocalDate targetDate) {
        this.goalType = goalType;
        this.name = name;
        this.targetAmount = targetAmount;
        this.targetDate = targetDate;
    }

    public String getGoalType() { return goalType; }
    public void setGoalType(String goalType) { this.goalType = goalType; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getTargetAmount() { return targetAmount; }
    public void setTargetAmount(BigDecimal targetAmount) { this.targetAmount = targetAmount; }

    public LocalDate getTargetDate() { return targetDate; }
    public void setTargetDate(LocalDate targetDate) { this.targetDate = targetDate; }
}
