package com.flowwise.service;

import com.flowwise.dto.BusinessAccountDTO;
import com.flowwise.dto.MerchantDTO;
import com.flowwise.dto.MerchantDetailDTO;
import com.flowwise.entity.BusinessAccount;
import com.flowwise.entity.Merchant;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.BusinessAccountRepository;
import com.flowwise.repository.MerchantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MerchantService {

    private final MerchantRepository merchantRepository;
    private final BusinessAccountRepository accountRepository;

    public MerchantService(MerchantRepository merchantRepository, BusinessAccountRepository accountRepository) {
        this.merchantRepository = merchantRepository;
        this.accountRepository = accountRepository;
    }

    public List<MerchantDTO> getAllMerchants() {
        return merchantRepository.findAll().stream()
                .map(this::mapToMerchantDTO)
                .collect(Collectors.toList());
    }

    public MerchantDetailDTO getMerchantDetail(Long merchantId) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found with ID: " + merchantId));

        List<BusinessAccountDTO> accountDTOs = accountRepository.findByMerchantId(merchantId).stream()
                .map(this::mapToAccountDTO)
                .collect(Collectors.toList());

        BigDecimal totalAvailableCash = accountDTOs.stream()
                .map(BusinessAccountDTO::getCurrentBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new MerchantDetailDTO(mapToMerchantDTO(merchant), accountDTOs, totalAvailableCash);
    }

    public List<BusinessAccountDTO> getMerchantAccounts(Long merchantId) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }

        return accountRepository.findByMerchantId(merchantId).stream()
                .map(this::mapToAccountDTO)
                .collect(Collectors.toList());
    }

    private MerchantDTO mapToMerchantDTO(Merchant m) {
        return new MerchantDTO(
                m.getId(),
                m.getBusinessName(),
                m.getDisplayName(),
                m.getBusinessType(),
                m.getIndustry(),
                m.getDemoGstin(),
                m.getCreatedAt(),
                m.getUpdatedAt()
        );
    }

    private BusinessAccountDTO mapToAccountDTO(BusinessAccount a) {
        return new BusinessAccountDTO(
                a.getId(),
                a.getMerchant().getId(),
                a.getInstitutionName(),
                a.getAccountType(),
                a.getMaskedAccountRef(),
                a.getCurrentBalance(),
                a.getCurrency(),
                a.getStatus(),
                a.getCreatedAt(),
                a.getUpdatedAt()
        );
    }
}
