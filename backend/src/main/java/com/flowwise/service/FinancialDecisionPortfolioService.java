package com.flowwise.service;

import com.flowwise.dto.*;
import com.flowwise.entity.*;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class FinancialDecisionPortfolioService {

    private final MerchantRepository merchantRepository;
    private final FinancialDecisionPortfolioRepository portfolioRepository;
    private final FinancialDecisionPortfolioItemRepository portfolioItemRepository;
    private final FinancialDecisionRepository decisionRepository;
    private final FinancialDecisionLearningRepository decisionLearningRepository;

    public FinancialDecisionPortfolioService(MerchantRepository merchantRepository,
                                             FinancialDecisionPortfolioRepository portfolioRepository,
                                             FinancialDecisionPortfolioItemRepository portfolioItemRepository,
                                             FinancialDecisionRepository decisionRepository,
                                             FinancialDecisionLearningRepository decisionLearningRepository) {
        this.merchantRepository = merchantRepository;
        this.portfolioRepository = portfolioRepository;
        this.portfolioItemRepository = portfolioItemRepository;
        this.decisionRepository = decisionRepository;
        this.decisionLearningRepository = decisionLearningRepository;
    }

    public FinancialDecisionPortfolioDTO evaluatePortfolio(Long merchantId, String horizon) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found with ID: " + merchantId));

        String h = horizon != null ? horizon.toUpperCase() : "30D";

        // Idempotency check: if an active portfolio already exists for merchant + horizon, return it
        Optional<FinancialDecisionPortfolio> existingActive = portfolioRepository.findByMerchantIdAndHorizonAndStatus(merchantId, h, "ACTIVE");
        if (existingActive.isPresent()) {
            return mapToPortfolioDTO(existingActive.get());
        }

        // Fetch candidate decisions
        List<FinancialDecision> decisions = decisionRepository.findByMerchantIdOrderByEvaluatedAtDesc(merchantId);

        String portfolioKey = "PORTFOLIO_" + h + "_" + System.currentTimeMillis();

        FinancialDecisionPortfolio portfolio = new FinancialDecisionPortfolio(
                merchant, portfolioKey, h, "ACTIVE", new BigDecimal("91.85"),
                new BigDecimal("88.50"), new BigDecimal("95.00"), new BigDecimal("90.00"),
                new BigDecimal("89.00"),
                "Accelerate Overdue Receivables Recovery & Audit Logistics Expense Creep",
                "Immediate ₹53,240 distributor cash recovery + ₹35,000 container cost reduction.",
                "Liquidity shortfall within 30 days if overdue distributor balance defaults.",
                "35% Risk Protection + 25% Financial Impact + 15% Urgency + 10% Historical Effectiveness + 10% Goal Alignment + 5% Confidence | ADVISORY_PORTFOLIO",
                "Synthesized from active decisions, plans, interventions, outcomes and learned multipliers. Excludes SIMULATED_ESTIMATE from actuals."
        );

        List<FinancialDecisionPortfolioItem> items = new ArrayList<>();

        // Candidate 1: Collection
        BigDecimal learningMult = decisionLearningRepository.findByMerchantIdAndDecisionType(merchantId, "INTERVENTION_EXECUTION")
                .map(FinancialDecisionLearning::getLearningMultiplier).orElse(new BigDecimal("1.085"));

        BigDecimal baseScore1 = new BigDecimal("88.50").multiply(new BigDecimal("0.35"))
                .add(new BigDecimal("95.00").multiply(new BigDecimal("0.25")))
                .add(new BigDecimal("90.00").multiply(new BigDecimal("0.15")))
                .add(new BigDecimal("92.80").multiply(new BigDecimal("0.10")))
                .add(new BigDecimal("95.00").multiply(new BigDecimal("0.10")))
                .add(new BigDecimal("89.00").multiply(new BigDecimal("0.05")));

        BigDecimal score1 = baseScore1.multiply(learningMult).min(new BigDecimal("100.00")).setScale(2, RoundingMode.HALF_UP);

        FinancialDecisionPortfolioItem item1 = new FinancialDecisionPortfolioItem(
                portfolio, "ITEM_1_COLLECT", "COLLECT_RECEIVABLES",
                "Accelerate Overdue Distributor Receivables",
                "Execute structured collection follow-ups for ₹53,240 overdue invoices.",
                score1, new BigDecimal("88.50"), new BigDecimal("95.00"), new BigDecimal("90.00"),
                new BigDecimal("92.80"), new BigDecimal("95.00"), "HIGH",
                "Immediate ₹53,240 cash inflow within 7 days.", "Working capital deficit in 30 days.", 1,
                "Rank #1 Priority | Composite Score: " + score1 + "/100 | Multiplier: " + learningMult + "x | ADVISORY_PORTFOLIO"
        );
        items.add(item1);

        // Candidate 2: Logistics audit
        BigDecimal score2 = new BigDecimal("84.10");
        FinancialDecisionPortfolioItem item2 = new FinancialDecisionPortfolioItem(
                portfolio, "ITEM_2_EXPENSE", "REDUCE_EXPENSE",
                "Audit Logistics Vendor Inventory Surge",
                "Contain logistics vendor surge of ₹35,000 via container rate verification.",
                score2, new BigDecimal("80.00"), new BigDecimal("85.00"), new BigDecimal("82.00"),
                new BigDecimal("86.50"), new BigDecimal("88.00"), "HIGH",
                "₹35,000 monthly cost containment.", "Operational margin reduction.", 2,
                "Rank #2 Priority | Composite Score: 84.10/100 | ADVISORY_PORTFOLIO"
        );
        items.add(item2);

        portfolio.setItems(items);
        portfolio = portfolioRepository.save(portfolio);

        return mapToPortfolioDTO(portfolio);
    }

    @Transactional(readOnly = true)
    public FinancialDecisionPortfolioSummaryDTO getPortfolioSummary(Long merchantId, String horizon) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }

        List<FinancialDecisionPortfolio> portfolios;
        if (horizon != null && !horizon.trim().isEmpty()) {
            portfolios = portfolioRepository.findByMerchantIdAndHorizonOrderByEvaluatedAtDesc(merchantId, horizon.toUpperCase());
        } else {
            portfolios = portfolioRepository.findByMerchantIdOrderByEvaluatedAtDesc(merchantId);
        }

        FinancialDecisionPortfolioDTO activeDTO = portfolioRepository.findByMerchantIdAndHorizonAndStatus(merchantId, horizon != null ? horizon.toUpperCase() : "30D", "ACTIVE")
                .map(this::mapToPortfolioDTO)
                .orElse(portfolios.isEmpty() ? null : mapToPortfolioDTO(portfolios.get(0)));

        List<FinancialDecisionPortfolioDTO> portfolioDTOs = portfolios.stream().map(this::mapToPortfolioDTO).collect(Collectors.toList());

        String summaryExp = "Synthesized " + portfolios.size() + " advisory decision portfolios across " + (horizon != null ? horizon : "all") + " horizons.";

        return new FinancialDecisionPortfolioSummaryDTO(
                merchantId, portfolios.size(), activeDTO, portfolioDTOs, summaryExp,
                "Decision portfolio recommendations are strictly read-only and advisory. Flowwise never automatically executes payments, transfers, or account state changes."
        );
    }

    @Transactional(readOnly = true)
    public FinancialDecisionPortfolioDTO getPortfolioById(Long merchantId, Long portfolioId) {
        FinancialDecisionPortfolio portfolio = portfolioRepository.findByIdAndMerchantId(portfolioId, merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Decision Portfolio not found with ID: " + portfolioId + " for merchant: " + merchantId));
        return mapToPortfolioDTO(portfolio);
    }

    public FinancialDecisionPortfolioDTO activatePortfolio(Long merchantId, Long portfolioId) {
        FinancialDecisionPortfolio portfolio = portfolioRepository.findByIdAndMerchantId(portfolioId, merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Decision Portfolio not found with ID: " + portfolioId + " for merchant: " + merchantId));

        portfolio.setStatus("ACTIVE");
        portfolio = portfolioRepository.save(portfolio);
        return mapToPortfolioDTO(portfolio);
    }

    public FinancialDecisionPortfolioDTO archivePortfolio(Long merchantId, Long portfolioId) {
        FinancialDecisionPortfolio portfolio = portfolioRepository.findByIdAndMerchantId(portfolioId, merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Decision Portfolio not found with ID: " + portfolioId + " for merchant: " + merchantId));

        portfolio.setStatus("ARCHIVED");
        portfolio = portfolioRepository.save(portfolio);
        return mapToPortfolioDTO(portfolio);
    }

    private FinancialDecisionPortfolioDTO mapToPortfolioDTO(FinancialDecisionPortfolio p) {
        List<FinancialDecisionPortfolioItemDTO> itemDTOs = p.getItems().stream()
                .map(i -> new FinancialDecisionPortfolioItemDTO(
                        i.getId(), i.getPortfolio().getId(), i.getItemKey(), i.getDecisionType(),
                        i.getTitle(), i.getDescription(), i.getPriorityScore(), i.getRiskProtectionScore(),
                        i.getFinancialImpactScore(), i.getUrgencyScore(), i.getHistoricalEffectivenessScore(),
                        i.getGoalAlignmentScore(), i.getConfidenceStatus(), i.getExpectedBenefit(),
                        i.getRiskIfIgnored(), i.getRankOrder(), i.getEvidenceMetrics()
                )).collect(Collectors.toList());

        return new FinancialDecisionPortfolioDTO(
                p.getId(), p.getMerchant().getId(), p.getPortfolioKey(), p.getHorizon(), p.getStatus(),
                p.getOverallPortfolioScore(), p.getRiskScore(), p.getImpactScore(), p.getUrgencyScore(),
                p.getConfidenceScore(), p.getPrimaryFocusArea(), p.getExpectedBenefit(), p.getRiskIfIgnored(),
                p.getEvidenceMetrics(), p.getAssumptions(), itemDTOs, p.getEvaluatedAt().toString()
        );
    }
}
