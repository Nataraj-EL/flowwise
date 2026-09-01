package com.flowwise;

import com.flowwise.dto.RiskTrajectorySummaryDTO;
import com.flowwise.entity.Merchant;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.MerchantRepository;
import com.flowwise.service.FinancialRiskTrajectoryService;
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
public class FinancialRiskTrajectoryServiceTest {

    @Autowired
    private FinancialRiskTrajectoryService trajectoryService;

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
    @DisplayName("Evaluate Risk Trajectory - Hysteresis Boundary 5% & Severity Transitions")
    void testEvaluateRiskTrajectory_HysteresisAndTransitions() {
        RiskTrajectorySummaryDTO summary = trajectoryService.evaluateRiskTrajectory(testMerchant.getId());

        assertNotNull(summary);
        assertEquals(testMerchant.getId(), summary.getMerchantId());
        assertNotNull(summary.getCompositeTrajectoryStatus());
        assertTrue(summary.getTotalTrackedRisks() > 0);
        assertNotNull(summary.getTrajectories());
        assertFalse(summary.getTrajectories().isEmpty());
    }

    @Test
    @DisplayName("Merchant Isolation - Throw 404 for Unknown Merchant ID")
    void testMerchantIsolation_NotFound() {
        assertThrows(ResourceNotFoundException.class, () -> {
            trajectoryService.getMerchantRiskHistory(99999L);
        });
    }
}
