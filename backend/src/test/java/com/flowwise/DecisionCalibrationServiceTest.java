package com.flowwise;

import com.flowwise.dto.*;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.service.DecisionCalibrationService;
import com.flowwise.service.FlowwiseIntelligenceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class DecisionCalibrationServiceTest {

    @Autowired
    private DecisionCalibrationService calibrationService;

    @Autowired
    private FlowwiseIntelligenceService aiService;

    @Test
    void testEvaluateCalibration_ReturnsValidRecordAndOptionFactors() {
        DecisionCalibrationDTO calibration = calibrationService.evaluateCalibration(1L);
        assertNotNull(calibration);
        assertEquals("CURRENT_CALIBRATION_BASELINE", calibration.getCalibrationKey());
        assertNotNull(calibration.getConfidenceLevel());
        assertNotNull(calibration.getOptionPerformances());
        assertEquals(5, calibration.getOptionPerformances().size());
    }

    @Test
    void testBoundedMultiplierLogic_EnforcesLimitsAndMinimumSamples() {
        DecisionCalibrationDTO calibration = calibrationService.evaluateCalibration(1L);
        for (OptionPerformanceDTO opt : calibration.getOptionPerformances()) {
            if (opt.getTotalSampleCount() < 3) {
                // Minimum 3 samples required for active multiplier adjustment
                assertEquals(0, new BigDecimal("1.00").compareTo(opt.getCalibrationMultiplier()));
            } else {
                // Must be bounded between 0.80 and 1.20
                assertTrue(opt.getCalibrationMultiplier().compareTo(new BigDecimal("0.80")) >= 0);
                assertTrue(opt.getCalibrationMultiplier().compareTo(new BigDecimal("1.20")) <= 0);
            }
        }
    }

    @Test
    void testCrossMerchantAccess_ThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            calibrationService.getMerchantCalibration(999L);
        });
    }

    @Test
    void testAiGrounding_CalibrationQueries() {
        IntelligenceResponseDTO response = aiService.processMerchantQuery(1L, "Did my previous recommendations work? Recommendation calibration");
        assertNotNull(response);
        assertNotNull(response.getAnswer());
        assertTrue(response.getAnswer().contains("Calibration") || response.getAnswer().contains("Success Rate") || response.getAnswer().contains("Evaluated"));
    }
}
