package com.flowwise.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class BusinessAccountDTO {
    private Long id;
    private Long merchantId;
    private String institutionName;
    private String accountType;
    private String maskedAccountRef;
    private BigDecimal currentBalance;
    private String currency;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;

    public BusinessAccountDTO() {}

    public BusinessAccountDTO(Long id, Long merchantId, String institutionName, String accountType, 
                              String maskedAccountRef, BigDecimal currentBalance, String currency, 
                              String status, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.merchantId = merchantId;
        this.institutionName = institutionName;
        this.accountType = accountType;
        this.maskedAccountRef = maskedAccountRef;
        this.currentBalance = currentBalance;
        this.currency = currency;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

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

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
