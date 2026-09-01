package com.flowwise.dto;

import java.math.BigDecimal;
import java.util.List;

public class FinancialExecutionScheduleDTO {
    private Long id;
    private Long merchantId;
    private String scheduleKey;
    private String horizon;
    private String status;
    private BigDecimal overallScheduleScore;
    private BigDecimal capacityScore;
    private BigDecimal riskScore;
    private BigDecimal impactScore;
    private BigDecimal urgencyScore;
    private Integer totalActions;
    private Integer scheduledActions;
    private Integer deferredActions;
    private String primaryFocus;
    private String expectedBenefit;
    private String riskIfDeferred;
    private String evidenceMetrics;
    private String assumptions;
    private List<FinancialExecutionScheduleItemDTO> items;
    private String evaluatedAt;

    public FinancialExecutionScheduleDTO() {}

    public FinancialExecutionScheduleDTO(Long id, Long merchantId, String scheduleKey, String horizon, String status,
                                        BigDecimal overallScheduleScore, BigDecimal capacityScore, BigDecimal riskScore,
                                        BigDecimal impactScore, BigDecimal urgencyScore, Integer totalActions,
                                        Integer scheduledActions, Integer deferredActions, String primaryFocus,
                                        String expectedBenefit, String riskIfDeferred, String evidenceMetrics,
                                        String assumptions, List<FinancialExecutionScheduleItemDTO> items,
                                        String evaluatedAt) {
        this.id = id;
        this.merchantId = merchantId;
        this.scheduleKey = scheduleKey;
        this.horizon = horizon;
        this.status = status;
        this.overallScheduleScore = overallScheduleScore;
        this.capacityScore = capacityScore;
        this.riskScore = riskScore;
        this.impactScore = impactScore;
        this.urgencyScore = urgencyScore;
        this.totalActions = totalActions;
        this.scheduledActions = scheduledActions;
        this.deferredActions = deferredActions;
        this.primaryFocus = primaryFocus;
        this.expectedBenefit = expectedBenefit;
        this.riskIfDeferred = riskIfDeferred;
        this.evidenceMetrics = evidenceMetrics;
        this.assumptions = assumptions;
        this.items = items;
        this.evaluatedAt = evaluatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public String getScheduleKey() { return scheduleKey; }
    public void setScheduleKey(String scheduleKey) { this.scheduleKey = scheduleKey; }

    public String getHorizon() { return horizon; }
    public void setHorizon(String horizon) { this.horizon = horizon; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getOverallScheduleScore() { return overallScheduleScore; }
    public void setOverallScheduleScore(BigDecimal overallScheduleScore) { this.overallScheduleScore = overallScheduleScore; }

    public BigDecimal getCapacityScore() { return capacityScore; }
    public void setCapacityScore(BigDecimal capacityScore) { this.capacityScore = capacityScore; }

    public BigDecimal getRiskScore() { return riskScore; }
    public void setRiskScore(BigDecimal riskScore) { this.riskScore = riskScore; }

    public BigDecimal getImpactScore() { return impactScore; }
    public void setImpactScore(BigDecimal impactScore) { this.impactScore = impactScore; }

    public BigDecimal getUrgencyScore() { return urgencyScore; }
    public void setUrgencyScore(BigDecimal urgencyScore) { this.urgencyScore = urgencyScore; }

    public Integer getTotalActions() { return totalActions; }
    public void setTotalActions(Integer totalActions) { this.totalActions = totalActions; }

    public Integer getScheduledActions() { return scheduledActions; }
    public void setScheduledActions(Integer scheduledActions) { this.scheduledActions = scheduledActions; }

    public Integer getDeferredActions() { return deferredActions; }
    public void setDeferredActions(Integer deferredActions) { this.deferredActions = deferredActions; }

    public String getPrimaryFocus() { return primaryFocus; }
    public void setPrimaryFocus(String primaryFocus) { this.primaryFocus = primaryFocus; }

    public String getExpectedBenefit() { return expectedBenefit; }
    public void setExpectedBenefit(String expectedBenefit) { this.expectedBenefit = expectedBenefit; }

    public String getRiskIfDeferred() { return riskIfDeferred; }
    public void setRiskIfDeferred(String riskIfDeferred) { this.riskIfDeferred = riskIfDeferred; }

    public String getEvidenceMetrics() { return evidenceMetrics; }
    public void setEvidenceMetrics(String evidenceMetrics) { this.evidenceMetrics = evidenceMetrics; }

    public String getAssumptions() { return assumptions; }
    public void setAssumptions(String assumptions) { this.assumptions = assumptions; }

    public List<FinancialExecutionScheduleItemDTO> getItems() { return items; }
    public void setItems(List<FinancialExecutionScheduleItemDTO> items) { this.items = items; }

    public String getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(String evaluatedAt) { this.evaluatedAt = evaluatedAt; }
}
