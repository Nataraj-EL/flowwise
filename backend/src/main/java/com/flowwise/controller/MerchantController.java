package com.flowwise.controller;

import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.BusinessAccountDTO;
import com.flowwise.dto.MerchantDTO;
import com.flowwise.dto.MerchantDetailDTO;
import com.flowwise.service.MerchantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/merchants")
@CrossOrigin(origins = "*")
public class MerchantController {

    private final MerchantService merchantService;

    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MerchantDTO>>> getAllMerchants() {
        List<MerchantDTO> merchants = merchantService.getAllMerchants();
        return ResponseEntity.ok(ApiResponse.success(merchants));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MerchantDetailDTO>> getMerchantById(@PathVariable Long id) {
        MerchantDetailDTO detail = merchantService.getMerchantDetail(id);
        return ResponseEntity.ok(ApiResponse.success(detail));
    }

    @GetMapping("/{id}/accounts")
    public ResponseEntity<ApiResponse<List<BusinessAccountDTO>>> getMerchantAccounts(@PathVariable Long id) {
        List<BusinessAccountDTO> accounts = merchantService.getMerchantAccounts(id);
        return ResponseEntity.ok(ApiResponse.success(accounts));
    }
}
