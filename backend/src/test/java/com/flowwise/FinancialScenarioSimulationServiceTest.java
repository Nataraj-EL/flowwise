package com.flowwise;

import com.flowwise.dto.FinancialScenarioDTO;
import com.flowwise.dto.FinancialScenarioSummaryDTO;
import com.flowwise.entity.Merchant;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.MerchantRepository;
import com.flowwise.service.FinancialScenarioSimulationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
public class FinancialScenarioSimulationServiceTest {

    @Autowired
    private FinancialScenarioSimulationService scenarioService;

    @Autowired
    private MerchantRepository merchantRepository;

    private Merchant testMerchant;

    @BeforeEach
    void setUp() {
        testMerchant = merchantRepository.findById(1L).orElseGet(() -> {
            Merchant m = new Merchant();
            m.setBusinessName("Test Enterprise");
            m.setDisplayName("Test Enterprise");
            m.setBusinessType("RETAIL");
            m.setIndustry("TECHNOLOGY");
            m.setDemoGstin("27AAAAA0000A1Z5");
            return merchantRepository.save(m);
        });
    }

    @Test
    @DisplayName("Evaluate Scenario - 6-Factor Deterministic Simulation & Idempotency")
    void testEvaluateScenario() {
        FinancialScenarioSummaryDTO summary = scenarioService.evaluateScenario(testMerchant.getId(), "30D", "Test Receivables Simulation");

        assertNotNull(summary);
        assertEquals(testMerchant.getId(), summary.getMerchantId());
        assertEquals("30D", summary.getActiveHorizon());
        assertNotNull(summary.getTopRankedScenario());
        assertTrue(summary.getTopProjectedScore().compareTo(summary.getBaselineScore()) >= 0);
        assertTrue(summary.getTopRankedScenario().getEvidenceMetrics().contains("SIMULATED_ESTIMATE"));

        // Test Idempotency: Second evaluation with identical key returns cached scenario
        FinancialScenarioSummaryDTO summary2 = scenarioService.evaluateScenario(testMerchant.getId(), "30D", "Test Receivables Simulation");
        assertEquals(summary.getTopRankedScenario().getId(), summary2.getTopRankedScenario().getId());
    }

    @Test
    @DisplayName("Scenario Archival - Status Change to ARCHIVED")
    void testArchiveScenario() {
        FinancialScenarioSummaryDTO summary = scenarioService.evaluateScenario(testMerchant.getId(), "30D", "Test Archival Scenario");
        Long scenarioId = summary.getTopRankedScenario().getId();

        FinancialScenarioDTO archived = scenarioService.archiveScenario(testMerchant.getId(), scenarioId);
        assertEquals("ARCHIVED", archived.getStatus());
    }

    @Test
    @DisplayName("Merchant Isolation - Throw 404 for Unknown Merchant ID")
    void testMerchantIsolation_NotFound() {
        assertThrows(ResourceNotFoundException.class, () -> {
            scenarioService.getMerchantScenarioSummary(99999L, "30D");
        });
    }
}
