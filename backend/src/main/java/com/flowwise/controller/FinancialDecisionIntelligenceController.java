package com.flowwise.controller;

import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.DecisionAnalysisDTO;
import com.flowwise.service.FinancialDecisionIntelligenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class FinancialDecisionIntelligenceController {

    private final FinancialDecisionIntelligenceService intelligenceService;

    public FinancialDecisionIntelligenceController(FinancialDecisionIntelligenceService intelligenceService) {
        this.intelligenceService = intelligenceService;
    }

    @GetMapping("/merchants/{merchantId}/decision-intelligence")
    public ResponseEntity<ApiResponse<DecisionAnalysisDTO>> getDecisionIntelligence(
            @PathVariable Long merchantId) {

        DecisionAnalysisDTO analysis = intelligenceService.getMerchantDecisionAnalysis(merchantId);
        return ResponseEntity.ok(ApiResponse.success(analysis));
    }

    @GetMapping("/merchants/{merchantId}/decision-intelligence/analysis")
    public ResponseEntity<ApiResponse<DecisionAnalysisDTO>> getLatestAnalysis(
            @PathVariable Long merchantId) {

        DecisionAnalysisDTO analysis = intelligenceService.evaluateDecisionIntelligence(merchantId);
        return ResponseEntity.ok(ApiResponse.success(analysis));
    }
}
