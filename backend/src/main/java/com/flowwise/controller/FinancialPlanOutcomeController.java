package com.flowwise.controller;

import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.FinancialPlanOutcomeDTO;
import com.flowwise.dto.FinancialPlanOutcomeSummaryDTO;
import com.flowwise.dto.PlanOptimizationDTO;
import com.flowwise.service.FinancialPlanOutcomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/merchants/{merchantId}")
public class FinancialPlanOutcomeController {

    private final FinancialPlanOutcomeService outcomeService;

    public FinancialPlanOutcomeController(FinancialPlanOutcomeService outcomeService) {
        this.outcomeService = outcomeService;
    }

    @GetMapping("/financial-plan-outcomes")
    public ResponseEntity<ApiResponse<FinancialPlanOutcomeSummaryDTO>> getMerchantOutcomeSummary(
            @PathVariable Long merchantId,
            @RequestParam(required = false, defaultValue = "30D") String horizon) {
        FinancialPlanOutcomeSummaryDTO summary = outcomeService.getMerchantOutcomeSummary(merchantId, horizon);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @GetMapping("/financial-plan-outcomes/summary")
    public ResponseEntity<ApiResponse<FinancialPlanOutcomeSummaryDTO>> getMerchantOutcomeSummaryAlias(
            @PathVariable Long merchantId,
            @RequestParam(required = false, defaultValue = "30D") String horizon) {
        FinancialPlanOutcomeSummaryDTO summary = outcomeService.getMerchantOutcomeSummary(merchantId, horizon);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @GetMapping("/financial-plan-optimization")
    public ResponseEntity<ApiResponse<List<PlanOptimizationDTO>>> getMerchantOptimizationFactors(
            @PathVariable Long merchantId) {
        List<PlanOptimizationDTO> factors = outcomeService.getMerchantOptimizationFactors(merchantId);
        return ResponseEntity.ok(ApiResponse.success(factors));
    }

    @PostMapping("/financial-plans/{planId}/outcome/evaluate")
    public ResponseEntity<ApiResponse<FinancialPlanOutcomeDTO>> evaluatePlanOutcome(
            @PathVariable Long merchantId,
            @PathVariable Long planId,
            @RequestParam(required = false, defaultValue = "30D") String window) {
        FinancialPlanOutcomeDTO outcome = outcomeService.evaluatePlanOutcome(merchantId, planId, window);
        return ResponseEntity.ok(ApiResponse.success(outcome));
    }
}
