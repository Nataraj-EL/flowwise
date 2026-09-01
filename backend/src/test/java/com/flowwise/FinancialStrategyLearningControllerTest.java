package com.flowwise;

import com.flowwise.controller.FinancialStrategyLearningController;
import com.flowwise.dto.StrategyLearningSummaryDTO;
import com.flowwise.service.FinancialStrategyLearningService;
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

@WebMvcTest(FinancialStrategyLearningController.class)
public class FinancialStrategyLearningControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FinancialStrategyLearningService learningService;

    @Test
    @DisplayName("GET /api/v1/merchants/{id}/strategy-learning - Returns 200 OK")
    void testGetStrategyLearning_Success() throws Exception {
        StrategyLearningSummaryDTO summary = new StrategyLearningSummaryDTO(
                1L, 1, "COLLECT_RECEIVABLES", 1, new BigDecimal("1.085"),
                Collections.emptyList(), "Strategy learning summary", "Advisory Notice"
        );

        given(learningService.getMerchantStrategyLearning(eq(1L))).willReturn(summary);

        mockMvc.perform(get("/api/v1/merchants/1/strategy-learning")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.merchantId").value(1))
                .andExpect(jsonPath("$.data.topPerformingInterventionType").value("COLLECT_RECEIVABLES"));
    }

    @Test
    @DisplayName("POST /api/v1/merchants/{id}/strategy-learning/evaluate - Returns 200 OK")
    void testEvaluateStrategyLearning_Success() throws Exception {
        StrategyLearningSummaryDTO summary = new StrategyLearningSummaryDTO(
                1L, 1, "COLLECT_RECEIVABLES", 1, new BigDecimal("1.085"),
                Collections.emptyList(), "Strategy learning summary", "Advisory Notice"
        );

        given(learningService.evaluateStrategyLearning(eq(1L))).willReturn(summary);

        mockMvc.perform(post("/api/v1/merchants/1/strategy-learning/evaluate")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.merchantId").value(1));
    }
}
