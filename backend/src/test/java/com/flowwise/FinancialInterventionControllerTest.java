package com.flowwise;

import com.flowwise.controller.FinancialInterventionController;
import com.flowwise.dto.FinancialInterventionDTO;
import com.flowwise.dto.InterventionSummaryDTO;
import com.flowwise.service.FinancialInterventionService;
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

@WebMvcTest(FinancialInterventionController.class)
public class FinancialInterventionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FinancialInterventionService interventionService;

    @Test
    @DisplayName("GET /api/v1/merchants/{id}/interventions - Returns 200 OK")
    void testGetInterventions_Success() throws Exception {
        InterventionSummaryDTO summary = new InterventionSummaryDTO(
                1L, 2, 2, 2, "Accelerate Distributor Overdue Collections",
                Collections.emptyList(), Collections.emptyList(),
                "Intervention summary explanation", "Advisory Notice"
        );

        given(interventionService.getMerchantInterventionSummary(eq(1L))).willReturn(summary);

        mockMvc.perform(get("/api/v1/merchants/1/interventions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.merchantId").value(1))
                .andExpect(jsonPath("$.data.totalInterventionsCount").value(2));
    }

    @Test
    @DisplayName("POST /api/v1/merchants/{id}/interventions/evaluate - Returns 200 OK")
    void testEvaluateInterventions_Success() throws Exception {
        InterventionSummaryDTO summary = new InterventionSummaryDTO(
                1L, 2, 2, 2, "Accelerate Distributor Overdue Collections",
                Collections.emptyList(), Collections.emptyList(),
                "Evaluated intervention summary", "Advisory Notice"
        );

        given(interventionService.evaluateInterventions(eq(1L))).willReturn(summary);

        mockMvc.perform(post("/api/v1/merchants/1/interventions/evaluate")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.merchantId").value(1));
    }

    @Test
    @DisplayName("POST /api/v1/merchants/{id}/interventions/{id}/acknowledge - Returns 200 OK")
    void testAcknowledgeIntervention_Success() throws Exception {
        FinancialInterventionDTO dto = new FinancialInterventionDTO(
                10L, 1L, "INT_M1_COLLECT_OVERDUE", "COLLECT_RECEIVABLES",
                "Title", "Description", new BigDecimal("85.75"), new BigDecimal("85.00"),
                new BigDecimal("90.00"), "HIGH", "Benefit", "Risk", "LOW",
                null, null, null, null, "ACKNOWLEDGED", "Evidence", "Assumptions", "2026-09-01T10:00:00Z"
        );

        given(interventionService.acknowledgeIntervention(eq(1L), eq(10L))).willReturn(dto);

        mockMvc.perform(post("/api/v1/merchants/1/interventions/10/acknowledge")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.status").value("ACKNOWLEDGED"));
    }
}
