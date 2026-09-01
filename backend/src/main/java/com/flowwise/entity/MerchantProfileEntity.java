package com.flowwise.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "merchant_profiles")
public class MerchantProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String businessName;

    @Column(nullable = false)
    private String demoGstin;

    @Column(nullable = false)
    private String primaryCategory;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public MerchantProfileEntity() {}

    public MerchantProfileEntity(String businessName, String demoGstin, String primaryCategory) {
        this.businessName = businessName;
        this.demoGstin = demoGstin;
        this.primaryCategory = primaryCategory;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getBusinessName() { return businessName; }
    public void setBusinessName(String businessName) { this.businessName = businessName; }
    public String getDemoGstin() { return demoGstin; }
    public void setDemoGstin(String demoGstin) { this.demoGstin = demoGstin; }
    public String getPrimaryCategory() { return primaryCategory; }
    public void setPrimaryCategory(String primaryCategory) { this.primaryCategory = primaryCategory; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
