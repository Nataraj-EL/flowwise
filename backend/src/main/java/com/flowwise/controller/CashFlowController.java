package com.flowwise.controller;

import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.CashFlowSummaryDTO;
import com.flowwise.dto.MonthlyCashFlowDTO;
import com.flowwise.service.CashFlowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/merchants/{merchantId}/cash-flow")
@CrossOrigin(origins = "*")
public class CashFlowController {

    private final CashFlowService cashFlowService;

    public CashFlowController(CashFlowService cashFlowService) {
        this.cashFlowService = cashFlowService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<CashFlowSummaryDTO>> getCashFlowSummary(@PathVariable Long merchantId) {
        CashFlowSummaryDTO summary = cashFlowService.getCashFlowSummary(merchantId);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @GetMapping("/monthly")
    public ResponseEntity<ApiResponse<List<MonthlyCashFlowDTO>>> getMonthlyCashFlows(@PathVariable Long merchantId) {
        List<MonthlyCashFlowDTO> monthly = cashFlowService.getMonthlyCashFlows(merchantId);
        return ResponseEntity.ok(ApiResponse.success(monthly));
    }
}
