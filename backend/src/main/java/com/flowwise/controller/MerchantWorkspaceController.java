package com.flowwise.controller;

import com.flowwise.dto.AccountDetailDTO;
import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.MerchantWorkspaceDTO;
import com.flowwise.service.MerchantWorkspaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchants/{merchantId}")
@CrossOrigin(origins = "*")
public class MerchantWorkspaceController {

    private final MerchantWorkspaceService workspaceService;

    public MerchantWorkspaceController(MerchantWorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @GetMapping("/workspace")
    public ResponseEntity<ApiResponse<MerchantWorkspaceDTO>> getMerchantWorkspace(
            @PathVariable Long merchantId) {

        MerchantWorkspaceDTO workspace = workspaceService.getMerchantWorkspace(merchantId);
        return ResponseEntity.ok(ApiResponse.success(workspace));
    }

    @GetMapping("/accounts/{accountId}/summary")
    public ResponseEntity<ApiResponse<AccountDetailDTO>> getAccountSummary(
            @PathVariable Long merchantId,
            @PathVariable Long accountId) {

        AccountDetailDTO summary = workspaceService.getAccountSummary(merchantId, accountId);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }
}
