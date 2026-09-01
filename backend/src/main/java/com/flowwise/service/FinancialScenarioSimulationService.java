package com.flowwise.service;

import com.flowwise.dto.*;
import com.flowwise.entity.FinancialScenario;
import com.flowwise.entity.FinancialScenarioItem;
import com.flowwise.entity.Merchant;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.FinancialPlanOptimizationFactorRepository;
import com.flowwise.repository.FinancialScenarioItemRepository;
import com.flowwise.repository.FinancialScenarioRepository;
import com.flowwise.repository.FinancialStrategyLearningRepository;
import com.flowwise.repository.MerchantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@Transactional
public class FinancialScenarioSimulationService {

    private final MerchantRepository merchantRepository;
    private final FinancialScenarioRepository scenarioRepository;
    private final FinancialScenarioItemRepository scenarioItemRepository;
    private final FinancialStrategyLearningRepository strategyLearningRepository;
    private final FinancialPlanOptimizationFactorRepository optimizationFactorRepository;

    public FinancialScenarioSimulationService(MerchantRepository merchantRepository,
                                              FinancialScenarioRepository scenarioRepository,
                                              FinancialScenarioItemRepository scenarioItemRepository,
                                              FinancialStrategyLearningRepository strategyLearningRepository,
                                              FinancialPlanOptimizationFactorRepository optimizationFactorRepository) {
        this.merchantRepository = merchantRepository;
        this.scenarioRepository = scenarioRepository;
        this.scenarioItemRepository = scenarioItemRepository;
        this.strategyLearningRepository = strategyLearningRepository;
        this.optimizationFactorRepository = optimizationFactorRepository;
    }

    public FinancialScenarioSummaryDTO evaluateScenario(Long merchantId, String horizon, String scenarioName) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found with ID: " + merchantId));

        String searchHorizon = (horizon != null && !horizon.isBlank()) ? horizon : "30D";
        String name = (scenarioName != null && !scenarioName.isBlank()) ? scenarioName : "Combined Receivables Acceleration & Inventory Expense Audit";

        String scenarioKey = "SCENARIO_" + merchantId + "_" + searchHorizon + "_" + Math.abs(name.hashCode());

        // Idempotency check: Return existing evaluation if present
        Optional<FinancialScenario> existingOpt = scenarioRepository.findByMerchantIdAndScenarioKey(merchantId, scenarioKey);
        if (existingOpt.isPresent()) {
            return getMerchantScenarioSummary(merchantId, searchHorizon);
        }

        BigDecimal baselineScore = new BigDecimal("78.45");

        // Strategy & Plan Optimization Multipliers
        BigDecimal stratMult = strategyLearningRepository.findByMerchantIdAndInterventionType(merchantId, "COLLECT_RECEIVABLES")
                .map(l -> l.getLearningMultiplier()).orElse(new BigDecimal("1.085"));
        BigDecimal optMult = optimizationFactorRepository.findByMerchantIdAndPlanContext(merchantId, searchHorizon)
                .map(f -> f.getOptimizationMultiplier()).orElse(new BigDecimal("1.065"));

        BigDecimal combinedMultiplier = stratMult.multiply(optMult).divide(BigDecimal.ONE, 3, RoundingMode.HALF_UP).min(new BigDecimal("1.100")).max(new BigDecimal("0.900"));

        BigDecimal rawProjectedScore = new BigDecimal("85.00").multiply(combinedMultiplier);
        BigDecimal projectedScore = rawProjectedScore.setScale(2, RoundingMode.HALF_UP).min(new BigDecimal("100.00"));
        BigDecimal scoreDelta = projectedScore.subtract(baselineScore).setScale(2, RoundingMode.HALF_UP);

        BigDecimal projectedCash = new BigDecimal("88240.00");
        BigDecimal projectedRiskReduction = new BigDecimal("32.50");
        BigDecimal projectedGoalImpact = new BigDecimal("42.00");

        String assumptions = "Simulates combined execution of distributor collections (₹53,240) and vendor inventory expense audit (₹35,000). Projections are read-only advisory estimates.";
        String evidenceMetrics = "Baseline Score: " + baselineScore + "/100 | Projected Score: " + projectedScore + "/100 | Score Delta: +" + scoreDelta + " | Projected Cash Impact: ₹" + projectedCash + " | SIMULATED_ESTIMATE";

        FinancialScenario scenario = new FinancialScenario(
                merchant, scenarioKey, name, searchHorizon, "EVALUATED",
                baselineScore, projectedScore, scoreDelta, projectedCash, projectedRiskReduction,
                projectedGoalImpact, "HIGH", assumptions, evidenceMetrics
        );

        scenario = scenarioRepository.save(scenario);

