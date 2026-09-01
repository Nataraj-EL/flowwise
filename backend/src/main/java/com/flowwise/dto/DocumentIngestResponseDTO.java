package com.flowwise.dto;

import java.math.BigDecimal;

public class DocumentIngestResponseDTO {
    private Long captureId;
    private Long transactionId;
    private Long merchantId;
    private BigDecimal amount;
    private String category;
    private String counterparty;
    private String transactionReference;
    private String sourceType;
    private String ingestionTimestamp;
    private boolean alreadyIngested;

    public DocumentIngestResponseDTO() {}

    public DocumentIngestResponseDTO(Long captureId, Long transactionId, Long merchantId, 
                                     BigDecimal amount, String category, String counterparty, 
                                     String transactionReference, String sourceType, 
                                     String ingestionTimestamp, boolean alreadyIngested) {
        this.captureId = captureId;
        this.transactionId = transactionId;
        this.merchantId = merchantId;
        this.amount = amount;
        this.category = category;
        this.counterparty = counterparty;
        this.transactionReference = transactionReference;
        this.sourceType = sourceType;
        this.ingestionTimestamp = ingestionTimestamp;
        this.alreadyIngested = alreadyIngested;
    }

    public Long getCaptureId() { return captureId; }
    public void setCaptureId(Long captureId) { this.captureId = captureId; }

    public Long getTransactionId() { return transactionId; }
    public void setTransactionId(Long transactionId) { this.transactionId = transactionId; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getCounterparty() { return counterparty; }
    public void setCounterparty(String counterparty) { this.counterparty = counterparty; }

    public String getTransactionReference() { return transactionReference; }
    public void setTransactionReference(String transactionReference) { this.transactionReference = transactionReference; }

    public String getSourceType() { return sourceType; }
    public void setSourceType(String sourceType) { this.sourceType = sourceType; }

    public String getIngestionTimestamp() { return ingestionTimestamp; }
    public void setIngestionTimestamp(String ingestionTimestamp) { this.ingestionTimestamp = ingestionTimestamp; }

    public boolean isAlreadyIngested() { return alreadyIngested; }
    public void setAlreadyIngested(boolean alreadyIngested) { this.alreadyIngested = alreadyIngested; }
}
