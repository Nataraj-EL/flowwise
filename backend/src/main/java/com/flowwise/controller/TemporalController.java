package com.flowwise.controller;

import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.CategoryMovementDTO;
import com.flowwise.dto.TemporalSummaryDTO;
import com.flowwise.service.TemporalIntelligenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/merchants/{merchantId}/temporal")
@CrossOrigin(origins = "*")
public class TemporalController {

    private final TemporalIntelligenceService temporalService;

    public TemporalController(TemporalIntelligenceService temporalService) {
        this.temporalService = temporalService;
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<TemporalSummaryDTO>> getTemporalSummary(@PathVariable Long merchantId) {
        TemporalSummaryDTO summary = temporalService.getTemporalSummary(merchantId);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<CategoryMovementDTO>>> getCategoryMovements(@PathVariable Long merchantId) {
        TemporalSummaryDTO summary = temporalService.getTemporalSummary(merchantId);
        List<CategoryMovementDTO> movements = temporalService.calculateCategoryMovements(merchantId, summary.getCurrentMonth(), summary.getPreviousMonth());
        return ResponseEntity.ok(ApiResponse.success(movements));
    }
}
