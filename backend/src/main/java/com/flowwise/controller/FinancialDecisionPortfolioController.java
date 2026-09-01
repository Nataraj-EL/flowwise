package com.flowwise.controller;

import com.flowwise.dto.*;
import com.flowwise.service.FinancialDecisionPortfolioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class FinancialDecisionPortfolioController {

    private final FinancialDecisionPortfolioService portfolioService;

    public FinancialDecisionPortfolioController(FinancialDecisionPortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping("/merchants/{merchantId}/decision-portfolios")
    public ResponseEntity<ApiResponse<FinancialDecisionPortfolioSummaryDTO>> getPortfolios(
            @PathVariable Long merchantId,
            @RequestParam(required = false) String horizon) {
        FinancialDecisionPortfolioSummaryDTO summary = portfolioService.getPortfolioSummary(merchantId, horizon);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @GetMapping("/merchants/{merchantId}/decision-portfolios/{portfolioId}")
    public ResponseEntity<ApiResponse<FinancialDecisionPortfolioDTO>> getPortfolioById(
            @PathVariable Long merchantId,
            @PathVariable Long portfolioId) {
        FinancialDecisionPortfolioDTO portfolio = portfolioService.getPortfolioById(merchantId, portfolioId);
        return ResponseEntity.ok(ApiResponse.success(portfolio));
    }

    @PostMapping("/merchants/{merchantId}/decision-portfolios/evaluate")
    public ResponseEntity<ApiResponse<FinancialDecisionPortfolioDTO>> evaluatePortfolio(
            @PathVariable Long merchantId,
            @RequestParam(required = false, defaultValue = "30D") String horizon) {
        FinancialDecisionPortfolioDTO portfolio = portfolioService.evaluatePortfolio(merchantId, horizon);
        return ResponseEntity.ok(ApiResponse.success(portfolio));
    }

    @PostMapping("/merchants/{merchantId}/decision-portfolios/{portfolioId}/activate")
    public ResponseEntity<ApiResponse<FinancialDecisionPortfolioDTO>> activatePortfolio(
            @PathVariable Long merchantId,
            @PathVariable Long portfolioId) {
        FinancialDecisionPortfolioDTO portfolio = portfolioService.activatePortfolio(merchantId, portfolioId);
        return ResponseEntity.ok(ApiResponse.success(portfolio));
    }

    @PostMapping("/merchants/{merchantId}/decision-portfolios/{portfolioId}/archive")
    public ResponseEntity<ApiResponse<FinancialDecisionPortfolioDTO>> archivePortfolio(
            @PathVariable Long merchantId,
            @PathVariable Long portfolioId) {
        FinancialDecisionPortfolioDTO portfolio = portfolioService.archivePortfolio(merchantId, portfolioId);
        return ResponseEntity.ok(ApiResponse.success(portfolio));
    }
}
