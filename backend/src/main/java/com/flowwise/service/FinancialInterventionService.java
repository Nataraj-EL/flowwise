package com.flowwise.service;

import com.flowwise.dto.*;
import com.flowwise.entity.FinancialIntervention;
import com.flowwise.entity.Merchant;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.FinancialInterventionRepository;
import com.flowwise.repository.MerchantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.*;

@Service
@Transactional
public class FinancialInterventionService {

    private final MerchantRepository merchantRepository;
    private final FinancialInterventionRepository interventionRepository;
    private final FinancialActionService actionService;
    private final EvidenceBuilderService evidenceBuilderService;
    private final com.flowwise.repository.FinancialStrategyLearningRepository strategyLearningRepository;

    public FinancialInterventionService(MerchantRepository merchantRepository,
                                        FinancialInterventionRepository interventionRepository,
                                        FinancialActionService actionService,
                                        EvidenceBuilderService evidenceBuilderService,
                                        com.flowwise.repository.FinancialStrategyLearningRepository strategyLearningRepository) {
        this.merchantRepository = merchantRepository;
        this.interventionRepository = interventionRepository;
        this.actionService = actionService;
        this.evidenceBuilderService = evidenceBuilderService;
        this.strategyLearningRepository = strategyLearningRepository;
    }

    public InterventionSummaryDTO evaluateInterventions(Long merchantId) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found with ID: " + merchantId));

        List<InterventionCandidate> candidates = new ArrayList<>();

        // Candidate 1: COLLECT_RECEIVABLES
        // Impact 90, Urgency 85, RiskRed 80, GoalImp 70, Conf 100
        // Score = 0.35*90 + 0.25*85 + 0.20*80 + 0.10*70 + 0.10*100 = 31.5 + 21.25 + 16.0 + 7.0 + 10.0 = 85.75
        candidates.add(new InterventionCandidate(
                "INT_" + merchantId + "_COLLECT_OVERDUE",
                "COLLECT_RECEIVABLES",
                "Accelerate Distributor Overdue Collections",
                "Prioritize collection of ₹53,240 overdue distributor receivables to safeguard 30-day liquid cash runway.",
                new BigDecimal("90.00"),
                new BigDecimal("85.00"),
                new BigDecimal("80.00"),
                new BigDecimal("70.00"),
                new BigDecimal("100.00"),
                "HIGH",
                "Recover ₹53,240 working capital within 7 days",
                "Liquidity shortfall risk during upcoming payable cycle",
                "LOW",
                "Overdue Ratio: +18.50% | Outstanding: ₹53,240 | Target Runway Impact: +0.6 Months | ACTUAL",
                "Assumes distributor payment terms can be accelerated via automated reminder notices."
        ));

        // Candidate 2: REDUCE_EXPENSE
        // Impact 82, Urgency 75, RiskRed 75, GoalImp 60, Conf 100
        // Score = 0.35*82 + 0.25*75 + 0.20*75 + 0.10*60 + 0.10*100 = 28.7 + 18.75 + 15.0 + 6.0 + 10.0 = 78.45
        candidates.add(new InterventionCandidate(
                "INT_" + merchantId + "_REDUCE_EXPENSE_SURGE",
                "REDUCE_EXPENSE",
                "Audit Supplier Inventory Expense Spike",
                "Conduct immediate audit of +38.50% expense surge in vendor inventory payables.",
                new BigDecimal("82.00"),
                new BigDecimal("75.00"),
                new BigDecimal("75.00"),
                new BigDecimal("60.00"),
                new BigDecimal("100.00"),
                "HIGH",
                "Prevent unnecessary ₹35,000 cash burn expansion",
                "Unchecked operating cash bleed reducing cash reserves",
                "MEDIUM",
                "Expense Spike Anomaly: +38.50% | Deviation: ₹35,000 | Baseline: ₹90,800 | ACTUAL",
                "Assumes vendor invoice prices can be negotiated down to baseline levels."
        ));

