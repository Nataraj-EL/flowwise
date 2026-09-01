package com.flowwise.controller;

import com.flowwise.dto.*;
import com.flowwise.service.AdvisoryActionPlanningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class AdvisoryActionPlanController {

    private final AdvisoryActionPlanningService actionPlanningService;

    public AdvisoryActionPlanController(AdvisoryActionPlanningService actionPlanningService) {
        this.actionPlanningService = actionPlanningService;
    }

    @GetMapping("/merchants/{merchantId}/advisory-action-plans")
    public ResponseEntity<ApiResponse<AdvisoryActionPlanSummaryDTO>> getActionPlans(
            @PathVariable Long merchantId,
            @RequestParam(required = false) String horizon) {
        AdvisoryActionPlanSummaryDTO summary = actionPlanningService.getPlanSummary(merchantId, horizon);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @GetMapping("/merchants/{merchantId}/advisory-action-plans/{planId}")
    public ResponseEntity<ApiResponse<AdvisoryActionPlanDTO>> getPlanById(
            @PathVariable Long merchantId,
            @PathVariable Long planId) {
        AdvisoryActionPlanDTO plan = actionPlanningService.getPlanById(merchantId, planId);
        return ResponseEntity.ok(ApiResponse.success(plan));
    }

    @PostMapping("/merchants/{merchantId}/advisory-action-plans/evaluate")
    public ResponseEntity<ApiResponse<AdvisoryActionPlanDTO>> evaluateActionPlan(
            @PathVariable Long merchantId,
            @RequestParam(required = false, defaultValue = "30D") String horizon) {
        AdvisoryActionPlanDTO plan = actionPlanningService.evaluateActionPlan(merchantId, horizon);
        return ResponseEntity.ok(ApiResponse.success(plan));
    }

    @PostMapping("/merchants/{merchantId}/advisory-action-plans/{planId}/activate")
    public ResponseEntity<ApiResponse<AdvisoryActionPlanDTO>> activatePlan(
            @PathVariable Long merchantId,
            @PathVariable Long planId) {
        AdvisoryActionPlanDTO plan = actionPlanningService.activatePlan(merchantId, planId);
        return ResponseEntity.ok(ApiResponse.success(plan));
    }

    @PostMapping("/merchants/{merchantId}/advisory-action-plans/{planId}/archive")
    public ResponseEntity<ApiResponse<AdvisoryActionPlanDTO>> archivePlan(
            @PathVariable Long merchantId,
            @PathVariable Long planId) {
        AdvisoryActionPlanDTO plan = actionPlanningService.archivePlan(merchantId, planId);
        return ResponseEntity.ok(ApiResponse.success(plan));
    }
}
