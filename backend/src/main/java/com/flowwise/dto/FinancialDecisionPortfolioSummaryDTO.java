package com.flowwise.dto;

import java.util.List;

public class FinancialDecisionPortfolioSummaryDTO {
    private Long merchantId;
    private int totalPortfoliosCount;
    private FinancialDecisionPortfolioDTO activePortfolio;
    private List<FinancialDecisionPortfolioDTO> portfolios;
    private String summaryExplanation;
    private String advisoryNotice;

    public FinancialDecisionPortfolioSummaryDTO() {}

    public FinancialDecisionPortfolioSummaryDTO(Long merchantId, int totalPortfoliosCount,
                                               FinancialDecisionPortfolioDTO activePortfolio,
                                               List<FinancialDecisionPortfolioDTO> portfolios,
                                               String summaryExplanation, String advisoryNotice) {
        this.merchantId = merchantId;
        this.totalPortfoliosCount = totalPortfoliosCount;
        this.activePortfolio = activePortfolio;
        this.portfolios = portfolios;
        this.summaryExplanation = summaryExplanation;
        this.advisoryNotice = advisoryNotice;
    }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public int getTotalPortfoliosCount() { return totalPortfoliosCount; }
    public void setTotalPortfoliosCount(int totalPortfoliosCount) { this.totalPortfoliosCount = totalPortfoliosCount; }

    public FinancialDecisionPortfolioDTO getActivePortfolio() { return activePortfolio; }
    public void setActivePortfolio(FinancialDecisionPortfolioDTO activePortfolio) { this.activePortfolio = activePortfolio; }

    public List<FinancialDecisionPortfolioDTO> getPortfolios() { return portfolios; }
    public void setPortfolios(List<FinancialDecisionPortfolioDTO> portfolios) { this.portfolios = portfolios; }

    public String getSummaryExplanation() { return summaryExplanation; }
    public void setSummaryExplanation(String summaryExplanation) { this.summaryExplanation = summaryExplanation; }

    public String getAdvisoryNotice() { return advisoryNotice; }
    public void setAdvisoryNotice(String advisoryNotice) { this.advisoryNotice = advisoryNotice; }
}
