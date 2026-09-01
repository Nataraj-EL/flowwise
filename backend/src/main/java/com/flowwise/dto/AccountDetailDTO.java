package com.flowwise.dto;

import java.math.BigDecimal;

public class AccountDetailDTO {
    private Long accountId;
    private String institutionName;
    private String accountType;
    private String maskedAccountRef;
    private BigDecimal currentBalance;
    private String currency;
    private String status;
    private BigDecimal cashContributionPct;
    private BigDecimal totalCredits;
    private BigDecimal totalDebits;
    private BigDecimal netCashFlow;
    private int transactionCount;

    public AccountDetailDTO() {}

    public AccountDetailDTO(Long accountId, String institutionName, String accountType, 
                            String maskedAccountRef, BigDecimal currentBalance, String currency, 
                            String status, BigDecimal cashContributionPct, BigDecimal totalCredits, 
                            BigDecimal totalDebits, BigDecimal netCashFlow, int transactionCount) {
        this.accountId = accountId;
        this.institutionName = institutionName;
        this.accountType = accountType;
        this.maskedAccountRef = maskedAccountRef;
        this.currentBalance = currentBalance;
        this.currency = currency;
        this.status = status;
        this.cashContributionPct = cashContributionPct;
        this.totalCredits = totalCredits;
        this.totalDebits = totalDebits;
        this.netCashFlow = netCashFlow;
        this.transactionCount = transactionCount;
    }

    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }

    public String getInstitutionName() { return institutionName; }
    public void setInstitutionName(String institutionName) { this.institutionName = institutionName; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public String getMaskedAccountRef() { return maskedAccountRef; }
    public void setMaskedAccountRef(String maskedAccountRef) { this.maskedAccountRef = maskedAccountRef; }

    public BigDecimal getCurrentBalance() { return currentBalance; }
    public void setCurrentBalance(BigDecimal currentBalance) { this.currentBalance = currentBalance; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getCashContributionPct() { return cashContributionPct; }
    public void setCashContributionPct(BigDecimal cashContributionPct) { this.cashContributionPct = cashContributionPct; }

    public BigDecimal getTotalCredits() { return totalCredits; }
    public void setTotalCredits(BigDecimal totalCredits) { this.totalCredits = totalCredits; }

    public BigDecimal getTotalDebits() { return totalDebits; }
    public void setTotalDebits(BigDecimal totalDebits) { this.totalDebits = totalDebits; }

    public BigDecimal getNetCashFlow() { return netCashFlow; }
    public void setNetCashFlow(BigDecimal netCashFlow) { this.netCashFlow = netCashFlow; }

    public int getTransactionCount() { return transactionCount; }
    public void setTransactionCount(int transactionCount) { this.transactionCount = transactionCount; }
}
