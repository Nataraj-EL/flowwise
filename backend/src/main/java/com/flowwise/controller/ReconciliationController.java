package com.flowwise.controller;

import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.ReconciliationIssueDTO;
import com.flowwise.dto.ReconciliationSummaryDTO;
import com.flowwise.dto.TransactionDTO;
import com.flowwise.service.ReconciliationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class ReconciliationController {

    private final ReconciliationService reconciliationService;

    public ReconciliationController(ReconciliationService reconciliationService) {
        this.reconciliationService = reconciliationService;
    }

    @GetMapping("/merchants/{merchantId}/reconciliation")
    public ResponseEntity<ApiResponse<ReconciliationSummaryDTO>> getReconciliationSummary(
            @PathVariable Long merchantId) {

        ReconciliationSummaryDTO summary = reconciliationService.getReconciliationSummary(merchantId);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @GetMapping("/merchants/{merchantId}/reconciliation/issues")
    public ResponseEntity<ApiResponse<List<ReconciliationIssueDTO>>> getReconciliationIssues(
            @PathVariable Long merchantId) {

        List<ReconciliationIssueDTO> issues = reconciliationService.getReconciliationIssues(merchantId);
        return ResponseEntity.ok(ApiResponse.success(issues));
    }

    @PostMapping("/transactions/{transactionId}/reconcile")
    public ResponseEntity<ApiResponse<TransactionDTO>> reconcileTransaction(
            @PathVariable Long transactionId,
            @RequestBody(required = false) Map<String, String> body) {

        String notes = (body != null && body.containsKey("notes")) ? body.get("notes") : "Reconciled by merchant";
        TransactionDTO dto = reconciliationService.reconcileTransaction(transactionId, notes);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }

    @PostMapping("/transactions/{transactionId}/ignore")
    public ResponseEntity<ApiResponse<TransactionDTO>> ignoreTransaction(
            @PathVariable Long transactionId,
            @RequestBody(required = false) Map<String, String> body) {

        String notes = (body != null && body.containsKey("notes")) ? body.get("notes") : "Ignored by merchant";
        TransactionDTO dto = reconciliationService.ignoreTransaction(transactionId, notes);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }
}
