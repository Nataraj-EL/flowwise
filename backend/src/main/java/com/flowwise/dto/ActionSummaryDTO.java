package com.flowwise.dto;

import java.util.List;

public class ActionSummaryDTO {
    private int totalActions;
    private int highPriorityCount;
    private int mediumPriorityCount;
    private int lowPriorityCount;
    private int openCount;
    private List<FinancialActionDTO> actions;

    public ActionSummaryDTO() {}

    public ActionSummaryDTO(int totalActions, int highPriorityCount, int mediumPriorityCount, 
                            int lowPriorityCount, int openCount, List<FinancialActionDTO> actions) {
        this.totalActions = totalActions;
        this.highPriorityCount = highPriorityCount;
        this.mediumPriorityCount = mediumPriorityCount;
        this.lowPriorityCount = lowPriorityCount;
        this.openCount = openCount;
        this.actions = actions;
    }

    public int getTotalActions() { return totalActions; }
    public void setTotalActions(int totalActions) { this.totalActions = totalActions; }

    public int getHighPriorityCount() { return highPriorityCount; }
    public void setHighPriorityCount(int highPriorityCount) { this.highPriorityCount = highPriorityCount; }

    public int getMediumPriorityCount() { return mediumPriorityCount; }
    public void setMediumPriorityCount(int mediumPriorityCount) { this.mediumPriorityCount = mediumPriorityCount; }

    public int getLowPriorityCount() { return lowPriorityCount; }
    public void setLowPriorityCount(int lowPriorityCount) { this.lowPriorityCount = lowPriorityCount; }

    public int getOpenCount() { return openCount; }
    public void setOpenCount(int openCount) { this.openCount = openCount; }

    public List<FinancialActionDTO> getActions() { return actions; }
    public void setActions(List<FinancialActionDTO> actions) { this.actions = actions; }
}
