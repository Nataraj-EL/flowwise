package com.flowwise.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "merchants")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "business_name", nullable = false)
    private String businessName;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "business_type", nullable = false)
    private String businessType;

    @Column(name = "industry", nullable = false)
    private String industry;

    @Column(name = "demo_gstin", nullable = false)
    private String demoGstin;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BusinessAccount> accounts = new ArrayList<>();

    public Merchant() {}

    public Merchant(String businessName, String displayName, String businessType, String industry, String demoGstin) {
        this.businessName = businessName;
        this.displayName = displayName;
        this.businessType = businessType;
        this.industry = industry;
        this.demoGstin = demoGstin;
    }

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
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

    public List<BusinessAccount> getAccounts() { return accounts; }
    public void setAccounts(List<BusinessAccount> accounts) { this.accounts = accounts; }

    public void addAccount(BusinessAccount account) {
        accounts.add(account);
        account.setMerchant(this);
    }
}
