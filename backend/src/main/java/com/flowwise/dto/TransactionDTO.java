package com.flowwise.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class TransactionDTO {
    private Long id;
    private Long merchantId;
    private Long businessAccountId;
    private String institutionName;
    private String transactionReference;
    private Instant transactionDate;
    private String description;
    private BigDecimal amount;
    private String type; // CREDIT / DEBIT
    private String category;
    private String subcategory;
    private String counterparty;
    private String paymentMethod;
    private String status;
    private String demoTag;

    public TransactionDTO() {}

    public TransactionDTO(Long id, Long merchantId, Long businessAccountId, String institutionName, 
                          String transactionReference, Instant transactionDate, String description, 
                          BigDecimal amount, String type, String category, String subcategory, 
                          String counterparty, String paymentMethod, String status) {
        this.id = id;
        this.merchantId = merchantId;
        this.businessAccountId = businessAccountId;
        this.institutionName = institutionName;
        this.transactionReference = transactionReference;
        this.transactionDate = transactionDate;
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.subcategory = subcategory;
        this.counterparty = counterparty;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.demoTag = "DEMO-DATA";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public Long getBusinessAccountId() { return businessAccountId; }
    public void setBusinessAccountId(Long businessAccountId) { this.businessAccountId = businessAccountId; }

    public String getInstitutionName() { return institutionName; }
    public void setInstitutionName(String institutionName) { this.institutionName = institutionName; }

    public String getTransactionReference() { return transactionReference; }
    public void setTransactionReference(String transactionReference) { this.transactionReference = transactionReference; }

    public Instant getTransactionDate() { return transactionDate; }
    public void setTransactionDate(Instant transactionDate) { this.transactionDate = transactionDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getSubcategory() { return subcategory; }
    public void setSubcategory(String subcategory) { this.subcategory = subcategory; }

    public String getCounterparty() { return counterparty; }
    public void setCounterparty(String counterparty) { this.counterparty = counterparty; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDemoTag() { return demoTag; }
    public void setDemoTag(String demoTag) { this.demoTag = demoTag; }
}
