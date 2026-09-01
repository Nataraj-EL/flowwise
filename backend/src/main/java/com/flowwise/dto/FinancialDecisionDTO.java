package com.flowwise.dto;

public class FinancialDecisionDTO {
    private Long id;
    private Long merchantId;
    private Long actionId;
    private String actionTitle;
    private Long goalId;
    private String goalName;
    private String decisionType;
    private String title;
    private String recommendation;
    private String decisionStatus; // PENDING, ACCEPTED, DECLINED, COMPLETED
    private String decisionNotes;
    private String decisionDate;
    private String outcomeStatus; // UNKNOWN, POSITIVE, NEGATIVE, NEUTRAL
    private String outcomeNotes;
    private String createdAt;
    private String updatedAt;

    public FinancialDecisionDTO() {}

    public FinancialDecisionDTO(Long id, Long merchantId, Long actionId, String actionTitle, 
                                Long goalId, String goalName, String decisionType, String title, 
                                String recommendation, String decisionStatus, String decisionNotes, 
                                String decisionDate, String outcomeStatus, String outcomeNotes, 
                                String createdAt, String updatedAt) {
        this.id = id;
        this.merchantId = merchantId;
        this.actionId = actionId;
        this.actionTitle = actionTitle;
        this.goalId = goalId;
        this.goalName = goalName;
        this.decisionType = decisionType;
        this.title = title;
        this.recommendation = recommendation;
        this.decisionStatus = decisionStatus;
        this.decisionNotes = decisionNotes;
        this.decisionDate = decisionDate;
        this.outcomeStatus = outcomeStatus;
        this.outcomeNotes = outcomeNotes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public Long getActionId() { return actionId; }
    public void setActionId(Long actionId) { this.actionId = actionId; }

    public String getActionTitle() { return actionTitle; }
    public void setActionTitle(String actionTitle) { this.actionTitle = actionTitle; }

    public Long getGoalId() { return goalId; }
    public void setGoalId(Long goalId) { this.goalId = goalId; }

    public String getGoalName() { return goalName; }
    public void setGoalName(String goalName) { this.goalName = goalName; }

    public String getDecisionType() { return decisionType; }
    public void setDecisionType(String decisionType) { this.decisionType = decisionType; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getRecommendation() { return recommendation; }
    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }

    public String getDecisionStatus() { return decisionStatus; }
    public void setDecisionStatus(String decisionStatus) { this.decisionStatus = decisionStatus; }

    public String getDecisionNotes() { return decisionNotes; }
    public void setDecisionNotes(String decisionNotes) { this.decisionNotes = decisionNotes; }

    public String getDecisionDate() { return decisionDate; }
    public void setDecisionDate(String decisionDate) { this.decisionDate = decisionDate; }

    public String getOutcomeStatus() { return outcomeStatus; }
    public void setOutcomeStatus(String outcomeStatus) { this.outcomeStatus = outcomeStatus; }

    public String getOutcomeNotes() { return outcomeNotes; }
    public void setOutcomeNotes(String outcomeNotes) { this.outcomeNotes = outcomeNotes; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
