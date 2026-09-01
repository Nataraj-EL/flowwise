package com.flowwise.controller;

import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.PayableDTO;
import com.flowwise.dto.PayablesSummaryDTO;
import com.flowwise.service.PayablesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/merchants/{merchantId}/payables")
@CrossOrigin(origins = "*")
public class PayablesController {

    private final PayablesService payablesService;

    public PayablesController(PayablesService payablesService) {
        this.payablesService = payablesService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PayableDTO>>> getMerchantPayables(
            @PathVariable Long merchantId) {

        List<PayableDTO> list = payablesService.getPayables(merchantId);
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<PayablesSummaryDTO>> getPayablesSummary(
            @PathVariable Long merchantId) {

        PayablesSummaryDTO summary = payablesService.getPayablesSummary(merchantId);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }
}
