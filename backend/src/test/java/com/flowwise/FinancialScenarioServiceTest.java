package com.flowwise;

import com.flowwise.dto.*;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.service.FinancialScenarioService;
import com.flowwise.service.FlowwiseIntelligenceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class FinancialScenarioServiceTest {

    @Autowired
    private FinancialScenarioService scenarioService;

    @Autowired
    private FlowwiseIntelligenceService intelligenceService;

    @Test
    void testGetScenarioComparison_ReturnsMultiScenarioComparison() {
        ScenarioComparisonDTO comparison = scenarioService.getScenarioComparison(1L);
        assertNotNull(comparison);
        assertNotNull(comparison.getBaselineScenario());
        assertNotNull(comparison.getCautiousScenario());
        assertNotNull(comparison.getStressScenario());
        assertNotNull(comparison.getPrimaryRiskAlert());
        assertTrue(comparison.getCurrentAvailableCash().compareTo(BigDecimal.ZERO) > 0);

        assertEquals("BASELINE", comparison.getBaselineScenario().getScenarioType());
        assertEquals("CAUTIOUS", comparison.getCautiousScenario().getScenarioType());
        assertEquals("STRESS", comparison.getStressScenario().getScenarioType());
    }

    @Test
    void testSimulateScenario_CustomSimulationReadonly() {
        ScenarioSimulationRequestDTO request = new ScenarioSimulationRequestDTO(
                "CUSTOM",
                "Custom Test Scenario",
                new BigDecimal("-15.00"),
                new BigDecimal("10.00"),
                new BigDecimal("75.00"),
                new BigDecimal("100.00"),
                false // Read-only simulation
        );

        FinancialScenarioDTO result = scenarioService.simulateScenario(1L, request);
        assertNotNull(result);
        assertEquals("CUSTOM", result.getScenarioType());
        assertEquals("Custom Test Scenario", result.getName());
        assertTrue(result.getProjected30dCash().compareTo(BigDecimal.ZERO) >= 0);
        assertTrue(result.isEstimate());
    }

    @Test
    void testSimulateScenario_InvalidCollectionBounds_ThrowsException() {
        ScenarioSimulationRequestDTO invalidReq = new ScenarioSimulationRequestDTO(
                "CUSTOM",
                "Invalid Bounds Test",
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                new BigDecimal("150.00"), // Invalid collection % > 100
                new BigDecimal("100.00"),
                false
        );

        assertThrows(IllegalArgumentException.class, () -> {
            scenarioService.simulateScenario(1L, invalidReq);
        });
    }

    @Test
    void testSimulateScenario_SaveScenario_PersistsEntity() {
        ScenarioSimulationRequestDTO saveReq = new ScenarioSimulationRequestDTO(
                "CUSTOM",
                "Saved Scenario Test",
                new BigDecimal("-5.00"),
                new BigDecimal("2.00"),
                new BigDecimal("90.00"),
                new BigDecimal("100.00"),
                true // Save scenario
        );

        FinancialScenarioDTO result = scenarioService.simulateScenario(1L, saveReq);
        assertNotNull(result);
        assertNotNull(result.getId());

        List<FinancialScenarioDTO> scenarios = scenarioService.getMerchantScenarios(1L);
        assertTrue(scenarios.stream().anyMatch(s -> "Saved Scenario Test".equals(s.getName())));
    }

    @Test
    void testCrossMerchantAccess_ThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            scenarioService.getScenarioComparison(999L);
        });
    }

    @Test
    void testAiGrounding_ScenarioQueries() {
        IntelligenceResponseDTO response = intelligenceService.processMerchantQuery(1L, "What happens if this trend continues under stress scenario?");
        assertNotNull(response);
        assertNotNull(response.getAnswer());
        assertTrue(response.getAnswer().contains("Scenario") || response.getAnswer().contains("Baseline") || response.getAnswer().contains("Stress") || response.getAnswer().contains("Projected"));
    }
}