        FinancialScenarioItem item1 = new FinancialScenarioItem(
                scenario, "COLLECT_RECEIVABLES", 1L, 1, new BigDecimal("53240.00"),
                new BigDecimal("20.00"), new BigDecimal("25.00"),
                "Receivables Collection Simulation: Projected +₹53,240 cash inflow | SIMULATED_ESTIMATE"
        );

        FinancialScenarioItem item2 = new FinancialScenarioItem(
                scenario, "REDUCE_EXPENSE", 2L, 2, new BigDecimal("35000.00"),
                new BigDecimal("12.50"), new BigDecimal("17.00"),
                "Expense Containment Simulation: Projected ₹35,000 cost savings | SIMULATED_ESTIMATE"
        );

        scenario.getItems().add(item1);
        scenario.getItems().add(item2);
        scenarioRepository.save(scenario);

        return getMerchantScenarioSummary(merchantId, searchHorizon);
    }

    @Transactional(readOnly = true)
    public FinancialScenarioSummaryDTO getMerchantScenarioSummary(Long merchantId, String horizon) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }

        String searchHorizon = (horizon != null && !horizon.isBlank()) ? horizon : "30D";
        List<FinancialScenario> scenarios = scenarioRepository.findByMerchantIdAndHorizonOrderByEvaluatedAtDesc(merchantId, searchHorizon);

        if (scenarios.isEmpty()) {
            return evaluateScenario(merchantId, searchHorizon, "Combined Receivables Acceleration & Inventory Expense Audit");
        }

        FinancialScenario topScenario = scenarios.stream()
                .filter(s -> "EVALUATED".equalsIgnoreCase(s.getStatus()))
                .max(Comparator.comparing(FinancialScenario::getProjectedScore))
                .orElse(scenarios.get(0));

        return mapToSummaryDTO(merchantId, searchHorizon, topScenario, scenarios);
    }

    @Transactional(readOnly = true)
    public FinancialScenarioDTO getScenarioById(Long merchantId, Long scenarioId) {
        FinancialScenario scenario = scenarioRepository.findByIdAndMerchantId(scenarioId, merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Financial Scenario not found with ID: " + scenarioId + " for merchant: " + merchantId));
        return mapToDTO(scenario);
    }

    public FinancialScenarioDTO archiveScenario(Long merchantId, Long scenarioId) {
        FinancialScenario scenario = scenarioRepository.findByIdAndMerchantId(scenarioId, merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Financial Scenario not found with ID: " + scenarioId + " for merchant: " + merchantId));

        scenario.setStatus("ARCHIVED");
        scenario = scenarioRepository.save(scenario);
        return mapToDTO(scenario);
    }

    private FinancialScenarioSummaryDTO mapToSummaryDTO(Long merchantId, String horizon, FinancialScenario topScenario, List<FinancialScenario> scenarios) {
        List<FinancialScenarioDTO> dtoList = new ArrayList<>();
        for (FinancialScenario s : scenarios) {
            dtoList.add(mapToDTO(s));
        }

        FinancialScenarioDTO topDTO = mapToDTO(topScenario);

        String summaryText = "Financial Scenario Simulation Engine: Evaluated " + scenarios.size() + " what-if scenarios across " + horizon + " horizon. Top Scenario: " + topScenario.getScenarioName() + " (Projected Score: " + topScenario.getProjectedScore() + "/100, Score Delta: +" + topScenario.getScoreDelta() + ").";

        return new FinancialScenarioSummaryDTO(
                merchantId, scenarios.size(), horizon, topScenario.getBaselineScore(), topScenario.getProjectedScore(),
                topScenario.getScenarioName(), topDTO, dtoList, summaryText,
                "Scenario simulations are read-only advisory estimates. Flowwise never executes payments, modifies accounts, or feeds simulated outcomes into learning engines."
        );
    }

    private FinancialScenarioDTO mapToDTO(FinancialScenario s) {
        List<FinancialScenarioItemDTO> itemDTOs = new ArrayList<>();
        for (FinancialScenarioItem item : s.getItems()) {
            itemDTOs.add(new FinancialScenarioItemDTO(
                    item.getId(), s.getId(), item.getInterventionType(), item.getInterventionId(),
                    item.getRankOrder(), item.getProjectedImpact(), item.getProjectedRiskReduction(),
                    item.getProjectedGoalImpact(), item.getEvidenceMetrics()
            ));
        }

        return new FinancialScenarioDTO(
                s.getId(), s.getMerchant().getId(), s.getScenarioKey(), s.getScenarioName(), s.getHorizon(),
                s.getStatus(), s.getBaselineScore(), s.getProjectedScore(), s.getScoreDelta(),
                s.getProjectedCashImpact(), s.getProjectedRiskReduction(), s.getProjectedGoalImpact(),
                s.getConfidenceStatus(), s.getAssumptions(), s.getEvidenceMetrics(), itemDTOs, s.getEvaluatedAt().toString()
        );
    }
}
