package com.flowwise.controller;

import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.FinancialInterventionDTO;
import com.flowwise.dto.InterventionSummaryDTO;
import com.flowwise.service.FinancialInterventionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchants/{merchantId}/interventions")
public class FinancialInterventionController {

    private final FinancialInterventionService interventionService;

    public FinancialInterventionController(FinancialInterventionService interventionService) {
        this.interventionService = interventionService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<InterventionSummaryDTO>> getInterventions(@PathVariable Long merchantId) {
        InterventionSummaryDTO summary = interventionService.getMerchantInterventionSummary(merchantId);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @PostMapping("/evaluate")
    public ResponseEntity<ApiResponse<InterventionSummaryDTO>> evaluateInterventions(@PathVariable Long merchantId) {
        InterventionSummaryDTO summary = interventionService.evaluateInterventions(merchantId);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @PostMapping("/{interventionId}/acknowledge")
    public ResponseEntity<ApiResponse<FinancialInterventionDTO>> acknowledgeIntervention(
            @PathVariable Long merchantId, @PathVariable Long interventionId) {
        FinancialInterventionDTO dto = interventionService.acknowledgeIntervention(merchantId, interventionId);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }

    @PostMapping("/{interventionId}/complete")
    public ResponseEntity<ApiResponse<FinancialInterventionDTO>> completeIntervention(
            @PathVariable Long merchantId, @PathVariable Long interventionId) {
        FinancialInterventionDTO dto = interventionService.completeIntervention(merchantId, interventionId);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }

    @PostMapping("/{interventionId}/dismiss")
    public ResponseEntity<ApiResponse<FinancialInterventionDTO>> dismissIntervention(
            @PathVariable Long merchantId, @PathVariable Long interventionId) {
        FinancialInterventionDTO dto = interventionService.dismissIntervention(merchantId, interventionId);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }
}
