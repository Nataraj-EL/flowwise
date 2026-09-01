package com.flowwise.service;

import com.flowwise.dto.*;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.MerchantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ForecastingService {

    private final MerchantRepository merchantRepository;
    private final MerchantService merchantService;
    private final CashFlowService cashFlowService;

    public ForecastingService(MerchantRepository merchantRepository,
                              MerchantService merchantService,
                              CashFlowService cashFlowService) {
        this.merchantRepository = merchantRepository;
        this.merchantService = merchantService;
        this.cashFlowService = cashFlowService;
    }

    public ForecastSummaryDTO getForecastSummary(Long merchantId) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }

        MerchantDetailDTO merchantDetail = merchantService.getMerchantDetail(merchantId);
        CashFlowSummaryDTO cashFlow = cashFlowService.getCashFlowSummary(merchantId);

        BigDecimal availableCash = merchantDetail.getTotalAvailableCash();
        BigDecimal burnRate = cashFlow.getBurnRate(); // Monthly average outflow
        
        // Approximate monthly inflow from historical average (Total Inflows / months)
        BigDecimal totalInflows = cashFlow.getTotalInflows();
        BigDecimal monthlyInflow = totalInflows.compareTo(BigDecimal.ZERO) > 0 ? totalInflows : BigDecimal.ZERO;

        List<PeriodProjectionDTO> projections = new ArrayList<>();
        int[] daysList = {30, 60, 90};

        for (int days : daysList) {
            BigDecimal factor = BigDecimal.valueOf(days).divide(new BigDecimal("30"), 2, RoundingMode.HALF_UP);
            BigDecimal projectedInflow = monthlyInflow.multiply(factor).setScale(2, RoundingMode.HALF_UP);
            BigDecimal projectedOutflow = burnRate.multiply(factor).setScale(2, RoundingMode.HALF_UP);
            BigDecimal projectedEndingCash = availableCash.add(projectedInflow).subtract(projectedOutflow);

            BigDecimal projectedRunway;
            if (burnRate.compareTo(BigDecimal.ZERO) <= 0 || projectedEndingCash.compareTo(BigDecimal.ZERO) <= 0) {
                projectedRunway = BigDecimal.ZERO.setScale(1, RoundingMode.HALF_UP);
            } else {
                projectedRunway = projectedEndingCash.divide(burnRate, 1, RoundingMode.HALF_UP);
            }

            projections.add(new PeriodProjectionDTO(days, projectedInflow, projectedOutflow, projectedEndingCash, projectedRunway));
        }

        List<String> assumptions = Arrays.asList(
                "Linear extrapolation based on historical transaction ledger burn rate (₹" + burnRate + "/month).",
                "Projections assume no unmodeled capital injections or macro revenue shocks over 90 days.",
                "Ending cash balance includes reserved operating capital across connected accounts."
        );

        return new ForecastSummaryDTO(
                availableCash,
                monthlyInflow,
                burnRate,
                projections,
                assumptions,
                true // Explicitly marked as ESTIMATE
        );
    }

    public ScenarioResultDTO simulateScenario(Long merchantId, ScenarioRequestDTO requestDTO) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }

        BigDecimal amount = (requestDTO != null && requestDTO.getAmount() != null) ? requestDTO.getAmount() : new BigDecimal("80000");
        String category = (requestDTO != null && requestDTO.getCategory() != null) ? requestDTO.getCategory() : "INVENTORY";

        MerchantDetailDTO merchantDetail = merchantService.getMerchantDetail(merchantId);
        CashFlowSummaryDTO cashFlow = cashFlowService.getCashFlowSummary(merchantId);

        BigDecimal availableCash = merchantDetail.getTotalAvailableCash();
        BigDecimal burnRate = cashFlow.getBurnRate();
        BigDecimal baselineRunway = cashFlow.getCashRunwayMonths();

        BigDecimal scenarioEndingCash = availableCash.subtract(amount);

        BigDecimal scenarioRunway;
        if (burnRate.compareTo(BigDecimal.ZERO) <= 0 || scenarioEndingCash.compareTo(BigDecimal.ZERO) <= 0) {
            scenarioRunway = BigDecimal.ZERO.setScale(1, RoundingMode.HALF_UP);
        } else {
            scenarioRunway = scenarioEndingCash.divide(burnRate, 1, RoundingMode.HALF_UP);
        }

        BigDecimal cashImpact = amount.negate();
        BigDecimal runwayImpact = scenarioRunway.subtract(baselineRunway);

        String riskStatus;
        if (scenarioRunway.compareTo(new BigDecimal("3.0")) >= 0) {
            riskStatus = "FEASIBLE";
        } else if (scenarioRunway.compareTo(new BigDecimal("1.5")) >= 0) {
            riskStatus = "CAUTION";
        } else {
            riskStatus = "HIGH_RISK";
        }

        List<String> assumptions = Arrays.asList(
                "Simulates an immediate one-time outlay of ₹" + amount + " for " + category + ".",
                "Models immediate cash deduction from total available liquidity (₹" + availableCash + ").",
                "Assumes zero immediate cash inflow returns within current 7-day settlement window."
        );

        return new ScenarioResultDTO(
                amount,
                category,
                availableCash,
                scenarioEndingCash,
                baselineRunway,
                scenarioRunway,
                cashImpact,
                runwayImpact,
                riskStatus,
                assumptions,
                true // Explicitly marked as ESTIMATE
        );
    }
}
