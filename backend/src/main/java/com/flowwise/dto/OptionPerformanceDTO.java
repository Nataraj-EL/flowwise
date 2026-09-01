package com.flowwise.dto;

import java.math.BigDecimal;

public class OptionPerformanceDTO {
    private Long id;
    private String optionKey; // PAY_NOW, DEFER, COLLECT_RECEIVABLES, REDUCE_EXPENSE, BUILD_RESERVE
    private int totalSampleCount;
    private int positiveOutcomeCount;
    private int negativeOutcomeCount;
    private BigDecimal successRatePct;
    private BigDecimal calibrationMultiplier; // Bounded 0.80 - 1.20
    private BigDecimal avgCashImpactVariance;
    private String accuracyStatus; // ACCURATE, OVERESTIMATED, UNDERESTIMATED, UNCALIBRATED

    public OptionPerformanceDTO() {}

    public OptionPerformanceDTO(Long id, String optionKey, int totalSampleCount, 
                                int positiveOutcomeCount, int negativeOutcomeCount, 
                                BigDecimal successRatePct, BigDecimal calibrationMultiplier, 
                                BigDecimal avgCashImpactVariance, String accuracyStatus) {
        this.id = id;
        this.optionKey = optionKey;
        this.totalSampleCount = totalSampleCount;
        this.positiveOutcomeCount = positiveOutcomeCount;
        this.negativeOutcomeCount = negativeOutcomeCount;
        this.successRatePct = successRatePct;
        this.calibrationMultiplier = calibrationMultiplier;
        this.avgCashImpactVariance = avgCashImpactVariance;
        this.accuracyStatus = accuracyStatus;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getOptionKey() { return optionKey; }
    public void setOptionKey(String optionKey) { this.optionKey = optionKey; }

    public int getTotalSampleCount() { return totalSampleCount; }
    public void setTotalSampleCount(int totalSampleCount) { this.totalSampleCount = totalSampleCount; }

    public int getPositiveOutcomeCount() { return positiveOutcomeCount; }
    public void setPositiveOutcomeCount(int positiveOutcomeCount) { this.positiveOutcomeCount = positiveOutcomeCount; }

    public int getNegativeOutcomeCount() { return negativeOutcomeCount; }
    public void setNegativeOutcomeCount(int negativeOutcomeCount) { this.negativeOutcomeCount = negativeOutcomeCount; }

    public BigDecimal getSuccessRatePct() { return successRatePct; }
    public void setSuccessRatePct(BigDecimal successRatePct) { this.successRatePct = successRatePct; }

    public BigDecimal getCalibrationMultiplier() { return calibrationMultiplier; }
    public void setCalibrationMultiplier(BigDecimal calibrationMultiplier) { this.calibrationMultiplier = calibrationMultiplier; }

    public BigDecimal getAvgCashImpactVariance() { return avgCashImpactVariance; }
    public void setAvgCashImpactVariance(BigDecimal avgCashImpactVariance) { this.avgCashImpactVariance = avgCashImpactVariance; }

    public String getAccuracyStatus() { return accuracyStatus; }
    public void setAccuracyStatus(String accuracyStatus) { this.accuracyStatus = accuracyStatus; }
}
