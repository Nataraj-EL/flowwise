package com.flowwise.service;

import com.flowwise.dto.*;
import com.flowwise.entity.FinancialDecisionAnalysis;
import com.flowwise.entity.FinancialDecisionOption;
import com.flowwise.entity.Merchant;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.FinancialDecisionAnalysisRepository;
import com.flowwise.repository.MerchantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.*;

@Service
@Transactional
public class FinancialDecisionIntelligenceService {

    private final MerchantRepository merchantRepository;
    private final FinancialDecisionAnalysisRepository analysisRepository;
    private final CashFlowService cashFlowService;
    private final ReceivablesService receivablesService;
    private final PayablesService payablesService;
    private final FinancialGoalService goalService;

    public FinancialDecisionIntelligenceService(MerchantRepository merchantRepository,
                                                FinancialDecisionAnalysisRepository analysisRepository,
                                                CashFlowService cashFlowService,
                                                ReceivablesService receivablesService,
                                                PayablesService payablesService,
                                                FinancialGoalService goalService) {
        this.merchantRepository = merchantRepository;
        this.analysisRepository = analysisRepository;
        this.cashFlowService = cashFlowService;
        this.receivablesService = receivablesService;
        this.payablesService = payablesService;
        this.goalService = goalService;
    }

    public DecisionAnalysisDTO evaluateDecisionIntelligence(Long merchantId) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found with ID: " + merchantId));

        CashFlowSummaryDTO cashFlow = cashFlowService.getCashFlowSummary(merchantId);
        ReceivablesSummaryDTO receivables = receivablesService.getReceivablesSummary(merchantId);
        PayablesSummaryDTO payables = payablesService.getPayablesSummary(merchantId);
        List<FinancialGoalDTO> goals = goalService.getMerchantGoals(merchantId);

        BigDecimal availableCash = (cashFlow.getOperatingInflows() != null && cashFlow.getOperatingInflows().compareTo(BigDecimal.ZERO) > 0)
                ? cashFlow.getOperatingInflows() : new BigDecimal("485000");

        String dataQualityStatus = "SUFFICIENT";
        if (cashFlow.getTotalInflows() == null || cashFlow.getTotalInflows().compareTo(BigDecimal.ZERO) <= 0) {
            dataQualityStatus = "INSUFFICIENT_DATA";
        }

        String fingerprint = "fp_m" + merchantId + "_c" + availableCash.intValue() + "_r" + (receivables.getTotalOutstanding() != null ? receivables.getTotalOutstanding().intValue() : 0);

        // Build Option Candidates
        List<DecisionOptionDTO> options = new ArrayList<>();

        // Option 1: COLLECT_RECEIVABLES
        BigDecimal recOverdue = receivables.getTotalOverdue() != null ? receivables.getTotalOverdue() : new BigDecimal("165000");
        BigDecimal collectCash7d = availableCash.add(recOverdue.multiply(new BigDecimal("0.50")));
        BigDecimal collectCash30d = availableCash.add(recOverdue.multiply(new BigDecimal("0.80")));
        BigDecimal collectCash90d = availableCash.add(recOverdue);
        options.add(buildOption("COLLECT_RECEIVABLES", "Accelerate Distributor Receivable Collection",
                "Execute targeted follow-ups on ₹" + recOverdue + " overdue distributor receivables to boost immediate liquid reserves.",
                new BigDecimal("90.00"), new BigDecimal("85.00"), new BigDecimal("95.00"), new BigDecimal("80.00"), new BigDecimal("80.00"),
                collectCash7d, collectCash30d, collectCash90d, "FEASIBLE", "POSITIVE",
                "Assumes 80% collection rate on overdue distributor invoices.", "Overdue: ₹" + recOverdue + " | Cash Boost: +80%"));

        // Option 2: PAY_NOW
        BigDecimal pay7d = payables.getDue7Days() != null ? payables.getDue7Days() : new BigDecimal("95000");
        BigDecimal payNowCash7d = availableCash.subtract(pay7d).max(BigDecimal.ZERO);
        BigDecimal payNowCash30d = payNowCash7d.add(new BigDecimal("125000"));
        BigDecimal payNowCash90d = payNowCash30d.add(new BigDecimal("270000"));
        options.add(buildOption("PAY_NOW", "Settle Upcoming 7-Day Vendor Payables In Full",
                "Pay ₹" + pay7d + " mandatory vendor bills immediately to capture prompt settlement terms.",
                new BigDecimal("70.00"), new BigDecimal("95.00"), new BigDecimal("75.00"), new BigDecimal("85.00"), new BigDecimal("70.00"),
                payNowCash7d, payNowCash30d, payNowCash90d, "FEASIBLE", "NEUTRAL",
                "Deducts ₹" + pay7d + " immediately from liquid reserves.", "Payables Due: ₹" + pay7d + " | Immediate Settlement"));

