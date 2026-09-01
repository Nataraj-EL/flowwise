package com.flowwise;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CashFlowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetCashFlowSummary_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1/cash-flow")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.totalInflows").exists())
                .andExpect(jsonPath("$.data.burnRate").exists())
                .andExpect(jsonPath("$.data.cashRunwayMonths").exists())
                .andExpect(jsonPath("$.data.liquidityStatus").value("CRITICAL"));
    }

    @Test
    void testGetMonthlyCashFlows_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1/cash-flow/monthly")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].month").exists());
    }

    @Test
    void testGetCashFlowSummary_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/999/cash-flow")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error").value("Merchant not found with ID: 999"));
    }
}
