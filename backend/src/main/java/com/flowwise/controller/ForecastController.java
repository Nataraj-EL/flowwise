package com.flowwise.controller;

import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.ForecastSummaryDTO;
import com.flowwise.dto.ScenarioRequestDTO;
import com.flowwise.dto.ScenarioResultDTO;
import com.flowwise.service.ForecastingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchants/{merchantId}/forecast")
@CrossOrigin(origins = "*")
public class ForecastController {

    private final ForecastingService forecastingService;

    public ForecastController(ForecastingService forecastingService) {
        this.forecastingService = forecastingService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ForecastSummaryDTO>> getForecastSummary(@PathVariable Long merchantId) {
        ForecastSummaryDTO summary = forecastingService.getForecastSummary(merchantId);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @PostMapping("/scenario")
    public ResponseEntity<ApiResponse<ScenarioResultDTO>> simulateScenario(
            @PathVariable Long merchantId,
            @RequestBody(required = false) ScenarioRequestDTO scenarioDTO) {
        
        ScenarioResultDTO result = forecastingService.simulateScenario(merchantId, scenarioDTO);
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
