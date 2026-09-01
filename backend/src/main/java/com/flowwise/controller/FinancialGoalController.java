package com.flowwise.controller;

import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.CreateGoalRequestDTO;
import com.flowwise.dto.FinancialGoalDTO;
import com.flowwise.service.FinancialGoalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/merchants/{merchantId}")
@CrossOrigin(origins = "*")
public class FinancialGoalController {

    private final FinancialGoalService goalService;

    public FinancialGoalController(FinancialGoalService goalService) {
        this.goalService = goalService;
    }

    @PostMapping("/goals")
    public ResponseEntity<ApiResponse<FinancialGoalDTO>> createGoal(
            @PathVariable Long merchantId,
            @RequestBody CreateGoalRequestDTO request) {

        FinancialGoalDTO created = goalService.createGoal(merchantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(created));
    }

    @GetMapping("/goals")
    public ResponseEntity<ApiResponse<List<FinancialGoalDTO>>> getMerchantGoals(
            @PathVariable Long merchantId) {

        List<FinancialGoalDTO> goals = goalService.getMerchantGoals(merchantId);
        return ResponseEntity.ok(ApiResponse.success(goals));
    }

    @GetMapping("/goals/{goalId}")
    public ResponseEntity<ApiResponse<FinancialGoalDTO>> getGoalById(
            @PathVariable Long merchantId,
            @PathVariable Long goalId) {

        FinancialGoalDTO goal = goalService.getGoalById(merchantId, goalId);
        return ResponseEntity.ok(ApiResponse.success(goal));
    }

    @PostMapping("/goals/{goalId}/evaluate")
    public ResponseEntity<ApiResponse<FinancialGoalDTO>> evaluateGoal(
            @PathVariable Long merchantId,
            @PathVariable Long goalId) {

        FinancialGoalDTO evaluated = goalService.evaluateAndSaveGoal(merchantId, goalId);
        return ResponseEntity.ok(ApiResponse.success(evaluated));
    }

    @PostMapping("/goals/{goalId}/archive")
    public ResponseEntity<ApiResponse<FinancialGoalDTO>> archiveGoal(
            @PathVariable Long merchantId,
            @PathVariable Long goalId) {

        FinancialGoalDTO archived = goalService.archiveGoal(merchantId, goalId);
        return ResponseEntity.ok(ApiResponse.success(archived));
    }
}
