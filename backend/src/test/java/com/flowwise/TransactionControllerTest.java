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
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetTransactions_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1/transactions")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].counterparty").exists())
                .andExpect(jsonPath("$.data[0].amount").exists());
    }

    @Test
    void testGetTransactions_Filtered() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1/transactions")
                .param("type", "CREDIT")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].type").value("CREDIT"));
    }

    @Test
    void testGetTransactionSummary_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1/transactions/summary")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.totalCredits").exists())
                .andExpect(jsonPath("$.data.totalDebits").exists())
                .andExpect(jsonPath("$.data.transactionCount").exists());
    }

    @Test
    void testGetTransactions_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/999/transactions")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error").value("Merchant not found with ID: 999"));
    }
}
