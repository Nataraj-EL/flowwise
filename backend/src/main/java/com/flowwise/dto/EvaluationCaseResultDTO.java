package com.flowwise.dto;

import java.math.BigDecimal;

public class EvaluationCaseResultDTO {
    private String caseId;
    private String question;
    private String category;
    private String responseText;
    private boolean grounded;
    private boolean numericalConsistent;
    private boolean relevant;
    private boolean evidenceCovered;
    private boolean fallbackUsed;
    private long latencyMs;
    private BigDecimal score;

    public EvaluationCaseResultDTO() {}

    public EvaluationCaseResultDTO(String caseId, String question, String category, String responseText, 
                                  boolean grounded, boolean numericalConsistent, boolean relevant, 
                                  boolean evidenceCovered, boolean fallbackUsed, long latencyMs, BigDecimal score) {
        this.caseId = caseId;
        this.question = question;
        this.category = category;
        this.responseText = responseText;
        this.grounded = grounded;
        this.numericalConsistent = numericalConsistent;
        this.relevant = relevant;
        this.evidenceCovered = evidenceCovered;
        this.fallbackUsed = fallbackUsed;
        this.latencyMs = latencyMs;
        this.score = score;
    }

    public String getCaseId() { return caseId; }
    public void setCaseId(String caseId) { this.caseId = caseId; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getResponseText() { return responseText; }
    public void setResponseText(String responseText) { this.responseText = responseText; }

    public boolean isGrounded() { return grounded; }
    public void setGrounded(boolean grounded) { this.grounded = grounded; }

    public boolean isNumericalConsistent() { return numericalConsistent; }
    public void setNumericalConsistent(boolean numericalConsistent) { this.numericalConsistent = numericalConsistent; }

    public boolean isRelevant() { return relevant; }
    public void setRelevant(boolean relevant) { this.relevant = relevant; }

    public boolean isEvidenceCovered() { return evidenceCovered; }
    public void setEvidenceCovered(boolean evidenceCovered) { this.evidenceCovered = evidenceCovered; }

    public boolean isFallbackUsed() { return fallbackUsed; }
    public void setFallbackUsed(boolean fallbackUsed) { this.fallbackUsed = fallbackUsed; }

    public long getLatencyMs() { return latencyMs; }
    public void setLatencyMs(long latencyMs) { this.latencyMs = latencyMs; }

    public BigDecimal getScore() { return score; }
    public void setScore(BigDecimal score) { this.score = score; }
}
