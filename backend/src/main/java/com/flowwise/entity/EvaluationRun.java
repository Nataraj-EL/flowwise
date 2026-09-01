package com.flowwise.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "evaluation_runs")
public class EvaluationRun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "run_timestamp", nullable = false)
    private OffsetDateTime runTimestamp;

    @Column(name = "total_cases", nullable = false)
    private Integer totalCases;

    @Column(name = "overall_score", nullable = false)
    private BigDecimal overallScore;

    @Column(name = "grounding_score", nullable = false)
    private BigDecimal groundingScore;

    @Column(name = "numerical_consistency_score", nullable = false)
    private BigDecimal numericalConsistencyScore;

    @Column(name = "relevance_score", nullable = false)
    private BigDecimal relevanceScore;

    @Column(name = "evidence_coverage_score", nullable = false)
    private BigDecimal evidenceCoverageScore;

    @Column(name = "unsupported_claims_count", nullable = false)
    private Integer unsupportedClaimsCount;

    @Column(name = "fallback_rate", nullable = false)
    private BigDecimal fallbackRate;

    @Column(name = "avg_latency_ms", nullable = false)
    private BigDecimal avgLatencyMs;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @OneToMany(mappedBy = "evaluationRun", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EvaluationCaseResult> caseResults = new ArrayList<>();

    public EvaluationRun() {}

    @PrePersist
    protected void onCreate() {
        if (runTimestamp == null) runTimestamp = OffsetDateTime.now();
        if (createdAt == null) createdAt = OffsetDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public OffsetDateTime getRunTimestamp() { return runTimestamp; }
    public void setRunTimestamp(OffsetDateTime runTimestamp) { this.runTimestamp = runTimestamp; }

    public Integer getTotalCases() { return totalCases; }
    public void setTotalCases(Integer totalCases) { this.totalCases = totalCases; }

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

    public Integer getUnsupportedClaimsCount() { return unsupportedClaimsCount; }
    public void setUnsupportedClaimsCount(Integer unsupportedClaimsCount) { this.unsupportedClaimsCount = unsupportedClaimsCount; }

    public BigDecimal getFallbackRate() { return fallbackRate; }
    public void setFallbackRate(BigDecimal fallbackRate) { this.fallbackRate = fallbackRate; }

    public BigDecimal getAvgLatencyMs() { return avgLatencyMs; }
    public void setAvgLatencyMs(BigDecimal avgLatencyMs) { this.avgLatencyMs = avgLatencyMs; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

    public List<EvaluationCaseResult> getCaseResults() { return caseResults; }
    public void setCaseResults(List<EvaluationCaseResult> caseResults) { this.caseResults = caseResults; }
}
