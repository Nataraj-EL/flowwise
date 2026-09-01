package com.flowwise;

import com.flowwise.dto.EvaluationSummaryDTO;
import com.flowwise.service.EvaluationService;
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
class EvaluationServiceTest {

    @Autowired
    private EvaluationService evaluationService;

    @Test
    void testRunEvaluationSuite_Success() {
        EvaluationSummaryDTO summary = evaluationService.runEvaluationSuite();

        assertNotNull(summary);
        assertNotNull(summary.getRunId());
        assertEquals(15, summary.getTotalCases());
        assertEquals(15, summary.getCaseResults().size());

        assertNotNull(summary.getOverallScore());
        assertTrue(summary.getOverallScore().compareTo(BigDecimal.ZERO) >= 0);
        assertTrue(summary.getOverallScore().compareTo(new BigDecimal("100.0")) <= 0);

        assertNotNull(summary.getGroundingScore());
        assertNotNull(summary.getNumericalConsistencyScore());
        assertNotNull(summary.getRelevanceScore());
        assertNotNull(summary.getEvidenceCoverageScore());
        assertNotNull(summary.getFallbackRate());
        assertNotNull(summary.getAvgLatencyMs());
    }

    @Test
    void testGetLatestEvaluationSummary_Success() {
        evaluationService.runEvaluationSuite();
        EvaluationSummaryDTO summary = evaluationService.getLatestEvaluationSummary();

        assertNotNull(summary);
        assertEquals(15, summary.getTotalCases());
    }
}
