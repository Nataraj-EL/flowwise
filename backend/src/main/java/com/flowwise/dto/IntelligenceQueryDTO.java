package com.flowwise.dto;

public class IntelligenceQueryDTO {
    private String question;

    public IntelligenceQueryDTO() {}

    public IntelligenceQueryDTO(String question) {
        this.question = question;
    }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
}
