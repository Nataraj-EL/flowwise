package com.flowwise.dto;

public class EvidenceItemDTO {
    private String metricName;
    private Object value;
    private String unit;
    private String source;
    private String period;
    private String calculationType; // ACTUAL, ESTIMATE
    private String assumptions;
    private String confidenceStatus; // HIGH, MODERATE, LIMITED

    public EvidenceItemDTO() {}

    public EvidenceItemDTO(String metricName, Object value, String unit, String source, 
                           String period, String calculationType, String assumptions, String confidenceStatus) {
        this.metricName = metricName;
        this.value = value;
        this.unit = unit;
        this.source = source;
        this.period = period;
        this.calculationType = calculationType;
        this.assumptions = assumptions;
        this.confidenceStatus = confidenceStatus;
    }

    public String getMetricName() { return metricName; }
    public void setMetricName(String metricName) { this.metricName = metricName; }

    public Object getValue() { return value; }
    public void setValue(Object value) { this.value = value; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }

    public String getCalculationType() { return calculationType; }
    public void setCalculationType(String calculationType) { this.calculationType = calculationType; }

    public String getAssumptions() { return assumptions; }
    public void setAssumptions(String assumptions) { this.assumptions = assumptions; }

    public String getConfidenceStatus() { return confidenceStatus; }
    public void setConfidenceStatus(String confidenceStatus) { this.confidenceStatus = confidenceStatus; }
}
