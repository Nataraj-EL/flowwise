package com.flowwise.dto;

public class HealthFactorDTO {
    private String factorName;
    private int score;
    private int maxScore;
    private String trend; // IMPROVING, STABLE, DETERIORATING
    private String explanation;

    public HealthFactorDTO() {}

    public HealthFactorDTO(String factorName, int score, int maxScore, String trend, String explanation) {
        this.factorName = factorName;
        this.score = score;
        this.maxScore = maxScore;
        this.trend = trend;
        this.explanation = explanation;
    }

    public String getFactorName() { return factorName; }
    public void setFactorName(String factorName) { this.factorName = factorName; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public int getMaxScore() { return maxScore; }
    public void setMaxScore(int maxScore) { this.maxScore = maxScore; }

    public String getTrend() { return trend; }
    public void setTrend(String trend) { this.trend = trend; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }
}
