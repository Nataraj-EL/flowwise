package com.flowwise;

import com.flowwise.dto.FinancialPlanDTO;
import com.flowwise.dto.FinancialPlanSummaryDTO;
import com.flowwise.entity.Merchant;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.MerchantRepository;
import com.flowwise.service.FinancialPlanSynthesisService;
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
public class FinancialPlanSynthesisServiceTest {

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
    @DisplayName("Evaluate Financial Plan - 6-Factor Synthesis Scoring & Active Status Assignment")
    void testEvaluateFinancialPlan() {
        FinancialPlanSummaryDTO summary = planSynthesisService.evaluateFinancialPlan(testMerchant.getId(), "30D");

        assertNotNull(summary);
        assertEquals(testMerchant.getId(), summary.getMerchantId());
        assertEquals("30D", summary.getActiveHorizon());
        assertNotNull(summary.getActivePlan());
        assertEquals("ACTIVE", summary.getActivePlan().getStatus());
        assertTrue(summary.getActivePlanScore().compareTo(BigDecimal.ZERO) >= 0);
        assertTrue(summary.getActivePlanScore().compareTo(new BigDecimal("100.00")) <= 0);
    }

    @Test
    @DisplayName("Item Score Calculation Test - 30% Risk + 25% Impact + 20% Urgency + 10% Goal + 10% Effectiveness + 5% Conf")
    void testComputeItemScore() {
        BigDecimal score = planSynthesisService.computeItemScore(
                new BigDecimal("85.00"), new BigDecimal("90.00"), new BigDecimal("85.00"),
                new BigDecimal("70.00"), new BigDecimal("92.50"), new BigDecimal("100.00")
        );
        // 25.5 + 22.5 + 17.0 + 7.0 + 9.25 + 5.0 = 86.25
        assertEquals(new BigDecimal("86.25"), score);
    }

    @Test
    @DisplayName("Plan Lifecycle - Activate & Archive Plan Transitions")
    void testPlanLifecycle() {
        FinancialPlanSummaryDTO summary = planSynthesisService.evaluateFinancialPlan(testMerchant.getId(), "30D");
        Long planId = summary.getActivePlan().getId();

        FinancialPlanDTO archived = planSynthesisService.archivePlan(testMerchant.getId(), planId);
        assertEquals("ARCHIVED", archived.getStatus());

        FinancialPlanDTO activated = planSynthesisService.activatePlan(testMerchant.getId(), planId);
        assertEquals("ACTIVE", activated.getStatus());
    }

    @Test
    @DisplayName("Merchant Isolation - Throw 404 for Unknown Merchant ID")
    void testMerchantIsolation_NotFound() {
        assertThrows(ResourceNotFoundException.class, () -> {
            planSynthesisService.getMerchantPlanSummary(99999L, "30D");
        });
    }
}
