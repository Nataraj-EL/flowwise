package com.flowwise.controller;

import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.BusinessHealthDTO;
import com.flowwise.service.BusinessHealthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchants/{merchantId}/health")
@CrossOrigin(origins = "*")
public class HealthController {

    private final BusinessHealthService businessHealthService;

    public HealthController(BusinessHealthService businessHealthService) {
        this.businessHealthService = businessHealthService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<BusinessHealthDTO>> getBusinessHealth(@PathVariable Long merchantId) {
        BusinessHealthDTO health = businessHealthService.calculateBusinessHealth(merchantId);
        return ResponseEntity.ok(ApiResponse.success(health));
    }
}
