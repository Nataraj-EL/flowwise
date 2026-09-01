package com.flowwise.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "receivables")
public class Receivable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "merchant_id", nullable = false)
    private Long merchantId;

    @Column(name = "counterparty", nullable = false)
    private String counterparty;

    @Column(name = "invoice_reference", nullable = false, unique = true)
    private String invoiceReference;

    @Column(name = "invoice_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal invoiceAmount;

    @Column(name = "amount_received", nullable = false, precision = 15, scale = 2)
    private BigDecimal amountReceived = BigDecimal.ZERO;

    @Column(name = "outstanding_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal outstandingAmount;

    @Column(name = "invoice_date", nullable = false)
    private OffsetDateTime invoiceDate;

    @Column(name = "due_date", nullable = false)
    private OffsetDateTime dueDate;

    @Column(name = "status", nullable = false)
    private String status; // CURRENT, OVERDUE_1_30, OVERDUE_31_60, OVERDUE_60_PLUS, PAID

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    public Receivable() {}

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) createdAt = OffsetDateTime.now();
        if (updatedAt == null) updatedAt = OffsetDateTime.now();
        if (amountReceived == null) amountReceived = BigDecimal.ZERO;
        if (invoiceAmount != null && outstandingAmount == null) {
            outstandingAmount = invoiceAmount.subtract(amountReceived);
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
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

    public OffsetDateTime getInvoiceDate() { return invoiceDate; }
    public void setInvoiceDate(OffsetDateTime invoiceDate) { this.invoiceDate = invoiceDate; }

    public OffsetDateTime getDueDate() { return dueDate; }
    public void setDueDate(OffsetDateTime dueDate) { this.dueDate = dueDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
