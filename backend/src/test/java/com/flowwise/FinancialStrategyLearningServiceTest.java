package com.flowwise;

import com.flowwise.dto.StrategyLearningSummaryDTO;
import com.flowwise.entity.Merchant;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.MerchantRepository;
import com.flowwise.service.FinancialStrategyLearningService;
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
public class FinancialStrategyLearningServiceTest {

    @Autowired
    private FinancialStrategyLearningService learningService;

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
    @DisplayName("Evaluate Strategy Learning - Deterministic Multipliers Bounded 0.900-1.100")
    void testEvaluateStrategyLearning() {
        StrategyLearningSummaryDTO summary = learningService.evaluateStrategyLearning(testMerchant.getId());

        assertNotNull(summary);
        assertEquals(testMerchant.getId(), summary.getMerchantId());
        assertTrue(summary.getTotalEvaluatedStrategiesCount() > 0);
        assertNotNull(summary.getTopPerformingInterventionType());

        assertTrue(summary.getAverageLearningMultiplier().compareTo(new BigDecimal("0.900")) >= 0);
        assertTrue(summary.getAverageLearningMultiplier().compareTo(new BigDecimal("1.100")) <= 0);
    }

    @Test
    @DisplayName("Confidence Status Boundaries - HIGH >= 5, MODERATE >= 3, LIMITED >= 1, INSUFFICIENT < 1")
    void testConfidenceStatusBoundaries() {
        assertEquals("HIGH", learningService.deriveConfidenceStatus(5));
        assertEquals("MODERATE", learningService.deriveConfidenceStatus(3));
        assertEquals("LIMITED", learningService.deriveConfidenceStatus(1));
        assertEquals("INSUFFICIENT_DATA", learningService.deriveConfidenceStatus(0));
    }

    @Test
    @DisplayName("Multiplier Computation Test - High Score Boosts Multiplier Up To 1.100")
    void testComputeMultiplier() {
        assertEquals(new BigDecimal("1.085"), learningService.computeMultiplier(new BigDecimal("92.50"), 5));
        assertEquals(new BigDecimal("1.100"), learningService.computeMultiplier(new BigDecimal("100.00"), 5));
        assertEquals(new BigDecimal("0.900"), learningService.computeMultiplier(new BigDecimal("0.00"), 5));
        assertEquals(new BigDecimal("1.000"), learningService.computeMultiplier(new BigDecimal("92.50"), 0));
    }

    @Test
    @DisplayName("Merchant Isolation - Throw 404 for Unknown Merchant ID")
    void testMerchantIsolation_NotFound() {
        assertThrows(ResourceNotFoundException.class, () -> {
            learningService.getMerchantStrategyLearning(99999L);
        });
    }
}
