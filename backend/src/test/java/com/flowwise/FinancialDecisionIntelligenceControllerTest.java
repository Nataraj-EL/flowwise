package com.flowwise;

import com.flowwise.controller.FinancialDecisionController;
import com.flowwise.dto.FinancialDecisionDTO;
import com.flowwise.dto.FinancialDecisionSummaryDTO;
import com.flowwise.service.FinancialDecisionIntelligenceService;
import com.flowwise.service.FinancialDecisionService;
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

@WebMvcTest(FinancialDecisionController.class)
public class FinancialDecisionIntelligenceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FinancialDecisionService decisionService;

    @MockBean
    private FinancialDecisionIntelligenceService decisionIntelligenceService;

    @Test
    @DisplayName("GET /api/v1/merchants/{id}/financial-decisions - Returns 200 OK")
    void testGetFinancialDecisionSummary_Success() throws Exception {
        FinancialDecisionSummaryDTO summary = new FinancialDecisionSummaryDTO(
                1L, 1, new BigDecimal("92.45"), "Top Decision Title",
                null, Collections.emptyList(), "Summary Explanation", "Advisory Notice"
        );

        given(decisionIntelligenceService.getMerchantDecisionSummary(eq(1L))).willReturn(summary);

        mockMvc.perform(get("/api/v1/merchants/1/financial-decisions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.merchantId").value(1));
    }

    @Test
    @DisplayName("POST /api/v1/merchants/{id}/financial-decisions/{id}/acknowledge - Returns 200 OK")
    void testAcknowledgeFinancialDecision_Success() throws Exception {
        FinancialDecisionDTO dto = new FinancialDecisionDTO(
                10L, 1L, "DECISION_1", "INTERVENTION_EXECUTION", "Title",
                "Recommendation", "ACKNOWLEDGED", new BigDecimal("92.45"), new BigDecimal("88.50"),
                new BigDecimal("95.00"), new BigDecimal("90.00"), new BigDecimal("89.00"),
                "Benefit", "Risk", 1L, 1L, 1L, "Evidence", "Assumptions",
                "Tradeoffs", "HIGH", Collections.emptyList(), "2026-09-01T10:00:00Z"
        );

        given(decisionIntelligenceService.acknowledgeDecision(eq(1L), eq(10L))).willReturn(dto);

        mockMvc.perform(post("/api/v1/merchants/1/financial-decisions/10/acknowledge")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.status").value("ACKNOWLEDGED"));
    }
}
