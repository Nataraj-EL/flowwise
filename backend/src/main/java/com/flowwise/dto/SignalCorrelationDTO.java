package com.flowwise.dto;

import java.math.BigDecimal;

public class SignalCorrelationDTO {
    private Long id;
    private Long merchantId;
    private String correlationKey;
    private String primaryTarget;
    private String likelyRootCause;
    private BigDecimal correlationScore;
    private String confidenceStatus;
    private int contributingSignalsCount;
    private String matchedSignalsJson;
    private String rankingFormula;
    private String detectionWindow;
    private String evidenceMetrics;
    private String evaluatedAt;

    public SignalCorrelationDTO() {}

    public SignalCorrelationDTO(Long id, Long merchantId, String correlationKey, String primaryTarget,
                                String likelyRootCause, BigDecimal correlationScore, String confidenceStatus,
                                int contributingSignalsCount, String matchedSignalsJson, String rankingFormula,
                                String detectionWindow, String evidenceMetrics, String evaluatedAt) {
        this.id = id;
        this.merchantId = merchantId;
        this.correlationKey = correlationKey;
        this.primaryTarget = primaryTarget;
        this.likelyRootCause = likelyRootCause;
        this.correlationScore = correlationScore;
        this.confidenceStatus = confidenceStatus;
        this.contributingSignalsCount = contributingSignalsCount;
        this.matchedSignalsJson = matchedSignalsJson;
        this.rankingFormula = rankingFormula;
        this.detectionWindow = detectionWindow;
        this.evidenceMetrics = evidenceMetrics;
        this.evaluatedAt = evaluatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

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

    public int getContributingSignalsCount() { return contributingSignalsCount; }
    public void setContributingSignalsCount(int contributingSignalsCount) { this.contributingSignalsCount = contributingSignalsCount; }

    public String getMatchedSignalsJson() { return matchedSignalsJson; }
    public void setMatchedSignalsJson(String matchedSignalsJson) { this.matchedSignalsJson = matchedSignalsJson; }

    public String getRankingFormula() { return rankingFormula; }
    public void setRankingFormula(String rankingFormula) { this.rankingFormula = rankingFormula; }

    public String getDetectionWindow() { return detectionWindow; }
    public void setDetectionWindow(String detectionWindow) { this.detectionWindow = detectionWindow; }

    public String getEvidenceMetrics() { return evidenceMetrics; }
    public void setEvidenceMetrics(String evidenceMetrics) { this.evidenceMetrics = evidenceMetrics; }

    public String getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(String evaluatedAt) { this.evaluatedAt = evaluatedAt; }
}
