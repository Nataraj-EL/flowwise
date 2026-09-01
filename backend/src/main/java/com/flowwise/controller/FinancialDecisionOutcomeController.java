package com.flowwise.controller;

import com.flowwise.dto.*;
import com.flowwise.service.FinancialDecisionOutcomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class FinancialDecisionOutcomeController {

    private final FinancialDecisionOutcomeService outcomeService;

    public FinancialDecisionOutcomeController(FinancialDecisionOutcomeService outcomeService) {
        this.outcomeService = outcomeService;
    }

    @GetMapping("/merchants/{merchantId}/financial-decision-outcomes")
    public ResponseEntity<ApiResponse<List<FinancialDecisionOutcomeDTO>>> getDecisionOutcomes(
            @PathVariable Long merchantId,
            @RequestParam(required = false) String window) {
        FinancialDecisionOutcomeSummaryDTO summary = outcomeService.getOutcomeSummary(merchantId, window);
        return ResponseEntity.ok(ApiResponse.success(summary.getOutcomes()));
    }

    @GetMapping("/merchants/{merchantId}/financial-decision-outcomes/summary")
    public ResponseEntity<ApiResponse<FinancialDecisionOutcomeSummaryDTO>> getDecisionOutcomeSummary(
            @PathVariable Long merchantId,
            @RequestParam(required = false) String window) {
        FinancialDecisionOutcomeSummaryDTO summary = outcomeService.getOutcomeSummary(merchantId, window);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @GetMapping("/merchants/{merchantId}/financial-decision-learning")
    public ResponseEntity<ApiResponse<List<DecisionLearningDTO>>> getDecisionLearnings(
            @PathVariable Long merchantId) {
        List<DecisionLearningDTO> learnings = outcomeService.getDecisionLearnings(merchantId);
        return ResponseEntity.ok(ApiResponse.success(learnings));
    }

    @PostMapping("/merchants/{merchantId}/financial-decisions/{decisionId}/outcome/evaluate")
    public ResponseEntity<ApiResponse<FinancialDecisionOutcomeDTO>> evaluateDecisionOutcome(
            @PathVariable Long merchantId,
            @PathVariable Long decisionId,
            @RequestParam(required = false, defaultValue = "30D") String window) {
        FinancialDecisionOutcomeDTO outcome = outcomeService.evaluateDecisionOutcome(merchantId, decisionId, window);
        return ResponseEntity.ok(ApiResponse.success(outcome));
    }
}
