package com.flowwise.controller;

import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.CorrelationSummaryDTO;
import com.flowwise.service.FinancialSignalCorrelationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchants/{merchantId}/correlations")
public class FinancialSignalCorrelationController {

    private final FinancialSignalCorrelationService correlationService;

    public FinancialSignalCorrelationController(FinancialSignalCorrelationService correlationService) {
        this.correlationService = correlationService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<CorrelationSummaryDTO>> getCorrelations(@PathVariable Long merchantId) {
        CorrelationSummaryDTO summary = correlationService.getMerchantCorrelationSummary(merchantId);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @PostMapping("/evaluate")
    public ResponseEntity<ApiResponse<CorrelationSummaryDTO>> evaluateCorrelations(@PathVariable Long merchantId) {
        CorrelationSummaryDTO summary = correlationService.evaluateSignalCorrelations(merchantId);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }
}
