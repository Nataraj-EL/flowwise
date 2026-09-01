package com.flowwise;

import com.flowwise.controller.AdvisoryActionOutcomeController;
import com.flowwise.dto.AdvisoryActionOutcomeDTO;
import com.flowwise.dto.AdvisoryActionOutcomeSummaryDTO;
import com.flowwise.service.AdvisoryActionOutcomeService;
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

@WebMvcTest(AdvisoryActionOutcomeController.class)
public class AdvisoryActionOutcomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdvisoryActionOutcomeService outcomeService;

    @Test
    @DisplayName("GET /api/v1/merchants/{id}/advisory-action-outcomes/summary - Returns 200 OK")
    void testGetActionOutcomeSummary_Success() throws Exception {
        AdvisoryActionOutcomeSummaryDTO summary = new AdvisoryActionOutcomeSummaryDTO(
                1L, 1, 1, 0, 0, 0, new BigDecimal("93.60"),
                Collections.emptyList(), Collections.emptyList(), "Summary Explanation", "Advisory Notice"
        );

        given(outcomeService.getOutcomeSummary(eq(1L), eq(null))).willReturn(summary);

        mockMvc.perform(get("/api/v1/merchants/1/advisory-action-outcomes/summary")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.merchantId").value(1))
                .andExpect(jsonPath("$.data.successfulCount").value(1));
    }

    @Test
    @DisplayName("POST /api/v1/merchants/{id}/advisory-action-plans/{planId}/steps/{stepId}/outcome/evaluate - Returns 200 OK")
    void testEvaluateActionOutcome_Success() throws Exception {
        AdvisoryActionOutcomeDTO outcome = new AdvisoryActionOutcomeDTO(
                1L, 1L, 1L, 1L, "30D", "SUCCESSFUL", new BigDecimal("94.20"), new BigDecimal("95.85"),
                new BigDecimal("1.75"), "Expected Outcome", "Actual Outcome", new BigDecimal("88.50"),
                new BigDecimal("90.00"), new BigDecimal("53240.00"), new BigDecimal("54150.00"),
                new BigDecimal("93.60"), "HIGH", "Evidence", "Assumptions", "2026-09-01T10:00:00Z"
        );

        given(outcomeService.evaluateActionOutcome(eq(1L), eq(1L), eq(1L), eq("30D"))).willReturn(outcome);

        mockMvc.perform(post("/api/v1/merchants/1/advisory-action-plans/1/steps/1/outcome/evaluate?window=30D")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.outcomeStatus").value("SUCCESSFUL"));
    }
}
