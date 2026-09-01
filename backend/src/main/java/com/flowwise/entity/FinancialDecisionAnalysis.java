package com.flowwise.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "financial_decision_analyses")
public class FinancialDecisionAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(name = "analysis_key", nullable = false, length = 64)
    private String analysisKey;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "recommended_option", nullable = false, length = 64)
    private String recommendedOption;

    @Column(name = "baseline_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal baselineScore = BigDecimal.ZERO;

    @Column(name = "data_quality_status", nullable = false, length = 32)
    private String dataQualityStatus = "SUFFICIENT"; // SUFFICIENT, INSUFFICIENT_DATA

    @Column(name = "input_fingerprint", length = 128)
    private String inputFingerprint;

    @Column(name = "summary_explanation", columnDefinition = "TEXT")
    private String summaryExplanation;

    @Column(name = "evaluated_at", nullable = false)
    private Instant evaluatedAt = Instant.now();

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    @OneToMany(mappedBy = "analysis", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("rankOrder ASC")
    private List<FinancialDecisionOption> options = new ArrayList<>();

    public FinancialDecisionAnalysis() {}

    public FinancialDecisionAnalysis(Merchant merchant, String analysisKey, String title, String recommendedOption,
                                     BigDecimal baselineScore, String dataQualityStatus, String inputFingerprint,
                                     String summaryExplanation) {
        this.merchant = merchant;
        this.analysisKey = analysisKey;
        this.title = title;
        this.recommendedOption = recommendedOption;
        this.baselineScore = baselineScore != null ? baselineScore : BigDecimal.ZERO;
        this.dataQualityStatus = dataQualityStatus != null ? dataQualityStatus : "SUFFICIENT";
        this.inputFingerprint = inputFingerprint;
        this.summaryExplanation = summaryExplanation;
        this.evaluatedAt = Instant.now();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Merchant getMerchant() { return merchant; }
    public void setMerchant(Merchant merchant) { this.merchant = merchant; }

    public String getAnalysisKey() { return analysisKey; }
    public void setAnalysisKey(String analysisKey) { this.analysisKey = analysisKey; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getRecommendedOption() { return recommendedOption; }
    public void setRecommendedOption(String recommendedOption) { this.recommendedOption = recommendedOption; }

    public BigDecimal getBaselineScore() { return baselineScore; }
    public void setBaselineScore(BigDecimal baselineScore) { this.baselineScore = baselineScore; }

    public String getDataQualityStatus() { return dataQualityStatus; }
    public void setDataQualityStatus(String dataQualityStatus) { this.dataQualityStatus = dataQualityStatus; }

    public String getInputFingerprint() { return inputFingerprint; }
    public void setInputFingerprint(String inputFingerprint) { this.inputFingerprint = inputFingerprint; }

    public String getSummaryExplanation() { return summaryExplanation; }
    public void setSummaryExplanation(String summaryExplanation) { this.summaryExplanation = summaryExplanation; }

    public Instant getEvaluatedAt() { return evaluatedAt; }
    public void setEvaluatedAt(Instant evaluatedAt) { this.evaluatedAt = evaluatedAt; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    public List<FinancialDecisionOption> getOptions() { return options; }
    public void setOptions(List<FinancialDecisionOption> options) { this.options = options; }
}
