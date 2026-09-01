package com.flowwise.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "financial_goals")
public class FinancialGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(name = "goal_type", nullable = false, length = 64)
    private String goalType; // CASH_RESERVE, WORKING_CAPITAL, DEBT_REDUCTION, RECEIVABLES_COLLECTION, EXPENSE_REDUCTION

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "target_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal targetAmount;

    @Column(name = "initial_baseline_amount", precision = 19, scale = 2)
    private BigDecimal initialBaselineAmount;

    @Column(name = "target_date", nullable = false)
    private LocalDate targetDate;

    @Column(name = "status", nullable = false, length = 32)
    private String status = "ACTIVE"; // ACTIVE, ACHIEVED, AT_RISK, EXPIRED, ARCHIVED

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    public FinancialGoal() {}

    public FinancialGoal(Merchant merchant, String goalType, String name, BigDecimal targetAmount, BigDecimal initialBaselineAmount, LocalDate targetDate) {
        this.merchant = merchant;
        this.goalType = goalType;
        this.name = name;
        this.targetAmount = targetAmount;
        this.initialBaselineAmount = initialBaselineAmount != null ? initialBaselineAmount : BigDecimal.ZERO;
        this.targetDate = targetDate;
        this.status = "ACTIVE";
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Merchant getMerchant() { return merchant; }
    public void setMerchant(Merchant merchant) { this.merchant = merchant; }

    public String getGoalType() { return goalType; }
    public void setGoalType(String goalType) { this.goalType = goalType; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getTargetAmount() { return targetAmount; }
    public void setTargetAmount(BigDecimal targetAmount) { this.targetAmount = targetAmount; }

    public BigDecimal getInitialBaselineAmount() { return initialBaselineAmount; }
    public void setInitialBaselineAmount(BigDecimal initialBaselineAmount) { this.initialBaselineAmount = initialBaselineAmount; }

    public LocalDate getTargetDate() { return targetDate; }
    public void setTargetDate(LocalDate targetDate) { this.targetDate = targetDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
