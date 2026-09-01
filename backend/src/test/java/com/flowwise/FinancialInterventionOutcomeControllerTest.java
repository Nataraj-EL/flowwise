package com.flowwise;

import com.flowwise.controller.FinancialInterventionOutcomeController;
import com.flowwise.dto.InterventionEffectivenessSummaryDTO;
import com.flowwise.dto.InterventionOutcomeDTO;
import com.flowwise.service.FinancialInterventionOutcomeService;
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

@WebMvcTest(FinancialInterventionOutcomeController.class)
public class FinancialInterventionOutcomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FinancialInterventionOutcomeService outcomeService;

    @Test
    @DisplayName("GET /api/v1/merchants/{id}/intervention-outcomes - Returns 200 OK")
    void testGetInterventionOutcomes_Success() throws Exception {
        InterventionEffectivenessSummaryDTO summary = new InterventionEffectivenessSummaryDTO(
                1L, 1, 1, 0, 0, 0, new BigDecimal("92.50"),
                Collections.emptyList(), "Outcome summary explanation", "Advisory Notice"
        );

        given(outcomeService.getMerchantOutcomeSummary(eq(1L))).willReturn(summary);

        mockMvc.perform(get("/api/v1/merchants/1/intervention-outcomes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.merchantId").value(1))
                .andExpect(jsonPath("$.data.totalEvaluatedOutcomesCount").value(1));
    }

    @Test
    @DisplayName("POST /api/v1/merchants/{id}/interventions/{id}/outcome/evaluate - Returns 200 OK")
    void testEvaluateOutcome_Success() throws Exception {
        InterventionOutcomeDTO dto = new InterventionOutcomeDTO(
                10L, 1L, 1L, "COLLECT_RECEIVABLES", "SUCCESSFUL", "30D",
                "Expected Benefit", "OBSERVED_OUTCOME: Actual Benefit", BigDecimal.ZERO,
                new BigDecimal("53240.00"), new BigDecimal("53240.00"), BigDecimal.ZERO,
                new BigDecimal("80.00"), new BigDecimal("85.00"), new BigDecimal("15.00"),
                new BigDecimal("92.50"), "HIGH", "Evidence", "Assumptions", "2026-09-01T10:00:00Z"
        );

        given(outcomeService.evaluateInterventionOutcome(eq(1L), eq(1L), eq("30D"))).willReturn(dto);

        mockMvc.perform(post("/api/v1/merchants/1/interventions/1/outcome/evaluate?window=30D")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.outcomeStatus").value("SUCCESSFUL"));
    }
}
