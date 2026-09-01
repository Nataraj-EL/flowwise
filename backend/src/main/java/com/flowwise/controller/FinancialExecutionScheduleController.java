package com.flowwise.controller;

import com.flowwise.dto.*;
import com.flowwise.service.FinancialExecutionSchedulingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class FinancialExecutionScheduleController {

    private final FinancialExecutionSchedulingService schedulingService;

    public FinancialExecutionScheduleController(FinancialExecutionSchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    @GetMapping("/merchants/{merchantId}/execution-schedules")
    public ResponseEntity<ApiResponse<FinancialExecutionScheduleSummaryDTO>> getScheduleSummary(
            @PathVariable Long merchantId,
            @RequestParam(required = false, defaultValue = "30D") String horizon) {
        FinancialExecutionScheduleSummaryDTO summary = schedulingService.getScheduleSummary(merchantId, horizon);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @GetMapping("/merchants/{merchantId}/execution-schedules/{scheduleId}")
    public ResponseEntity<ApiResponse<FinancialExecutionScheduleDTO>> getScheduleById(
            @PathVariable Long merchantId,
            @PathVariable Long scheduleId) {
        FinancialExecutionScheduleDTO schedule = schedulingService.getScheduleById(merchantId, scheduleId);
        return ResponseEntity.ok(ApiResponse.success(schedule));
    }

    @PostMapping("/merchants/{merchantId}/execution-schedules/evaluate")
    public ResponseEntity<ApiResponse<FinancialExecutionScheduleDTO>> evaluateSchedule(
            @PathVariable Long merchantId,
            @RequestParam(required = false, defaultValue = "30D") String horizon) {
        FinancialExecutionScheduleDTO schedule = schedulingService.evaluateSchedule(merchantId, horizon);
        return ResponseEntity.ok(ApiResponse.success(schedule));
    }

    @PostMapping("/merchants/{merchantId}/execution-schedules/{scheduleId}/activate")
    public ResponseEntity<ApiResponse<FinancialExecutionScheduleDTO>> activateSchedule(
            @PathVariable Long merchantId,
            @PathVariable Long scheduleId) {
        FinancialExecutionScheduleDTO schedule = schedulingService.activateSchedule(merchantId, scheduleId);
        return ResponseEntity.ok(ApiResponse.success(schedule));
    }

    @PostMapping("/merchants/{merchantId}/execution-schedules/{scheduleId}/archive")
    public ResponseEntity<ApiResponse<FinancialExecutionScheduleDTO>> archiveSchedule(
            @PathVariable Long merchantId,
            @PathVariable Long scheduleId) {
        FinancialExecutionScheduleDTO schedule = schedulingService.archiveSchedule(merchantId, scheduleId);
        return ResponseEntity.ok(ApiResponse.success(schedule));
    }
}
