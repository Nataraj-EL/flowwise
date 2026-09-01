package com.flowwise.service;

import com.flowwise.dto.CategoryTotalDTO;
import com.flowwise.dto.TransactionDTO;
import com.flowwise.dto.TransactionSummaryDTO;
import com.flowwise.entity.Transaction;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.MerchantRepository;
import com.flowwise.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final MerchantRepository merchantRepository;
    private final TransactionClassificationService classificationService;

    public TransactionService(TransactionRepository transactionRepository, 
                              MerchantRepository merchantRepository,
                              TransactionClassificationService classificationService) {
        this.transactionRepository = transactionRepository;
        this.merchantRepository = merchantRepository;
        this.classificationService = classificationService;
    }

    public List<TransactionDTO> getMerchantTransactions(Long merchantId, String type, String category, String search) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }

        List<Transaction> transactions = transactionRepository.findFilteredTransactions(merchantId, type, category, search);
        return transactions.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public TransactionSummaryDTO getTransactionSummary(Long merchantId) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }

        List<Transaction> allTransactions = transactionRepository.findByMerchantIdOrderByTransactionDateDesc(merchantId);

        BigDecimal totalCredits = BigDecimal.ZERO;
        BigDecimal totalDebits = BigDecimal.ZERO;

        for (Transaction t : allTransactions) {
            if ("CREDIT".equalsIgnoreCase(t.getType())) {
                totalCredits = totalCredits.add(t.getAmount());
            } else if ("DEBIT".equalsIgnoreCase(t.getType())) {
                totalDebits = totalDebits.add(t.getAmount());
            }
        }

        BigDecimal netCashFlow = totalCredits.subtract(totalDebits);

        // Group by Category & Type
        Map<String, Map<String, List<Transaction>>> grouped = allTransactions.stream()
                .collect(Collectors.groupingBy(Transaction::getCategory, Collectors.groupingBy(t -> t.getType().toUpperCase())));

        List<CategoryTotalDTO> categoryTotals = allTransactions.stream()
                .collect(Collectors.groupingBy(Transaction::getCategory))
                .entrySet().stream()
                .map(entry -> {
                    String cat = entry.getKey();
                    List<Transaction> catTxns = entry.getValue();
                    String catType = catTxns.get(0).getType();
                    BigDecimal sum = catTxns.stream()
                            .map(Transaction::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    return new CategoryTotalDTO(cat, catType, sum, catTxns.size());
                })
                .collect(Collectors.toList());

        return new TransactionSummaryDTO(
                totalCredits,
                totalDebits,
                netCashFlow,
                allTransactions.size(),
                categoryTotals
        );
    }

    private TransactionDTO mapToDTO(Transaction t) {
        // Run classification check if category is unassigned/other
        String category = t.getCategory();
        if (category == null || "OTHER".equalsIgnoreCase(category)) {
            category = classificationService.classifyTransaction(t.getDescription(), t.getCounterparty(), t.getType());
        }

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
                category,
                t.getSubcategory(),
                t.getCounterparty(),
                t.getPaymentMethod(),
                t.getStatus()
        );
    }
}
