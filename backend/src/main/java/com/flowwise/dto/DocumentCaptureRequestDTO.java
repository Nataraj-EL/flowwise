package com.flowwise.dto;

import java.math.BigDecimal;

public class DocumentCaptureRequestDTO {
    private String documentType; // RECEIPT, INVOICE, EXPENSE
    private String fileName;
    private String fileData; // base64 or URL
    private String fileType;
    private Long fileSize;
    private BigDecimal amount;
    private String vendorName;
    private String category;

    public DocumentCaptureRequestDTO() {}

    public DocumentCaptureRequestDTO(String documentType, String fileName, String fileData, 
                                     String fileType, Long fileSize, BigDecimal amount, 
                                     String vendorName, String category) {
        this.documentType = documentType;
        this.fileName = fileName;
        this.fileData = fileData;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.amount = amount;
        this.vendorName = vendorName;
        this.category = category;
    }

    public String getDocumentType() { return documentType; }
    public void setDocumentType(String documentType) { this.documentType = documentType; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getFileData() { return fileData; }
    public void setFileData(String fileData) { this.fileData = fileData; }

    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }

    public Long getFileSize() { return fileSize; }
    public void setFileSize(Long fileSize) { this.fileSize = fileSize; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getVendorName() { return vendorName; }
    public void setVendorName(String vendorName) { this.vendorName = vendorName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
