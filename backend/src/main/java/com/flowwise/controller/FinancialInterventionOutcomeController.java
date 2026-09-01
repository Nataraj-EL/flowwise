package com.flowwise.controller;

import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.InterventionEffectivenessSummaryDTO;
import com.flowwise.dto.InterventionOutcomeDTO;
import com.flowwise.service.FinancialInterventionOutcomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchants/{merchantId}")
public class FinancialInterventionOutcomeController {

    private final FinancialInterventionOutcomeService outcomeService;

    public FinancialInterventionOutcomeController(FinancialInterventionOutcomeService outcomeService) {
        this.outcomeService = outcomeService;
    }

    @GetMapping("/intervention-outcomes")
    public ResponseEntity<ApiResponse<InterventionEffectivenessSummaryDTO>> getInterventionOutcomes(@PathVariable Long merchantId) {
        InterventionEffectivenessSummaryDTO summary = outcomeService.getMerchantOutcomeSummary(merchantId);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @GetMapping("/intervention-outcomes/summary")
    public ResponseEntity<ApiResponse<InterventionEffectivenessSummaryDTO>> getInterventionOutcomeSummary(@PathVariable Long merchantId) {
        InterventionEffectivenessSummaryDTO summary = outcomeService.getMerchantOutcomeSummary(merchantId);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @PostMapping("/interventions/{interventionId}/outcome/evaluate")
    public ResponseEntity<ApiResponse<InterventionOutcomeDTO>> evaluateOutcome(
            @PathVariable Long merchantId,
            @PathVariable Long interventionId,
            @RequestParam(required = false, defaultValue = "30D") String window) {
        InterventionOutcomeDTO dto = outcomeService.evaluateInterventionOutcome(merchantId, interventionId, window);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }
}
