package com.flowwise.dto;

import java.math.BigDecimal;
import java.util.List;

public class EvaluationSummaryDTO {
    private Long runId;
    private String runTimestamp;
    private String benchmarkVersion;
    private int totalCases;
    private BigDecimal overallScore;
    private BigDecimal groundingScore;
    private BigDecimal numericalConsistencyScore;
    private BigDecimal relevanceScore;
    private BigDecimal evidenceCoverageScore;
    private int unsupportedClaimsCount;
    private BigDecimal fallbackRate;
    private BigDecimal avgLatencyMs;
    private List<EvaluationCaseResultDTO> caseResults;

    public EvaluationSummaryDTO() {}

    public EvaluationSummaryDTO(Long runId, String runTimestamp, String benchmarkVersion, int totalCases, 
                                BigDecimal overallScore, BigDecimal groundingScore, BigDecimal numericalConsistencyScore, 
                                BigDecimal relevanceScore, BigDecimal evidenceCoverageScore, int unsupportedClaimsCount, 
                                BigDecimal fallbackRate, BigDecimal avgLatencyMs, List<EvaluationCaseResultDTO> caseResults) {
        this.runId = runId;
        this.runTimestamp = runTimestamp;
        this.benchmarkVersion = benchmarkVersion;
        this.totalCases = totalCases;
        this.overallScore = overallScore;
        this.groundingScore = groundingScore;
        this.numericalConsistencyScore = numericalConsistencyScore;
        this.relevanceScore = relevanceScore;
        this.evidenceCoverageScore = evidenceCoverageScore;
        this.unsupportedClaimsCount = unsupportedClaimsCount;
        this.fallbackRate = fallbackRate;
        this.avgLatencyMs = avgLatencyMs;
        this.caseResults = caseResults;
    }

    public Long getRunId() { return runId; }
    public void setRunId(Long runId) { this.runId = runId; }

    public String getRunTimestamp() { return runTimestamp; }
    public void setRunTimestamp(String runTimestamp) { this.runTimestamp = runTimestamp; }

    public String getBenchmarkVersion() { return benchmarkVersion; }
    public void setBenchmarkVersion(String benchmarkVersion) { this.benchmarkVersion = benchmarkVersion; }

    public int getTotalCases() { return totalCases; }
    public void setTotalCases(int totalCases) { this.totalCases = totalCases; }

    public BigDecimal getOverallScore() { return overallScore; }
    public void setOverallScore(BigDecimal overallScore) { this.overallScore = overallScore; }

    public BigDecimal getGroundingScore() { return groundingScore; }
    public void setGroundingScore(BigDecimal groundingScore) { this.groundingScore = groundingScore; }

    public BigDecimal getNumericalConsistencyScore() { return numericalConsistencyScore; }
    public void setNumericalConsistencyScore(BigDecimal numericalConsistencyScore) { this.numericalConsistencyScore = numericalConsistencyScore; }

    public BigDecimal getRelevanceScore() { return relevanceScore; }
    public void setRelevanceScore(BigDecimal relevanceScore) { this.relevanceScore = relevanceScore; }

    public BigDecimal getEvidenceCoverageScore() { return evidenceCoverageScore; }
    public void setEvidenceCoverageScore(BigDecimal evidenceCoverageScore) { this.evidenceCoverageScore = evidenceCoverageScore; }

    public int getUnsupportedClaimsCount() { return unsupportedClaimsCount; }
    public void setUnsupportedClaimsCount(int unsupportedClaimsCount) { this.unsupportedClaimsCount = unsupportedClaimsCount; }

    public BigDecimal getFallbackRate() { return fallbackRate; }
    public void setFallbackRate(BigDecimal fallbackRate) { this.fallbackRate = fallbackRate; }

    public BigDecimal getAvgLatencyMs() { return avgLatencyMs; }
    public void setAvgLatencyMs(BigDecimal avgLatencyMs) { this.avgLatencyMs = avgLatencyMs; }

    public List<EvaluationCaseResultDTO> getCaseResults() { return caseResults; }
    public void setCaseResults(List<EvaluationCaseResultDTO> caseResults) { this.caseResults = caseResults; }
}
