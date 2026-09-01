package com.flowwise.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "financial_decisions")
public class FinancialDecision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_id")
    private FinancialAction action;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_id")
    private FinancialGoal goal;

    @Column(name = "decision_type", nullable = false, length = 64)
    private String decisionType;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "recommendation", columnDefinition = "TEXT")
    private String recommendation;

    @Column(name = "decision_status", nullable = false, length = 32)
    private String decisionStatus = "PENDING"; // PENDING, ACCEPTED, DECLINED, COMPLETED

    @Column(name = "decision_notes", length = 1000)
    private String decisionNotes;

    @Column(name = "decision_date", nullable = false)
    private LocalDate decisionDate = LocalDate.now();

    @Column(name = "outcome_status", nullable = false, length = 32)
    private String outcomeStatus = "UNKNOWN"; // UNKNOWN, POSITIVE, NEGATIVE, NEUTRAL

    @Column(name = "outcome_notes", length = 1000)
    private String outcomeNotes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    public FinancialDecision() {}

    public FinancialDecision(Merchant merchant, FinancialAction action, FinancialGoal goal,
                             String decisionType, String title, String recommendation,
                             String decisionNotes, LocalDate decisionDate) {
        this.merchant = merchant;
        this.action = action;
        this.goal = goal;
        this.decisionType = decisionType;
        this.title = title;
        this.recommendation = recommendation;
        this.decisionStatus = "PENDING";
        this.decisionNotes = decisionNotes;
        this.decisionDate = decisionDate != null ? decisionDate : LocalDate.now();
        this.outcomeStatus = "UNKNOWN";
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

    public FinancialAction getAction() { return action; }
    public void setAction(FinancialAction action) { this.action = action; }

    public FinancialGoal getGoal() { return goal; }
    public void setGoal(FinancialGoal goal) { this.goal = goal; }

    public String getDecisionType() { return decisionType; }
    public void setDecisionType(String decisionType) { this.decisionType = decisionType; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getRecommendation() { return recommendation; }
    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }

    public String getDecisionStatus() { return decisionStatus; }
    public void setDecisionStatus(String decisionStatus) { this.decisionStatus = decisionStatus; }

    public String getDecisionNotes() { return decisionNotes; }
    public void setDecisionNotes(String decisionNotes) { this.decisionNotes = decisionNotes; }

    public LocalDate getDecisionDate() { return decisionDate; }
    public void setDecisionDate(LocalDate decisionDate) { this.decisionDate = decisionDate; }

    public String getOutcomeStatus() { return outcomeStatus; }
    public void setOutcomeStatus(String outcomeStatus) { this.outcomeStatus = outcomeStatus; }

    public String getOutcomeNotes() { return outcomeNotes; }
    public void setOutcomeNotes(String outcomeNotes) { this.outcomeNotes = outcomeNotes; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
