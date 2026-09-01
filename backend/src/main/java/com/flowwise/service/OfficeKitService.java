package com.flowwise.service;

import com.flowwise.dto.DocumentCaptureRequestDTO;
import com.flowwise.dto.DocumentCaptureResponseDTO;
import com.flowwise.dto.DocumentConfirmRequestDTO;
import com.flowwise.dto.DocumentIngestResponseDTO;
import com.flowwise.entity.BusinessAccount;
import com.flowwise.entity.DocumentCapture;
import com.flowwise.entity.Merchant;
import com.flowwise.entity.Transaction;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.BusinessAccountRepository;
import com.flowwise.repository.DocumentCaptureRepository;
import com.flowwise.repository.MerchantRepository;
import com.flowwise.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OfficeKitService {

    private final MerchantRepository merchantRepository;
    private final BusinessAccountRepository businessAccountRepository;
    private final TransactionRepository transactionRepository;
    private final DocumentCaptureRepository captureRepository;
    private final DocumentExtractionAdapter extractionAdapter;
    private final TransactionClassificationService classificationService;

    public OfficeKitService(MerchantRepository merchantRepository,
                            BusinessAccountRepository businessAccountRepository,
                            TransactionRepository transactionRepository,
                            DocumentCaptureRepository captureRepository,
                            DocumentExtractionAdapter extractionAdapter,
                            TransactionClassificationService classificationService) {
        this.merchantRepository = merchantRepository;
        this.businessAccountRepository = businessAccountRepository;
        this.transactionRepository = transactionRepository;
        this.captureRepository = captureRepository;
        this.extractionAdapter = extractionAdapter;
        this.classificationService = classificationService;
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

    public DocumentIngestResponseDTO ingestCapture(Long captureId) {
        DocumentCapture capture = captureRepository.findById(captureId)
                .orElseThrow(() -> new ResourceNotFoundException("Document capture not found with ID: " + captureId));

        // 1. Validation Rule: Only CONFIRMED captures can be ingested!
        if (!"CONFIRMED".equalsIgnoreCase(capture.getStatus())) {
            throw new IllegalStateException("Only CONFIRMED document captures can be ingested into the financial ledger. Current status: " + capture.getStatus());
        }

        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

        // 2. Idempotency Rule: Prevent Duplicate Ingestion
        if (capture.getIngested() || transactionRepository.existsBySourceCaptureId(captureId)) {
            Optional<Transaction> existingTxOpt = transactionRepository.findBySourceCaptureId(captureId);
            if (existingTxOpt.isPresent()) {
                Transaction existingTx = existingTxOpt.get();
                return new DocumentIngestResponseDTO(
                        capture.getId(),
                        existingTx.getId(),
                        capture.getMerchantId(),
                        existingTx.getAmount(),
                        existingTx.getCategory(),
                        existingTx.getCounterparty(),
                        existingTx.getTransactionReference(),
                        existingTx.getSourceType(),
                        existingTx.getIngestionTimestamp() != null ? existingTx.getIngestionTimestamp().toString() : "",
                        true // already ingested
                );
            }
        }

        Merchant merchant = merchantRepository.findById(capture.getMerchantId())
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found with ID: " + capture.getMerchantId()));

        List<BusinessAccount> accounts = businessAccountRepository.findByMerchantId(merchant.getId());
        BusinessAccount account = accounts.isEmpty() ? null : accounts.get(0);
        if (account == null) {
            throw new IllegalStateException("No connected business account found for merchant ID: " + merchant.getId());
        }

        // Create New Transaction
        Transaction tx = new Transaction();
        tx.setMerchant(merchant);
        tx.setBusinessAccount(account);
        tx.setTransactionReference(capture.getExtractedReference() != null ? capture.getExtractedReference() : "TX-OK-" + captureId);
        tx.setTransactionDate(capture.getExtractedDate() != null ? capture.getExtractedDate().toInstant() : Instant.now());
        tx.setDescription("[OFFICE KIT] " + capture.getDocumentType() + " - " + (capture.getExtractedVendor() != null ? capture.getExtractedVendor() : "Vendor Scan"));
        tx.setAmount(capture.getExtractedAmount());
        tx.setType("DEBIT");
        tx.setCategory(capture.getExtractedCategory() != null ? capture.getExtractedCategory() : "OPERATIONS");
        tx.setCounterparty(capture.getExtractedVendor() != null ? capture.getExtractedVendor() : "Merchant Capture");
        tx.setPaymentMethod("OFFICE_KIT_SCAN");
        tx.setStatus("SETTLED");

        // Transaction Audit Provenance
        tx.setSourceType("OFFICE_KIT");
        tx.setSourceCaptureId(captureId);
        tx.setIngestionTimestamp(Instant.now());

        // Run Classification & Categorization Engine
        String inferredCategory = classificationService.classifyTransaction(tx.getDescription(), tx.getCounterparty(), tx.getType());
        if (tx.getCategory() == null || tx.getCategory().isBlank() || "OTHER".equalsIgnoreCase(tx.getCategory())) {
            tx.setCategory(inferredCategory);
        } else {
            tx.setSubcategory(inferredCategory);
        }

        Transaction savedTx = transactionRepository.save(tx);

        // Update Document Capture Entity
        capture.setIngested(true);
        capture.setIngestedTransactionId(savedTx.getId());
        capture.setIngestedAt(OffsetDateTime.now());
        captureRepository.save(capture);

        return new DocumentIngestResponseDTO(
                capture.getId(),
                savedTx.getId(),
                merchant.getId(),
                savedTx.getAmount(),
                savedTx.getCategory(),
                savedTx.getCounterparty(),
                savedTx.getTransactionReference(),
                savedTx.getSourceType(),
                capture.getIngestedAt() != null ? formatter.format(capture.getIngestedAt()) : "",
                false // newly ingested
        );
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
