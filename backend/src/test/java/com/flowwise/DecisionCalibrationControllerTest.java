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
class DecisionCalibrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetCalibration_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1/decision-calibration")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.confidenceLevel").exists())
                .andExpect(jsonPath("$.data.optionPerformances").isArray());
    }

    @Test
    void testEvaluatePerformance_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1/decision-calibration/performance")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.calibrationKey").value("CURRENT_CALIBRATION_BASELINE"));
    }

    @Test
    void testCrossMerchantAccess_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/999/decision-calibration")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
