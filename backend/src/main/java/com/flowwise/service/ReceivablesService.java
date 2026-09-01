package com.flowwise.service;

import com.flowwise.dto.ReceivableDTO;
import com.flowwise.dto.ReceivablesSummaryDTO;
import com.flowwise.entity.Receivable;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.MerchantRepository;
import com.flowwise.repository.ReceivableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ReceivablesService {

    private final MerchantRepository merchantRepository;
    private final ReceivableRepository receivableRepository;

    public ReceivablesService(MerchantRepository merchantRepository,
                             ReceivableRepository receivableRepository) {
        this.merchantRepository = merchantRepository;
        this.receivableRepository = receivableRepository;
    }

    public List<ReceivableDTO> getReceivables(Long merchantId) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }

        return receivableRepository.findByMerchantIdOrderByDueDateAsc(merchantId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ReceivablesSummaryDTO getReceivablesSummary(Long merchantId) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }

        List<Receivable> list = receivableRepository.findByMerchantIdOrderByDueDateAsc(merchantId);
        OffsetDateTime now = OffsetDateTime.now();

        BigDecimal totalOutstanding = BigDecimal.ZERO;
        BigDecimal totalInvoiceAmount = BigDecimal.ZERO;
        BigDecimal totalAmountReceived = BigDecimal.ZERO;
        BigDecimal currentReceivables = BigDecimal.ZERO;
        BigDecimal overdue1To30 = BigDecimal.ZERO;
        BigDecimal overdue31To60 = BigDecimal.ZERO;
        BigDecimal overdue60Plus = BigDecimal.ZERO;

        Map<String, BigDecimal> counterpartyTotals = new HashMap<>();
        int overdueCount = 0;

        List<ReceivableDTO> dtoList = new ArrayList<>();

        for (Receivable r : list) {
            ReceivableDTO dto = mapToDTO(r);
            dtoList.add(dto);

            BigDecimal outstanding = r.getOutstandingAmount() != null ? r.getOutstandingAmount() : BigDecimal.ZERO;
            BigDecimal invoiceAmt = r.getInvoiceAmount() != null ? r.getInvoiceAmount() : BigDecimal.ZERO;
            BigDecimal receivedAmt = r.getAmountReceived() != null ? r.getAmountReceived() : BigDecimal.ZERO;

            totalOutstanding = totalOutstanding.add(outstanding);
            totalInvoiceAmount = totalInvoiceAmount.add(invoiceAmt);
            totalAmountReceived = totalAmountReceived.add(receivedAmt);

            if (outstanding.compareTo(BigDecimal.ZERO) > 0) {
                counterpartyTotals.put(
                        r.getCounterparty(),
                        counterpartyTotals.getOrDefault(r.getCounterparty(), BigDecimal.ZERO).add(outstanding)
                );

                if (r.getDueDate() != null && r.getDueDate().isBefore(now)) {
                    overdueCount++;
                    long days = ChronoUnit.DAYS.between(r.getDueDate(), now);
                    if (days <= 30) {
                        overdue1To30 = overdue1To30.add(outstanding);
                    } else if (days <= 60) {
                        overdue31To60 = overdue31To60.add(outstanding);
                    } else {
                        overdue60Plus = overdue60Plus.add(outstanding);
                    }
                } else {
                    currentReceivables = currentReceivables.add(outstanding);
                }
            }
        }

        BigDecimal totalOverdue = overdue1To30.add(overdue31To60).add(overdue60Plus);

        BigDecimal collectionRatePct = totalInvoiceAmount.compareTo(BigDecimal.ZERO) > 0
                ? totalAmountReceived.multiply(new BigDecimal("100")).divide(totalInvoiceAmount, 1, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        BigDecimal overdueRatioPct = totalOutstanding.compareTo(BigDecimal.ZERO) > 0
                ? totalOverdue.multiply(new BigDecimal("100")).divide(totalOutstanding, 1, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        String largestCounterparty = "N/A";
        BigDecimal largestAmount = BigDecimal.ZERO;
        for (Map.Entry<String, BigDecimal> entry : counterpartyTotals.entrySet()) {
            if (entry.getValue().compareTo(largestAmount) > 0) {
                largestAmount = entry.getValue();
                largestCounterparty = entry.getKey();
            }
        }

        BigDecimal concentrationRatioPct = totalOutstanding.compareTo(BigDecimal.ZERO) > 0
                ? largestAmount.multiply(new BigDecimal("100")).divide(totalOutstanding, 1, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        BigDecimal nearTermCollection = currentReceivables.add(overdue1To30);

        return new ReceivablesSummaryDTO(
                totalOutstanding.setScale(2, RoundingMode.HALF_UP),
                currentReceivables.setScale(2, RoundingMode.HALF_UP),
                overdue1To30.setScale(2, RoundingMode.HALF_UP),
                overdue31To60.setScale(2, RoundingMode.HALF_UP),
                overdue60Plus.setScale(2, RoundingMode.HALF_UP),
                totalOverdue.setScale(2, RoundingMode.HALF_UP),
                collectionRatePct,
                overdueRatioPct,
                largestCounterparty,
                largestAmount.setScale(2, RoundingMode.HALF_UP),
                concentrationRatioPct,
                nearTermCollection.setScale(2, RoundingMode.HALF_UP),
                list.size(),
                overdueCount,
                dtoList
        );
    }

    private ReceivableDTO mapToDTO(Receivable entity) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        OffsetDateTime now = OffsetDateTime.now();
        long daysOverdue = 0;
        if (entity.getDueDate() != null && entity.getDueDate().isBefore(now) && entity.getOutstandingAmount().compareTo(BigDecimal.ZERO) > 0) {
            daysOverdue = ChronoUnit.DAYS.between(entity.getDueDate(), now);
        }

        return new ReceivableDTO(
                entity.getId(),
                entity.getMerchantId(),
                entity.getCounterparty(),
                entity.getInvoiceReference(),
                entity.getInvoiceAmount(),
                entity.getAmountReceived(),
                entity.getOutstandingAmount(),
                entity.getInvoiceDate() != null ? formatter.format(entity.getInvoiceDate()) : "",
                entity.getDueDate() != null ? formatter.format(entity.getDueDate()) : "",
                entity.getStatus(),
                daysOverdue
        );
    }
}
