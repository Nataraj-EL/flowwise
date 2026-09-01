package com.flowwise.dto;

import java.math.BigDecimal;

public class ReconciliationIssueDTO {
    private String id;
    private Long transactionId;
    private String issueType; // DUPLICATE, UNCATEGORIZED, SUSPICIOUS_AMOUNT, OFFICE_KIT_PENDING
    private String severity; // HIGH, MEDIUM, LOW
    private String description;
    private String counterparty;
    private BigDecimal amount;
    private String transactionDate;
    private String reconciliationStatus; // UNREVIEWED, RECONCILED, IGNORED, FLAGGED
    private String evidenceDetails;

    public ReconciliationIssueDTO() {}

    public ReconciliationIssueDTO(String id, Long transactionId, String issueType, 
                                  String severity, String description, String counterparty, 
                                  BigDecimal amount, String transactionDate, 
                                  String reconciliationStatus, String evidenceDetails) {
        this.id = id;
        this.transactionId = transactionId;
        this.issueType = issueType;
        this.severity = severity;
        this.description = description;
        this.counterparty = counterparty;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.reconciliationStatus = reconciliationStatus;
        this.evidenceDetails = evidenceDetails;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Long getTransactionId() { return transactionId; }
    public void setTransactionId(Long transactionId) { this.transactionId = transactionId; }

    public String getIssueType() { return issueType; }
    public void setIssueType(String issueType) { this.issueType = issueType; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCounterparty() { return counterparty; }
    public void setCounterparty(String counterparty) { this.counterparty = counterparty; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getTransactionDate() { return transactionDate; }
    public void setTransactionDate(String transactionDate) { this.transactionDate = transactionDate; }

    public String getReconciliationStatus() { return reconciliationStatus; }
    public void setReconciliationStatus(String reconciliationStatus) { this.reconciliationStatus = reconciliationStatus; }

    public String getEvidenceDetails() { return evidenceDetails; }
    public void setEvidenceDetails(String evidenceDetails) { this.evidenceDetails = evidenceDetails; }
}
