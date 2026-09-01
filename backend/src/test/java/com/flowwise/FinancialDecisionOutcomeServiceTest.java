package com.flowwise;

import com.flowwise.dto.DecisionLearningDTO;
import com.flowwise.dto.FinancialDecisionOutcomeDTO;
import com.flowwise.dto.FinancialDecisionOutcomeSummaryDTO;
import com.flowwise.entity.FinancialDecision;
import com.flowwise.entity.Merchant;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.FinancialDecisionRepository;
import com.flowwise.repository.MerchantRepository;
import com.flowwise.service.FinancialDecisionOutcomeService;
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
public class FinancialDecisionOutcomeServiceTest {

    @Autowired
    private FinancialDecisionOutcomeService outcomeService;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private FinancialDecisionRepository decisionRepository;

    private Merchant testMerchant;
    private FinancialDecision completedDecision;

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

        completedDecision = decisionRepository.findById(4L).map(d -> {
            d.setStatus("COMPLETED");
            d.setDecisionStatus("COMPLETED");
            return decisionRepository.save(d);
        }).orElseGet(() -> {
            FinancialDecision d = new FinancialDecision(
                    testMerchant, "DECISION_COMPLETED_TEST", "INTERVENTION_EXECUTION",
                    "Completed Decision Title", "Recommendation Text", "COMPLETED",
                    new BigDecimal("92.45"), new BigDecimal("88.50"), new BigDecimal("95.00"),
                    new BigDecimal("90.00"), new BigDecimal("89.00"), "Benefit", "Risk",
                    1L, 1L, 1L, "Evidence", "Assumptions", "Tradeoffs", "HIGH"
            );
            d.setStatus("COMPLETED");
            d.setDecisionStatus("COMPLETED");
            return decisionRepository.save(d);
        });
    }

    @Test
    @DisplayName("Evaluate Decision Outcome - SUCCESSFUL Classification & Multiplier Bounds")
    void testEvaluateDecisionOutcome_Success() {
        FinancialDecisionOutcomeDTO outcome = outcomeService.evaluateDecisionOutcome(testMerchant.getId(), completedDecision.getId(), "30D");

        assertNotNull(outcome);
        assertEquals("SUCCESSFUL", outcome.getOutcomeStatus());
        assertEquals("30D", outcome.getEvaluationWindow());
        assertTrue(outcome.getEffectivenessScore().compareTo(new BigDecimal("80.00")) >= 0);

        List<DecisionLearningDTO> learnings = outcomeService.getDecisionLearnings(testMerchant.getId());
        assertFalse(learnings.isEmpty());
        BigDecimal mult = learnings.get(0).getLearningMultiplier();
        assertTrue(mult.compareTo(new BigDecimal("0.900")) >= 0 && mult.compareTo(new BigDecimal("1.100")) <= 0);
    }

    @Test
    @DisplayName("Idempotent Outcome Evaluation & Outcome Immutability")
    void testOutcomeEvaluation_Idempotency() {
        FinancialDecisionOutcomeDTO outcome1 = outcomeService.evaluateDecisionOutcome(testMerchant.getId(), completedDecision.getId(), "30D");
        FinancialDecisionOutcomeDTO outcome2 = outcomeService.evaluateDecisionOutcome(testMerchant.getId(), completedDecision.getId(), "30D");

        assertEquals(outcome1.getId(), outcome2.getId());
        assertEquals(outcome1.getEffectivenessScore(), outcome2.getEffectivenessScore());
    }

    @Test
    @DisplayName("Merchant Isolation - Throw 404 for Invalid Merchant")
    void testMerchantIsolation_NotFound() {
        assertThrows(ResourceNotFoundException.class, () -> {
            outcomeService.getOutcomeSummary(99999L, "30D");
        });
    }
}
