package com.flowwise.dto;

import java.math.BigDecimal;

public class DecisionSummaryDTO {
    private int totalDecisions;
    private int pendingCount;
    private int acceptedCount;
    private int declinedCount;
    private int completedCount;
    private int positiveOutcomeCount;
    private int negativeOutcomeCount;
    private int neutralOutcomeCount;
    private int unknownOutcomeCount;
    private BigDecimal successRatePct;

    public DecisionSummaryDTO() {}

    public DecisionSummaryDTO(int totalDecisions, int pendingCount, int acceptedCount, 
                              int declinedCount, int completedCount, int positiveOutcomeCount, 
                              int negativeOutcomeCount, int neutralOutcomeCount, 
                              int unknownOutcomeCount, BigDecimal successRatePct) {
        this.totalDecisions = totalDecisions;
        this.pendingCount = pendingCount;
        this.acceptedCount = acceptedCount;
        this.declinedCount = declinedCount;
        this.completedCount = completedCount;
        this.positiveOutcomeCount = positiveOutcomeCount;
        this.negativeOutcomeCount = negativeOutcomeCount;
        this.neutralOutcomeCount = neutralOutcomeCount;
        this.unknownOutcomeCount = unknownOutcomeCount;
        this.successRatePct = successRatePct;
    }

    public int getTotalDecisions() { return totalDecisions; }
    public void setTotalDecisions(int totalDecisions) { this.totalDecisions = totalDecisions; }

    public int getPendingCount() { return pendingCount; }
    public void setPendingCount(int pendingCount) { this.pendingCount = pendingCount; }

    public int getAcceptedCount() { return acceptedCount; }
    public void setAcceptedCount(int acceptedCount) { this.acceptedCount = acceptedCount; }

    public int getDeclinedCount() { return declinedCount; }
    public void setDeclinedCount(int declinedCount) { this.declinedCount = declinedCount; }

    public int getCompletedCount() { return completedCount; }
    public void setCompletedCount(int completedCount) { this.completedCount = completedCount; }

    public int getPositiveOutcomeCount() { return positiveOutcomeCount; }
    public void setPositiveOutcomeCount(int positiveOutcomeCount) { this.positiveOutcomeCount = positiveOutcomeCount; }

    public int getNegativeOutcomeCount() { return negativeOutcomeCount; }
    public void setNegativeOutcomeCount(int negativeOutcomeCount) { this.negativeOutcomeCount = negativeOutcomeCount; }

    public int getNeutralOutcomeCount() { return neutralOutcomeCount; }
    public void setNeutralOutcomeCount(int neutralOutcomeCount) { this.neutralOutcomeCount = neutralOutcomeCount; }

    public int getUnknownOutcomeCount() { return unknownOutcomeCount; }
    public void setUnknownOutcomeCount(int unknownOutcomeCount) { this.unknownOutcomeCount = unknownOutcomeCount; }

    public BigDecimal getSuccessRatePct() { return successRatePct; }
    public void setSuccessRatePct(BigDecimal successRatePct) { this.successRatePct = successRatePct; }
}
