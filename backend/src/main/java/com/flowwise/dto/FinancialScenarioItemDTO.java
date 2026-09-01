package com.flowwise.dto;

import java.math.BigDecimal;

public class FinancialScenarioItemDTO {
    private Long id;
    private Long scenarioId;
    private String interventionType;
    private Long interventionId;
    private Integer rankOrder;
    private BigDecimal projectedImpact;
    private BigDecimal projectedRiskReduction;
    private BigDecimal projectedGoalImpact;
    private String evidenceMetrics;

    public FinancialScenarioItemDTO() {}

    public FinancialScenarioItemDTO(Long id, Long scenarioId, String interventionType, Long interventionId,
                                    Integer rankOrder, BigDecimal projectedImpact, BigDecimal projectedRiskReduction,
                                    BigDecimal projectedGoalImpact, String evidenceMetrics) {
        this.id = id;
        this.scenarioId = scenarioId;
        this.interventionType = interventionType;
        this.interventionId = interventionId;
        this.rankOrder = rankOrder;
        this.projectedImpact = projectedImpact;
        this.projectedRiskReduction = projectedRiskReduction;
        this.projectedGoalImpact = projectedGoalImpact;
        this.evidenceMetrics = evidenceMetrics;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getScenarioId() { return scenarioId; }
    public void setScenarioId(Long scenarioId) { this.scenarioId = scenarioId; }

    public String getInterventionType() { return interventionType; }
    public void setInterventionType(String interventionType) { this.interventionType = interventionType; }

    public Long getInterventionId() { return interventionId; }
    public void setInterventionId(Long interventionId) { this.interventionId = interventionId; }

    public Integer getRankOrder() { return rankOrder; }
    public void setRankOrder(Integer rankOrder) { this.rankOrder = rankOrder; }

    public BigDecimal getProjectedImpact() { return projectedImpact; }
    public void setProjectedImpact(BigDecimal projectedImpact) { this.projectedImpact = projectedImpact; }

    public BigDecimal getProjectedRiskReduction() { return projectedRiskReduction; }
    public void setProjectedRiskReduction(BigDecimal projectedRiskReduction) { this.projectedRiskReduction = projectedRiskReduction; }

    public BigDecimal getProjectedGoalImpact() { return projectedGoalImpact; }
    public void setProjectedGoalImpact(BigDecimal projectedGoalImpact) { this.projectedGoalImpact = projectedGoalImpact; }

    public String getEvidenceMetrics() { return evidenceMetrics; }
    public void setEvidenceMetrics(String evidenceMetrics) { this.evidenceMetrics = evidenceMetrics; }
}
