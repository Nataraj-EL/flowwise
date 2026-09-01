package com.flowwise.dto;

import java.math.BigDecimal;
import java.util.List;

public class PaymentPlanDTO {
    private BigDecimal safePaymentCapacity;
    private BigDecimal totalObligations;
    private BigDecimal recommendedPaymentTotal;
    private BigDecimal deferredPaymentTotal;
    private List<PaymentItemDTO> prioritizedPayments;
    private List<PaymentItemDTO> deferredPayments;
    private String executionAdvice;

    public PaymentPlanDTO() {}

    public PaymentPlanDTO(BigDecimal safePaymentCapacity, BigDecimal totalObligations, 
                          BigDecimal recommendedPaymentTotal, BigDecimal deferredPaymentTotal, 
                          List<PaymentItemDTO> prioritizedPayments, List<PaymentItemDTO> deferredPayments, 
                          String executionAdvice) {
        this.safePaymentCapacity = safePaymentCapacity;
        this.totalObligations = totalObligations;
        this.recommendedPaymentTotal = recommendedPaymentTotal;
        this.deferredPaymentTotal = deferredPaymentTotal;
        this.prioritizedPayments = prioritizedPayments;
        this.deferredPayments = deferredPayments;
        this.executionAdvice = executionAdvice;
    }

    public BigDecimal getSafePaymentCapacity() { return safePaymentCapacity; }
    public void setSafePaymentCapacity(BigDecimal safePaymentCapacity) { this.safePaymentCapacity = safePaymentCapacity; }

    public BigDecimal getTotalObligations() { return totalObligations; }
    public void setTotalObligations(BigDecimal totalObligations) { this.totalObligations = totalObligations; }

    public BigDecimal getRecommendedPaymentTotal() { return recommendedPaymentTotal; }
    public void setRecommendedPaymentTotal(BigDecimal recommendedPaymentTotal) { this.recommendedPaymentTotal = recommendedPaymentTotal; }

    public BigDecimal getDeferredPaymentTotal() { return deferredPaymentTotal; }
    public void setDeferredPaymentTotal(BigDecimal deferredPaymentTotal) { this.deferredPaymentTotal = deferredPaymentTotal; }

    public List<PaymentItemDTO> getPrioritizedPayments() { return prioritizedPayments; }
    public void setPrioritizedPayments(List<PaymentItemDTO> prioritizedPayments) { this.prioritizedPayments = prioritizedPayments; }

    public List<PaymentItemDTO> getDeferredPayments() { return deferredPayments; }
    public void setDeferredPayments(List<PaymentItemDTO> deferredPayments) { this.deferredPayments = deferredPayments; }

    public String getExecutionAdvice() { return executionAdvice; }
    public void setExecutionAdvice(String executionAdvice) { this.executionAdvice = executionAdvice; }
}
