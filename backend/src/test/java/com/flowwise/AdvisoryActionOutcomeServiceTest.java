package com.flowwise;

import com.flowwise.dto.AdvisoryActionLearningDTO;
import com.flowwise.dto.AdvisoryActionOutcomeDTO;
import com.flowwise.dto.AdvisoryActionOutcomeSummaryDTO;
import com.flowwise.entity.AdvisoryActionPlan;
import com.flowwise.entity.AdvisoryActionPlanStep;
import com.flowwise.entity.Merchant;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.AdvisoryActionPlanRepository;
import com.flowwise.repository.AdvisoryActionPlanStepRepository;
import com.flowwise.repository.MerchantRepository;
import com.flowwise.service.AdvisoryActionOutcomeService;
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
public class AdvisoryActionOutcomeServiceTest {

    @Autowired
    private AdvisoryActionOutcomeService outcomeService;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private AdvisoryActionPlanRepository planRepository;

    @Autowired
    private AdvisoryActionPlanStepRepository stepRepository;

    private Merchant testMerchant;
    private AdvisoryActionPlan completedPlan;
    private AdvisoryActionPlanStep completedStep;

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

        completedPlan = planRepository.findById(1L).orElseGet(() -> {
            AdvisoryActionPlan p = new AdvisoryActionPlan(
                    testMerchant, "PLAN_COMPLETED_TEST", "30D", "COMPLETED",
                    new BigDecimal("93.50"), 1, 1, 0, "Action Title", "Benefit", "Risk",
                    "Evidence", "Assumptions"
            );
            return planRepository.save(p);
        });

        completedStep = stepRepository.findById(1L).map(s -> {
            s.setReadinessStatus("COMPLETED");
            return stepRepository.save(s);
        }).orElseGet(() -> {
            AdvisoryActionPlanStep s = new AdvisoryActionPlanStep(
                    completedPlan, "STEP_COMPLETED_TEST", 1, "COLLECT_RECEIVABLES",
                    "Step Title", "Description", "COMPLETED", new BigDecimal("94.20"),
                    new BigDecimal("92.45"), new BigDecimal("88.50"), new BigDecimal("90.00"),
                    new BigDecimal("100.00"), "HIGH", "LOW", "Prereq", "Outcome", "Evidence"
            );
            s.setReadinessStatus("COMPLETED");
            return stepRepository.save(s);
        });
    }

    @Test
    @DisplayName("Evaluate Action Outcome - SUCCESSFUL Classification & Multiplier Bounds")
    void testEvaluateActionOutcome_Success() {
        AdvisoryActionOutcomeDTO outcome = outcomeService.evaluateActionOutcome(testMerchant.getId(), completedPlan.getId(), completedStep.getId(), "30D");

        assertNotNull(outcome);
        assertEquals("SUCCESSFUL", outcome.getOutcomeStatus());
        assertEquals("30D", outcome.getEvaluationWindow());
        assertTrue(outcome.getEffectivenessScore().compareTo(new BigDecimal("80.00")) >= 0);

        List<AdvisoryActionLearningDTO> learnings = outcomeService.getActionLearnings(testMerchant.getId());
        assertFalse(learnings.isEmpty());
        BigDecimal mult = learnings.get(0).getLearningMultiplier();
        assertTrue(mult.compareTo(new BigDecimal("0.900")) >= 0 && mult.compareTo(new BigDecimal("1.100")) <= 0);
    }

    @Test
    @DisplayName("Idempotent Outcome Evaluation & Outcome Immutability")
    void testOutcomeEvaluation_Idempotency() {
        AdvisoryActionOutcomeDTO outcome1 = outcomeService.evaluateActionOutcome(testMerchant.getId(), completedPlan.getId(), completedStep.getId(), "30D");
        AdvisoryActionOutcomeDTO outcome2 = outcomeService.evaluateActionOutcome(testMerchant.getId(), completedPlan.getId(), completedStep.getId(), "30D");

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
