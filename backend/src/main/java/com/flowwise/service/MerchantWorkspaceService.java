package com.flowwise.service;

import com.flowwise.dto.AccountDetailDTO;
import com.flowwise.dto.MerchantWorkspaceDTO;
import com.flowwise.entity.BusinessAccount;
import com.flowwise.entity.Merchant;
import com.flowwise.entity.Transaction;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.BusinessAccountRepository;
import com.flowwise.repository.MerchantRepository;
import com.flowwise.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MerchantWorkspaceService {

    private final MerchantRepository merchantRepository;
    private final BusinessAccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public MerchantWorkspaceService(MerchantRepository merchantRepository,
                                   BusinessAccountRepository accountRepository,
                                   TransactionRepository transactionRepository) {
        this.merchantRepository = merchantRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public MerchantWorkspaceDTO getMerchantWorkspace(Long merchantId) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found with ID: " + merchantId));

        List<BusinessAccount> accounts = accountRepository.findByMerchantId(merchantId);

        BigDecimal totalAvailableCash = accounts.stream()
                .map(BusinessAccount::getCurrentBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<Transaction> allMerchantTxs = transactionRepository.findByMerchantIdOrderByTransactionDateDesc(merchantId);

        BigDecimal consolidatedNet = BigDecimal.ZERO;
        int consolidatedCount = allMerchantTxs.size();

        List<AccountDetailDTO> accountDetails = new ArrayList<>();
        for (BusinessAccount acc : accounts) {
            BigDecimal bal = acc.getCurrentBalance() != null ? acc.getCurrentBalance() : BigDecimal.ZERO;

            BigDecimal contributionPct = BigDecimal.ZERO;
            if (totalAvailableCash.compareTo(BigDecimal.ZERO) > 0) {
                contributionPct = bal.multiply(new BigDecimal("100"))
                        .divide(totalAvailableCash, 2, RoundingMode.HALF_UP);
            }

            List<Transaction> accTxs = allMerchantTxs.stream()
                    .filter(t -> t.getBusinessAccount() != null && t.getBusinessAccount().getId().equals(acc.getId()))
                    .toList();

            BigDecimal credits = accTxs.stream()
                    .filter(t -> "CREDIT".equalsIgnoreCase(t.getType()))
                    .map(Transaction::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal debits = accTxs.stream()
                    .filter(t -> "DEBIT".equalsIgnoreCase(t.getType()))
                    .map(Transaction::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal net = credits.subtract(debits);
            consolidatedNet = consolidatedNet.add(net);

            accountDetails.add(new AccountDetailDTO(
                    acc.getId(),
                    acc.getInstitutionName(),
                    acc.getAccountType(),
                    acc.getMaskedAccountRef(),
                    bal.setScale(2, RoundingMode.HALF_UP),
                    acc.getCurrency(),
                    acc.getStatus(),
                    contributionPct,
                    credits.setScale(2, RoundingMode.HALF_UP),
                    debits.setScale(2, RoundingMode.HALF_UP),
                    net.setScale(2, RoundingMode.HALF_UP),
                    accTxs.size()
            ));
        }

        return new MerchantWorkspaceDTO(
                merchant.getId(),
                merchant.getBusinessName(),
                merchant.getDisplayName(),
                merchant.getBusinessType(),
                merchant.getIndustry(),
                merchant.getDemoGstin(),
                totalAvailableCash.setScale(2, RoundingMode.HALF_UP),
                accounts.size(),
                accountDetails,
                consolidatedNet.setScale(2, RoundingMode.HALF_UP),
                consolidatedCount
        );
    }

    public AccountDetailDTO getAccountSummary(Long merchantId, Long accountId) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }

        BusinessAccount acc = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Business account not found with ID: " + accountId));

        if (!acc.getMerchant().getId().equals(merchantId)) {
            throw new ResourceNotFoundException("Business account ID " + accountId + " does not belong to Merchant ID " + merchantId);
        }

        MerchantWorkspaceDTO workspace = getMerchantWorkspace(merchantId);
        return workspace.getAccounts().stream()
                .filter(a -> a.getAccountId().equals(accountId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Account summary unavailable for ID: " + accountId));
    }
}
