package com.flowwise.controller;

import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.WorkingCapitalSummaryDTO;
import com.flowwise.service.WorkingCapitalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchants/{merchantId}/working-capital")
@CrossOrigin(origins = "*")
public class WorkingCapitalController {

    private final WorkingCapitalService workingCapitalService;

    public WorkingCapitalController(WorkingCapitalService workingCapitalService) {
        this.workingCapitalService = workingCapitalService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<WorkingCapitalSummaryDTO>> getWorkingCapitalSummary(
            @PathVariable Long merchantId) {

        WorkingCapitalSummaryDTO summary = workingCapitalService.getWorkingCapitalSummary(merchantId);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }
}
