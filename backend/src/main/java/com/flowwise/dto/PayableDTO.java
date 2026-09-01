package com.flowwise.dto;

import java.math.BigDecimal;

public class PayableDTO {
    private Long id;
    private Long merchantId;
    private String vendor;
    private String billReference;
    private BigDecimal billAmount;
    private BigDecimal amountPaid;
    private BigDecimal outstandingAmount;
    private String billDate;
    private String dueDate;
    private String category; // INVENTORY, RENT, UTILITIES, LOGISTICS, SOFTWARE, TAX
    private String status; // DUE_TODAY, DUE_7_DAYS, DUE_30_DAYS, OVERDUE, PAID
    private long daysUntilDue;

    public PayableDTO() {}

    public PayableDTO(Long id, Long merchantId, String vendor, String billReference, 
                      BigDecimal billAmount, BigDecimal amountPaid, BigDecimal outstandingAmount, 
                      String billDate, String dueDate, String category, String status, long daysUntilDue) {
        this.id = id;
        this.merchantId = merchantId;
        this.vendor = vendor;
        this.billReference = billReference;
        this.billAmount = billAmount;
        this.amountPaid = amountPaid;
        this.outstandingAmount = outstandingAmount;
        this.billDate = billDate;
        this.dueDate = dueDate;
        this.category = category;
        this.status = status;
        this.daysUntilDue = daysUntilDue;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public String getVendor() { return vendor; }
    public void setVendor(String vendor) { this.vendor = vendor; }

    public String getBillReference() { return billReference; }
    public void setBillReference(String billReference) { this.billReference = billReference; }

    public BigDecimal getBillAmount() { return billAmount; }
    public void setBillAmount(BigDecimal billAmount) { this.billAmount = billAmount; }

    public BigDecimal getAmountPaid() { return amountPaid; }
    public void setAmountPaid(BigDecimal amountPaid) { this.amountPaid = amountPaid; }

    public BigDecimal getOutstandingAmount() { return outstandingAmount; }
    public void setOutstandingAmount(BigDecimal outstandingAmount) { this.outstandingAmount = outstandingAmount; }

    public String getBillDate() { return billDate; }
    public void setBillDate(String billDate) { this.billDate = billDate; }

    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public long getDaysUntilDue() { return daysUntilDue; }
    public void setDaysUntilDue(long daysUntilDue) { this.daysUntilDue = daysUntilDue; }
}
