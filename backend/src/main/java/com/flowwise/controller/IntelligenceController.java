package com.flowwise.controller;

import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.FinancialEvidenceSummaryDTO;
import com.flowwise.dto.IntelligenceQueryDTO;
import com.flowwise.dto.IntelligenceResponseDTO;
import com.flowwise.service.EvidenceBuilderService;
import com.flowwise.service.FlowwiseIntelligenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchants/{merchantId}/intelligence")
@CrossOrigin(origins = "*")
public class IntelligenceController {

    private final FlowwiseIntelligenceService intelligenceService;
    private final EvidenceBuilderService evidenceBuilderService;

    public IntelligenceController(FlowwiseIntelligenceService intelligenceService,
                                  EvidenceBuilderService evidenceBuilderService) {
        this.intelligenceService = intelligenceService;
        this.evidenceBuilderService = evidenceBuilderService;
    }

    @PostMapping("/query")
    public ResponseEntity<ApiResponse<IntelligenceResponseDTO>> processQuery(
            @PathVariable Long merchantId,
            @RequestBody(required = false) IntelligenceQueryDTO queryDTO) {

        String question = (queryDTO != null && queryDTO.getQuestion() != null) ? queryDTO.getQuestion() : "How is my cash flow?";
        IntelligenceResponseDTO response = intelligenceService.processMerchantQuery(merchantId, question);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/evidence")
    public ResponseEntity<ApiResponse<FinancialEvidenceSummaryDTO>> getEvidence(
            @PathVariable Long merchantId,
            @RequestBody(required = false) IntelligenceQueryDTO queryDTO) {

        String question = (queryDTO != null && queryDTO.getQuestion() != null) ? queryDTO.getQuestion() : "Can I afford ₹80,000 of inventory this week?";
        FinancialEvidenceSummaryDTO summary = evidenceBuilderService.buildEvidenceSummary(merchantId, question);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }
}
