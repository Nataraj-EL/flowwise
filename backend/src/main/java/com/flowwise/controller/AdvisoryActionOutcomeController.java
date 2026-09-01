package com.flowwise.controller;

import com.flowwise.dto.*;
import com.flowwise.service.AdvisoryActionOutcomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class AdvisoryActionOutcomeController {

    private final AdvisoryActionOutcomeService outcomeService;

    public AdvisoryActionOutcomeController(AdvisoryActionOutcomeService outcomeService) {
        this.outcomeService = outcomeService;
    }

    @GetMapping("/merchants/{merchantId}/advisory-action-outcomes")
    public ResponseEntity<ApiResponse<List<AdvisoryActionOutcomeDTO>>> getActionOutcomes(
            @PathVariable Long merchantId,
            @RequestParam(required = false) String window) {
        AdvisoryActionOutcomeSummaryDTO summary = outcomeService.getOutcomeSummary(merchantId, window);
        return ResponseEntity.ok(ApiResponse.success(summary.getOutcomes()));
    }

    @GetMapping("/merchants/{merchantId}/advisory-action-outcomes/summary")
    public ResponseEntity<ApiResponse<AdvisoryActionOutcomeSummaryDTO>> getActionOutcomeSummary(
            @PathVariable Long merchantId,
            @RequestParam(required = false) String window) {
        AdvisoryActionOutcomeSummaryDTO summary = outcomeService.getOutcomeSummary(merchantId, window);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @GetMapping("/merchants/{merchantId}/advisory-action-learning")
    public ResponseEntity<ApiResponse<List<AdvisoryActionLearningDTO>>> getActionLearnings(
            @PathVariable Long merchantId) {
        List<AdvisoryActionLearningDTO> learnings = outcomeService.getActionLearnings(merchantId);
        return ResponseEntity.ok(ApiResponse.success(learnings));
    }

    @PostMapping("/merchants/{merchantId}/advisory-action-plans/{planId}/steps/{stepId}/outcome/evaluate")
    public ResponseEntity<ApiResponse<AdvisoryActionOutcomeDTO>> evaluateActionOutcome(
            @PathVariable Long merchantId,
            @PathVariable Long planId,
            @PathVariable Long stepId,
            @RequestParam(required = false, defaultValue = "30D") String window) {
        AdvisoryActionOutcomeDTO outcome = outcomeService.evaluateActionOutcome(merchantId, planId, stepId, window);
        return ResponseEntity.ok(ApiResponse.success(outcome));
    }
}
