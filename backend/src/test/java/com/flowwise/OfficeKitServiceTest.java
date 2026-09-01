package com.flowwise;

import com.flowwise.dto.DocumentCaptureRequestDTO;
import com.flowwise.dto.DocumentCaptureResponseDTO;
import com.flowwise.dto.DocumentConfirmRequestDTO;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.service.OfficeKitService;
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
class OfficeKitServiceTest {

    @Autowired
    private OfficeKitService officeKitService;

    @Test
    void testCreateCapture_Success() {
        DocumentCaptureRequestDTO request = new DocumentCaptureRequestDTO(
                "RECEIPT", "receipt_store.jpg", "data:image/jpeg;base64,demo", "image/jpeg", 2048L, null, null, null
        );

        DocumentCaptureResponseDTO response = officeKitService.createCapture(1L, request);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals("RECEIPT", response.getDocumentType());
        assertEquals("EXTRACTED", response.getStatus());
        assertNotNull(response.getExtractedAmount());
        assertNotNull(response.getExtractedVendor());
        assertNotNull(response.getExtractedCategory());
    }

    @Test
    void testGetMerchantCaptures_Success() {
        officeKitService.createCapture(1L, new DocumentCaptureRequestDTO("INVOICE", "invoice.pdf", null, "application/pdf", 1024L, null, null, null));
        List<DocumentCaptureResponseDTO> list = officeKitService.getMerchantCaptures(1L);

        assertNotNull(list);
        assertFalse(list.isEmpty());
    }

    @Test
    void testConfirmCapture_Success() {
        DocumentCaptureResponseDTO created = officeKitService.createCapture(1L, new DocumentCaptureRequestDTO("EXPENSE", "fuel.jpg", null, "image/jpeg", 1024L, null, null, null));

        DocumentConfirmRequestDTO confirmRequest = new DocumentConfirmRequestDTO(
                new BigDecimal("1500.00"), "Custom Vendor [DEMO]", "OPERATIONS", "REF-CUSTOM"
        );

        DocumentCaptureResponseDTO confirmed = officeKitService.confirmCapture(created.getId(), confirmRequest);

        assertNotNull(confirmed);
        assertEquals("CONFIRMED", confirmed.getStatus());
        assertEquals(0, new BigDecimal("1500.00").compareTo(confirmed.getExtractedAmount()));
        assertEquals("Custom Vendor [DEMO]", confirmed.getExtractedVendor());
    }

    @Test
    void testDiscardCapture_Success() {
        DocumentCaptureResponseDTO created = officeKitService.createCapture(1L, new DocumentCaptureRequestDTO("RECEIPT", "receipt.jpg", null, "image/jpeg", 1024L, null, null, null));
        DocumentCaptureResponseDTO discarded = officeKitService.discardCapture(created.getId());

        assertNotNull(discarded);
        assertEquals("DISCARDED", discarded.getStatus());
    }

    @Test
    void testCreateCapture_NotFoundThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            officeKitService.createCapture(999L, new DocumentCaptureRequestDTO());
        });
    }
}
