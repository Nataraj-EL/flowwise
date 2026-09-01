package com.flowwise.dto;

import java.util.Map;

public class IntelligenceResponseDTO {
    private String question;
    private String answer;
    private Map<String, Object> evidenceSummary;
    private boolean localAiActive;
    private String modelUsed;
    private String disclaimer;

    public IntelligenceResponseDTO() {}

    public IntelligenceResponseDTO(String question, String answer, Map<String, Object> evidenceSummary, 
                                   boolean localAiActive, String modelUsed, String disclaimer) {
        this.question = question;
        this.answer = answer;
        this.evidenceSummary = evidenceSummary;
        this.localAiActive = localAiActive;
        this.modelUsed = modelUsed;
        this.disclaimer = disclaimer;
    }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    public Map<String, Object> getEvidenceSummary() { return evidenceSummary; }
    public void setEvidenceSummary(Map<String, Object> evidenceSummary) { this.evidenceSummary = evidenceSummary; }

    public boolean isLocalAiActive() { return localAiActive; }
    public void setLocalAiActive(boolean localAiActive) { this.localAiActive = localAiActive; }

    public String getModelUsed() { return modelUsed; }
    public void setModelUsed(String modelUsed) { this.modelUsed = modelUsed; }

    public String getDisclaimer() { return disclaimer; }
    public void setDisclaimer(String disclaimer) { this.disclaimer = disclaimer; }
}
