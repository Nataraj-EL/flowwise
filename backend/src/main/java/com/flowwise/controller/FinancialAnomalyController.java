package com.flowwise.controller;

import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.AnomalySummaryDTO;
import com.flowwise.dto.FinancialAnomalyDTO;
import com.flowwise.service.FinancialAnomalyDetectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchants/{merchantId}")
public class FinancialAnomalyController {

    private final FinancialAnomalyDetectionService anomalyService;

    public FinancialAnomalyController(FinancialAnomalyDetectionService anomalyService) {
        this.anomalyService = anomalyService;
    }

    @GetMapping("/anomalies")
    public ResponseEntity<ApiResponse<AnomalySummaryDTO>> getAnomalies(@PathVariable Long merchantId) {
        AnomalySummaryDTO summary = anomalyService.getMerchantAnomalySummary(merchantId);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @PostMapping("/anomalies/evaluate")
    public ResponseEntity<ApiResponse<AnomalySummaryDTO>> evaluateAnomalies(@PathVariable Long merchantId) {
        AnomalySummaryDTO summary = anomalyService.evaluateMerchantAnomalies(merchantId);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @PostMapping("/anomalies/{anomalyId}/acknowledge")
    public ResponseEntity<ApiResponse<FinancialAnomalyDTO>> acknowledgeAnomaly(@PathVariable Long merchantId,
                                                                                @PathVariable Long anomalyId) {
        FinancialAnomalyDTO dto = anomalyService.acknowledgeAnomaly(merchantId, anomalyId);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }

    @PostMapping("/anomalies/{anomalyId}/resolve")
    public ResponseEntity<ApiResponse<FinancialAnomalyDTO>> resolveAnomaly(@PathVariable Long merchantId,
                                                                            @PathVariable Long anomalyId) {
        FinancialAnomalyDTO dto = anomalyService.resolveAnomaly(merchantId, anomalyId);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }
}