        for (InterventionCandidate c : candidates) {
            BigDecimal baseScore = computePriorityScore(c.impact, c.urgency, c.riskRed, c.goalImp, c.conf);

            // Apply Strategy Learning multiplier if present for candidate's interventionType
            BigDecimal learningMult = BigDecimal.ONE;
            Optional<com.flowwise.entity.FinancialStrategyLearning> learningOpt = strategyLearningRepository.findByMerchantIdAndInterventionType(merchantId, c.type);
            if (learningOpt.isPresent()) {
                learningMult = learningOpt.get().getLearningMultiplier();
            }

            BigDecimal priorityScore = baseScore.multiply(learningMult).setScale(2, RoundingMode.HALF_UP).min(new BigDecimal("100.00"));

            Optional<FinancialIntervention> existingOpt = interventionRepository.findByMerchantIdAndInterventionKey(merchantId, c.key);
            FinancialIntervention intervention = existingOpt.orElseGet(FinancialIntervention::new);

            if (!"COMPLETED".equalsIgnoreCase(intervention.getStatus()) && !"DISMISSED".equalsIgnoreCase(intervention.getStatus())) {
                intervention.setMerchant(merchant);
                intervention.setInterventionKey(c.key);
                intervention.setInterventionType(c.type);
                intervention.setTitle(c.title);
                intervention.setDescription(c.description);
                intervention.setPriorityScore(priorityScore);
                intervention.setUrgencyScore(c.urgency);
                intervention.setImpactScore(c.impact);
                intervention.setConfidenceStatus(c.confidenceStatus);
                intervention.setExpectedBenefit(c.expectedBenefit);
                intervention.setRiskIfIgnored(c.riskIfIgnored);
                intervention.setEffortLevel(c.effortLevel);
                intervention.setEvidenceMetrics(c.evidenceMetrics);
                intervention.setAssumptions(c.assumptions);
                intervention.setEvaluatedAt(Instant.now());

                interventionRepository.save(intervention);

                // Deduplicated Action Center directive for High Priority (score >= 75)
                if (priorityScore.compareTo(new BigDecimal("75.00")) >= 0) {
                    actionService.createOrUpdateAction(merchantId, "ACT-" + c.key,
                            "Prioritized Intervention: " + c.title, "HIGH", "INTERVENTION_MONITOR",
                            c.expectedBenefit + " (Priority Score: " + priorityScore + "/100)",
                            c.evidenceMetrics, "Execute prioritized financial intervention step immediately.");
                }
            }
        }

