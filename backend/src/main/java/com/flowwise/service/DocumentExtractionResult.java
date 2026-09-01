package com.flowwise.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class DocumentExtractionResult {
    private BigDecimal amount;
    private String vendorName;
    private String category;
    private BigDecimal tax;
    private String reference;
    private OffsetDateTime extractedDate;

    public DocumentExtractionResult() {}

    public DocumentExtractionResult(BigDecimal amount, String vendorName, String category, 
                                    BigDecimal tax, String reference, OffsetDateTime extractedDate) {
        this.amount = amount;
        this.vendorName = vendorName;
        this.category = category;
        this.tax = tax;
        this.reference = reference;
        this.extractedDate = extractedDate;
    }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getVendorName() { return vendorName; }
    public void setVendorName(String vendorName) { this.vendorName = vendorName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public BigDecimal getTax() { return tax; }
    public void setTax(BigDecimal tax) { this.tax = tax; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public OffsetDateTime getExtractedDate() { return extractedDate; }
    public void setExtractedDate(OffsetDateTime extractedDate) { this.extractedDate = extractedDate; }
}
