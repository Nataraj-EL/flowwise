package com.flowwise;

import com.flowwise.controller.FinancialDecisionPortfolioController;
import com.flowwise.dto.FinancialDecisionPortfolioDTO;
import com.flowwise.dto.FinancialDecisionPortfolioSummaryDTO;
import com.flowwise.service.FinancialDecisionPortfolioService;
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

@WebMvcTest(FinancialDecisionPortfolioController.class)
public class FinancialDecisionPortfolioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FinancialDecisionPortfolioService portfolioService;

    @Test
    @DisplayName("GET /api/v1/merchants/{id}/decision-portfolios - Returns 200 OK")
    void testGetPortfolios_Success() throws Exception {
        FinancialDecisionPortfolioSummaryDTO summary = new FinancialDecisionPortfolioSummaryDTO(
                1L, 1, null, Collections.emptyList(), "Summary Explanation", "Advisory Notice"
        );

        given(portfolioService.getPortfolioSummary(eq(1L), eq(null))).willReturn(summary);

        mockMvc.perform(get("/api/v1/merchants/1/decision-portfolios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.merchantId").value(1));
    }

    @Test
    @DisplayName("POST /api/v1/merchants/{id}/decision-portfolios/evaluate - Returns 200 OK")
    void testEvaluatePortfolio_Success() throws Exception {
        FinancialDecisionPortfolioDTO portfolio = new FinancialDecisionPortfolioDTO(
                1L, 1L, "PORTFOLIO_30D_KEY", "30D", "ACTIVE", new BigDecimal("91.85"),
                new BigDecimal("88.50"), new BigDecimal("95.00"), new BigDecimal("90.00"),
                new BigDecimal("89.00"), "Focus Area", "Expected Benefit", "Risk If Ignored",
                "Evidence Metrics", "Assumptions", Collections.emptyList(), "2026-09-01T10:00:00Z"
        );

        given(portfolioService.evaluatePortfolio(eq(1L), eq("30D"))).willReturn(portfolio);

        mockMvc.perform(post("/api/v1/merchants/1/decision-portfolios/evaluate?horizon=30D")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.status").value("ACTIVE"));
    }
}
