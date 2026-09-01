package com.flowwise.dto;

import java.util.List;

public class FinancialExecutionScheduleSummaryDTO {
    private Long merchantId;
    private int totalSchedulesCount;
    private FinancialExecutionScheduleDTO activeSchedule;
    private List<FinancialExecutionScheduleDTO> schedules;
    private String summaryExplanation;
    private String advisoryNotice;

    public FinancialExecutionScheduleSummaryDTO() {}

    public FinancialExecutionScheduleSummaryDTO(Long merchantId, int totalSchedulesCount,
                                                FinancialExecutionScheduleDTO activeSchedule,
                                                List<FinancialExecutionScheduleDTO> schedules,
                                                String summaryExplanation, String advisoryNotice) {
        this.merchantId = merchantId;
        this.totalSchedulesCount = totalSchedulesCount;
        this.activeSchedule = activeSchedule;
        this.schedules = schedules;
        this.summaryExplanation = summaryExplanation;
        this.advisoryNotice = advisoryNotice;
    }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public int getTotalSchedulesCount() { return totalSchedulesCount; }
    public void setTotalSchedulesCount(int totalSchedulesCount) { this.totalSchedulesCount = totalSchedulesCount; }

    public FinancialExecutionScheduleDTO getActiveSchedule() { return activeSchedule; }
    public void setActiveSchedule(FinancialExecutionScheduleDTO activeSchedule) { this.activeSchedule = activeSchedule; }

    public List<FinancialExecutionScheduleDTO> getSchedules() { return schedules; }
    public void setSchedules(List<FinancialExecutionScheduleDTO> schedules) { this.schedules = schedules; }

    public String getSummaryExplanation() { return summaryExplanation; }
    public void setSummaryExplanation(String summaryExplanation) { this.summaryExplanation = summaryExplanation; }

    public String getAdvisoryNotice() { return advisoryNotice; }
    public void setAdvisoryNotice(String advisoryNotice) { this.advisoryNotice = advisoryNotice; }
}
