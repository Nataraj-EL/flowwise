package com.flowwise.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "signal_correlations")
public class SignalCorrelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(name = "correlation_key", nullable = false, length = 64)
    private String correlationKey;

    @Column(name = "primary_target", nullable = false, length = 128)
    private String primaryTarget;

    @Column(name = "likely_root_cause", nullable = false, length = 128)
    private String likelyRootCause;

    @Column(name = "correlation_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal correlationScore = BigDecimal.ZERO;

    @Column(name = "confidence_status", nullable = false, length = 32)
    private String confidenceStatus = "HIGH"; // HIGH, MODERATE, LIMITED, INSUFFICIENT_DATA

    @Column(name = "contributing_signals_count", nullable = false)
    private Integer contributingSignalsCount = 1;

    @Column(name = "matched_signals_json", nullable = false, columnDefinition = "TEXT")
    private String matchedSignalsJson;

    @Column(name = "ranking_formula", nullable = false, length = 255)
    private String rankingFormula = "Weighted Contribution Score (0-100)";

    @Column(name = "detection_window", nullable = false, length = 64)
    private String detectionWindow = "30-Day Window";

    @Column(name = "evidence_metrics", nullable = false, columnDefinition = "TEXT")
    private String evidenceMetrics;

    @Column(name = "evaluated_at", nullable = false)
    private Instant evaluatedAt = Instant.now();

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    public SignalCorrelation() {}

    public SignalCorrelation(Merchant merchant, String correlationKey, String primaryTarget,
                             String likelyRootCause, BigDecimal correlationScore, String confidenceStatus,
                             Integer contributingSignalsCount, String matchedSignalsJson, String rankingFormula,
                             String detectionWindow, String evidenceMetrics) {
        this.merchant = merchant;
        this.correlationKey = correlationKey;
        this.primaryTarget = primaryTarget;
        this.likelyRootCause = likelyRootCause;
        this.correlationScore = correlationScore != null ? correlationScore : BigDecimal.ZERO;
        this.confidenceStatus = confidenceStatus != null ? confidenceStatus : "HIGH";
        this.contributingSignalsCount = contributingSignalsCount != null ? contributingSignalsCount : 1;
        this.matchedSignalsJson = matchedSignalsJson;
        this.rankingFormula = rankingFormula != null ? rankingFormula : "Weighted Contribution Score (0-100)";
        this.detectionWindow = detectionWindow != null ? detectionWindow : "30-Day Window";
        this.evidenceMetrics = evidenceMetrics;
        this.evaluatedAt = Instant.now();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Merchant getMerchant() { return merchant; }
    public void setMerchant(Merchant merchant) { this.merchant = merchant; }

    public String getCorrelationKey() { return correlationKey; }
    public void setCorrelationKey(String correlationKey) { this.correlationKey = correlationKey; }

    public String getPrimaryTarget() { return primaryTarget; }
    public void setPrimaryTarget(String primaryTarget) { this.primaryTarget = primaryTarget; }

    public String getLikelyRootCause() { return likelyRootCause; }
    public void setLikelyRootCause(String likelyRootCause) { this.likelyRootCause = likelyRootCause; }

    public BigDecimal getCorrelationScore() { return correlationScore; }
    public void setCorrelationScore(BigDecimal correlationScore) { this.correlationScore = correlationScore; }

    public String getConfidenceStatus() { return confidenceStatus; }
    public void setConfidenceStatus(String confidenceStatus) { this.confidenceStatus = confidenceStatus; }

    public Integer getContributingSignalsCount() { return contributingSignalsCount; }
    public void setContributingSignalsCount(Integer contributingSignalsCount) { this.contributingSignalsCount = contributingSignalsCount; }

    public String getMatchedSignalsJson() { return matchedSignalsJson; }
    public void setMatchedSignalsJson(String matchedSignalsJson) { this.matchedSignalsJson = matchedSignalsJson; }

    public String getRankingFormula() { return rankingFormula; }
    public void setRankingFormula(String rankingFormula) { this.rankingFormula = rankingFormula; }

    public String getDetectionWindow() { return detectionWindow; }
    public void setDetectionWindow(String detectionWindow) { this.detectionWindow = detectionWindow; }

    public String getEvidenceMetrics() { return evidenceMetrics; }
    public void setEvidenceMetrics(String evidenceMetrics) { this.evidenceMetrics = evidenceMetrics; }

    public Instant getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(Instant evaluatedAt) { this.evaluatedAt = evaluatedAt; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
