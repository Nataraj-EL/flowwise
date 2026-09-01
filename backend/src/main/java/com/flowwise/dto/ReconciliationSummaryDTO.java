package com.flowwise.dto;

import java.math.BigDecimal;
import java.util.List;

public class ReconciliationSummaryDTO {
    private int totalTransactions;
    private int reconciledCount;
    private int unreviewedCount;
    private int ignoredCount;
    private int flaggedCount;
    private int duplicateIssuesCount;
    private int uncategorizedIssuesCount;
    private int suspiciousIssuesCount;
    private int officeKitPendingCount;
    private BigDecimal reconciliationHealthPct;
    private List<ReconciliationIssueDTO> issues;

    public ReconciliationSummaryDTO() {}

    public ReconciliationSummaryDTO(int totalTransactions, int reconciledCount, int unreviewedCount, 
                                    int ignoredCount, int flaggedCount, int duplicateIssuesCount, 
                                    int uncategorizedIssuesCount, int suspiciousIssuesCount, 
                                    int officeKitPendingCount, BigDecimal reconciliationHealthPct, 
                                    List<ReconciliationIssueDTO> issues) {
        this.totalTransactions = totalTransactions;
        this.reconciledCount = reconciledCount;
        this.unreviewedCount = unreviewedCount;
        this.ignoredCount = ignoredCount;
        this.flaggedCount = flaggedCount;
        this.duplicateIssuesCount = duplicateIssuesCount;
        this.uncategorizedIssuesCount = uncategorizedIssuesCount;
        this.suspiciousIssuesCount = suspiciousIssuesCount;
        this.officeKitPendingCount = officeKitPendingCount;
        this.reconciliationHealthPct = reconciliationHealthPct;
        this.issues = issues;
    }

    public int getTotalTransactions() { return totalTransactions; }
    public void setTotalTransactions(int totalTransactions) { this.totalTransactions = totalTransactions; }

    public int getReconciledCount() { return reconciledCount; }
    public void setReconciledCount(int reconciledCount) { this.reconciledCount = reconciledCount; }

    public int getUnreviewedCount() { return unreviewedCount; }
    public void setUnreviewedCount(int unreviewedCount) { this.unreviewedCount = unreviewedCount; }

    public int getIgnoredCount() { return ignoredCount; }
    public void setIgnoredCount(int ignoredCount) { this.ignoredCount = ignoredCount; }

    public int getFlaggedCount() { return flaggedCount; }
    public void setFlaggedCount(int flaggedCount) { this.flaggedCount = flaggedCount; }

    public int getDuplicateIssuesCount() { return duplicateIssuesCount; }
    public void setDuplicateIssuesCount(int duplicateIssuesCount) { this.duplicateIssuesCount = duplicateIssuesCount; }

    public int getUncategorizedIssuesCount() { return uncategorizedIssuesCount; }
    public void setUncategorizedIssuesCount(int uncategorizedIssuesCount) { this.uncategorizedIssuesCount = uncategorizedIssuesCount; }

    public int getSuspiciousIssuesCount() { return suspiciousIssuesCount; }
    public void setSuspiciousIssuesCount(int suspiciousIssuesCount) { this.suspiciousIssuesCount = suspiciousIssuesCount; }

    public int getOfficeKitPendingCount() { return officeKitPendingCount; }
    public void setOfficeKitPendingCount(int officeKitPendingCount) { this.officeKitPendingCount = officeKitPendingCount; }

    public BigDecimal getReconciliationHealthPct() { return reconciliationHealthPct; }
    public void setReconciliationHealthPct(BigDecimal reconciliationHealthPct) { this.reconciliationHealthPct = reconciliationHealthPct; }

    public List<ReconciliationIssueDTO> getIssues() { return issues; }
    public void setIssues(List<ReconciliationIssueDTO> issues) { this.issues = issues; }
}