        // Option 3: BUILD_RESERVE
        BigDecimal reserveMonthly = new BigDecimal("25000");
        BigDecimal resCash7d = availableCash;
        BigDecimal resCash30d = availableCash.add(reserveMonthly);
        BigDecimal resCash90d = availableCash.add(reserveMonthly.multiply(new BigDecimal("3")));
        options.add(buildOption("BUILD_RESERVE", "Accumulate Emergency Working Capital Reserve",
                "Ringfence 20% of net monthly cash flow (₹25,000/mo) into dedicated cash reserve account.",
                new BigDecimal("85.00"), new BigDecimal("70.00"), new BigDecimal("80.00"), new BigDecimal("75.00"), new BigDecimal("50.00"),
                resCash7d, resCash30d, resCash90d, "FEASIBLE", "POSITIVE",
                "Ringfences ₹25,000 monthly from operating surplus.", "Monthly Reserve: ₹25,000 | 90D Reserve Total: ₹75,000"));

        // Option 4: DEFER
        BigDecimal deferCash7d = availableCash.add(new BigDecimal("45000"));
        BigDecimal deferCash30d = availableCash.add(new BigDecimal("20000"));
        BigDecimal deferCash90d = availableCash;
        options.add(buildOption("DEFER", "Defer Non-Critical Payables By 14 Days",
                "Request 14-day vendor extension on ₹45,000 non-essential inventory payables.",
                new BigDecimal("60.00"), new BigDecimal("50.00"), new BigDecimal("55.00"), new BigDecimal("45.00"), new BigDecimal("80.00"),
                deferCash7d, deferCash30d, deferCash90d, "CAUTION", "NEGATIVE",
                "Preserves near-term cash but increases 30-day vendor liability.", "Deferred Bills: ₹45,000 | Vendor Terms Warning"));

        // Option 5: REDUCE_EXPENSE
        BigDecimal expRedCash7d = availableCash;
        BigDecimal expRedCash30d = availableCash.add(new BigDecimal("15000"));
        BigDecimal expRedCash90d = availableCash.add(new BigDecimal("45000"));
        options.add(buildOption("REDUCE_EXPENSE", "Trim Non-Essential Operating Overhead",
                "Cut 10% non-essential discretionary expenses to reduce baseline monthly burn rate.",
                new BigDecimal("75.00"), new BigDecimal("70.00"), new BigDecimal("70.00"), new BigDecimal("70.00"), new BigDecimal("60.00"),
                expRedCash7d, expRedCash30d, expRedCash90d, "FEASIBLE", "POSITIVE",
                "Trims ₹15,000 monthly discretionary operating expenses.", "Monthly Expense Savings: ₹15,000"));

        // Sort Options by Deterministic Tie-Breaker
        options.sort((o1, o2) -> {
            int cmpScore = o2.getCompositeScore().compareTo(o1.getCompositeScore());
            if (cmpScore != 0) return cmpScore;
            int cmpRisk = o2.getRiskScore().compareTo(o1.getRiskScore());
            if (cmpRisk != 0) return cmpRisk;
            int cmpLiq = o2.getLiquidityScore().compareTo(o1.getLiquidityScore());
            if (cmpLiq != 0) return cmpLiq;
            int cmpGoal = o2.getGoalScore().compareTo(o1.getGoalScore());
            if (cmpGoal != 0) return cmpGoal;
            int cmpUrg = o2.getUrgencyScore().compareTo(o1.getUrgencyScore());
            if (cmpUrg != 0) return cmpUrg;
            return o1.getOptionKey().compareTo(o2.getOptionKey());
        });

        // Assign Rank Order
        for (int i = 0; i < options.size(); i++) {
            options.get(i).setRankOrder(i + 1);
        }

        DecisionOptionDTO topOption = options.get(0);

        String summaryAdvice = "Prioritizing '" + topOption.getTitle() + "' (Composite Score: " + topOption.getCompositeScore() 
                + "/100) provides optimal cash preservation and risk mitigation without incurring vendor penalties.";

        // Persist / Update Idempotently
        Optional<FinancialDecisionAnalysis> existing = analysisRepository.findByMerchantIdAndAnalysisKey(merchantId, "CURRENT_OPERATING_DECISION");

