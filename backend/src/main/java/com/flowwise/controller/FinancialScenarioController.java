package com.flowwise.controller;

import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.FinancialScenarioDTO;
import com.flowwise.dto.ScenarioComparisonDTO;
import com.flowwise.dto.ScenarioSimulationRequestDTO;
import com.flowwise.service.FinancialScenarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class FinancialScenarioController {

    private final FinancialScenarioService scenarioService;

    public FinancialScenarioController(FinancialScenarioService scenarioService) {
        this.scenarioService = scenarioService;
    }

    @GetMapping("/merchants/{merchantId}/scenarios")
    public ResponseEntity<ApiResponse<List<FinancialScenarioDTO>>> getMerchantScenarios(
            @PathVariable Long merchantId) {

        List<FinancialScenarioDTO> scenarios = scenarioService.getMerchantScenarios(merchantId);
        return ResponseEntity.ok(ApiResponse.success(scenarios));
    }

    @GetMapping("/merchants/{merchantId}/scenarios/comparison")
    public ResponseEntity<ApiResponse<ScenarioComparisonDTO>> getScenarioComparison(
            @PathVariable Long merchantId) {

        ScenarioComparisonDTO comparison = scenarioService.getScenarioComparison(merchantId);
        return ResponseEntity.ok(ApiResponse.success(comparison));
    }

    @PostMapping("/merchants/{merchantId}/scenarios/simulate")
    public ResponseEntity<ApiResponse<FinancialScenarioDTO>> simulateScenario(
            @PathVariable Long merchantId,
            @RequestBody ScenarioSimulationRequestDTO request) {

        FinancialScenarioDTO result = scenarioService.simulateScenario(merchantId, request);
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
