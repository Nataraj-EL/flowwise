package com.flowwise;

import com.flowwise.dto.InterventionEffectivenessSummaryDTO;
import com.flowwise.dto.InterventionOutcomeDTO;
import com.flowwise.entity.FinancialIntervention;
import com.flowwise.entity.Merchant;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.FinancialInterventionRepository;
import com.flowwise.repository.MerchantRepository;
import com.flowwise.service.FinancialInterventionOutcomeService;
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
public class FinancialInterventionOutcomeServiceTest {

    @Autowired
    private FinancialInterventionOutcomeService outcomeService;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private FinancialInterventionRepository interventionRepository;

    private Merchant testMerchant;
    private FinancialIntervention testIntervention;

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

        testIntervention = new FinancialIntervention(
                testMerchant, "INT_TEST_COMPLETED", "COLLECT_RECEIVABLES",
                "Test Completed Intervention", "Description", new BigDecimal("88.40"),
                new BigDecimal("85.00"), new BigDecimal("90.00"), "HIGH",
                "Recover ₹53,240 working capital within 7 days", "Risk if ignored", "LOW",
                "Evidence", "Assumptions"
        );
        testIntervention.setStatus("COMPLETED");
        testIntervention = interventionRepository.save(testIntervention);
    }

    @Test
    @DisplayName("Evaluate Intervention Outcome - COMPLETED Intervention Eligibility & Bounded Effectiveness Score")
    void testEvaluateInterventionOutcome() {
        InterventionOutcomeDTO outcome = outcomeService.evaluateInterventionOutcome(testMerchant.getId(), testIntervention.getId(), "30D");

        assertNotNull(outcome);
        assertEquals(testMerchant.getId(), outcome.getMerchantId());
        assertEquals(testIntervention.getId(), outcome.getInterventionId());
        assertEquals("SUCCESSFUL", outcome.getOutcomeStatus());
        assertTrue(outcome.getEffectivenessScore().compareTo(BigDecimal.ZERO) >= 0);
        assertTrue(outcome.getEffectivenessScore().compareTo(new BigDecimal("100.00")) <= 0);
        assertTrue(outcome.getActualBenefit().contains("OBSERVED_OUTCOME"));
    }

    @Test
    @DisplayName("Outcome Classification Test - SUCCESSFUL >= 80, PARTIAL >= 50, INEFFECTIVE < 50")
    void testClassifyOutcome() {
        assertEquals("SUCCESSFUL", outcomeService.classifyOutcome(new BigDecimal("85.00")));
        assertEquals("PARTIAL", outcomeService.classifyOutcome(new BigDecimal("65.00")));
        assertEquals("INEFFECTIVE", outcomeService.classifyOutcome(new BigDecimal("40.00")));
    }

    @Test
    @DisplayName("Eligibility Check - Throw Exception for OPEN / ACKNOWLEDGED Interventions")
    void testIneligibleStatus() {
        FinancialIntervention openItv = new FinancialIntervention(
                testMerchant, "INT_TEST_OPEN", "REDUCE_EXPENSE",
                "Test Open Intervention", "Description", new BigDecimal("75.00"),
                new BigDecimal("70.00"), new BigDecimal("70.00"), "HIGH",
                "Benefit", "Risk", "MEDIUM", "Evidence", "Assumptions"
        );
        openItv.setStatus("OPEN");
        openItv = interventionRepository.save(openItv);

        final Long openId = openItv.getId();
        assertThrows(IllegalArgumentException.class, () -> {
            outcomeService.evaluateInterventionOutcome(testMerchant.getId(), openId, "30D");
        });
    }

    @Test
    @DisplayName("Merchant Isolation - Throw 404 for Unknown Merchant ID")
    void testMerchantIsolation_NotFound() {
        assertThrows(ResourceNotFoundException.class, () -> {
            outcomeService.getMerchantOutcomeSummary(99999L);
        });
    }
}
