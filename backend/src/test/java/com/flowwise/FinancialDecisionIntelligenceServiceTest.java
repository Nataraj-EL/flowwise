package com.flowwise;

import com.flowwise.dto.FinancialDecisionDTO;
import com.flowwise.dto.FinancialDecisionSummaryDTO;
import com.flowwise.entity.Merchant;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.MerchantRepository;
import com.flowwise.service.FinancialDecisionIntelligenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class FinancialDecisionIntelligenceServiceTest {

    @Autowired
    private FinancialDecisionIntelligenceService decisionIntelligenceService;

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
    @DisplayName("Evaluate Decisions - 5-Factor Ranking & Idempotency")
    void testEvaluateDecisions() {
        FinancialDecisionSummaryDTO summary = decisionIntelligenceService.evaluateDecisions(testMerchant.getId());

        assertNotNull(summary);
        assertEquals(testMerchant.getId(), summary.getMerchantId());
        assertNotNull(summary.getTopRecommendation());
        assertTrue(summary.getTopDecisionScore().compareTo(java.math.BigDecimal.ZERO) > 0);
        assertTrue(summary.getTopRecommendation().getEvidenceMetrics().contains("ADVISORY_RECOMMENDATION"));

        // Idempotency check
        FinancialDecisionSummaryDTO summary2 = decisionIntelligenceService.evaluateDecisions(testMerchant.getId());
        assertEquals(summary.getTopRecommendation().getId(), summary2.getTopRecommendation().getId());
    }

    @Test
    @DisplayName("Acknowledge & Complete Decision - Lifecycle Transitions")
    void testAcknowledgeAndCompleteDecision() {
        FinancialDecisionSummaryDTO summary = decisionIntelligenceService.evaluateDecisions(testMerchant.getId());
        Long decisionId = summary.getTopRecommendation().getId();

        FinancialDecisionDTO ack = decisionIntelligenceService.acknowledgeDecision(testMerchant.getId(), decisionId);
        assertEquals("ACKNOWLEDGED", ack.getStatus());

        FinancialDecisionDTO comp = decisionIntelligenceService.completeDecision(testMerchant.getId(), decisionId);
        assertEquals("COMPLETED", comp.getStatus());
        assertEquals("POSITIVE", comp.getOutcomeStatus());
    }

    @Test
    @DisplayName("Merchant Isolation - Throw 404 for Unknown Merchant ID")
    void testMerchantIsolation_NotFound() {
        assertThrows(ResourceNotFoundException.class, () -> {
            decisionIntelligenceService.getMerchantDecisionSummary(99999L);
        });
    }
}
