package com.flowwise.controller;

import com.flowwise.dto.*;
import com.flowwise.service.FinancialDecisionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class FinancialDecisionController {

    private final FinancialDecisionService decisionService;

    public FinancialDecisionController(FinancialDecisionService decisionService) {
        this.decisionService = decisionService;
    }

    @PostMapping("/merchants/{merchantId}/decisions")
    public ResponseEntity<ApiResponse<FinancialDecisionDTO>> createDecision(
            @PathVariable Long merchantId,
            @RequestBody CreateDecisionRequestDTO request) {

        FinancialDecisionDTO created = decisionService.createDecision(merchantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(created));
    }

    @GetMapping("/merchants/{merchantId}/decisions")
    public ResponseEntity<ApiResponse<List<FinancialDecisionDTO>>> getMerchantDecisions(
            @PathVariable Long merchantId) {

        List<FinancialDecisionDTO> decisions = decisionService.getMerchantDecisions(merchantId);
        return ResponseEntity.ok(ApiResponse.success(decisions));
    }

    @GetMapping("/merchants/{merchantId}/decisions/summary")
    public ResponseEntity<ApiResponse<DecisionSummaryDTO>> getDecisionSummary(
            @PathVariable Long merchantId) {

        DecisionSummaryDTO summary = decisionService.getDecisionSummary(merchantId);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @PostMapping("/merchants/{merchantId}/decisions/{decisionId}/accept")
    public ResponseEntity<ApiResponse<FinancialDecisionDTO>> acceptDecision(
            @PathVariable Long merchantId,
            @PathVariable Long decisionId,
            @RequestBody(required = false) Map<String, String> body) {

        String notes = (body != null && body.containsKey("notes")) ? body.get("notes") : null;
        FinancialDecisionDTO updated = decisionService.acceptDecision(merchantId, decisionId, notes);
        return ResponseEntity.ok(ApiResponse.success(updated));
    }

    @PostMapping("/merchants/{merchantId}/decisions/{decisionId}/decline")
    public ResponseEntity<ApiResponse<FinancialDecisionDTO>> declineDecision(
            @PathVariable Long merchantId,
            @PathVariable Long decisionId,
            @RequestBody(required = false) Map<String, String> body) {

        String notes = (body != null && body.containsKey("notes")) ? body.get("notes") : null;
        FinancialDecisionDTO updated = decisionService.declineDecision(merchantId, decisionId, notes);
        return ResponseEntity.ok(ApiResponse.success(updated));
    }

    @PostMapping("/merchants/{merchantId}/decisions/{decisionId}/complete")
    public ResponseEntity<ApiResponse<FinancialDecisionDTO>> completeDecision(
            @PathVariable Long merchantId,
            @PathVariable Long decisionId,
            @RequestBody(required = false) Map<String, String> body) {

        String notes = (body != null && body.containsKey("notes")) ? body.get("notes") : null;
        FinancialDecisionDTO updated = decisionService.completeDecision(merchantId, decisionId, notes);
        return ResponseEntity.ok(ApiResponse.success(updated));
    }

    @PostMapping("/merchants/{merchantId}/decisions/{decisionId}/outcome")
    public ResponseEntity<ApiResponse<FinancialDecisionDTO>> recordOutcome(
            @PathVariable Long merchantId,
            @PathVariable Long decisionId,
            @RequestBody DecisionOutcomeDTO outcome) {

        FinancialDecisionDTO updated = decisionService.recordOutcome(merchantId, decisionId, outcome);
        return ResponseEntity.ok(ApiResponse.success(updated));
    }

    // Legacy un-scoped aliases for merchant ID 1
    @PostMapping("/decisions/{decisionId}/accept")
    public ResponseEntity<ApiResponse<FinancialDecisionDTO>> legacyAccept(
            @PathVariable Long decisionId,
            @RequestBody(required = false) Map<String, String> body) {
        return acceptDecision(1L, decisionId, body);
    }

    @PostMapping("/decisions/{decisionId}/decline")
    public ResponseEntity<ApiResponse<FinancialDecisionDTO>> legacyDecline(
            @PathVariable Long decisionId,
            @RequestBody(required = false) Map<String, String> body) {
        return declineDecision(1L, decisionId, body);
    }

    @PostMapping("/decisions/{decisionId}/complete")
    public ResponseEntity<ApiResponse<FinancialDecisionDTO>> legacyComplete(
            @PathVariable Long decisionId,
            @RequestBody(required = false) Map<String, String> body) {
        return completeDecision(1L, decisionId, body);
    }

    @PostMapping("/decisions/{decisionId}/outcome")
    public ResponseEntity<ApiResponse<FinancialDecisionDTO>> legacyOutcome(
            @PathVariable Long decisionId,
            @RequestBody DecisionOutcomeDTO outcome) {
        return recordOutcome(1L, decisionId, outcome);
    }
}
