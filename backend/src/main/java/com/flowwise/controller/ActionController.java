package com.flowwise.controller;

import com.flowwise.dto.ActionSummaryDTO;
import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.FinancialActionDTO;
import com.flowwise.service.FinancialActionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class ActionController {

    private final FinancialActionService actionService;

    public ActionController(FinancialActionService actionService) {
        this.actionService = actionService;
    }

    @GetMapping("/api/v1/merchants/{merchantId}/actions")
    public ResponseEntity<ApiResponse<ActionSummaryDTO>> getMerchantActions(
            @PathVariable Long merchantId) {

        ActionSummaryDTO summary = actionService.getMerchantActions(merchantId);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @PostMapping("/api/v1/actions/{actionId}/dismiss")
    public ResponseEntity<ApiResponse<FinancialActionDTO>> dismissAction(
            @PathVariable Long actionId) {

        FinancialActionDTO response = actionService.dismissAction(actionId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/api/v1/actions/{actionId}/resolve")
    public ResponseEntity<ApiResponse<FinancialActionDTO>> resolveAction(
            @PathVariable Long actionId) {

        FinancialActionDTO response = actionService.resolveAction(actionId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
