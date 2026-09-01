package com.flowwise.service;

import com.flowwise.dto.DocumentCaptureRequestDTO;
import com.flowwise.dto.DocumentCaptureResponseDTO;
import com.flowwise.dto.DocumentConfirmRequestDTO;
import com.flowwise.entity.DocumentCapture;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.DocumentCaptureRepository;
import com.flowwise.repository.MerchantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OfficeKitService {

    private final MerchantRepository merchantRepository;
    private final DocumentCaptureRepository captureRepository;
    private final DocumentExtractionAdapter extractionAdapter;

    public OfficeKitService(MerchantRepository merchantRepository,
                            DocumentCaptureRepository captureRepository,
                            DocumentExtractionAdapter extractionAdapter) {
        this.merchantRepository = merchantRepository;
        this.captureRepository = captureRepository;
        this.extractionAdapter = extractionAdapter;
    }

    public DocumentCaptureResponseDTO createCapture(Long merchantId, DocumentCaptureRequestDTO requestDTO) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }

        DocumentCapture capture = new DocumentCapture();
        capture.setMerchantId(merchantId);
        capture.setDocumentType(requestDTO != null && requestDTO.getDocumentType() != null ? requestDTO.getDocumentType() : "RECEIPT");
        capture.setFileName(requestDTO != null ? requestDTO.getFileName() : "photo_receipt.jpg");
        capture.setFileType(requestDTO != null && requestDTO.getFileType() != null ? requestDTO.getFileType() : "image/jpeg");
        capture.setFileSize(requestDTO != null && requestDTO.getFileSize() != null ? requestDTO.getFileSize() : 1024L);
        capture.setFileUrlOrData(requestDTO != null ? requestDTO.getFileData() : null);
        capture.setStatus("CAPTURED");

        if (requestDTO != null) {
            capture.setExtractedAmount(requestDTO.getAmount());
            capture.setExtractedVendor(requestDTO.getVendorName());
            capture.setExtractedCategory(requestDTO.getCategory());
        }

        // Run Pluggable Extraction Adapter
        DocumentExtractionResult extractionResult = extractionAdapter.extract(capture);

        capture.setExtractedAmount(extractionResult.getAmount());
        capture.setExtractedVendor(extractionResult.getVendorName());
        capture.setExtractedCategory(extractionResult.getCategory());
        capture.setExtractedTax(extractionResult.getTax());
        capture.setExtractedReference(extractionResult.getReference());
        capture.setExtractedDate(extractionResult.getExtractedDate());
        capture.setStatus("EXTRACTED");

        DocumentCapture saved = captureRepository.save(capture);
        return mapToDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<DocumentCaptureResponseDTO> getMerchantCaptures(Long merchantId) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }

        return captureRepository.findByMerchantIdOrderByCapturedAtDesc(merchantId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DocumentCaptureResponseDTO getCaptureDetail(Long captureId) {
        DocumentCapture capture = captureRepository.findById(captureId)
                .orElseThrow(() -> new ResourceNotFoundException("Document capture not found with ID: " + captureId));
        return mapToDTO(capture);
    }

    public DocumentCaptureResponseDTO confirmCapture(Long captureId, DocumentConfirmRequestDTO confirmDTO) {
        DocumentCapture capture = captureRepository.findById(captureId)
                .orElseThrow(() -> new ResourceNotFoundException("Document capture not found with ID: " + captureId));

        if (confirmDTO != null) {
            if (confirmDTO.getAmount() != null) capture.setExtractedAmount(confirmDTO.getAmount());
            if (confirmDTO.getVendorName() != null) capture.setExtractedVendor(confirmDTO.getVendorName());
            if (confirmDTO.getCategory() != null) capture.setExtractedCategory(confirmDTO.getCategory());
            if (confirmDTO.getReference() != null) capture.setExtractedReference(confirmDTO.getReference());
        }

        capture.setStatus("CONFIRMED");
        DocumentCapture saved = captureRepository.save(capture);
        return mapToDTO(saved);
    }

    public DocumentCaptureResponseDTO discardCapture(Long captureId) {
        DocumentCapture capture = captureRepository.findById(captureId)
                .orElseThrow(() -> new ResourceNotFoundException("Document capture not found with ID: " + captureId));

        capture.setStatus("DISCARDED");
        DocumentCapture saved = captureRepository.save(capture);
        return mapToDTO(saved);
    }

    private DocumentCaptureResponseDTO mapToDTO(DocumentCapture entity) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        return new DocumentCaptureResponseDTO(
                entity.getId(),
                entity.getMerchantId(),
                entity.getDocumentType(),
                entity.getFileName(),
                entity.getFileType(),
                entity.getFileSize(),
                entity.getFileUrlOrData(),
                entity.getCapturedAt() != null ? formatter.format(entity.getCapturedAt()) : "",
                entity.getStatus(),
                entity.getExtractedAmount(),
                entity.getExtractedVendor(),
                entity.getExtractedCategory(),
                entity.getExtractedDate() != null ? formatter.format(entity.getExtractedDate()) : "",
                entity.getExtractedTax(),
                entity.getExtractedReference(),
                entity.getCreatedAt() != null ? formatter.format(entity.getCreatedAt()) : "",
                entity.getUpdatedAt() != null ? formatter.format(entity.getUpdatedAt()) : ""
        );
    }
}
