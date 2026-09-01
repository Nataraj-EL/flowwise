package com.flowwise.controller;

import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.TransactionDTO;
import com.flowwise.dto.TransactionSummaryDTO;
import com.flowwise.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/merchants/{merchantId}/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TransactionDTO>>> getTransactions(
            @PathVariable Long merchantId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search) {
        
        List<TransactionDTO> transactions = transactionService.getMerchantTransactions(merchantId, type, category, search);
        return ResponseEntity.ok(ApiResponse.success(transactions));
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<TransactionSummaryDTO>> getTransactionSummary(@PathVariable Long merchantId) {
        TransactionSummaryDTO summary = transactionService.getTransactionSummary(merchantId);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }
}
