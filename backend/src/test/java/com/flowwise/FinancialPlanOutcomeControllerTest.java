package com.flowwise;

import com.flowwise.controller.FinancialPlanOutcomeController;
import com.flowwise.dto.FinancialPlanOutcomeDTO;
import com.flowwise.dto.FinancialPlanOutcomeSummaryDTO;
import com.flowwise.dto.PlanOptimizationDTO;
import com.flowwise.service.FinancialPlanOutcomeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FinancialPlanOutcomeController.class)
public class FinancialPlanOutcomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FinancialPlanOutcomeService outcomeService;

    @Test
    @DisplayName("GET /api/v1/merchants/{id}/financial-plan-outcomes - Returns 200 OK")
    void testGetMerchantOutcomeSummary_Success() throws Exception {
        FinancialPlanOutcomeSummaryDTO summary = new FinancialPlanOutcomeSummaryDTO(
                1L, 1, 1, 0, 0, 0,
                new BigDecimal("91.50"), Collections.emptyList(), Collections.emptyList(),
                "Outcome summary", "Advisory Notice"
        );

        given(outcomeService.getMerchantOutcomeSummary(eq(1L), eq("30D"))).willReturn(summary);

        mockMvc.perform(get("/api/v1/merchants/1/financial-plan-outcomes?horizon=30D")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.merchantId").value(1))
                .andExpect(jsonPath("$.data.successfulCount").value(1));
    }

    @Test
    @DisplayName("GET /api/v1/merchants/{id}/financial-plan-optimization - Returns 200 OK")
    void testGetMerchantOptimizationFactors_Success() throws Exception {
        PlanOptimizationDTO factor = new PlanOptimizationDTO(
                1L, 1L, "30D", 5, new BigDecimal("91.50"), new BigDecimal("1.065"), "HIGH", "2026-09-01T10:00:00Z"
        );

        given(outcomeService.getMerchantOptimizationFactors(eq(1L))).willReturn(Collections.singletonList(factor));

        mockMvc.perform(get("/api/v1/merchants/1/financial-plan-optimization")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].planContext").value("30D"))
                .andExpect(jsonPath("$.data[0].optimizationMultiplier").value(1.065));
    }

    @Test
    @DisplayName("POST /api/v1/merchants/{id}/financial-plans/{planId}/outcome/evaluate - Returns 200 OK")
    void testEvaluatePlanOutcome_Success() throws Exception {
        FinancialPlanOutcomeDTO outcome = new FinancialPlanOutcomeDTO(
                10L, 1L, 1L, "30D", "SUCCESSFUL", new BigDecimal("86.25"), new BigDecimal("89.50"),
                new BigDecimal("3.77"), new BigDecimal("53240.00"), new BigDecimal("56000.00"),
                new BigDecimal("5.18"), new BigDecimal("25.00"), new BigDecimal("28.50"),
                new BigDecimal("30.00"), new BigDecimal("35.00"), new BigDecimal("91.50"),
                "HIGH", "Evidence", "Assumptions", "2026-09-01T10:00:00Z"
        );

        given(outcomeService.evaluatePlanOutcome(eq(1L), eq(1L), eq("30D"))).willReturn(outcome);

        mockMvc.perform(post("/api/v1/merchants/1/financial-plans/1/outcome/evaluate?window=30D")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.outcomeStatus").value("SUCCESSFUL"));
    }
}