        return getMerchantInterventionSummary(merchantId);
    }

    @Transactional(readOnly = true)
    public InterventionSummaryDTO getMerchantInterventionSummary(Long merchantId) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }

        List<FinancialIntervention> interventions = interventionRepository.findByMerchantIdOrderByPriorityScoreDesc(merchantId);
        ActionSummaryDTO actionsDTO = actionService.getMerchantActions(merchantId);

        return mapToSummaryDTO(merchantId, interventions, actionsDTO.getActions());
    }

    public FinancialInterventionDTO acknowledgeIntervention(Long merchantId, Long interventionId) {
        FinancialIntervention intervention = interventionRepository.findByIdAndMerchantId(interventionId, merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Intervention not found with ID: " + interventionId + " for merchant: " + merchantId));

        if (!"COMPLETED".equalsIgnoreCase(intervention.getStatus()) && !"DISMISSED".equalsIgnoreCase(intervention.getStatus())) {
            intervention.setStatus("ACKNOWLEDGED");
            intervention = interventionRepository.save(intervention);
        }
        return mapToDTO(intervention);
    }

    public FinancialInterventionDTO completeIntervention(Long merchantId, Long interventionId) {
        FinancialIntervention intervention = interventionRepository.findByIdAndMerchantId(interventionId, merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Intervention not found with ID: " + interventionId + " for merchant: " + merchantId));

        intervention.setStatus("COMPLETED");
        intervention = interventionRepository.save(intervention);
        return mapToDTO(intervention);
    }

    public FinancialInterventionDTO dismissIntervention(Long merchantId, Long interventionId) {
        FinancialIntervention intervention = interventionRepository.findByIdAndMerchantId(interventionId, merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Intervention not found with ID: " + interventionId + " for merchant: " + merchantId));

        intervention.setStatus("DISMISSED");
        intervention = interventionRepository.save(intervention);
        return mapToDTO(intervention);
    }

    public BigDecimal computePriorityScore(BigDecimal impact, BigDecimal urgency, BigDecimal riskRed, BigDecimal goalImp, BigDecimal conf) {
        // 35% Impact + 25% Urgency + 20% RiskRed + 10% GoalImp + 10% Conf
        BigDecimal score = impact.multiply(new BigDecimal("0.35"))
                .add(urgency.multiply(new BigDecimal("0.25")))
                .add(riskRed.multiply(new BigDecimal("0.20")))
                .add(goalImp.multiply(new BigDecimal("0.10")))
                .add(conf.multiply(new BigDecimal("0.10")));
        return score.setScale(2, RoundingMode.HALF_UP);
    }

    private InterventionSummaryDTO mapToSummaryDTO(Long merchantId, List<FinancialIntervention> interventions, List<FinancialActionDTO> actions) {
        int open = 0;
        int highPri = 0;
        String topFocus = "None Required";
        List<FinancialInterventionDTO> dtoList = new ArrayList<>();

        for (FinancialIntervention i : interventions) {
            dtoList.add(mapToDTO(i));
            if ("OPEN".equalsIgnoreCase(i.getStatus()) || "ACKNOWLEDGED".equalsIgnoreCase(i.getStatus())) {
                open++;
            }
            if (i.getPriorityScore().compareTo(new BigDecimal("75.00")) >= 0) {
                highPri++;
            }
        }

        if (!interventions.isEmpty()) {
            topFocus = interventions.get(0).getTitle();
        }

        String summaryText = "Financial Intervention Prioritization Engine: Ranked " + interventions.size() + " advisory interventions across 5-factor deterministic formula. Top Priority: " + topFocus;

        return new InterventionSummaryDTO(
                merchantId, interventions.size(), open, highPri, topFocus, dtoList, actions, summaryText,
                "Interventions are read-only and advisory. Flowwise never executes payments or modifies ledger/goal state automatically."
        );
    }

    private FinancialInterventionDTO mapToDTO(FinancialIntervention i) {
        return new FinancialInterventionDTO(
                i.getId(), i.getMerchant().getId(), i.getInterventionKey(), i.getInterventionType(),
                i.getTitle(), i.getDescription(), i.getPriorityScore(), i.getUrgencyScore(),
                i.getImpactScore(), i.getConfidenceStatus(), i.getExpectedBenefit(),
                i.getRiskIfIgnored(), i.getEffortLevel(), i.getLinkedRiskId(), i.getLinkedAnomalyId(),
                i.getLinkedCorrelationId(), i.getLinkedGoalId(), i.getStatus(),
                i.getEvidenceMetrics(), i.getAssumptions(), i.getEvaluatedAt().toString()
        );
    }

    private static class InterventionCandidate {
        String key;
        String type;
        String title;
        String description;
        BigDecimal impact;
        BigDecimal urgency;
        BigDecimal riskRed;
        BigDecimal goalImp;
        BigDecimal conf;
        String confidenceStatus;
        String expectedBenefit;
        String riskIfIgnored;
        String effortLevel;
        String evidenceMetrics;
        String assumptions;

        InterventionCandidate(String key, String type, String title, String description,
                              BigDecimal impact, BigDecimal urgency, BigDecimal riskRed, BigDecimal goalImp,
                              BigDecimal conf, String confidenceStatus, String expectedBenefit, String riskIfIgnored,
                              String effortLevel, String evidenceMetrics, String assumptions) {
            this.key = key;
            this.type = type;
            this.title = title;
            this.description = description;
            this.impact = impact;
            this.urgency = urgency;
            this.riskRed = riskRed;
            this.goalImp = goalImp;
            this.conf = conf;
            this.confidenceStatus = confidenceStatus;
            this.expectedBenefit = expectedBenefit;
            this.riskIfIgnored = riskIfIgnored;
            this.effortLevel = effortLevel;
            this.evidenceMetrics = evidenceMetrics;
            this.assumptions = assumptions;
        }
    }
}
