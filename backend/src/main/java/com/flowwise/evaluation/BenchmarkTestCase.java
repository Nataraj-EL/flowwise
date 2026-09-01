package com.flowwise.evaluation;

import java.util.List;

public class BenchmarkTestCase {
    private String caseId;
    private String question;
    private String category;
    private List<String> expectedKeywords;
    private List<String> expectedEvidenceKeys;
    private boolean outOfScopeOrInsufficient;

    public BenchmarkTestCase() {}

    public BenchmarkTestCase(String caseId, String question, String category, 
                             List<String> expectedKeywords, List<String> expectedEvidenceKeys, 
                             boolean outOfScopeOrInsufficient) {
        this.caseId = caseId;
        this.question = question;
        this.category = category;
        this.expectedKeywords = expectedKeywords;
        this.expectedEvidenceKeys = expectedEvidenceKeys;
        this.outOfScopeOrInsufficient = outOfScopeOrInsufficient;
    }

    public String getCaseId() { return caseId; }
    public void setCaseId(String caseId) { this.caseId = caseId; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public List<String> getExpectedKeywords() { return expectedKeywords; }
    public void setExpectedKeywords(List<String> expectedKeywords) { this.expectedKeywords = expectedKeywords; }

    public List<String> getExpectedEvidenceKeys() { return expectedEvidenceKeys; }
    public void setExpectedEvidenceKeys(List<String> expectedEvidenceKeys) { this.expectedEvidenceKeys = expectedEvidenceKeys; }

    public boolean isOutOfScopeOrInsufficient() { return outOfScopeOrInsufficient; }
    public void setOutOfScopeOrInsufficient(boolean outOfScopeOrInsufficient) { this.outOfScopeOrInsufficient = outOfScopeOrInsufficient; }
}
