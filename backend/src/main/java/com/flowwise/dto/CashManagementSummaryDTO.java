package com.flowwise.dto;

import java.math.BigDecimal;
import java.util.List;

public class CashManagementSummaryDTO {
    private BigDecimal currentAvailableCash;
    private BigDecimal upcoming7DayObligations;
    private BigDecimal upcoming30DayObligations;
    private BigDecimal expected7DayCollections;
    private BigDecimal expected30DayCollections;
    private BigDecimal projected7DayCashPosition;
    private BigDecimal projected30DayCashPosition;
    private BigDecimal safePaymentCapacity;
    private String paymentRiskStatus; // SAFE, CAUTION, AT_RISK
    private List<PaymentItemDTO> topRecommendedPayments;
    private String summaryExplanation;
    private String calculationBasis;
    private List<String> assumptions;
    private String advisoryNotice;

    public CashManagementSummaryDTO() {}

    public CashManagementSummaryDTO(BigDecimal currentAvailableCash, BigDecimal upcoming7DayObligations, 
                                    BigDecimal upcoming30DayObligations, BigDecimal expected7DayCollections, 
                                    BigDecimal expected30DayCollections, BigDecimal projected7DayCashPosition, 
                                    BigDecimal projected30DayCashPosition, BigDecimal safePaymentCapacity, 
                                    String paymentRiskStatus, List<PaymentItemDTO> topRecommendedPayments, 
                                    String summaryExplanation, String calculationBasis, 
                                    List<String> assumptions, String advisoryNotice) {
        this.currentAvailableCash = currentAvailableCash;
        this.upcoming7DayObligations = upcoming7DayObligations;
        this.upcoming30DayObligations = upcoming30DayObligations;
        this.expected7DayCollections = expected7DayCollections;
        this.expected30DayCollections = expected30DayCollections;
        this.projected7DayCashPosition = projected7DayCashPosition;
        this.projected30DayCashPosition = projected30DayCashPosition;
        this.safePaymentCapacity = safePaymentCapacity;
        this.paymentRiskStatus = paymentRiskStatus;
        this.topRecommendedPayments = topRecommendedPayments;
        this.summaryExplanation = summaryExplanation;
        this.calculationBasis = calculationBasis;
        this.assumptions = assumptions;
        this.advisoryNotice = advisoryNotice;
    }

    public BigDecimal getCurrentAvailableCash() { return currentAvailableCash; }
    public void setCurrentAvailableCash(BigDecimal currentAvailableCash) { this.currentAvailableCash = currentAvailableCash; }

    public BigDecimal getUpcoming7DayObligations() { return upcoming7DayObligations; }
    public void setUpcoming7DayObligations(BigDecimal upcoming7DayObligations) { this.upcoming7DayObligations = upcoming7DayObligations; }

    public BigDecimal getUpcoming30DayObligations() { return upcoming30DayObligations; }
    public void setUpcoming30DayObligations(BigDecimal upcoming30DayObligations) { this.upcoming30DayObligations = upcoming30DayObligations; }

    public BigDecimal getExpected7DayCollections() { return expected7DayCollections; }
    public void setExpected7DayCollections(BigDecimal expected7DayCollections) { this.expected7DayCollections = expected7DayCollections; }

    public BigDecimal getExpected30DayCollections() { return expected30DayCollections; }
    public void setExpected30DayCollections(BigDecimal expected30DayCollections) { this.expected30DayCollections = expected30DayCollections; }

    public BigDecimal getProjected7DayCashPosition() { return projected7DayCashPosition; }
    public void setProjected7DayCashPosition(BigDecimal projected7DayCashPosition) { this.projected7DayCashPosition = projected7DayCashPosition; }

    public BigDecimal getProjected30DayCashPosition() { return projected30DayCashPosition; }
    public void setProjected30DayCashPosition(BigDecimal projected30DayCashPosition) { this.projected30DayCashPosition = projected30DayCashPosition; }

    public BigDecimal getSafePaymentCapacity() { return safePaymentCapacity; }
    public void setSafePaymentCapacity(BigDecimal safePaymentCapacity) { this.safePaymentCapacity = safePaymentCapacity; }

    public String getPaymentRiskStatus() { return paymentRiskStatus; }
    public void setPaymentRiskStatus(String paymentRiskStatus) { this.paymentRiskStatus = paymentRiskStatus; }

    public List<PaymentItemDTO> getTopRecommendedPayments() { return topRecommendedPayments; }
    public void setTopRecommendedPayments(List<PaymentItemDTO> topRecommendedPayments) { this.topRecommendedPayments = topRecommendedPayments; }

    public String getSummaryExplanation() { return summaryExplanation; }
    public void setSummaryExplanation(String summaryExplanation) { this.summaryExplanation = summaryExplanation; }

    public String getCalculationBasis() { return calculationBasis; }
    public void setCalculationBasis(String calculationBasis) { this.calculationBasis = calculationBasis; }

    public List<String> getAssumptions() { return assumptions; }
    public void setAssumptions(List<String> assumptions) { this.assumptions = assumptions; }

    public String getAdvisoryNotice() { return advisoryNotice; }
    public void setAdvisoryNotice(String advisoryNotice) { this.advisoryNotice = advisoryNotice; }
}
