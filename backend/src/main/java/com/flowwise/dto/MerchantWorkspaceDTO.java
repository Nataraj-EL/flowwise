package com.flowwise.dto;

import java.math.BigDecimal;
import java.util.List;

public class MerchantWorkspaceDTO {
    private Long merchantId;
    private String businessName;
    private String displayName;
    private String businessType;
    private String industry;
    private String demoGstin;
    private BigDecimal totalAvailableCash;
    private int connectedAccountsCount;
    private List<AccountDetailDTO> accounts;
    private BigDecimal consolidatedNetCashFlow;
    private int consolidatedTransactionCount;

    public MerchantWorkspaceDTO() {}

    public MerchantWorkspaceDTO(Long merchantId, String businessName, String displayName, 
                                String businessType, String industry, String demoGstin, 
                                BigDecimal totalAvailableCash, int connectedAccountsCount, 
                                List<AccountDetailDTO> accounts, BigDecimal consolidatedNetCashFlow, 
                                int consolidatedTransactionCount) {
        this.merchantId = merchantId;
        this.businessName = businessName;
        this.displayName = displayName;
        this.businessType = businessType;
        this.industry = industry;
        this.demoGstin = demoGstin;
        this.totalAvailableCash = totalAvailableCash;
        this.connectedAccountsCount = connectedAccountsCount;
        this.accounts = accounts;
        this.consolidatedNetCashFlow = consolidatedNetCashFlow;
        this.consolidatedTransactionCount = consolidatedTransactionCount;
    }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

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

    public BigDecimal getTotalAvailableCash() { return totalAvailableCash; }
    public void setTotalAvailableCash(BigDecimal totalAvailableCash) { this.totalAvailableCash = totalAvailableCash; }

    public int getConnectedAccountsCount() { return connectedAccountsCount; }
    public void setConnectedAccountsCount(int connectedAccountsCount) { this.connectedAccountsCount = connectedAccountsCount; }

    public List<AccountDetailDTO> getAccounts() { return accounts; }
    public void setAccounts(List<AccountDetailDTO> accounts) { this.accounts = accounts; }

    public BigDecimal getConsolidatedNetCashFlow() { return consolidatedNetCashFlow; }
    public void setConsolidatedNetCashFlow(BigDecimal consolidatedNetCashFlow) { this.consolidatedNetCashFlow = consolidatedNetCashFlow; }

    public int getConsolidatedTransactionCount() { return consolidatedTransactionCount; }
    public void setConsolidatedTransactionCount(int consolidatedTransactionCount) { this.consolidatedTransactionCount = consolidatedTransactionCount; }
}
