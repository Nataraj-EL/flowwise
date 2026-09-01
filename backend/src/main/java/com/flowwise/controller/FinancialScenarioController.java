package com.flowwise.controller;

import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.FinancialScenarioDTO;
import com.flowwise.dto.FinancialScenarioSummaryDTO;
import com.flowwise.service.FinancialScenarioSimulationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchants/{merchantId}")
public class FinancialScenarioController {

    private final FinancialScenarioSimulationService scenarioService;

    public FinancialScenarioController(FinancialScenarioSimulationService scenarioService) {
        this.scenarioService = scenarioService;
    }

    @GetMapping("/financial-scenarios")
    public ResponseEntity<ApiResponse<FinancialScenarioSummaryDTO>> getFinancialScenarios(
            @PathVariable Long merchantId,
            @RequestParam(required = false, defaultValue = "30D") String horizon) {
        FinancialScenarioSummaryDTO summary = scenarioService.getMerchantScenarioSummary(merchantId, horizon);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @GetMapping("/financial-scenarios/{scenarioId}")
    public ResponseEntity<ApiResponse<FinancialScenarioDTO>> getScenarioById(
            @PathVariable Long merchantId,
            @PathVariable Long scenarioId) {
        FinancialScenarioDTO dto = scenarioService.getScenarioById(merchantId, scenarioId);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }

    @PostMapping("/financial-scenarios/evaluate")
    public ResponseEntity<ApiResponse<FinancialScenarioSummaryDTO>> evaluateScenario(
            @PathVariable Long merchantId,
            @RequestParam(required = false, defaultValue = "30D") String horizon,
            @RequestParam(required = false) String scenarioName) {
        FinancialScenarioSummaryDTO summary = scenarioService.evaluateScenario(merchantId, horizon, scenarioName);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @PostMapping("/financial-scenarios/{scenarioId}/archive")
    public ResponseEntity<ApiResponse<FinancialScenarioDTO>> archiveScenario(
            @PathVariable Long merchantId,
            @PathVariable Long scenarioId) {
        FinancialScenarioDTO dto = scenarioService.archiveScenario(merchantId, scenarioId);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }
}