        FinancialDecisionAnalysis analysis = existing.orElseGet(FinancialDecisionAnalysis::new);
        analysis.setMerchant(merchant);
        analysis.setAnalysisKey("CURRENT_OPERATING_DECISION");
        analysis.setTitle("Quarterly Liquidity Optimization Options");
        analysis.setRecommendedOption(topOption.getOptionKey());
        analysis.setBaselineScore(topOption.getCompositeScore());
        analysis.setDataQualityStatus(dataQualityStatus);
        analysis.setInputFingerprint(fingerprint);
        analysis.setSummaryExplanation(summaryAdvice);
        analysis.setEvaluatedAt(Instant.now());

        // Rebuild Options Entity collection
        analysis.getOptions().clear();
        for (DecisionOptionDTO optDTO : options) {
            FinancialDecisionOption optEntity = new FinancialDecisionOption(
                    analysis,
                    optDTO.getOptionKey(),
                    optDTO.getTitle(),
                    optDTO.getDescription(),
                    optDTO.getCompositeScore(),
                    optDTO.getLiquidityScore(),
                    optDTO.getCoverageScore(),
                    optDTO.getGoalScore(),
                    optDTO.getRiskScore(),
                    optDTO.getUrgencyScore(),
                    optDTO.getProjected7dCash(),
                    optDTO.getProjected30dCash(),
                    optDTO.getProjected90dCash(),
                    optDTO.getRiskStatus(),
                    optDTO.getGoalImpactStatus(),
                    optDTO.getAssumptions(),
                    optDTO.getEvidenceMetrics(),
                    optDTO.getRankOrder()
            );
            analysis.getOptions().add(optEntity);
        }

        FinancialDecisionAnalysis saved = analysisRepository.save(analysis);

        return mapToDTO(saved);
    }

    @Transactional(readOnly = true)
    public DecisionAnalysisDTO getMerchantDecisionAnalysis(Long merchantId) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }
        return evaluateDecisionIntelligence(merchantId);
    }

    private DecisionOptionDTO buildOption(String key, String title, String desc,
                                          BigDecimal liqScore, BigDecimal covScore, BigDecimal goalScore,
                                          BigDecimal riskScore, BigDecimal urgScore,
                                          BigDecimal p7d, BigDecimal p30d, BigDecimal p90d,
                                          String riskStatus, String goalImpact, String assumptions, String metrics) {

        // Composite Score = (liq * 0.25) + (cov * 0.20) + (goal * 0.25) + (risk * 0.15) + (urg * 0.15)
        BigDecimal comp = liqScore.multiply(new BigDecimal("0.25"))
                .add(covScore.multiply(new BigDecimal("0.20")))
                .add(goalScore.multiply(new BigDecimal("0.25")))
                .add(riskScore.multiply(new BigDecimal("0.15")))
                .add(urgScore.multiply(new BigDecimal("0.15")))
                .setScale(2, RoundingMode.HALF_UP);

        return new DecisionOptionDTO(
                null, key, title, desc, comp, liqScore, covScore, goalScore, riskScore, urgScore,
                p7d, p30d, p90d, riskStatus, goalImpact, assumptions, metrics, 1, true
        );
    }

    private DecisionAnalysisDTO mapToDTO(FinancialDecisionAnalysis a) {
        List<DecisionOptionDTO> optionDTOs = new ArrayList<>();
        for (FinancialDecisionOption o : a.getOptions()) {
            optionDTOs.add(new DecisionOptionDTO(
                    o.getId(),
                    o.getOptionKey(),
                    o.getTitle(),
                    o.getDescription(),
                    o.getCompositeScore(),
                    o.getLiquidityScore(),
                    o.getCoverageScore(),
                    o.getGoalScore(),
                    o.getRiskScore(),
                    o.getUrgencyScore(),
                    o.getProjected7dCash(),
                    o.getProjected30dCash(),
                    o.getProjected90dCash(),
                    o.getRiskStatus(),
                    o.getGoalImpactStatus(),
                    o.getAssumptions(),
                    o.getEvidenceMetrics(),
                    o.getRankOrder(),
                    true
            ));
        }

        return new DecisionAnalysisDTO(
                a.getId(),
                a.getMerchant().getId(),
                a.getAnalysisKey(),
                a.getTitle(),
                a.getRecommendedOption(),
                a.getBaselineScore(),
                a.getDataQualityStatus(),
                a.getInputFingerprint(),
                a.getSummaryExplanation(),
                a.getEvaluatedAt().toString(),
                optionDTOs,
                "Decision analysis is read-only and advisory. Evaluating options does not move funds or modify accounts."
        );
    }
}
