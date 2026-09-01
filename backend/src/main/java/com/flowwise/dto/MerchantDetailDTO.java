package com.flowwise.dto;

import java.math.BigDecimal;
import java.util.List;

public class MerchantDetailDTO {
    private MerchantDTO merchant;
    private List<BusinessAccountDTO> accounts;
    private BigDecimal totalAvailableCash;
    private int connectedAccountsCount;

    public MerchantDetailDTO() {}

    public MerchantDetailDTO(MerchantDTO merchant, List<BusinessAccountDTO> accounts, BigDecimal totalAvailableCash) {
        this.merchant = merchant;
        this.accounts = accounts;
        this.totalAvailableCash = totalAvailableCash;
        this.connectedAccountsCount = accounts != null ? accounts.size() : 0;
    }

    public MerchantDTO getMerchant() { return merchant; }
    public void setMerchant(MerchantDTO merchant) { this.merchant = merchant; }

    public List<BusinessAccountDTO> getAccounts() { return accounts; }
    public void setAccounts(List<BusinessAccountDTO> accounts) { 
        this.accounts = accounts; 
        this.connectedAccountsCount = accounts != null ? accounts.size() : 0;
    }

    public BigDecimal getTotalAvailableCash() { return totalAvailableCash; }
    public void setTotalAvailableCash(BigDecimal totalAvailableCash) { this.totalAvailableCash = totalAvailableCash; }

    public int getConnectedAccountsCount() { return connectedAccountsCount; }
    public void setConnectedAccountsCount(int connectedAccountsCount) { this.connectedAccountsCount = connectedAccountsCount; }
}
