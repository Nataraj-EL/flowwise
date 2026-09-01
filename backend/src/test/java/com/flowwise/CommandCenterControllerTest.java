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
class CommandCenterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetCommandCenterSnapshot_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1/command-center")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.overallFinancialStatus").exists())
                .andExpect(jsonPath("$.data.overallHealthScore").exists())
                .andExpect(jsonPath("$.data.availableCash").exists())
                .andExpect(jsonPath("$.data.top3Priorities").exists());
    }
}
