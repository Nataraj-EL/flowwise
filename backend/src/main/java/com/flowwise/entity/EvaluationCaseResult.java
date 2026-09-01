package com.flowwise.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "evaluation_case_results")
public class EvaluationCaseResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "run_id", nullable = false)
    @JsonIgnore
    private EvaluationRun evaluationRun;

    @Column(name = "case_id", nullable = false)
    private String caseId;

    @Column(name = "question", nullable = false, columnDefinition = "TEXT")
    private String question;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "ground_truth_expected", columnDefinition = "TEXT")
    private String groundTruthExpected;

    @Column(name = "response_text", columnDefinition = "TEXT")
    private String responseText;

    @Column(name = "grounded", nullable = false)
    private Boolean grounded;

    @Column(name = "numerical_consistent", nullable = false)
    private Boolean numericalConsistent;

    @Column(name = "relevant", nullable = false)
    private Boolean relevant;

    @Column(name = "evidence_covered", nullable = false)
    private Boolean evidenceCovered;

    @Column(name = "fallback_used", nullable = false)
    private Boolean fallbackUsed;

    @Column(name = "latency_ms", nullable = false)
    private Long latencyMs;

    @Column(name = "score", nullable = false)
    private BigDecimal score;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    public EvaluationCaseResult() {}

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) createdAt = OffsetDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public EvaluationRun getEvaluationRun() { return evaluationRun; }
    public void setEvaluationRun(EvaluationRun evaluationRun) { this.evaluationRun = evaluationRun; }

    public String getCaseId() { return caseId; }
    public void setCaseId(String caseId) { this.caseId = caseId; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getGroundTruthExpected() { return groundTruthExpected; }
    public void setGroundTruthExpected(String groundTruthExpected) { this.groundTruthExpected = groundTruthExpected; }

    public String getResponseText() { return responseText; }
    public void setResponseText(String responseText) { this.responseText = responseText; }

    public Boolean getGrounded() { return grounded; }
    public void setGrounded(Boolean grounded) { this.grounded = grounded; }

    public Boolean getNumericalConsistent() { return numericalConsistent; }
    public void setNumericalConsistent(Boolean numericalConsistent) { this.numericalConsistent = numericalConsistent; }

    public Boolean getRelevant() { return relevant; }
    public void setRelevant(Boolean relevant) { this.relevant = relevant; }

    public Boolean getEvidenceCovered() { return evidenceCovered; }
    public void setEvidenceCovered(Boolean evidenceCovered) { this.evidenceCovered = evidenceCovered; }

    public Boolean getFallbackUsed() { return fallbackUsed; }
    public void setFallbackUsed(Boolean fallbackUsed) { this.fallbackUsed = fallbackUsed; }

    public Long getLatencyMs() { return latencyMs; }
    public void setLatencyMs(Long latencyMs) { this.latencyMs = latencyMs; }

    public BigDecimal getScore() { return score; }
    public void setScore(BigDecimal score) { this.score = score; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
