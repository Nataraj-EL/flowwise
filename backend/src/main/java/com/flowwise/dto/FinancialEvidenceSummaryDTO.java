package com.flowwise.dto;

import java.util.List;

public class FinancialEvidenceSummaryDTO {
    private String question;
    private String intentCategory; // AFFORDABILITY, CASH_FLOW, HEALTH, TEMPORAL, FORECAST
    private List<EvidenceItemDTO> evidenceItems;
    private List<String> assumptions;
    private String overallStatus; // FEASIBLE, HEALTHY, WATCH, AT_RISK, CAUTION, INSUFFICIENT_DATA
    private String conclusion;

    public FinancialEvidenceSummaryDTO() {}

    public FinancialEvidenceSummaryDTO(String question, String intentCategory, List<EvidenceItemDTO> evidenceItems, 
                                      List<String> assumptions, String overallStatus, String conclusion) {
        this.question = question;
        this.intentCategory = intentCategory;
        this.evidenceItems = evidenceItems;
        this.assumptions = assumptions;
        this.overallStatus = overallStatus;
        this.conclusion = conclusion;
    }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getIntentCategory() { return intentCategory; }
    public void setIntentCategory(String intentCategory) { this.intentCategory = intentCategory; }

    public List<EvidenceItemDTO> getEvidenceItems() { return evidenceItems; }
    public void setEvidenceItems(List<EvidenceItemDTO> evidenceItems) { this.evidenceItems = evidenceItems; }

    public List<String> getAssumptions() { return assumptions; }
    public void setAssumptions(List<String> assumptions) { this.assumptions = assumptions; }

    public String getOverallStatus() { return overallStatus; }
    public void setOverallStatus(String overallStatus) { this.overallStatus = overallStatus; }

    public String getConclusion() { return conclusion; }
    public void setConclusion(String conclusion) { this.conclusion = conclusion; }
}
