package com.flowwise;

import com.flowwise.controller.FinancialSignalCorrelationController;
import com.flowwise.dto.CorrelationSummaryDTO;
import com.flowwise.service.FinancialSignalCorrelationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FinancialSignalCorrelationController.class)
public class FinancialSignalCorrelationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FinancialSignalCorrelationService correlationService;

    @Test
    @DisplayName("GET /api/v1/merchants/{id}/correlations - Returns 200 OK")
    void testGetCorrelations_Success() throws Exception {
        CorrelationSummaryDTO summary = new CorrelationSummaryDTO(
                1L, 2, 2, "LIKELY_CONTRIBUTOR: Test Root Cause",
                Collections.emptyList(), Collections.emptyList(),
                "Correlation summary explanation", "Advisory Notice"
        );

        given(correlationService.getMerchantCorrelationSummary(eq(1L))).willReturn(summary);

        mockMvc.perform(get("/api/v1/merchants/1/correlations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.merchantId").value(1))
                .andExpect(jsonPath("$.data.totalCorrelationsCount").value(2));
    }

    @Test
    @DisplayName("POST /api/v1/merchants/{id}/correlations/evaluate - Returns 200 OK")
    void testEvaluateCorrelations_Success() throws Exception {
        CorrelationSummaryDTO summary = new CorrelationSummaryDTO(
                1L, 2, 2, "LIKELY_CONTRIBUTOR: Test Root Cause",
                Collections.emptyList(), Collections.emptyList(),
                "Evaluated correlation summary", "Advisory Notice"
        );

        given(correlationService.evaluateSignalCorrelations(eq(1L))).willReturn(summary);

        mockMvc.perform(post("/api/v1/merchants/1/correlations/evaluate")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.merchantId").value(1));
    }
}
