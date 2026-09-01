package com.flowwise.dto;

import java.math.BigDecimal;
import java.util.List;

public class FinancialPlanDTO {
    private Long id;
    private Long merchantId;
    private String planKey;
    private String horizon;
    private String status;
    private BigDecimal overallPlanScore;
    private String primaryFocusArea;
    private String summaryExplanation;
    private String assumptions;
    private List<FinancialPlanItemDTO> items;
    private String evaluatedAt;

    public FinancialPlanDTO() {}

    public FinancialPlanDTO(Long id, Long merchantId, String planKey, String horizon, String status,
                            BigDecimal overallPlanScore, String primaryFocusArea, String summaryExplanation,
                            String assumptions, List<FinancialPlanItemDTO> items, String evaluatedAt) {
        this.id = id;
        this.merchantId = merchantId;
        this.planKey = planKey;
        this.horizon = horizon;
        this.status = status;
        this.overallPlanScore = overallPlanScore;
        this.primaryFocusArea = primaryFocusArea;
        this.summaryExplanation = summaryExplanation;
        this.assumptions = assumptions;
        this.items = items;
        this.evaluatedAt = evaluatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public String getPlanKey() { return planKey; }
    public void setPlanKey(String planKey) { this.planKey = planKey; }

    public String getHorizon() { return horizon; }
    public void setHorizon(String horizon) { this.horizon = horizon; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getOverallPlanScore() { return overallPlanScore; }
    public void setOverallPlanScore(BigDecimal overallPlanScore) { this.overallPlanScore = overallPlanScore; }

    public String getPrimaryFocusArea() { return primaryFocusArea; }
    public void setPrimaryFocusArea(String primaryFocusArea) { this.primaryFocusArea = primaryFocusArea; }

    public String getSummaryExplanation() { return summaryExplanation; }
    public void setSummaryExplanation(String summaryExplanation) { this.summaryExplanation = summaryExplanation; }

    public String getAssumptions() { return assumptions; }
    public void setAssumptions(String assumptions) { this.assumptions = assumptions; }

    public List<FinancialPlanItemDTO> getItems() { return items; }
    public void setItems(List<FinancialPlanItemDTO> items) { this.items = items; }

    public String getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(String evaluatedAt) { this.evaluatedAt = evaluatedAt; }
}
