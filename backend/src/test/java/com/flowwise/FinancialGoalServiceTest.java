package com.flowwise;

import com.flowwise.dto.CreateGoalRequestDTO;
import com.flowwise.dto.FinancialGoalDTO;
import com.flowwise.dto.IntelligenceResponseDTO;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.service.FinancialGoalService;
import com.flowwise.service.FlowwiseIntelligenceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class FinancialGoalServiceTest {

    @Autowired
    private FinancialGoalService goalService;

    @Autowired
    private FlowwiseIntelligenceService intelligenceService;

    @Test
    void testCreateAndEvaluateAccumulationGoal_Success() {
        CreateGoalRequestDTO req = new CreateGoalRequestDTO(
                "CASH_RESERVE",
                "Q4 Cash Safety Buffer",
                new BigDecimal("600000.00"),
                LocalDate.now().plusMonths(6)
        );

        FinancialGoalDTO created = goalService.createGoal(1L, req);

        assertNotNull(created);
        assertEquals("CASH_RESERVE", created.getGoalType());
        assertEquals("ACCUMULATION", created.getGoalCategoryType());
        assertNotNull(created.getProgressPct());
        assertNotNull(created.getRequiredMonthlyPace());
        assertTrue(created.getDaysRemaining() > 0);
    }

    @Test
    void testCreateAndEvaluateReductionGoal_Success() {
        CreateGoalRequestDTO req = new CreateGoalRequestDTO(
                "DEBT_REDUCTION",
                "Payable Debt Clearance",
                new BigDecimal("50000.00"),
                LocalDate.now().plusMonths(3)
        );

        FinancialGoalDTO created = goalService.createGoal(1L, req);

        assertNotNull(created);
        assertEquals("DEBT_REDUCTION", created.getGoalType());
        assertEquals("REDUCTION", created.getGoalCategoryType());
        assertNotNull(created.getRemainingAmount());
    }

    @Test
    void testGetMerchantGoals_Success() {
        List<FinancialGoalDTO> goals = goalService.getMerchantGoals(1L);
        assertNotNull(goals);
        assertFalse(goals.isEmpty());
    }

    @Test
    void testCrossMerchantAccess_ThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            goalService.getGoalById(2L, 1L); // Merchant 2 accessing Merchant 1's goal
        });
    }

    @Test
    void testAiGrounding_GoalQueries() {
        IntelligenceResponseDTO response = intelligenceService.processMerchantQuery(1L, "How am I doing against my goal?");
        assertNotNull(response);
        assertNotNull(response.getAnswer());
        assertTrue(response.getAnswer().contains("Goal") || response.getAnswer().contains("Progress") || response.getAnswer().contains("Target") || response.getAnswer().contains("Summary"));
    }

    @Test
    void testGoal_NotFoundThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            goalService.getGoalById(1L, 999L);
        });
    }
}
