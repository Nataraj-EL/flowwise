package com.flowwise.dto;

import java.math.BigDecimal;

public class PaymentItemDTO {
    private Long payableId;
    private String vendor;
    private String billReference;
    private BigDecimal billAmount;
    private BigDecimal outstandingAmount;
    private String billDate;
    private String dueDate;
    private String category;
    private String priority; // P1_CRITICAL, P2_HIGH, P3_MEDIUM, P4_DEFERRABLE
    private String priorityReason;
    private int daysUntilDue;
    private String advisoryStatus; // RECOMMENDED, HOLD_NEEDS_FUNDS, DEFERRED

    public PaymentItemDTO() {}

    public PaymentItemDTO(Long payableId, String vendor, String billReference, 
                          BigDecimal billAmount, BigDecimal outstandingAmount, 
                          String billDate, String dueDate, String category, 
                          String priority, String priorityReason, int daysUntilDue, 
                          String advisoryStatus) {
        this.payableId = payableId;
        this.vendor = vendor;
        this.billReference = billReference;
        this.billAmount = billAmount;
        this.outstandingAmount = outstandingAmount;
        this.billDate = billDate;
        this.dueDate = dueDate;
        this.category = category;
        this.priority = priority;
        this.priorityReason = priorityReason;
        this.daysUntilDue = daysUntilDue;
        this.advisoryStatus = advisoryStatus;
    }

    public Long getPayableId() { return payableId; }
    public void setPayableId(Long payableId) { this.payableId = payableId; }

    public String getVendor() { return vendor; }
    public void setVendor(String vendor) { this.vendor = vendor; }

    public String getBillReference() { return billReference; }
    public void setBillReference(String billReference) { this.billReference = billReference; }

    public BigDecimal getBillAmount() { return billAmount; }
    public void setBillAmount(BigDecimal billAmount) { this.billAmount = billAmount; }

    public BigDecimal getOutstandingAmount() { return outstandingAmount; }
    public void setOutstandingAmount(BigDecimal outstandingAmount) { this.outstandingAmount = outstandingAmount; }

    public String getBillDate() { return billDate; }
    public void setBillDate(String billDate) { this.billDate = billDate; }

    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getPriorityReason() { return priorityReason; }
    public void setPriorityReason(String priorityReason) { this.priorityReason = priorityReason; }

    public int getDaysUntilDue() { return daysUntilDue; }
    public void setDaysUntilDue(int daysUntilDue) { this.daysUntilDue = daysUntilDue; }

    public String getAdvisoryStatus() { return advisoryStatus; }
    public void setAdvisoryStatus(String advisoryStatus) { this.advisoryStatus = advisoryStatus; }
}
