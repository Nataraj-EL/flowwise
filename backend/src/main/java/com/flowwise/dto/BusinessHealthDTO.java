package com.flowwise.dto;

import java.util.List;

public class BusinessHealthDTO {
    private int overallScore; // 0 - 100
    private String healthStatus; // HEALTHY, WATCH, AT_RISK
    private List<HealthFactorDTO> factorScores;
    private List<String> positiveSignals;
    private List<String> riskSignals;
    private String summaryExplanation;

    public BusinessHealthDTO() {}

    public BusinessHealthDTO(int overallScore, String healthStatus, List<HealthFactorDTO> factorScores, 
                             List<String> positiveSignals, List<String> riskSignals, String summaryExplanation) {
        this.overallScore = overallScore;
        this.healthStatus = healthStatus;
        this.factorScores = factorScores;
        this.positiveSignals = positiveSignals;
        this.riskSignals = riskSignals;
        this.summaryExplanation = summaryExplanation;
    }

    public int getOverallScore() { return overallScore; }
    public void setOverallScore(int overallScore) { this.overallScore = overallScore; }

    public String getHealthStatus() { return healthStatus; }
    public void setHealthStatus(String healthStatus) { this.healthStatus = healthStatus; }

    public List<HealthFactorDTO> getFactorScores() { return factorScores; }
    public void setFactorScores(List<HealthFactorDTO> factorScores) { this.factorScores = factorScores; }

    public List<String> getPositiveSignals() { return positiveSignals; }
    public void setPositiveSignals(List<String> positiveSignals) { this.positiveSignals = positiveSignals; }

    public List<String> getRiskSignals() { return riskSignals; }
    public void setRiskSignals(List<String> riskSignals) { this.riskSignals = riskSignals; }

    public String getSummaryExplanation() { return summaryExplanation; }
    public void setSummaryExplanation(String summaryExplanation) { this.summaryExplanation = summaryExplanation; }
}
