package com.flowwise.dto;

import java.math.BigDecimal;

public class DocumentCaptureResponseDTO {
    private Long id;
    private Long merchantId;
    private String documentType;
    private String fileName;
    private String fileType;
    private Long fileSize;
    private String fileUrlOrData;
    private String capturedAt;
    private String status;
    private BigDecimal extractedAmount;
    private String extractedVendor;
    private String extractedCategory;
    private String extractedDate;
    private BigDecimal extractedTax;
    private String extractedReference;
    private String createdAt;
    private String updatedAt;

    public DocumentCaptureResponseDTO() {}

    public DocumentCaptureResponseDTO(Long id, Long merchantId, String documentType, String fileName, 
                                      String fileType, Long fileSize, String fileUrlOrData, String capturedAt, 
                                      String status, BigDecimal extractedAmount, String extractedVendor, 
                                      String extractedCategory, String extractedDate, BigDecimal extractedTax, 
                                      String extractedReference, String createdAt, String updatedAt) {
        this.id = id;
        this.merchantId = merchantId;
        this.documentType = documentType;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.fileUrlOrData = fileUrlOrData;
        this.capturedAt = capturedAt;
        this.status = status;
        this.extractedAmount = extractedAmount;
        this.extractedVendor = extractedVendor;
        this.extractedCategory = extractedCategory;
        this.extractedDate = extractedDate;
        this.extractedTax = extractedTax;
        this.extractedReference = extractedReference;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public String getCapturedAt() { return capturedAt; }
    public void setCapturedAt(String capturedAt) { this.capturedAt = capturedAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getExtractedAmount() { return extractedAmount; }
    public void setExtractedAmount(BigDecimal extractedAmount) { this.extractedAmount = extractedAmount; }

    public String getExtractedVendor() { return extractedVendor; }
    public void setExtractedVendor(String extractedVendor) { this.extractedVendor = extractedVendor; }

    public String getExtractedCategory() { return extractedCategory; }
    public void setExtractedCategory(String extractedCategory) { this.extractedCategory = extractedCategory; }

    public String getExtractedDate() { return extractedDate; }
    public void setExtractedDate(String extractedDate) { this.extractedDate = extractedDate; }

    public BigDecimal getExtractedTax() { return extractedTax; }
    public void setExtractedTax(BigDecimal extractedTax) { this.extractedTax = extractedTax; }

    public String getExtractedReference() { return extractedReference; }
    public void setExtractedReference(String extractedReference) { this.extractedReference = extractedReference; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
