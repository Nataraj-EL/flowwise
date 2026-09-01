package com.flowwise.dto;

import java.math.BigDecimal;
import java.util.List;

public class ReceivablesSummaryDTO {
    private BigDecimal totalOutstanding;
    private BigDecimal currentReceivables;
    private BigDecimal overdue1To30Days;
    private BigDecimal overdue31To60Days;
    private BigDecimal overdue60PlusDays;
    private BigDecimal totalOverdue;
    private BigDecimal collectionRatePct;
    private BigDecimal overdueRatioPct;
    private String largestOutstandingCounterparty;
    private BigDecimal largestCounterpartyAmount;
    private BigDecimal concentrationRatioPct;
    private BigDecimal estimatedNearTermCollection;
    private int totalInvoicesCount;
    private int overdueInvoicesCount;
    private List<ReceivableDTO> receivables;

    public ReceivablesSummaryDTO() {}

    public ReceivablesSummaryDTO(BigDecimal totalOutstanding, BigDecimal currentReceivables, 
                                 BigDecimal overdue1To30Days, BigDecimal overdue31To60Days, 
                                 BigDecimal overdue60PlusDays, BigDecimal totalOverdue, 
                                 BigDecimal collectionRatePct, BigDecimal overdueRatioPct, 
                                 String largestOutstandingCounterparty, BigDecimal largestCounterpartyAmount, 
                                 BigDecimal concentrationRatioPct, BigDecimal estimatedNearTermCollection, 
                                 int totalInvoicesCount, int overdueInvoicesCount, 
                                 List<ReceivableDTO> receivables) {
        this.totalOutstanding = totalOutstanding;
        this.currentReceivables = currentReceivables;
        this.overdue1To30Days = overdue1To30Days;
        this.overdue31To60Days = overdue31To60Days;
        this.overdue60PlusDays = overdue60PlusDays;
        this.totalOverdue = totalOverdue;
        this.collectionRatePct = collectionRatePct;
        this.overdueRatioPct = overdueRatioPct;
        this.largestOutstandingCounterparty = largestOutstandingCounterparty;
        this.largestCounterpartyAmount = largestCounterpartyAmount;
        this.concentrationRatioPct = concentrationRatioPct;
        this.estimatedNearTermCollection = estimatedNearTermCollection;
        this.totalInvoicesCount = totalInvoicesCount;
        this.overdueInvoicesCount = overdueInvoicesCount;
        this.receivables = receivables;
    }

    public BigDecimal getTotalOutstanding() { return totalOutstanding; }
    public void setTotalOutstanding(BigDecimal totalOutstanding) { this.totalOutstanding = totalOutstanding; }

    public BigDecimal getCurrentReceivables() { return currentReceivables; }
    public void setCurrentReceivables(BigDecimal currentReceivables) { this.currentReceivables = currentReceivables; }

    public BigDecimal getOverdue1To30Days() { return overdue1To30Days; }
    public void setOverdue1To30Days(BigDecimal overdue1To30Days) { this.overdue1To30Days = overdue1To30Days; }

    public BigDecimal getOverdue31To60Days() { return overdue31To60Days; }
    public void setOverdue31To60Days(BigDecimal overdue31To60Days) { this.overdue31To60Days = overdue31To60Days; }

    public BigDecimal getOverdue60PlusDays() { return overdue60PlusDays; }
    public void setOverdue60PlusDays(BigDecimal overdue60PlusDays) { this.overdue60PlusDays = overdue60PlusDays; }

    public BigDecimal getTotalOverdue() { return totalOverdue; }
    public void setTotalOverdue(BigDecimal totalOverdue) { this.totalOverdue = totalOverdue; }

    public BigDecimal getCollectionRatePct() { return collectionRatePct; }
    public void setCollectionRatePct(BigDecimal collectionRatePct) { this.collectionRatePct = collectionRatePct; }

    public BigDecimal getOverdueRatioPct() { return overdueRatioPct; }
    public void setOverdueRatioPct(BigDecimal overdueRatioPct) { this.overdueRatioPct = overdueRatioPct; }

    public String getLargestOutstandingCounterparty() { return largestOutstandingCounterparty; }
    public void setLargestOutstandingCounterparty(String largestOutstandingCounterparty) { this.largestOutstandingCounterparty = largestOutstandingCounterparty; }

    public BigDecimal getLargestCounterpartyAmount() { return largestCounterpartyAmount; }
    public void setLargestCounterpartyAmount(BigDecimal largestCounterpartyAmount) { this.largestCounterpartyAmount = largestCounterpartyAmount; }

    public BigDecimal getConcentrationRatioPct() { return concentrationRatioPct; }
    public void setConcentrationRatioPct(BigDecimal concentrationRatioPct) { this.concentrationRatioPct = concentrationRatioPct; }

    public BigDecimal getEstimatedNearTermCollection() { return estimatedNearTermCollection; }
    public void setEstimatedNearTermCollection(BigDecimal estimatedNearTermCollection) { this.estimatedNearTermCollection = estimatedNearTermCollection; }

    public int getTotalInvoicesCount() { return totalInvoicesCount; }
    public void setTotalInvoicesCount(int totalInvoicesCount) { this.totalInvoicesCount = totalInvoicesCount; }

    public int getOverdueInvoicesCount() { return overdueInvoicesCount; }
    public void setOverdueInvoicesCount(int overdueInvoicesCount) { this.overdueInvoicesCount = overdueInvoicesCount; }

    public List<ReceivableDTO> getReceivables() { return receivables; }
    public void setReceivables(List<ReceivableDTO> receivables) { this.receivables = receivables; }
}
