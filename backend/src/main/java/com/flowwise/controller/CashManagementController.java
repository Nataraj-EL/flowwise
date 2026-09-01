package com.flowwise.controller;

import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.CashManagementSummaryDTO;
import com.flowwise.dto.PaymentPlanDTO;
import com.flowwise.service.CashManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchants")
@CrossOrigin(origins = "*")
public class CashManagementController {

    private final CashManagementService cashManagementService;

    public CashManagementController(CashManagementService cashManagementService) {
        this.cashManagementService = cashManagementService;
    }

    @GetMapping("/{merchantId}/cash-management")
    public ResponseEntity<ApiResponse<CashManagementSummaryDTO>> getCashManagementSummary(
            @PathVariable Long merchantId) {

        CashManagementSummaryDTO summary = cashManagementService.getCashManagementSummary(merchantId);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @GetMapping("/{merchantId}/cash-management/payment-plan")
    public ResponseEntity<ApiResponse<PaymentPlanDTO>> getPaymentPlan(
            @PathVariable Long merchantId) {

        PaymentPlanDTO plan = cashManagementService.getPaymentPlan(merchantId);
        return ResponseEntity.ok(ApiResponse.success(plan));
    }
}
