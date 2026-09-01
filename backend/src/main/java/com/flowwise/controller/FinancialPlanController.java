package com.flowwise.controller;

import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.FinancialPlanDTO;
import com.flowwise.dto.FinancialPlanSummaryDTO;
import com.flowwise.service.FinancialPlanSynthesisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchants/{merchantId}")
public class FinancialPlanController {

    private final FinancialPlanSynthesisService planSynthesisService;

    public FinancialPlanController(FinancialPlanSynthesisService planSynthesisService) {
        this.planSynthesisService = planSynthesisService;
    }

    @GetMapping("/financial-plans")
    public ResponseEntity<ApiResponse<FinancialPlanSummaryDTO>> getFinancialPlans(
            @PathVariable Long merchantId,
            @RequestParam(required = false, defaultValue = "30D") String horizon) {
        FinancialPlanSummaryDTO summary = planSynthesisService.getMerchantPlanSummary(merchantId, horizon);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @GetMapping("/financial-plans/{planId}")
    public ResponseEntity<ApiResponse<FinancialPlanDTO>> getFinancialPlanById(
            @PathVariable Long merchantId,
            @PathVariable Long planId) {
        FinancialPlanDTO dto = planSynthesisService.getPlanById(merchantId, planId);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }

    @PostMapping("/financial-plans/evaluate")
    public ResponseEntity<ApiResponse<FinancialPlanSummaryDTO>> evaluateFinancialPlan(
            @PathVariable Long merchantId,
            @RequestParam(required = false, defaultValue = "30D") String horizon) {
        FinancialPlanSummaryDTO summary = planSynthesisService.evaluateFinancialPlan(merchantId, horizon);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @PostMapping("/financial-plans/{planId}/activate")
    public ResponseEntity<ApiResponse<FinancialPlanDTO>> activateFinancialPlan(
            @PathVariable Long merchantId,
            @PathVariable Long planId) {
        FinancialPlanDTO dto = planSynthesisService.activatePlan(merchantId, planId);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }

    @PostMapping("/financial-plans/{planId}/archive")
    public ResponseEntity<ApiResponse<FinancialPlanDTO>> archiveFinancialPlan(
            @PathVariable Long merchantId,
            @PathVariable Long planId) {
        FinancialPlanDTO dto = planSynthesisService.archivePlan(merchantId, planId);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }
}
