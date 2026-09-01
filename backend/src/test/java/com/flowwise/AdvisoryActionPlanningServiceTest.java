package com.flowwise;

import com.flowwise.dto.AdvisoryActionPlanDTO;
import com.flowwise.dto.AdvisoryActionPlanSummaryDTO;
import com.flowwise.entity.Merchant;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.MerchantRepository;
import com.flowwise.service.AdvisoryActionPlanningService;
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
public class AdvisoryActionPlanningServiceTest {

    @Autowired
    private AdvisoryActionPlanningService actionPlanningService;

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
    @DisplayName("Evaluate Action Plan - Returns Valid Ordered Steps & Readiness Score")
    void testEvaluateActionPlan_Success() {
        AdvisoryActionPlanDTO plan = actionPlanningService.evaluateActionPlan(testMerchant.getId(), "30D");

        assertNotNull(plan);
        assertEquals("30D", plan.getHorizon());
        assertEquals("ACTIVE", plan.getStatus());
        assertTrue(plan.getOverallReadinessScore().compareTo(BigDecimal.ZERO) > 0);
        assertNotNull(plan.getSteps());
        assertFalse(plan.getSteps().isEmpty());

        // Step 1 should be READY and step score > 75
        assertEquals(1, plan.getSteps().get(0).getStepNumber());
        assertEquals("READY", plan.getSteps().get(0).getReadinessStatus());
        assertTrue(plan.getSteps().get(0).getStepScore().compareTo(new BigDecimal("75.00")) > 0);
    }

    @Test
    @DisplayName("Get Plan Summary - Valid Summary & Merchant Isolation")
    void testGetPlanSummary_Success() {
        AdvisoryActionPlanSummaryDTO summary = actionPlanningService.getPlanSummary(testMerchant.getId(), "30D");

        assertNotNull(summary);
        assertEquals(testMerchant.getId(), summary.getMerchantId());
        assertTrue(summary.getTotalPlansCount() > 0);
        assertNotNull(summary.getActivePlan());
    }

    @Test
    @DisplayName("Merchant Isolation - Throw 404 for Invalid Merchant")
    void testMerchantIsolation_NotFound() {
        assertThrows(ResourceNotFoundException.class, () -> {
            actionPlanningService.getPlanSummary(99999L, "30D");
        });
    }
}
