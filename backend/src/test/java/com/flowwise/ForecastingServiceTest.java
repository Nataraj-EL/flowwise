package com.flowwise;

import com.flowwise.dto.ForecastSummaryDTO;
import com.flowwise.dto.ScenarioRequestDTO;
import com.flowwise.dto.ScenarioResultDTO;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.service.ForecastingService;
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
class ForecastingServiceTest {

    @Autowired
    private ForecastingService forecastingService;

    @Test
    void testGetForecastSummary_Success() {
        ForecastSummaryDTO forecast = forecastingService.getForecastSummary(1L);

        assertNotNull(forecast);
        assertNotNull(forecast.getCurrentAvailableCash());
        assertNotNull(forecast.getAverageMonthlyOutflow());
        assertNotNull(forecast.getProjections());
        assertEquals(3, forecast.getProjections().size());
        assertTrue(forecast.isEstimate());
        assertFalse(forecast.getAssumptions().isEmpty());
    }

    @Test
    void testSimulateScenario_Success() {
        ScenarioRequestDTO request = new ScenarioRequestDTO(new BigDecimal("80000"), "INVENTORY");
        ScenarioResultDTO result = forecastingService.simulateScenario(1L, request);

        assertNotNull(result);
        assertEquals(0, new BigDecimal("80000").compareTo(result.getRequestedAmount()));
        assertEquals("INVENTORY", result.getCategory());
        assertNotNull(result.getBaselineEndingCash());
        assertNotNull(result.getScenarioEndingCash());

        // Scenario Ending Cash = Baseline - 80000
        assertEquals(0, result.getBaselineEndingCash().subtract(new BigDecimal("80000")).compareTo(result.getScenarioEndingCash()));
        
        assertNotNull(result.getRiskStatus());
        assertTrue(result.isEstimate());
    }

    @Test
    void testGetForecastSummary_NotFoundThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            forecastingService.getForecastSummary(999L);
        });
    }
}
