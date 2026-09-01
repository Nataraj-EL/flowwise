package com.flowwise;

import com.flowwise.controller.FinancialDecisionOutcomeController;
import com.flowwise.dto.FinancialDecisionOutcomeDTO;
import com.flowwise.dto.FinancialDecisionOutcomeSummaryDTO;
import com.flowwise.service.FinancialDecisionOutcomeService;
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

@WebMvcTest(FinancialDecisionOutcomeController.class)
public class FinancialDecisionOutcomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FinancialDecisionOutcomeService outcomeService;

    @Test
    @DisplayName("GET /api/v1/merchants/{id}/financial-decision-outcomes/summary - Returns 200 OK")
    void testGetDecisionOutcomeSummary_Success() throws Exception {
        FinancialDecisionOutcomeSummaryDTO summary = new FinancialDecisionOutcomeSummaryDTO(
                1L, 1, 1, 0, 0, 0, new BigDecimal("92.80"),
                Collections.emptyList(), Collections.emptyList(), "Summary Explanation", "Advisory Notice"
        );

        given(outcomeService.getOutcomeSummary(eq(1L), eq(null))).willReturn(summary);

        mockMvc.perform(get("/api/v1/merchants/1/financial-decision-outcomes/summary")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.merchantId").value(1))
                .andExpect(jsonPath("$.data.successfulCount").value(1));
    }

    @Test
    @DisplayName("POST /api/v1/merchants/{id}/financial-decisions/{id}/outcome/evaluate - Returns 200 OK")
    void testEvaluateDecisionOutcome_Success() throws Exception {
        FinancialDecisionOutcomeDTO outcome = new FinancialDecisionOutcomeDTO(
                1L, 1L, 4L, "SUCCESSFUL", "30D", new BigDecimal("92.45"), new BigDecimal("94.10"),
                new BigDecimal("1.78"), new BigDecimal("53240.00"), new BigDecimal("54150.00"),
                new BigDecimal("1.71"), new BigDecimal("88.50"), new BigDecimal("90.00"),
                new BigDecimal("95.00"), new BigDecimal("96.00"), new BigDecimal("92.80"),
                "HIGH", "Evidence", "Assumptions", "2026-09-01T10:00:00Z"
        );

        given(outcomeService.evaluateDecisionOutcome(eq(1L), eq(4L), eq("30D"))).willReturn(outcome);

        mockMvc.perform(post("/api/v1/merchants/1/financial-decisions/4/outcome/evaluate?window=30D")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.outcomeStatus").value("SUCCESSFUL"));
    }
}
