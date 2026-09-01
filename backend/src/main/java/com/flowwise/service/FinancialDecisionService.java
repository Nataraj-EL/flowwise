package com.flowwise.service;

import com.flowwise.dto.*;
import com.flowwise.entity.FinancialAction;
import com.flowwise.entity.FinancialDecision;
import com.flowwise.entity.FinancialGoal;
import com.flowwise.entity.Merchant;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.FinancialActionRepository;
import com.flowwise.repository.FinancialDecisionRepository;
import com.flowwise.repository.FinancialGoalRepository;
import com.flowwise.repository.MerchantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class FinancialDecisionService {

    private final MerchantRepository merchantRepository;
    private final FinancialDecisionRepository decisionRepository;
    private final FinancialActionRepository actionRepository;
    private final FinancialGoalRepository goalRepository;

    public FinancialDecisionService(MerchantRepository merchantRepository,
                                    FinancialDecisionRepository decisionRepository,
                                    FinancialActionRepository actionRepository,
                                    FinancialGoalRepository goalRepository) {
        this.merchantRepository = merchantRepository;
        this.decisionRepository = decisionRepository;
        this.actionRepository = actionRepository;
        this.goalRepository = goalRepository;
    }

    public FinancialDecisionDTO createDecision(Long merchantId, CreateDecisionRequestDTO request) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found with ID: " + merchantId));

        FinancialAction action = null;
        if (request.getActionId() != null) {
            action = actionRepository.findById(request.getActionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Action not found with ID: " + request.getActionId()));
            if (!action.getMerchantId().equals(merchantId)) {
                throw new IllegalArgumentException("Action ID " + request.getActionId() + " does not belong to merchant " + merchantId);
            }
            // Check duplicate active decision for action
            Optional<FinancialDecision> existing = decisionRepository.findByMerchantIdAndActionIdAndDecisionStatusIn(
                    merchantId, request.getActionId(), List.of("PENDING", "ACCEPTED"));
            if (existing.isPresent()) {
                throw new IllegalStateException("An active decision already exists for Action ID: " + request.getActionId());
            }
        }

        FinancialGoal goal = null;
        if (request.getGoalId() != null) {
            goal = goalRepository.findById(request.getGoalId())
                    .orElseThrow(() -> new ResourceNotFoundException("Goal not found with ID: " + request.getGoalId()));
            if (!goal.getMerchant().getId().equals(merchantId)) {
                throw new IllegalArgumentException("Goal ID " + request.getGoalId() + " does not belong to merchant " + merchantId);
            }
            // Check duplicate active decision for goal
            Optional<FinancialDecision> existing = decisionRepository.findByMerchantIdAndGoalIdAndDecisionStatusIn(
                    merchantId, request.getGoalId(), List.of("PENDING", "ACCEPTED"));
            if (existing.isPresent()) {
                throw new IllegalStateException("An active decision already exists for Goal ID: " + request.getGoalId());
            }
        }

        FinancialDecision decision = new FinancialDecision(
                merchant,
                action,
                goal,
                request.getDecisionType() != null ? request.getDecisionType() : "GENERAL_DECISION",
                request.getTitle(),
                request.getRecommendation(),
                request.getDecisionNotes(),
                request.getDecisionDate() != null ? request.getDecisionDate() : LocalDate.now()
        );

        FinancialDecision saved = decisionRepository.save(decision);
        return mapToDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<FinancialDecisionDTO> getMerchantDecisions(Long merchantId) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }

        List<FinancialDecision> decisions = decisionRepository.findByMerchantIdOrderByDecisionDateDescCreatedAtDesc(merchantId);
        return decisions.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DecisionSummaryDTO getDecisionSummary(Long merchantId) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }

        List<FinancialDecision> decisions = decisionRepository.findByMerchantIdOrderByDecisionDateDescCreatedAtDesc(merchantId);

        int total = decisions.size();
        int pending = 0;
        int accepted = 0;
        int declined = 0;
        int completed = 0;
        int positive = 0;
        int negative = 0;
        int neutral = 0;
        int unknown = 0;

        for (FinancialDecision d : decisions) {
            switch (d.getDecisionStatus().toUpperCase()) {
                case "PENDING" -> pending++;
                case "ACCEPTED" -> accepted++;
                case "DECLINED" -> declined++;
                case "COMPLETED" -> completed++;
            }

            switch (d.getOutcomeStatus().toUpperCase()) {
                case "POSITIVE" -> positive++;
                case "NEGATIVE" -> negative++;
                case "NEUTRAL" -> neutral++;
                default -> unknown++;
            }
        }

        BigDecimal successRate = BigDecimal.ZERO;
        if (completed > 0) {
            successRate = BigDecimal.valueOf(positive)
                    .multiply(new BigDecimal("100"))
                    .divide(BigDecimal.valueOf(completed), 2, RoundingMode.HALF_UP);
        }

        return new DecisionSummaryDTO(
                total,
                pending,
                accepted,
                declined,
                completed,
                positive,
                negative,
                neutral,
                unknown,
                successRate
        );
    }

    public FinancialDecisionDTO acceptDecision(Long merchantId, Long decisionId, String notes) {
        FinancialDecision decision = getValidDecision(merchantId, decisionId);

        if (!"PENDING".equalsIgnoreCase(decision.getDecisionStatus())) {
            throw new IllegalStateException("Cannot ACCEPT decision in status: " + decision.getDecisionStatus() + ". Must be PENDING.");
        }

        decision.setDecisionStatus("ACCEPTED");
        if (notes != null && !notes.trim().isEmpty()) {
            decision.setDecisionNotes(notes);
        }

        FinancialDecision saved = decisionRepository.save(decision);
        return mapToDTO(saved);
    }

    public FinancialDecisionDTO declineDecision(Long merchantId, Long decisionId, String notes) {
        FinancialDecision decision = getValidDecision(merchantId, decisionId);

        if (!"PENDING".equalsIgnoreCase(decision.getDecisionStatus())) {
            throw new IllegalStateException("Cannot DECLINE decision in status: " + decision.getDecisionStatus() + ". Must be PENDING.");
        }

        decision.setDecisionStatus("DECLINED");
        if (notes != null && !notes.trim().isEmpty()) {
            decision.setDecisionNotes(notes);
        }

        FinancialDecision saved = decisionRepository.save(decision);
        return mapToDTO(saved);
    }

    public FinancialDecisionDTO completeDecision(Long merchantId, Long decisionId, String notes) {
        FinancialDecision decision = getValidDecision(merchantId, decisionId);

        if (!"ACCEPTED".equalsIgnoreCase(decision.getDecisionStatus())) {
            throw new IllegalStateException("Cannot COMPLETE decision in status: " + decision.getDecisionStatus() + ". Must be ACCEPTED first.");
        }

        decision.setDecisionStatus("COMPLETED");
        if (notes != null && !notes.trim().isEmpty()) {
            decision.setDecisionNotes(notes);
        }

        FinancialDecision saved = decisionRepository.save(decision);
        return mapToDTO(saved);
    }

    public FinancialDecisionDTO recordOutcome(Long merchantId, Long decisionId, DecisionOutcomeDTO outcome) {
        FinancialDecision decision = getValidDecision(merchantId, decisionId);

        if (!"COMPLETED".equalsIgnoreCase(decision.getDecisionStatus())) {
            throw new IllegalStateException("Outcome recording allowed ONLY for COMPLETED decisions. Current status: " + decision.getDecisionStatus());
        }

        String status = outcome.getOutcomeStatus() != null ? outcome.getOutcomeStatus().toUpperCase().trim() : "NEUTRAL";
        if (!List.of("POSITIVE", "NEGATIVE", "NEUTRAL", "UNKNOWN").contains(status)) {
            throw new IllegalArgumentException("Invalid outcome status: " + status);
        }

        decision.setOutcomeStatus(status);
        if (outcome.getOutcomeNotes() != null) {
            decision.setOutcomeNotes(outcome.getOutcomeNotes());
        }

        FinancialDecision saved = decisionRepository.save(decision);
        return mapToDTO(saved);
    }

    private FinancialDecision getValidDecision(Long merchantId, Long decisionId) {
        return decisionRepository.findByIdAndMerchantId(decisionId, merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Decision not found with ID: " + decisionId + " for merchant: " + merchantId));
    }

    private FinancialDecisionDTO mapToDTO(FinancialDecision d) {
        DateTimeFormatter isoDate = DateTimeFormatter.ISO_LOCAL_DATE;

        return new FinancialDecisionDTO(
                d.getId(),
                d.getMerchant().getId(),
                d.getAction() != null ? d.getAction().getId() : null,
                d.getAction() != null ? d.getAction().getTitle() : null,
                d.getGoal() != null ? d.getGoal().getId() : null,
                d.getGoal() != null ? d.getGoal().getName() : null,
                d.getDecisionType(),
                d.getTitle(),
                d.getRecommendation(),
                d.getDecisionStatus(),
                d.getDecisionNotes(),
                isoDate.format(d.getDecisionDate()),
                d.getOutcomeStatus(),
                d.getOutcomeNotes(),
                d.getCreatedAt().toString(),
                d.getUpdatedAt().toString()
        );
    }
}
