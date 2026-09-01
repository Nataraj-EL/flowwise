package com.flowwise.dto;

import java.math.BigDecimal;
import java.util.List;

public class PayablesSummaryDTO {
    private BigDecimal totalOutstanding;
    private BigDecimal dueToday;
    private BigDecimal due7Days;
    private BigDecimal due30Days;
    private BigDecimal totalOverdue;
    private BigDecimal totalPaid;
    private BigDecimal paymentCoverageRatioPct;
    private BigDecimal upcomingPayablePressure;
    private String largestVendorObligation;
    private BigDecimal largestVendorAmount;
    private int totalBillsCount;
    private int overdueBillsCount;
    private List<PayableDTO> payables;

    public PayablesSummaryDTO() {}

    public PayablesSummaryDTO(BigDecimal totalOutstanding, BigDecimal dueToday, BigDecimal due7Days, 
                              BigDecimal due30Days, BigDecimal totalOverdue, BigDecimal totalPaid, 
                              BigDecimal paymentCoverageRatioPct, BigDecimal upcomingPayablePressure, 
                              String largestVendorObligation, BigDecimal largestVendorAmount, 
                              int totalBillsCount, int overdueBillsCount, List<PayableDTO> payables) {
        this.totalOutstanding = totalOutstanding;
        this.dueToday = dueToday;
        this.due7Days = due7Days;
        this.due30Days = due30Days;
        this.totalOverdue = totalOverdue;
        this.totalPaid = totalPaid;
        this.paymentCoverageRatioPct = paymentCoverageRatioPct;
        this.upcomingPayablePressure = upcomingPayablePressure;
        this.largestVendorObligation = largestVendorObligation;
        this.largestVendorAmount = largestVendorAmount;
        this.totalBillsCount = totalBillsCount;
        this.overdueBillsCount = overdueBillsCount;
        this.payables = payables;
    }

    public BigDecimal getTotalOutstanding() { return totalOutstanding; }
    public void setTotalOutstanding(BigDecimal totalOutstanding) { this.totalOutstanding = totalOutstanding; }

    public BigDecimal getDueToday() { return dueToday; }
    public void setDueToday(BigDecimal dueToday) { this.dueToday = dueToday; }

    public BigDecimal getDue7Days() { return due7Days; }
    public void setDue7Days(BigDecimal due7Days) { this.due7Days = due7Days; }

    public BigDecimal getDue30Days() { return due30Days; }
    public void setDue30Days(BigDecimal due30Days) { this.due30Days = due30Days; }

    public BigDecimal getTotalOverdue() { return totalOverdue; }
    public void setTotalOverdue(BigDecimal totalOverdue) { this.totalOverdue = totalOverdue; }

    public BigDecimal getTotalPaid() { return totalPaid; }
    public void setTotalPaid(BigDecimal totalPaid) { this.totalPaid = totalPaid; }

    public BigDecimal getPaymentCoverageRatioPct() { return paymentCoverageRatioPct; }
    public void setPaymentCoverageRatioPct(BigDecimal paymentCoverageRatioPct) { this.paymentCoverageRatioPct = paymentCoverageRatioPct; }

    public BigDecimal getUpcomingPayablePressure() { return upcomingPayablePressure; }
    public void setUpcomingPayablePressure(BigDecimal upcomingPayablePressure) { this.upcomingPayablePressure = upcomingPayablePressure; }

    public String getLargestVendorObligation() { return largestVendorObligation; }
    public void setLargestVendorObligation(String largestVendorObligation) { this.largestVendorObligation = largestVendorObligation; }

    public BigDecimal getLargestVendorAmount() { return largestVendorAmount; }
    public void setLargestVendorAmount(BigDecimal largestVendorAmount) { this.largestVendorAmount = largestVendorAmount; }

    public int getTotalBillsCount() { return totalBillsCount; }
    public void setTotalBillsCount(int totalBillsCount) { this.totalBillsCount = totalBillsCount; }

    public int getOverdueBillsCount() { return overdueBillsCount; }
    public void setOverdueBillsCount(int overdueBillsCount) { this.overdueBillsCount = overdueBillsCount; }

    public List<PayableDTO> getPayables() { return payables; }
    public void setPayables(List<PayableDTO> payables) { this.payables = payables; }
}
