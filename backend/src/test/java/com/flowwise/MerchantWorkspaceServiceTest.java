package com.flowwise;

import com.flowwise.dto.AccountDetailDTO;
import com.flowwise.dto.MerchantWorkspaceDTO;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.service.MerchantWorkspaceService;
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
class MerchantWorkspaceServiceTest {

    @Autowired
    private MerchantWorkspaceService workspaceService;

    @Test
    void testGetMerchantWorkspace_Success() {
        MerchantWorkspaceDTO workspace = workspaceService.getMerchantWorkspace(1L);

        assertNotNull(workspace);
        assertEquals(1L, workspace.getMerchantId());
        assertNotNull(workspace.getBusinessName());
        assertNotNull(workspace.getDemoGstin());
        assertNotNull(workspace.getTotalAvailableCash());
        assertTrue(workspace.getConnectedAccountsCount() > 0);
        assertNotNull(workspace.getAccounts());
        assertEquals(workspace.getConnectedAccountsCount(), workspace.getAccounts().size());

        // Verify account contribution shares sum to ~100%
        BigDecimal totalShare = workspace.getAccounts().stream()
                .map(AccountDetailDTO::getCashContributionPct)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        assertTrue(totalShare.compareTo(new BigDecimal("99.00")) >= 0 && totalShare.compareTo(new BigDecimal("101.00")) <= 0);
    }

    @Test
    void testGetAccountSummary_Success() {
        AccountDetailDTO summary = workspaceService.getAccountSummary(1L, 1L);

        assertNotNull(summary);
        assertEquals(1L, summary.getAccountId());
        assertNotNull(summary.getInstitutionName());
        assertNotNull(summary.getCurrentBalance());
        assertNotNull(summary.getCashContributionPct());
    }

    @Test
    void testGetWorkspace_NotFoundThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            workspaceService.getMerchantWorkspace(999L);
        });
    }

    @Test
    void testGetAccountSummary_MismatchThrowsException() {
        // Account 1 belongs to Merchant 1, so requesting with Merchant 999 should throw ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> {
            workspaceService.getAccountSummary(999L, 1L);
        });
    }
}
