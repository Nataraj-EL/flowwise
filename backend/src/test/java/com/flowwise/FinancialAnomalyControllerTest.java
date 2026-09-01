package com.flowwise;

import com.flowwise.controller.FinancialAnomalyController;
import com.flowwise.dto.AnomalySummaryDTO;
import com.flowwise.dto.FinancialAnomalyDTO;
import com.flowwise.service.FinancialAnomalyDetectionService;
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

@WebMvcTest(FinancialAnomalyController.class)
public class FinancialAnomalyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FinancialAnomalyDetectionService anomalyService;

    @Test
    @DisplayName("GET /api/v1/merchants/{id}/anomalies - Returns 200 OK")
    void testGetAnomalies_Success() throws Exception {
        AnomalySummaryDTO summary = new AnomalySummaryDTO(
                1L, 2, 0, 1, 1, 0, 2,
                Collections.emptyList(), Collections.emptyList(),
                "Anomaly summary explanation", "Advisory Notice"
        );

        given(anomalyService.getMerchantAnomalySummary(eq(1L))).willReturn(summary);

        mockMvc.perform(get("/api/v1/merchants/1/anomalies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.merchantId").value(1))
                .andExpect(jsonPath("$.data.totalAnomaliesCount").value(2));
    }

    @Test
    @DisplayName("POST /api/v1/merchants/{id}/anomalies/evaluate - Returns 200 OK")
    void testEvaluateAnomalies_Success() throws Exception {
        AnomalySummaryDTO summary = new AnomalySummaryDTO(
                1L, 2, 0, 1, 1, 0, 2,
                Collections.emptyList(), Collections.emptyList(),
                "Evaluated anomaly summary", "Advisory Notice"
        );

        given(anomalyService.evaluateMerchantAnomalies(eq(1L))).willReturn(summary);

        mockMvc.perform(post("/api/v1/merchants/1/anomalies/evaluate")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.merchantId").value(1));
    }
}
