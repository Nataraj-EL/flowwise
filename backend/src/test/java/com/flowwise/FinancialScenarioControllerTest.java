package com.flowwise;

import com.flowwise.controller.FinancialScenarioController;
import com.flowwise.dto.FinancialScenarioDTO;
import com.flowwise.dto.FinancialScenarioSummaryDTO;
import com.flowwise.service.FinancialScenarioSimulationService;
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

@WebMvcTest(FinancialScenarioController.class)
public class FinancialScenarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FinancialScenarioSimulationService scenarioService;

    @Test
    @DisplayName("GET /api/v1/merchants/{id}/financial-scenarios - Returns 200 OK")
    void testGetFinancialScenarios_Success() throws Exception {
        FinancialScenarioSummaryDTO summary = new FinancialScenarioSummaryDTO(
                1L, 1, "30D", new BigDecimal("78.45"), new BigDecimal("91.80"),
                "Top Scenario", null, Collections.emptyList(),
                "Scenario summary", "Advisory Notice"
        );

        given(scenarioService.getMerchantScenarioSummary(eq(1L), eq("30D"))).willReturn(summary);

        mockMvc.perform(get("/api/v1/merchants/1/financial-scenarios?horizon=30D")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.merchantId").value(1))
                .andExpect(jsonPath("$.data.activeHorizon").value("30D"));
    }

    @Test
    @DisplayName("POST /api/v1/merchants/{id}/financial-scenarios/{scenarioId}/archive - Returns 200 OK")
    void testArchiveScenario_Success() throws Exception {
        FinancialScenarioDTO dto = new FinancialScenarioDTO(
                10L, 1L, "SCENARIO_1_30D", "Test Scenario", "30D", "ARCHIVED",
                new BigDecimal("78.45"), new BigDecimal("91.80"), new BigDecimal("13.35"),
                new BigDecimal("88240.00"), new BigDecimal("32.50"), new BigDecimal("42.00"),
                "HIGH", "Assumptions", "Evidence", Collections.emptyList(), "2026-09-01T10:00:00Z"
        );

        given(scenarioService.archiveScenario(eq(1L), eq(10L))).willReturn(dto);

        mockMvc.perform(post("/api/v1/merchants/1/financial-scenarios/10/archive")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.status").value("ARCHIVED"));
    }
}
