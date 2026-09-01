package com.flowwise.dto;

import java.time.LocalDate;

public class CreateDecisionRequestDTO {
    private Long actionId;
    private Long goalId;
    private String decisionType;
    private String title;
    private String recommendation;
    private String decisionNotes;
    private LocalDate decisionDate;

    public CreateDecisionRequestDTO() {}

    public CreateDecisionRequestDTO(Long actionId, Long goalId, String decisionType, 
                                    String title, String recommendation, String decisionNotes, 
                                    LocalDate decisionDate) {
        this.actionId = actionId;
        this.goalId = goalId;
        this.decisionType = decisionType;
        this.title = title;
        this.recommendation = recommendation;
        this.decisionNotes = decisionNotes;
        this.decisionDate = decisionDate;
    }

    public Long getActionId() { return actionId; }
    public void setActionId(Long actionId) { this.actionId = actionId; }

    public Long getGoalId() { return goalId; }
    public void setGoalId(Long goalId) { this.goalId = goalId; }

    public String getDecisionType() { return decisionType; }
    public void setDecisionType(String decisionType) { this.decisionType = decisionType; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getRecommendation() { return recommendation; }
    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }

    public String getDecisionNotes() { return decisionNotes; }
    public void setDecisionNotes(String decisionNotes) { this.decisionNotes = decisionNotes; }

    public LocalDate getDecisionDate() { return decisionDate; }
    public void setDecisionDate(LocalDate decisionDate) { this.decisionDate = decisionDate; }
}
