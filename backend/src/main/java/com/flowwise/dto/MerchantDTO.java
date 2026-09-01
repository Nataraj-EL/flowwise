package com.flowwise.dto;

import java.time.Instant;

public class MerchantDTO {
    private Long id;
    private String businessName;
    private String displayName;
    private String businessType;
    private String industry;
    private String demoGstin;
    private Instant createdAt;
    private Instant updatedAt;

    public MerchantDTO() {}

    public MerchantDTO(Long id, String businessName, String displayName, String businessType, 
                       String industry, String demoGstin, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.businessName = businessName;
        this.displayName = displayName;
        this.businessType = businessType;
        this.industry = industry;
        this.demoGstin = demoGstin;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBusinessName() { return businessName; }
    public void setBusinessName(String businessName) { this.businessName = businessName; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getBusinessType() { return businessType; }
    public void setBusinessType(String businessType) { this.businessType = businessType; }

    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }

    public String getDemoGstin() { return demoGstin; }
    public void setDemoGstin(String demoGstin) { this.demoGstin = demoGstin; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
