package com.flowwise;

import com.flowwise.dto.*;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.service.FinancialDecisionIntelligenceService;
import com.flowwise.service.FlowwiseIntelligenceService;
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
class FinancialDecisionIntelligenceServiceTest {

    @Autowired
    private FinancialDecisionIntelligenceService intelligenceService;

    @Autowired
    private FlowwiseIntelligenceService aiService;

    @Test
    void testEvaluateDecisionIntelligence_ReturnsRankedOptionsAndScoreBreakdown() {
        DecisionAnalysisDTO analysis = intelligenceService.evaluateDecisionIntelligence(1L);
        assertNotNull(analysis);
        assertEquals("CURRENT_OPERATING_DECISION", analysis.getAnalysisKey());
        assertNotNull(analysis.getRecommendedOption());
        assertTrue(analysis.getBaselineScore().compareTo(BigDecimal.ZERO) > 0);
        assertNotNull(analysis.getOptions());
        assertEquals(5, analysis.getOptions().size());

        // Check Rank Order (Must be 1 to 5)
        for (int i = 0; i < analysis.getOptions().size(); i++) {
            assertEquals(i + 1, analysis.getOptions().get(i).getRankOrder());
        }

        // Verify Top Option match
        assertEquals(analysis.getRecommendedOption(), analysis.getOptions().get(0).getOptionKey());
    }

    @Test
    void testScoringWeightsMath_CompositeScoreEqualsWeightedSum() {
        DecisionAnalysisDTO analysis = intelligenceService.evaluateDecisionIntelligence(1L);
        DecisionOptionDTO opt = analysis.getOptions().get(0);

        // Composite = (liq * 0.25) + (cov * 0.20) + (goal * 0.25) + (risk * 0.15) + (urg * 0.15)
        BigDecimal expected = opt.getLiquidityScore().multiply(new BigDecimal("0.25"))
                .add(opt.getCoverageScore().multiply(new BigDecimal("0.20")))
                .add(opt.getGoalScore().multiply(new BigDecimal("0.25")))
                .add(opt.getRiskScore().multiply(new BigDecimal("0.15")))
                .add(opt.getUrgencyScore().multiply(new BigDecimal("0.15")))
                .setScale(2, BigDecimal.ROUND_HALF_UP);

        assertEquals(expected, opt.getCompositeScore());
    }

    @Test
    void testCrossMerchantAccess_ThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            intelligenceService.getMerchantDecisionAnalysis(999L);
        });
    }

    @Test
    void testAiGrounding_DecisionQueries() {
        IntelligenceResponseDTO response = aiService.processMerchantQuery(1L, "What should I do? Which option is safer?");
        assertNotNull(response);
        assertNotNull(response.getAnswer());
        assertTrue(response.getAnswer().contains("Decision") || response.getAnswer().contains("COLLECT_RECEIVABLES") || response.getAnswer().contains("Option") || response.getAnswer().contains("Analysis"));
    }
}
