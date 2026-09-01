package com.flowwise;

import com.flowwise.dto.CommandCenterSnapshotDTO;
import com.flowwise.dto.IntelligenceResponseDTO;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.service.CommandCenterService;
import com.flowwise.service.FlowwiseIntelligenceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CommandCenterServiceTest {

    @Autowired
    private CommandCenterService commandCenterService;

    @Autowired
    private FlowwiseIntelligenceService intelligenceService;

    @Test
    void testGetCommandCenterSnapshot_Success() {
        CommandCenterSnapshotDTO snapshot = commandCenterService.getCommandCenterSnapshot(1L);

        assertNotNull(snapshot);
        assertNotNull(snapshot.getOverallFinancialStatus());
        assertTrue(snapshot.getOverallHealthScore() >= 0 && snapshot.getOverallHealthScore() <= 100);
        assertNotNull(snapshot.getAvailableCash());
        assertNotNull(snapshot.getNetCashFlow());
        assertNotNull(snapshot.getWorkingCapitalCoverage());
        assertNotNull(snapshot.getReceivablesPressure());
        assertNotNull(snapshot.getPayablesPressure());
        assertNotNull(snapshot.getForecastRisk());
        assertNotNull(snapshot.getTop3Priorities());
        assertTrue(snapshot.getTop3Priorities().size() <= 3);
        assertNotNull(snapshot.getKeyPositiveSignal());
        assertNotNull(snapshot.getKeyRiskSignal());
        assertNotNull(snapshot.getWhatChangedSummary());
        assertNotNull(snapshot.getGeneratedAt());
    }

    @Test
    void testAiGrounding_CommandCenterBriefingQueries() {
        IntelligenceResponseDTO response = intelligenceService.processMerchantQuery(1L, "What should I know today? Give me a financial briefing.");
        assertNotNull(response);
        assertNotNull(response.getAnswer());
        assertTrue(response.getAnswer().contains("Command Center") || response.getAnswer().contains("Briefing") || response.getAnswer().contains("Status") || response.getAnswer().contains("Recommendation"));
    }

    @Test
    void testGetCommandCenter_NotFoundThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            commandCenterService.getCommandCenterSnapshot(999L);
        });
    }
}
