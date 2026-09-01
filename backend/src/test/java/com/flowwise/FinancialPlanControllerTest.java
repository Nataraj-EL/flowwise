package com.flowwise;

import com.flowwise.controller.FinancialPlanController;
import com.flowwise.dto.FinancialPlanDTO;
import com.flowwise.dto.FinancialPlanSummaryDTO;
import com.flowwise.service.FinancialPlanSynthesisService;
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

@WebMvcTest(FinancialPlanController.class)
public class FinancialPlanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FinancialPlanSynthesisService planSynthesisService;

    @Test
    @DisplayName("GET /api/v1/merchants/{id}/financial-plans - Returns 200 OK")
    void testGetFinancialPlans_Success() throws Exception {
        FinancialPlanSummaryDTO summary = new FinancialPlanSummaryDTO(
                1L, 1, "30D", new BigDecimal("86.25"), "Primary Focus Area",
                null, Collections.emptyList(), Collections.emptyList(),
                "Plan summary", "Advisory Notice"
        );

        given(planSynthesisService.getMerchantPlanSummary(eq(1L), eq("30D"))).willReturn(summary);

        mockMvc.perform(get("/api/v1/merchants/1/financial-plans?horizon=30D")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.merchantId").value(1))
                .andExpect(jsonPath("$.data.activeHorizon").value("30D"));
    }

    @Test
    @DisplayName("POST /api/v1/merchants/{id}/financial-plans/{planId}/activate - Returns 200 OK")
    void testActivateFinancialPlan_Success() throws Exception {
        FinancialPlanDTO dto = new FinancialPlanDTO(
                10L, 1L, "PLAN_1_30D", "30D", "ACTIVE", new BigDecimal("86.25"),
                "Focus", "Summary", "Assumptions", Collections.emptyList(), "2026-09-01T10:00:00Z"
        );

        given(planSynthesisService.activatePlan(eq(1L), eq(10L))).willReturn(dto);

        mockMvc.perform(post("/api/v1/merchants/1/financial-plans/10/activate")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.status").value("ACTIVE"));
    }
}
