package com.flowwise.service;

import com.flowwise.dto.BusinessHealthDTO;
import com.flowwise.dto.CashFlowSummaryDTO;
import com.flowwise.dto.HealthFactorDTO;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.MerchantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BusinessHealthService {

    private final MerchantRepository merchantRepository;
    private final CashFlowService cashFlowService;

    public BusinessHealthService(MerchantRepository merchantRepository, CashFlowService cashFlowService) {
        this.merchantRepository = merchantRepository;
        this.cashFlowService = cashFlowService;
    }

    public BusinessHealthDTO calculateBusinessHealth(Long merchantId) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }

        CashFlowSummaryDTO cashFlow = cashFlowService.getCashFlowSummary(merchantId);

        List<HealthFactorDTO> factorScores = new ArrayList<>();
        List<String> positiveSignals = new ArrayList<>();
        List<String> riskSignals = new ArrayList<>();

        // 1. Liquidity Factor (Max 25)
        BigDecimal runway = cashFlow.getCashRunwayMonths();
        int liquidityScore;
        String liquidityTrend = "STABLE";
        String liquidityExplanation;

        if (runway.compareTo(new BigDecimal("6.0")) >= 0) {
            liquidityScore = 25;
            liquidityTrend = "IMPROVING";
            liquidityExplanation = "Exceptional liquidity cushion covering >6 months of operating burn.";
            positiveSignals.add("Robust cash reserves covering over 6 months of operational burn.");
        } else if (runway.compareTo(new BigDecimal("4.0")) >= 0) {
            liquidityScore = 20;
            liquidityTrend = "STABLE";
            liquidityExplanation = "Healthy cash reserves covering 4-6 months of burn.";
            positiveSignals.add("Stable cash runway of " + runway + " months.");
        } else if (runway.compareTo(new BigDecimal("2.0")) >= 0) {
            liquidityScore = 12;
            liquidityTrend = "STABLE";
            liquidityExplanation = "Moderate cash cushion covering 2-4 months of burn.";
            riskSignals.add("Moderate cash runway cushion (" + runway + " months). Monitor cash burn.");
        } else if (runway.compareTo(new BigDecimal("1.0")) >= 0) {
            liquidityScore = 6;
            liquidityTrend = "DETERIORATING";
            liquidityExplanation = "Tight liquidity reserves under 2 months of operational burn.";
            riskSignals.add("Tight cash runway under 2 months. Immediate liquidity buffer recommended.");
        } else {
            liquidityScore = 0;
            liquidityTrend = "DETERIORATING";
            liquidityExplanation = "Critical liquidity pressure with under 1 month of cash runway.";
            riskSignals.add("CRITICAL: Cash runway is under 1 month based on current monthly burn rate.");
        }
        factorScores.add(new HealthFactorDTO("Liquidity Cushion", liquidityScore, 25, liquidityTrend, liquidityExplanation));

        // 2. Cash Flow Stability Factor (Max 25)
        BigDecimal inflows = cashFlow.getTotalInflows();
        BigDecimal netCash = cashFlow.getNetCashFlow();
        int stabilityScore;
        String stabilityTrend = "STABLE";
        String stabilityExplanation;

        if (inflows.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal netRatio = netCash.divide(inflows, 2, RoundingMode.HALF_UP);
            if (netRatio.compareTo(new BigDecimal("0.20")) >= 0) {
                stabilityScore = 25;
                stabilityTrend = "IMPROVING";
                stabilityExplanation = "Strong cash generation with net surplus exceeding 20% of inflows.";
                positiveSignals.add("High cash margin surplus of " + netRatio.multiply(new BigDecimal("100")).setScale(0, RoundingMode.HALF_UP) + "% of inflows.");
            } else if (netCash.compareTo(BigDecimal.ZERO) > 0) {
                stabilityScore = 18;
                stabilityTrend = "STABLE";
                stabilityExplanation = "Positive net cash flow maintained across ledger periods.";
                positiveSignals.add("Positive net cash flow maintained across active ledger.");
            } else if (netCash.compareTo(BigDecimal.ZERO) == 0) {
                stabilityScore = 10;
                stabilityTrend = "STABLE";
                stabilityExplanation = "Neutral cash flow position (break-even cash generation).";
            } else {
                stabilityScore = 0;
                stabilityTrend = "DETERIORATING";
                stabilityExplanation = "Negative cash flow position (cash deficit across ledger).";
                riskSignals.add("Net cash flow deficit detected across historical ledger.");
            }
        } else {
            stabilityScore = 0;
            stabilityTrend = "DETERIORATING";
            stabilityExplanation = "Insufficient inflow transaction volume to assess cash stability.";
            riskSignals.add("No recorded cash inflows in transaction ledger.");
        }
        factorScores.add(new HealthFactorDTO("Cash Flow Stability", stabilityScore, 25, stabilityTrend, stabilityExplanation));

        // 3. Expense Control Factor (Max 20)
        BigDecimal opInflows = cashFlow.getOperatingInflows();
        BigDecimal opOutflows = cashFlow.getOperatingOutflows();
        int expenseScore;
        String expenseTrend = "STABLE";
        String expenseExplanation;

        if (opInflows.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal expRatio = opOutflows.divide(opInflows, 2, RoundingMode.HALF_UP);
            if (expRatio.compareTo(new BigDecimal("0.60")) <= 0) {
                expenseScore = 20;
                expenseTrend = "IMPROVING";
                expenseExplanation = "Optimized operating expense ratio (<=60% of operating sales).";
                positiveSignals.add("Disciplined expense control with operating cost ratio <=60%.");
            } else if (expRatio.compareTo(new BigDecimal("0.80")) <= 0) {
                expenseScore = 14;
                expenseTrend = "STABLE";
                expenseExplanation = "Standard operating expense ratio (60%-80% of sales).";
            } else if (expRatio.compareTo(new BigDecimal("1.00")) <= 0) {
                expenseScore = 8;
                expenseTrend = "STABLE";
                expenseExplanation = "High operating cost ratio (80%-100% of sales).";
                riskSignals.add("Operating expenses consume over 80% of operating sales revenue.");
            } else {
                expenseScore = 0;
                expenseTrend = "DETERIORATING";
                expenseExplanation = "Operating expenses exceed operating sales revenue.";
                riskSignals.add("Operating costs exceed sales revenue (Operating deficit).");
            }
        } else {
            expenseScore = 10;
            expenseTrend = "STABLE";
            expenseExplanation = "Baseline expense control score applied (insufficient operating sales data).";
        }
        factorScores.add(new HealthFactorDTO("Expense Control", expenseScore, 20, expenseTrend, expenseExplanation));

        // 4. Receivables & Payable Pressure Factor (Max 15)
        BigDecimal payables = cashFlow.getUpcomingPayablePressure();
        int pressureScore;
        String pressureTrend = "STABLE";
        String pressureExplanation;

        if (netCash.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal payRatio = payables.divide(netCash, 2, RoundingMode.HALF_UP);
            if (payRatio.compareTo(new BigDecimal("0.30")) <= 0) {
                pressureScore = 15;
                pressureTrend = "IMPROVING";
                pressureExplanation = "Low payable pressure relative to available net cash surplus.";
                positiveSignals.add("Upcoming payable obligations are well within net cash reserves.");
            } else if (payRatio.compareTo(new BigDecimal("0.60")) <= 0) {
                pressureScore = 10;
                pressureTrend = "STABLE";
                pressureExplanation = "Moderate payable pressure relative to net cash surplus.";
            } else if (payRatio.compareTo(new BigDecimal("1.00")) <= 0) {
                pressureScore = 5;
                pressureTrend = "DETERIORATING";
                pressureExplanation = "Elevated payable commitments consuming most of net cash surplus.";
                riskSignals.add("Pending payable pressure consumes >60% of net cash surplus.");
            } else {
                pressureScore = 0;
                pressureTrend = "DETERIORATING";
                pressureExplanation = "Payable obligations exceed net cash surplus position.";
                riskSignals.add("Upcoming payable obligations exceed current net cash surplus.");
            }
        } else {
            pressureScore = 5;
            pressureTrend = "STABLE";
            pressureExplanation = "Moderate payable pressure rating under neutral/negative cash position.";
        }
        factorScores.add(new HealthFactorDTO("Payable Pressure Control", pressureScore, 15, pressureTrend, pressureExplanation));

        // 5. Cash Runway Duration Factor (Max 15)
        int runwayScore;
        String runwayTrend = "STABLE";
        String runwayExplanation;

        if (runway.compareTo(new BigDecimal("4.0")) >= 0) {
            runwayScore = 15;
            runwayTrend = "IMPROVING";
            runwayExplanation = "Sustained multi-month operating runway duration.";
        } else if (runway.compareTo(new BigDecimal("2.0")) >= 0) {
            runwayScore = 10;
            runwayTrend = "STABLE";
            runwayExplanation = "Standard multi-month operating runway duration.";
        } else if (runway.compareTo(new BigDecimal("1.0")) >= 0) {
            runwayScore = 5;
            runwayTrend = "DETERIORATING";
            runwayExplanation = "Short operating runway duration.";
        } else {
            runwayScore = 0;
            runwayTrend = "DETERIORATING";
            runwayExplanation = "Depleted operating runway duration.";
        }
        factorScores.add(new HealthFactorDTO("Runway Duration", runwayScore, 15, runwayTrend, runwayExplanation));

        // Aggregate Overall Score (Bounded 0 - 100)
        int totalScore = liquidityScore + stabilityScore + expenseScore + pressureScore + runwayScore;
        totalScore = Math.min(100, Math.max(0, totalScore));

        String healthStatus;
        if (totalScore >= 75) {
            healthStatus = "HEALTHY";
        } else if (totalScore >= 50) {
            healthStatus = "WATCH";
        } else {
            healthStatus = "AT_RISK";
        }

        String summaryExplanation = "Deterministic Flowwise Business Health Score evaluated from 5 core financial factors: Liquidity Cushion (" 
                + liquidityScore + "/25), Cash Flow Stability (" + stabilityScore + "/25), Expense Control (" 
                + expenseScore + "/20), Payable Pressure (" + pressureScore + "/15), and Runway Duration (" + runwayScore + "/15).";

        return new BusinessHealthDTO(
                totalScore,
                healthStatus,
                factorScores,
                positiveSignals,
                riskSignals,
                summaryExplanation
        );
    }
}
