package com.flowwise.controller;

import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.DocumentCaptureRequestDTO;
import com.flowwise.dto.DocumentCaptureResponseDTO;
import com.flowwise.dto.DocumentConfirmRequestDTO;
import com.flowwise.service.OfficeKitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class OfficeKitController {

    private final OfficeKitService officeKitService;

    public OfficeKitController(OfficeKitService officeKitService) {
        this.officeKitService = officeKitService;
    }

    @PostMapping("/api/v1/merchants/{merchantId}/office-kit/captures")
    public ResponseEntity<ApiResponse<DocumentCaptureResponseDTO>> createCapture(
            @PathVariable Long merchantId,
            @RequestBody(required = false) DocumentCaptureRequestDTO requestDTO) {

        DocumentCaptureResponseDTO response = officeKitService.createCapture(merchantId, requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }

    @GetMapping("/api/v1/merchants/{merchantId}/office-kit/captures")
    public ResponseEntity<ApiResponse<List<DocumentCaptureResponseDTO>>> getMerchantCaptures(
            @PathVariable Long merchantId) {

        List<DocumentCaptureResponseDTO> captures = officeKitService.getMerchantCaptures(merchantId);
        return ResponseEntity.ok(ApiResponse.success(captures));
    }

    @GetMapping("/api/v1/office-kit/captures/{captureId}")
    public ResponseEntity<ApiResponse<DocumentCaptureResponseDTO>> getCaptureDetail(
            @PathVariable Long captureId) {

        DocumentCaptureResponseDTO response = officeKitService.getCaptureDetail(captureId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/api/v1/office-kit/captures/{captureId}/confirm")
    public ResponseEntity<ApiResponse<DocumentCaptureResponseDTO>> confirmCapture(
            @PathVariable Long captureId,
            @RequestBody(required = false) DocumentConfirmRequestDTO confirmDTO) {

        DocumentCaptureResponseDTO response = officeKitService.confirmCapture(captureId, confirmDTO);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/api/v1/office-kit/captures/{captureId}/discard")
    public ResponseEntity<ApiResponse<DocumentCaptureResponseDTO>> discardCapture(
            @PathVariable Long captureId) {

        DocumentCaptureResponseDTO response = officeKitService.discardCapture(captureId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
