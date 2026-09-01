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
class MerchantWorkspaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetMerchantWorkspace_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1/workspace")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.businessName").exists())
                .andExpect(jsonPath("$.data.totalAvailableCash").exists())
                .andExpect(jsonPath("$.data.accounts").isArray());
    }

    @Test
    void testGetAccountSummary_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1/accounts/1/summary")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.institutionName").exists())
                .andExpect(jsonPath("$.data.currentBalance").exists());
    }
}
