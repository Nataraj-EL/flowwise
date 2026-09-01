package com.flowwise.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "ai_request_logs")
public class AiRequestLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp", nullable = false)
    private OffsetDateTime timestamp;

    @Column(name = "merchant_id", nullable = false)
    private Long merchantId;

    @Column(name = "query", nullable = false, columnDefinition = "TEXT")
    private String query;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "latency_ms", nullable = false)
    private Long latencyMs;

    @Column(name = "ollama_available", nullable = false)
    private Boolean ollamaAvailable;

    @Column(name = "fallback_used", nullable = false)
    private Boolean fallbackUsed;

    @Column(name = "evidence_count", nullable = false)
    private Integer evidenceCount;

    @Column(name = "evaluation_status", nullable = false)
    private String evaluationStatus;

    public AiRequestLog() {}

    @PrePersist
    protected void onCreate() {
        if (timestamp == null) timestamp = OffsetDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public OffsetDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(OffsetDateTime timestamp) { this.timestamp = timestamp; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public Long getLatencyMs() { return latencyMs; }
    public void setLatencyMs(Long latencyMs) { this.latencyMs = latencyMs; }

    public Boolean getOllamaAvailable() { return ollamaAvailable; }
    public void setOllamaAvailable(Boolean ollamaAvailable) { this.ollamaAvailable = ollamaAvailable; }

    public Boolean getFallbackUsed() { return fallbackUsed; }
    public void setFallbackUsed(Boolean fallbackUsed) { this.fallbackUsed = fallbackUsed; }

    public Integer getEvidenceCount() { return evidenceCount; }
    public void setEvidenceCount(Integer evidenceCount) { this.evidenceCount = evidenceCount; }

    public String getEvaluationStatus() { return evaluationStatus; }
    public void setEvaluationStatus(String evaluationStatus) { this.evaluationStatus = evaluationStatus; }
}
