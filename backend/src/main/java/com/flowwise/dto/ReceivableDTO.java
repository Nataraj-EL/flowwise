package com.flowwise.dto;

import java.math.BigDecimal;

public class ReceivableDTO {
    private Long id;
    private Long merchantId;
    private String counterparty;
    private String invoiceReference;
    private BigDecimal invoiceAmount;
    private BigDecimal amountReceived;
    private BigDecimal outstandingAmount;
    private String invoiceDate;
    private String dueDate;
    private String status; // CURRENT, OVERDUE_1_30, OVERDUE_31_60, OVERDUE_60_PLUS, PAID
    private long daysOverdue;

    public ReceivableDTO() {}

    public ReceivableDTO(Long id, Long merchantId, String counterparty, String invoiceReference, 
                         BigDecimal invoiceAmount, BigDecimal amountReceived, BigDecimal outstandingAmount, 
                         String invoiceDate, String dueDate, String status, long daysOverdue) {
        this.id = id;
        this.merchantId = merchantId;
        this.counterparty = counterparty;
        this.invoiceReference = invoiceReference;
        this.invoiceAmount = invoiceAmount;
        this.amountReceived = amountReceived;
        this.outstandingAmount = outstandingAmount;
        this.invoiceDate = invoiceDate;
        this.dueDate = dueDate;
        this.status = status;
        this.daysOverdue = daysOverdue;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public String getCounterparty() { return counterparty; }
    public void setCounterparty(String counterparty) { this.counterparty = counterparty; }

    public String getInvoiceReference() { return invoiceReference; }
    public void setInvoiceReference(String invoiceReference) { this.invoiceReference = invoiceReference; }

    public BigDecimal getInvoiceAmount() { return invoiceAmount; }
    public void setInvoiceAmount(BigDecimal invoiceAmount) { this.invoiceAmount = invoiceAmount; }

    public BigDecimal getAmountReceived() { return amountReceived; }
    public void setAmountReceived(BigDecimal amountReceived) { this.amountReceived = amountReceived; }

    public BigDecimal getOutstandingAmount() { return outstandingAmount; }
    public void setOutstandingAmount(BigDecimal outstandingAmount) { this.outstandingAmount = outstandingAmount; }

    public String getInvoiceDate() { return invoiceDate; }
    public void setInvoiceDate(String invoiceDate) { this.invoiceDate = invoiceDate; }

    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public long getDaysOverdue() { return daysOverdue; }
    public void setDaysOverdue(long daysOverdue) { this.daysOverdue = daysOverdue; }
}
