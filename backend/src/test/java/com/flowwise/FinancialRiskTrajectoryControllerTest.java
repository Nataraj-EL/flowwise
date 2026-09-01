package com.flowwise;

import com.flowwise.controller.FinancialRiskTrajectoryController;
import com.flowwise.dto.RiskTrajectorySummaryDTO;
import com.flowwise.service.FinancialRiskTrajectoryService;
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

@WebMvcTest(FinancialRiskTrajectoryController.class)
public class FinancialRiskTrajectoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FinancialRiskTrajectoryService trajectoryService;

    @Test
    @DisplayName("GET /api/v1/merchants/{id}/risk-history - Returns 200 OK")
    void testGetRiskHistory_Success() throws Exception {
        RiskTrajectorySummaryDTO summary = new RiskTrajectorySummaryDTO(
                1L, "WORSENING", 2, 1, 1, 0, 0,
                new BigDecimal("24.00"), Collections.emptyList(), Collections.emptyList(),
                "Risk Trajectory Engine summary", "Advisory Notice"
        );

        given(trajectoryService.getMerchantRiskHistory(eq(1L))).willReturn(summary);

        mockMvc.perform(get("/api/v1/merchants/1/risk-history")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.merchantId").value(1))
                .andExpect(jsonPath("$.data.compositeTrajectoryStatus").value("WORSENING"));
    }

    @Test
    @DisplayName("POST /api/v1/merchants/{id}/risk-history/evaluate - Returns 200 OK")
    void testEvaluateRiskTrajectory_Success() throws Exception {
        RiskTrajectorySummaryDTO summary = new RiskTrajectorySummaryDTO(
                1L, "STABLE", 2, 0, 2, 0, 0,
                new BigDecimal("0.00"), Collections.emptyList(), Collections.emptyList(),
                "Evaluated risk trajectory", "Advisory Notice"
        );

        given(trajectoryService.evaluateRiskTrajectory(eq(1L))).willReturn(summary);

        mockMvc.perform(post("/api/v1/merchants/1/risk-history/evaluate")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.compositeTrajectoryStatus").value("STABLE"));
    }
}
