package com.flowwise.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "document_captures")
public class DocumentCapture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "merchant_id", nullable = false)
    private Long merchantId;

    @Column(name = "document_type", nullable = false)
    private String documentType; // RECEIPT, INVOICE, EXPENSE

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "file_url_or_data", columnDefinition = "TEXT")
    private String fileUrlOrData;

    @Column(name = "captured_at", nullable = false)
    private OffsetDateTime capturedAt;

    @Column(name = "status", nullable = false)
    private String status; // CAPTURED, EXTRACTED, CONFIRMED, DISCARDED

    @Column(name = "extracted_amount")
    private BigDecimal extractedAmount;

    @Column(name = "extracted_vendor")
    private String extractedVendor;

    @Column(name = "extracted_category")
    private String extractedCategory;

    @Column(name = "extracted_date")
    private OffsetDateTime extractedDate;

    @Column(name = "extracted_tax")
    private BigDecimal extractedTax;

    @Column(name = "extracted_reference")
    private String extractedReference;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    public DocumentCapture() {}

    @PrePersist
    protected void onCreate() {
        if (capturedAt == null) capturedAt = OffsetDateTime.now();
        if (createdAt == null) createdAt = OffsetDateTime.now();
        if (updatedAt == null) updatedAt = OffsetDateTime.now();
        if (status == null) status = "CAPTURED";
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public String getDocumentType() { return documentType; }
    public void setDocumentType(String documentType) { this.documentType = documentType; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }

    public Long getFileSize() { return fileSize; }
    public void setFileSize(Long fileSize) { this.fileSize = fileSize; }

    public String getFileUrlOrData() { return fileUrlOrData; }
    public void setFileUrlOrData(String fileUrlOrData) { this.fileUrlOrData = fileUrlOrData; }

    public OffsetDateTime getCapturedAt() { return capturedAt; }
    public void setCapturedAt(OffsetDateTime capturedAt) { this.capturedAt = capturedAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getExtractedAmount() { return extractedAmount; }
    public void setExtractedAmount(BigDecimal extractedAmount) { this.extractedAmount = extractedAmount; }

    public String getExtractedVendor() { return extractedVendor; }
    public void setExtractedVendor(String extractedVendor) { this.extractedVendor = extractedVendor; }

    public String getExtractedCategory() { return extractedCategory; }
    public void setExtractedCategory(String extractedCategory) { this.extractedCategory = extractedCategory; }

    public OffsetDateTime getExtractedDate() { return extractedDate; }
    public void setExtractedDate(OffsetDateTime extractedDate) { this.extractedDate = extractedDate; }

    public BigDecimal getExtractedTax() { return extractedTax; }
    public void setExtractedTax(BigDecimal extractedTax) { this.extractedTax = extractedTax; }

    public String getExtractedReference() { return extractedReference; }
    public void setExtractedReference(String extractedReference) { this.extractedReference = extractedReference; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
