package com.flowwise.controller;

import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.ReceivableDTO;
import com.flowwise.dto.ReceivablesSummaryDTO;
import com.flowwise.service.ReceivablesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/merchants/{merchantId}/receivables")
@CrossOrigin(origins = "*")
public class ReceivablesController {

    private final ReceivablesService receivablesService;

    public ReceivablesController(ReceivablesService receivablesService) {
        this.receivablesService = receivablesService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReceivableDTO>>> getMerchantReceivables(
            @PathVariable Long merchantId) {

        List<ReceivableDTO> list = receivablesService.getReceivables(merchantId);
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<ReceivablesSummaryDTO>> getReceivablesSummary(
            @PathVariable Long merchantId) {

        ReceivablesSummaryDTO summary = receivablesService.getReceivablesSummary(merchantId);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }
}
