package com.flowwise.controller;

import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.FinancialInsightDTO;
import com.flowwise.dto.InsightSummaryDTO;
import com.flowwise.service.FinancialInsightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class FinancialInsightController {

    private final FinancialInsightService insightService;

    public FinancialInsightController(FinancialInsightService insightService) {
        this.insightService = insightService;
    }

    @GetMapping("/merchants/{merchantId}/insights")
    public ResponseEntity<ApiResponse<List<FinancialInsightDTO>>> getMerchantInsights(
            @PathVariable Long merchantId) {

        List<FinancialInsightDTO> insights = insightService.getMerchantInsights(merchantId);
        return ResponseEntity.ok(ApiResponse.success(insights));
    }

    @GetMapping("/merchants/{merchantId}/insights/summary")
    public ResponseEntity<ApiResponse<InsightSummaryDTO>> getInsightSummary(
            @PathVariable Long merchantId) {

        InsightSummaryDTO summary = insightService.getInsightSummary(merchantId);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @PostMapping("/merchants/{merchantId}/insights/{insightId}/acknowledge")
    public ResponseEntity<ApiResponse<FinancialInsightDTO>> acknowledgeInsight(
            @PathVariable Long merchantId,
            @PathVariable Long insightId) {

        FinancialInsightDTO updated = insightService.acknowledgeInsight(merchantId, insightId);
        return ResponseEntity.ok(ApiResponse.success(updated));
    }

    @PostMapping("/merchants/{merchantId}/insights/{insightId}/dismiss")
    public ResponseEntity<ApiResponse<FinancialInsightDTO>> dismissInsight(
            @PathVariable Long merchantId,
            @PathVariable Long insightId) {

        FinancialInsightDTO updated = insightService.dismissInsight(merchantId, insightId);
        return ResponseEntity.ok(ApiResponse.success(updated));
    }

    // Legacy un-scoped aliases for merchant ID 1
    @PostMapping("/insights/{insightId}/acknowledge")
    public ResponseEntity<ApiResponse<FinancialInsightDTO>> legacyAcknowledge(
            @PathVariable Long insightId) {
        return acknowledgeInsight(1L, insightId);
    }

    @PostMapping("/insights/{insightId}/dismiss")
    public ResponseEntity<ApiResponse<FinancialInsightDTO>> legacyDismiss(
            @PathVariable Long insightId) {
        return dismissInsight(1L, insightId);
    }
}
