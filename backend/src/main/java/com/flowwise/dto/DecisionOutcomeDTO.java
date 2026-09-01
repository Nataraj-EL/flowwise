package com.flowwise.dto;

public class DecisionOutcomeDTO {
    private String outcomeStatus; // POSITIVE, NEGATIVE, NEUTRAL
    private String outcomeNotes;

    public DecisionOutcomeDTO() {}

    public DecisionOutcomeDTO(String outcomeStatus, String outcomeNotes) {
        this.outcomeStatus = outcomeStatus;
        this.outcomeNotes = outcomeNotes;
    }

    public String getOutcomeStatus() { return outcomeStatus; }
    public void setOutcomeStatus(String outcomeStatus) { this.outcomeStatus = outcomeStatus; }

    public String getOutcomeNotes() { return outcomeNotes; }
    public void setOutcomeNotes(String outcomeNotes) { this.outcomeNotes = outcomeNotes; }
}
