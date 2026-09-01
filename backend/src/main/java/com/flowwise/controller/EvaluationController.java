package com.flowwise.controller;

import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.EvaluationSummaryDTO;
import com.flowwise.service.EvaluationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/evaluation")
@CrossOrigin(origins = "*")
public class EvaluationController {

    private final EvaluationService evaluationService;

    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @PostMapping("/run")
    public ResponseEntity<ApiResponse<EvaluationSummaryDTO>> runEvaluation() {
        EvaluationSummaryDTO summary = evaluationService.runEvaluationSuite();
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<EvaluationSummaryDTO>> getEvaluationSummary() {
        EvaluationSummaryDTO summary = evaluationService.getLatestEvaluationSummary();
        return ResponseEntity.ok(ApiResponse.success(summary));
    }
}
