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
class MerchantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllMerchants_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].businessName").value("Apex Retail Solutions [DEMO]"));
    }

    @Test
    void testGetMerchantById_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.merchant.businessName").value("Apex Retail Solutions [DEMO]"))
                .andExpect(jsonPath("$.data.connectedAccountsCount").value(3))
                .andExpect(jsonPath("$.data.totalAvailableCash").value(324300.0));
    }

    @Test
    void testGetMerchantById_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error").value("Merchant not found with ID: 999"));
    }

    @Test
    void testGetMerchantAccounts_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1/accounts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].institutionName").value("HDFC Bank"));
    }
}
