package com.flowwise.dto;

import java.math.BigDecimal;
import java.util.List;

public class TransactionSummaryDTO {
    private BigDecimal totalCredits;
    private BigDecimal totalDebits;
    private BigDecimal netCashFlow;
    private long transactionCount;
    private List<CategoryTotalDTO> categoryTotals;

    public TransactionSummaryDTO() {}

    public TransactionSummaryDTO(BigDecimal totalCredits, BigDecimal totalDebits, BigDecimal netCashFlow, 
                                 long transactionCount, List<CategoryTotalDTO> categoryTotals) {
        this.totalCredits = totalCredits;
        this.totalDebits = totalDebits;
        this.netCashFlow = netCashFlow;
        this.transactionCount = transactionCount;
        this.categoryTotals = categoryTotals;
    }

    public BigDecimal getTotalCredits() { return totalCredits; }
    public void setTotalCredits(BigDecimal totalCredits) { this.totalCredits = totalCredits; }

    public BigDecimal getTotalDebits() { return totalDebits; }
    public void setTotalDebits(BigDecimal totalDebits) { this.totalDebits = totalDebits; }

    public BigDecimal getNetCashFlow() { return netCashFlow; }
    public void setNetCashFlow(BigDecimal netCashFlow) { this.netCashFlow = netCashFlow; }

    public long getTransactionCount() { return transactionCount; }
    public void setTransactionCount(long transactionCount) { this.transactionCount = transactionCount; }

    public List<CategoryTotalDTO> getCategoryTotals() { return categoryTotals; }
    public void setCategoryTotals(List<CategoryTotalDTO> categoryTotals) { this.categoryTotals = categoryTotals; }
}
