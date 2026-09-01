package com.flowwise.controller;

import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.RiskAlertDTO;
import com.flowwise.dto.RiskMonitorSummaryDTO;
import com.flowwise.service.FinancialRiskDetectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class FinancialRiskDetectionController {

    private final FinancialRiskDetectionService riskService;

    public FinancialRiskDetectionController(FinancialRiskDetectionService riskService) {
        this.riskService = riskService;
    }

    @GetMapping("/merchants/{merchantId}/risk-monitor")
    public ResponseEntity<ApiResponse<RiskMonitorSummaryDTO>> getRiskMonitor(
            @PathVariable Long merchantId) {

        RiskMonitorSummaryDTO summary = riskService.getMerchantRiskMonitor(merchantId);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @PostMapping("/merchants/{merchantId}/risk-monitor/evaluate")
    public ResponseEntity<ApiResponse<RiskMonitorSummaryDTO>> evaluateRisks(
            @PathVariable Long merchantId) {

        RiskMonitorSummaryDTO summary = riskService.evaluateMerchantRisks(merchantId);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @PostMapping("/merchants/{merchantId}/risk-alerts/{alertId}/acknowledge")
    public ResponseEntity<ApiResponse<RiskAlertDTO>> acknowledgeAlert(
            @PathVariable Long merchantId,
            @PathVariable Long alertId) {

        RiskAlertDTO alert = riskService.acknowledgeRiskAlert(merchantId, alertId);
        return ResponseEntity.ok(ApiResponse.success(alert));
    }

    @PostMapping("/merchants/{merchantId}/risk-alerts/{alertId}/resolve")
    public ResponseEntity<ApiResponse<RiskAlertDTO>> resolveAlert(
            @PathVariable Long merchantId,
            @PathVariable Long alertId) {

        RiskAlertDTO alert = riskService.resolveRiskAlert(merchantId, alertId);
        return ResponseEntity.ok(ApiResponse.success(alert));
    }
}
