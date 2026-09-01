package com.flowwise.controller;

import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.StrategyLearningSummaryDTO;
import com.flowwise.service.FinancialStrategyLearningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchants/{merchantId}")
public class FinancialStrategyLearningController {

    private final FinancialStrategyLearningService learningService;

    public FinancialStrategyLearningController(FinancialStrategyLearningService learningService) {
        this.learningService = learningService;
    }

    @GetMapping("/strategy-learning")
    public ResponseEntity<ApiResponse<StrategyLearningSummaryDTO>> getStrategyLearning(@PathVariable Long merchantId) {
        StrategyLearningSummaryDTO summary = learningService.getMerchantStrategyLearning(merchantId);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @GetMapping("/strategy-learning/performance")
    public ResponseEntity<ApiResponse<StrategyLearningSummaryDTO>> getStrategyLearningPerformance(@PathVariable Long merchantId) {
        StrategyLearningSummaryDTO summary = learningService.getMerchantStrategyLearning(merchantId);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @PostMapping("/strategy-learning/evaluate")
    public ResponseEntity<ApiResponse<StrategyLearningSummaryDTO>> evaluateStrategyLearning(@PathVariable Long merchantId) {
        StrategyLearningSummaryDTO summary = learningService.evaluateStrategyLearning(merchantId);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }
}
