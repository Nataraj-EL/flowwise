package com.flowwise.controller;

import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.DecisionCalibrationDTO;
import com.flowwise.service.DecisionCalibrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class DecisionCalibrationController {

    private final DecisionCalibrationService calibrationService;

    public DecisionCalibrationController(DecisionCalibrationService calibrationService) {
        this.calibrationService = calibrationService;
    }

    @GetMapping("/merchants/{merchantId}/decision-calibration")
    public ResponseEntity<ApiResponse<DecisionCalibrationDTO>> getCalibration(
            @PathVariable Long merchantId) {

        DecisionCalibrationDTO calibration = calibrationService.getMerchantCalibration(merchantId);
        return ResponseEntity.ok(ApiResponse.success(calibration));
    }

    @GetMapping("/merchants/{merchantId}/decision-calibration/performance")
    public ResponseEntity<ApiResponse<DecisionCalibrationDTO>> evaluatePerformance(
            @PathVariable Long merchantId) {

        DecisionCalibrationDTO calibration = calibrationService.evaluateCalibration(merchantId);
        return ResponseEntity.ok(ApiResponse.success(calibration));
    }
}
