package com.flowwise.controller;

import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.IntelligenceQueryDTO;
import com.flowwise.dto.IntelligenceResponseDTO;
import com.flowwise.service.FlowwiseIntelligenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchants/{merchantId}/intelligence")
@CrossOrigin(origins = "*")
public class IntelligenceController {

    private final FlowwiseIntelligenceService intelligenceService;

    public IntelligenceController(FlowwiseIntelligenceService intelligenceService) {
        this.intelligenceService = intelligenceService;
    }

    @PostMapping("/query")
    public ResponseEntity<ApiResponse<IntelligenceResponseDTO>> queryIntelligence(
            @PathVariable Long merchantId,
            @RequestBody(required = false) IntelligenceQueryDTO queryDTO) {
        
        String question = (queryDTO != null) ? queryDTO.getQuestion() : "How is my cash flow?";
        IntelligenceResponseDTO response = intelligenceService.processMerchantQuery(merchantId, question);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
