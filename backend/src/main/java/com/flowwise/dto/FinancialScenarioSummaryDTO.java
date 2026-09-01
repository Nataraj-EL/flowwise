package com.flowwise.dto;

import java.math.BigDecimal;
import java.util.List;

public class FinancialScenarioSummaryDTO {
    private Long merchantId;
    private int totalEvaluatedScenariosCount;
    private String activeHorizon;
    private BigDecimal baselineScore;
    private BigDecimal topProjectedScore;
    private String topRankedScenarioName;
    private FinancialScenarioDTO topRankedScenario;
    private List<FinancialScenarioDTO> scenarios;
    private String summaryExplanation;
    private String advisoryNotice;

    public FinancialScenarioSummaryDTO() {}

    public FinancialScenarioSummaryDTO(Long merchantId, int totalEvaluatedScenariosCount, String activeHorizon,
                                       BigDecimal baselineScore, BigDecimal topProjectedScore,
                                       String topRankedScenarioName, FinancialScenarioDTO topRankedScenario,
                                       List<FinancialScenarioDTO> scenarios, String summaryExplanation,
                                       String advisoryNotice) {
        this.merchantId = merchantId;
        this.totalEvaluatedScenariosCount = totalEvaluatedScenariosCount;
        this.activeHorizon = activeHorizon;
        this.baselineScore = baselineScore;
        this.topProjectedScore = topProjectedScore;
        this.topRankedScenarioName = topRankedScenarioName;
        this.topRankedScenario = topRankedScenario;
        this.scenarios = scenarios;
        this.summaryExplanation = summaryExplanation;
        this.advisoryNotice = advisoryNotice;
    }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public int getTotalEvaluatedScenariosCount() { return totalEvaluatedScenariosCount; }
    public void setTotalEvaluatedScenariosCount(int totalEvaluatedScenariosCount) { this.totalEvaluatedScenariosCount = totalEvaluatedScenariosCount; }

    public String getActiveHorizon() { return activeHorizon; }
    public void setActiveHorizon(String activeHorizon) { this.activeHorizon = activeHorizon; }

    public BigDecimal getBaselineScore() { return baselineScore; }
    public void setBaselineScore(BigDecimal baselineScore) { this.baselineScore = baselineScore; }

    public BigDecimal getTopProjectedScore() { return topProjectedScore; }
    public void setTopProjectedScore(BigDecimal topProjectedScore) { this.topProjectedScore = topProjectedScore; }

    public String getTopRankedScenarioName() { return topRankedScenarioName; }
    public void setTopRankedScenarioName(String topRankedScenarioName) { this.topRankedScenarioName = topRankedScenarioName; }

    public FinancialScenarioDTO getTopRankedScenario() { return topRankedScenario; }
    public void setTopRankedScenario(FinancialScenarioDTO topRankedScenario) { this.topRankedScenario = topRankedScenario; }

    public List<FinancialScenarioDTO> getScenarios() { return scenarios; }
    public void setScenarios(List<FinancialScenarioDTO> scenarios) { this.scenarios = scenarios; }

    public String getSummaryExplanation() { return summaryExplanation; }
    public void setSummaryExplanation(String summaryExplanation) { this.summaryExplanation = summaryExplanation; }

    public String getAdvisoryNotice() { return advisoryNotice; }
    public void setAdvisoryNotice(String advisoryNotice) { this.advisoryNotice = advisoryNotice; }
}
