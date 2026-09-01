package com.flowwise.service;

import com.flowwise.dto.ReconciliationIssueDTO;
import com.flowwise.dto.ReconciliationSummaryDTO;
import com.flowwise.dto.TransactionDTO;
import com.flowwise.entity.DocumentCapture;
import com.flowwise.entity.Transaction;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.DocumentCaptureRepository;
import com.flowwise.repository.MerchantRepository;
import com.flowwise.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
public class ReconciliationService {

    private final MerchantRepository merchantRepository;
    private final TransactionRepository transactionRepository;
    private final DocumentCaptureRepository captureRepository;
    private final TransactionService transactionService;

    public ReconciliationService(MerchantRepository merchantRepository,
                                 TransactionRepository transactionRepository,
                                 DocumentCaptureRepository captureRepository,
                                 TransactionService transactionService) {
        this.merchantRepository = merchantRepository;
        this.transactionRepository = transactionRepository;
        this.captureRepository = captureRepository;
        this.transactionService = transactionService;
    }

    @Transactional(readOnly = true)
    public ReconciliationSummaryDTO getReconciliationSummary(Long merchantId) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }

        List<Transaction> txs = transactionRepository.findByMerchantIdOrderByTransactionDateDesc(merchantId);
        List<DocumentCapture> captures = captureRepository.findByMerchantIdOrderByCapturedAtDesc(merchantId);

        int totalTxCount = txs.size();
        int reconciledCount = 0;
        int unreviewedCount = 0;
        int ignoredCount = 0;

        for (Transaction t : txs) {
            String st = t.getReconciliationStatus();
            if ("RECONCILED".equalsIgnoreCase(st)) {
                reconciledCount++;
            } else if ("IGNORED".equalsIgnoreCase(st)) {
                ignoredCount++;
            } else {
                unreviewedCount++;
            }
        }

        List<DocumentCapture> pendingCaptures = captures.stream()
                .filter(c -> "CAPTURED".equalsIgnoreCase(c.getStatus()) || "EXTRACTED".equalsIgnoreCase(c.getStatus()))
                .toList();

        List<ReconciliationIssueDTO> issues = new ArrayList<>();
        int duplicateCount = 0;
        int uncategorizedCount = 0;
        int suspiciousCount = 0;

        // 1. Detect Duplicates: same amount & counterparty within 3 days
        Map<String, List<Transaction>> grouped = new HashMap<>();
        for (Transaction t : txs) {
            String key = t.getAmount().stripTrailingZeros().toPlainString() + "_" + t.getCounterparty().toLowerCase().trim();
            grouped.computeIfAbsent(key, k -> new ArrayList<>()).add(t);
        }

        DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE.withZone(ZoneId.systemDefault());

        for (List<Transaction> group : grouped.values()) {
            if (group.size() > 1) {
                for (int i = 0; i < group.size(); i++) {
                    for (int j = i + 1; j < group.size(); j++) {
                        Transaction t1 = group.get(i);
                        Transaction t2 = group.get(j);
                        long daysBetween = Math.abs(Duration.between(t1.getTransactionDate(), t2.getTransactionDate()).toDays());
                        if (daysBetween <= 3) {
                            duplicateCount++;
                            issues.add(new ReconciliationIssueDTO(
                                    "DUP-" + t1.getId() + "-" + t2.getId(),
                                    t1.getId(),
                                    "DUPLICATE",
                                    "HIGH",
                                    "Potential duplicate of Tx #" + t2.getTransactionReference() + " (" + t2.getCounterparty() + ", ₹" + t2.getAmount() + ")",
                                    t1.getCounterparty(),
                                    t1.getAmount(),
                                    dtf.format(t1.getTransactionDate()),
                                    t1.getReconciliationStatus() != null ? t1.getReconciliationStatus() : "UNREVIEWED",
                                    "Identical amount ₹" + t1.getAmount() + " and counterparty within " + daysBetween + " days."
                            ));
                            break;
                        }
                    }
                }
            }
        }

        // 2. Detect Uncategorized
        for (Transaction t : txs) {
            if (t.getCategory() == null || t.getCategory().trim().isEmpty() || "UNCATEGORIZED".equalsIgnoreCase(t.getCategory())) {
                uncategorizedCount++;
                issues.add(new ReconciliationIssueDTO(
                        "UNCAT-" + t.getId(),
                        t.getId(),
                        "UNCATEGORIZED",
                        "MEDIUM",
                        "Transaction missing financial classification",
                        t.getCounterparty(),
                        t.getAmount(),
                        dtf.format(t.getTransactionDate()),
                        t.getReconciliationStatus() != null ? t.getReconciliationStatus() : "UNREVIEWED",
                        "Tx #" + t.getTransactionReference() + " requires category mapping."
                ));
            }

            // 3. Detect Suspicious Amounts (> ₹1,00,000 DEBIT)
            if ("DEBIT".equalsIgnoreCase(t.getType()) && t.getAmount().compareTo(new BigDecimal("100000.00")) > 0) {
                suspiciousCount++;
                issues.add(new ReconciliationIssueDTO(
                        "SUSP-" + t.getId(),
                        t.getId(),
                        "SUSPICIOUS_AMOUNT",
                        "HIGH",
                        "Large high-value outflow of ₹" + t.getAmount(),
                        t.getCounterparty(),
                        t.getAmount(),
                        dtf.format(t.getTransactionDate()),
                        t.getReconciliationStatus() != null ? t.getReconciliationStatus() : "UNREVIEWED",
                        "Outflow exceeds ₹1,00,000 threshold for counterparty " + t.getCounterparty() + "."
                ));
            }
        }

        // 4. Add Office Kit Pending Review Captures
        for (DocumentCapture cap : pendingCaptures) {
            issues.add(new ReconciliationIssueDTO(
                    "OKIT-" + cap.getId(),
                    null,
                    "OFFICE_KIT_PENDING",
                    "MEDIUM",
                    "Captured " + cap.getDocumentType() + " awaiting merchant review/ingestion",
                    cap.getExtractedVendor() != null ? cap.getExtractedVendor() : "Office Kit Capture",
                    cap.getExtractedAmount() != null ? cap.getExtractedAmount() : BigDecimal.ZERO,
                    dtf.format(cap.getCapturedAt()),
                    "UNREVIEWED",
                    "Office Kit Capture #" + cap.getId() + " (" + cap.getFileName() + ") pending ingestion."
            ));
        }

        BigDecimal healthPct = BigDecimal.ZERO;
        if (totalTxCount > 0) {
            healthPct = BigDecimal.valueOf(reconciledCount + ignoredCount)
                    .multiply(new BigDecimal("100"))
                    .divide(BigDecimal.valueOf(totalTxCount), 2, RoundingMode.HALF_UP);
        }

        return new ReconciliationSummaryDTO(
                totalTxCount,
                reconciledCount,
                unreviewedCount,
                ignoredCount,
                issues.size(),
                duplicateCount,
                uncategorizedCount,
                suspiciousCount,
                pendingCaptures.size(),
                healthPct,
                issues
        );
    }

    @Transactional(readOnly = true)
    public List<ReconciliationIssueDTO> getReconciliationIssues(Long merchantId) {
        return getReconciliationSummary(merchantId).getIssues();
    }

    public TransactionDTO reconcileTransaction(Long transactionId, String notes) {
        Transaction tx = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with ID: " + transactionId));

        tx.setReconciliationStatus("RECONCILED");
        tx.setReconciliationNotes(notes != null ? notes : "Reconciled by merchant");
        tx.setReconciledAt(Instant.now());

        Transaction saved = transactionRepository.save(tx);
        return mapToDTO(saved);
    }

    public TransactionDTO ignoreTransaction(Long transactionId, String notes) {
        Transaction tx = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with ID: " + transactionId));

        tx.setReconciliationStatus("IGNORED");
        tx.setReconciliationNotes(notes != null ? notes : "Ignored by merchant");
        tx.setReconciledAt(Instant.now());

        Transaction saved = transactionRepository.save(tx);
        return mapToDTO(saved);
    }

    private TransactionDTO mapToDTO(Transaction t) {
        return new TransactionDTO(
                t.getId(),
                t.getMerchant().getId(),
                t.getBusinessAccount().getId(),
                t.getBusinessAccount().getInstitutionName(),
                t.getTransactionReference(),
                t.getTransactionDate(),
                t.getDescription(),
                t.getAmount(),
                t.getType(),
                t.getCategory(),
                t.getSubcategory(),
                t.getCounterparty(),
                t.getPaymentMethod(),
                t.getStatus()
        );
    }
}
