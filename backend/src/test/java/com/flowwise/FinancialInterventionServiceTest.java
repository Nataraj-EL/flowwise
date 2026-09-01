package com.flowwise;

import com.flowwise.dto.FinancialInterventionDTO;
import com.flowwise.dto.InterventionSummaryDTO;
import com.flowwise.entity.Merchant;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.MerchantRepository;
import com.flowwise.service.FinancialInterventionService;
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
public class FinancialInterventionServiceTest {

    @Autowired
    private FinancialInterventionService interventionService;

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
    @DisplayName("Evaluate Interventions - 5-Factor Deterministic Weighted Formula (35/25/20/10/10)")
    void testEvaluateInterventions() {
        InterventionSummaryDTO summary = interventionService.evaluateInterventions(testMerchant.getId());

        assertNotNull(summary);
        assertEquals(testMerchant.getId(), summary.getMerchantId());
        assertTrue(summary.getTotalInterventionsCount() > 0);
        assertNotNull(summary.getInterventions());
        assertFalse(summary.getInterventions().isEmpty());
    }

    @Test
    @DisplayName("Priority Score Calculation Formula Test")
    void testComputePriorityScore() {
        // Impact 100, Urgency 100, RiskRed 100, GoalImp 100, Conf 100 => 100.00
        BigDecimal score100 = interventionService.computePriorityScore(
                new BigDecimal("100.00"), new BigDecimal("100.00"), new BigDecimal("100.00"),
                new BigDecimal("100.00"), new BigDecimal("100.00")
        );
        assertEquals(new BigDecimal("100.00"), score100);

        // Impact 90, Urgency 85, RiskRed 80, GoalImp 70, Conf 100 => 85.75
        BigDecimal scoreCalc = interventionService.computePriorityScore(
                new BigDecimal("90.00"), new BigDecimal("85.00"), new BigDecimal("80.00"),
                new BigDecimal("70.00"), new BigDecimal("100.00")
        );
        assertEquals(new BigDecimal("85.75"), scoreCalc);
    }

    @Test
    @DisplayName("Lifecycle Transitions - OPEN -> ACKNOWLEDGED -> COMPLETED")
    void testLifecycleTransitions() {
        InterventionSummaryDTO summary = interventionService.evaluateInterventions(testMerchant.getId());
        Long interventionId = summary.getInterventions().get(0).getId();

        FinancialInterventionDTO acked = interventionService.acknowledgeIntervention(testMerchant.getId(), interventionId);
        assertEquals("ACKNOWLEDGED", acked.getStatus());

        FinancialInterventionDTO completed = interventionService.completeIntervention(testMerchant.getId(), interventionId);
        assertEquals("COMPLETED", completed.getStatus());
    }

    @Test
    @DisplayName("Merchant Isolation - Throw 404 for Unknown Merchant ID")
    void testMerchantIsolation_NotFound() {
        assertThrows(ResourceNotFoundException.class, () -> {
            interventionService.getMerchantInterventionSummary(99999L);
        });
    }
}
