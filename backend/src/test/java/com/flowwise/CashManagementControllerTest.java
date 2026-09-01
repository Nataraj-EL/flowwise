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
class CashManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetCashManagementSummary_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1/cash-management")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.currentAvailableCash").exists())
                .andExpect(jsonPath("$.data.safePaymentCapacity").exists())
                .andExpect(jsonPath("$.data.paymentRiskStatus").exists());
    }

    @Test
    void testGetPaymentPlan_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1/cash-management/payment-plan")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.prioritizedPayments").isArray())
                .andExpect(jsonPath("$.data.executionAdvice").exists());
    }
}
