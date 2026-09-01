package com.flowwise.controller;

import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.RiskTrajectorySummaryDTO;
import com.flowwise.service.FinancialRiskTrajectoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchants/{merchantId}/risk-history")
public class FinancialRiskTrajectoryController {

    private final FinancialRiskTrajectoryService trajectoryService;

    public FinancialRiskTrajectoryController(FinancialRiskTrajectoryService trajectoryService) {
        this.trajectoryService = trajectoryService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<RiskTrajectorySummaryDTO>> getRiskHistory(@PathVariable Long merchantId) {
        RiskTrajectorySummaryDTO summary = trajectoryService.getMerchantRiskHistory(merchantId);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @PostMapping("/evaluate")
    public ResponseEntity<ApiResponse<RiskTrajectorySummaryDTO>> evaluateRiskTrajectory(@PathVariable Long merchantId) {
        RiskTrajectorySummaryDTO summary = trajectoryService.evaluateRiskTrajectory(merchantId);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }
}
