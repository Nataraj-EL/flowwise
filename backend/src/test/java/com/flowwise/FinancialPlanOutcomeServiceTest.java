package com.flowwise;

import com.flowwise.dto.FinancialPlanOutcomeDTO;
import com.flowwise.dto.FinancialPlanOutcomeSummaryDTO;
import com.flowwise.dto.FinancialPlanSummaryDTO;
import com.flowwise.dto.PlanOptimizationDTO;
import com.flowwise.entity.Merchant;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.MerchantRepository;
import com.flowwise.service.FinancialPlanOutcomeService;
import com.flowwise.service.FinancialPlanSynthesisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
public class FinancialPlanOutcomeServiceTest {

    @Autowired
    private FinancialPlanOutcomeService outcomeService;

    @Autowired
    private FinancialPlanSynthesisService planSynthesisService;

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
    @DisplayName("Evaluate Plan Outcome - SUCCESSFUL Classification & Idempotency")
    void testEvaluatePlanOutcome() {
        FinancialPlanSummaryDTO planSummary = planSynthesisService.evaluateFinancialPlan(testMerchant.getId(), "30D");
        Long planId = planSummary.getActivePlan().getId();

        FinancialPlanOutcomeDTO outcome = outcomeService.evaluatePlanOutcome(testMerchant.getId(), planId, "30D");

        assertNotNull(outcome);
        assertEquals(testMerchant.getId(), outcome.getMerchantId());
        assertEquals(planId, outcome.getPlanId());
        assertEquals("SUCCESSFUL", outcome.getOutcomeStatus());
        assertTrue(outcome.getEffectivenessScore().compareTo(new BigDecimal("80.00")) >= 0);

        // Test Idempotency: Second call returns existing outcome
        FinancialPlanOutcomeDTO outcome2 = outcomeService.evaluatePlanOutcome(testMerchant.getId(), planId, "30D");
        assertEquals(outcome.getId(), outcome2.getId());
    }

    @Test
    @DisplayName("Classify Outcome - SUCCESSFUL, PARTIAL, INEFFECTIVE, INSUFFICIENT_DATA")
    void testClassifyOutcome() {
        assertEquals("SUCCESSFUL", outcomeService.classifyOutcome(new BigDecimal("85.00")));
        assertEquals("PARTIAL", outcomeService.classifyOutcome(new BigDecimal("65.00")));
        assertEquals("INEFFECTIVE", outcomeService.classifyOutcome(new BigDecimal("45.00")));
        assertEquals("INSUFFICIENT_DATA", outcomeService.classifyOutcome(null));
    }

    @Test
    @DisplayName("Adaptive Optimization Multiplier - Clamped to [0.900, 1.100]")
    void testUpdateOptimizationFactor_Bounds() {
        outcomeService.updateOptimizationFactor(testMerchant, "30D", new BigDecimal("100.00"));
        List<PlanOptimizationDTO> factors = outcomeService.getMerchantOptimizationFactors(testMerchant.getId());

        assertFalse(factors.isEmpty());
        PlanOptimizationDTO factor = factors.get(0);
        assertTrue(factor.getOptimizationMultiplier().compareTo(new BigDecimal("0.900")) >= 0);
        assertTrue(factor.getOptimizationMultiplier().compareTo(new BigDecimal("1.100")) <= 0);
    }

    @Test
    @DisplayName("Merchant Isolation - Throw 404 for Unknown Merchant ID")
    void testMerchantIsolation_NotFound() {
        assertThrows(ResourceNotFoundException.class, () -> {
            outcomeService.getMerchantOutcomeSummary(99999L, "30D");
        });
    }
}
