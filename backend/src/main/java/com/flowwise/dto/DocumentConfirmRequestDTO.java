package com.flowwise.dto;

import java.math.BigDecimal;

public class DocumentConfirmRequestDTO {
    private BigDecimal amount;
    private String vendorName;
    private String category;
    private String reference;

    public DocumentConfirmRequestDTO() {}

    public DocumentConfirmRequestDTO(BigDecimal amount, String vendorName, String category, String reference) {
        this.amount = amount;
        this.vendorName = vendorName;
        this.category = category;
        this.reference = reference;
    }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getVendorName() { return vendorName; }
    public void setVendorName(String vendorName) { this.vendorName = vendorName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
}
