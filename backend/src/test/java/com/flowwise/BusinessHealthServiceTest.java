package com.flowwise;

import com.flowwise.dto.BusinessHealthDTO;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.service.BusinessHealthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class BusinessHealthServiceTest {

    @Autowired
    private BusinessHealthService businessHealthService;

    @Test
    void testCalculateBusinessHealth_Success() {
        BusinessHealthDTO health = businessHealthService.calculateBusinessHealth(1L);

        assertNotNull(health);
        assertTrue(health.getOverallScore() >= 0 && health.getOverallScore() <= 100);
        assertNotNull(health.getHealthStatus());
        assertTrue(health.getHealthStatus().equals("HEALTHY") || health.getHealthStatus().equals("WATCH") || health.getHealthStatus().equals("AT_RISK"));

        assertNotNull(health.getFactorScores());
        assertEquals(5, health.getFactorScores().size());

        assertNotNull(health.getPositiveSignals());
        assertNotNull(health.getRiskSignals());
        assertNotNull(health.getSummaryExplanation());
    }

    @Test
    void testCalculateBusinessHealth_ThresholdStatusMapping() {
        BusinessHealthDTO health = businessHealthService.calculateBusinessHealth(1L);
        int score = health.getOverallScore();

        if (score >= 75) {
            assertEquals("HEALTHY", health.getHealthStatus());
        } else if (score >= 50) {
            assertEquals("WATCH", health.getHealthStatus());
        } else {
            assertEquals("AT_RISK", health.getHealthStatus());
        }
    }

    @Test
    void testCalculateBusinessHealth_NotFoundThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            businessHealthService.calculateBusinessHealth(999L);
        });
    }
}
