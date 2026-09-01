package com.flowwise;

import com.flowwise.dto.MerchantDetailDTO;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.service.MerchantService;
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
class MerchantServiceTest {

    @Autowired
    private MerchantService merchantService;

    @Test
    void testGetPrimaryMerchantDetail_Success() {
        MerchantDetailDTO detail = merchantService.getMerchantDetail(1L);

        assertNotNull(detail);
        assertNotNull(detail.getMerchant());
        assertEquals("Apex Retail Solutions [DEMO]", detail.getMerchant().getBusinessName());
        assertEquals(3, detail.getConnectedAccountsCount());
        
        // Expected sum: 210000 + 74300 + 40000 = 324300.00
        assertEquals(0, new BigDecimal("324300.00").compareTo(detail.getTotalAvailableCash()));
    }

    @Test
    void testGetMerchantDetail_NotFoundThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            merchantService.getMerchantDetail(999L);
        });
    }
}
