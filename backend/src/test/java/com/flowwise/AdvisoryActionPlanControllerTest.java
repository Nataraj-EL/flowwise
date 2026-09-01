package com.flowwise;

import com.flowwise.controller.AdvisoryActionPlanController;
import com.flowwise.dto.AdvisoryActionPlanDTO;
import com.flowwise.dto.AdvisoryActionPlanSummaryDTO;
import com.flowwise.service.AdvisoryActionPlanningService;
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

@WebMvcTest(AdvisoryActionPlanController.class)
public class AdvisoryActionPlanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdvisoryActionPlanningService actionPlanningService;

    @Test
    @DisplayName("GET /api/v1/merchants/{id}/advisory-action-plans - Returns 200 OK")
    void testGetActionPlans_Success() throws Exception {
        AdvisoryActionPlanSummaryDTO summary = new AdvisoryActionPlanSummaryDTO(
                1L, 1, null, Collections.emptyList(), "Summary Explanation", "Advisory Notice"
        );

        given(actionPlanningService.getPlanSummary(eq(1L), eq(null))).willReturn(summary);

        mockMvc.perform(get("/api/v1/merchants/1/advisory-action-plans")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.merchantId").value(1));
    }

    @Test
    @DisplayName("POST /api/v1/merchants/{id}/advisory-action-plans/evaluate - Returns 200 OK")
    void testEvaluateActionPlan_Success() throws Exception {
        AdvisoryActionPlanDTO plan = new AdvisoryActionPlanDTO(
                1L, 1L, "PLAN_30D_KEY", "30D", "ACTIVE", new BigDecimal("93.50"),
                2, 2, 0, "Primary Action", "Expected Benefit", "Risk If Delayed",
                "Evidence Metrics", "Assumptions", Collections.emptyList(), "2026-09-01T10:00:00Z"
        );

        given(actionPlanningService.evaluateActionPlan(eq(1L), eq("30D"))).willReturn(plan);

        mockMvc.perform(post("/api/v1/merchants/1/advisory-action-plans/evaluate?horizon=30D")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.status").value("ACTIVE"));
    }
}
