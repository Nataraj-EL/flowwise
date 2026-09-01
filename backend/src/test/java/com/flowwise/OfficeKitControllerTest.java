package com.flowwise;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flowwise.dto.DocumentCaptureRequestDTO;
import com.flowwise.dto.DocumentCaptureResponseDTO;
import com.flowwise.dto.DocumentConfirmRequestDTO;
import com.flowwise.service.OfficeKitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OfficeKitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OfficeKitService officeKitService;

    @Test
    void testCreateCapture_Success() throws Exception {
        DocumentCaptureRequestDTO request = new DocumentCaptureRequestDTO(
                "RECEIPT", "sample_receipt.jpg", "data:image/jpeg;base64,demo", "image/jpeg", 1024L, null, null, null
        );

        mockMvc.perform(post("/api/v1/merchants/1/office-kit/captures")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.documentType").value("RECEIPT"))
                .andExpect(jsonPath("$.data.status").value("EXTRACTED"))
                .andExpect(jsonPath("$.data.extractedAmount").exists());
    }

    @Test
    void testGetMerchantCaptures_Success() throws Exception {
        mockMvc.perform(get("/api/v1/merchants/1/office-kit/captures")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void testConfirmCapture_Success() throws Exception {
        DocumentCaptureResponseDTO created = officeKitService.createCapture(1L, new DocumentCaptureRequestDTO("INVOICE", "invoice.pdf", null, "application/pdf", 1024L, null, null, null));

        DocumentConfirmRequestDTO request = new DocumentConfirmRequestDTO(
                new BigDecimal("2500.00"), "Vendor Test", "OPERATIONS", "REF-TEST"
        );

        mockMvc.perform(post("/api/v1/office-kit/captures/" + created.getId() + "/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.status").value("CONFIRMED"));
    }

    @Test
    void testDiscardCapture_Success() throws Exception {
        DocumentCaptureResponseDTO created = officeKitService.createCapture(1L, new DocumentCaptureRequestDTO("EXPENSE", "expense.jpg", null, "image/jpeg", 1024L, null, null, null));

        mockMvc.perform(post("/api/v1/office-kit/captures/" + created.getId() + "/discard")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.status").value("DISCARDED"));
    }

    @Test
    void testIngestCapture_Success() throws Exception {
        DocumentCaptureResponseDTO created = officeKitService.createCapture(1L, new DocumentCaptureRequestDTO("RECEIPT", "rec.jpg", null, "image/jpeg", 1024L, null, null, null));
        DocumentCaptureResponseDTO confirmed = officeKitService.confirmCapture(created.getId(), new DocumentConfirmRequestDTO(new BigDecimal("2450.00"), "Metro Supplies", "OPERATIONS", "REC-8841"));

        mockMvc.perform(post("/api/v1/office-kit/captures/" + confirmed.getId() + "/ingest")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.sourceType").value("OFFICE_KIT"))
                .andExpect(jsonPath("$.data.alreadyIngested").value(false));
    }
}
