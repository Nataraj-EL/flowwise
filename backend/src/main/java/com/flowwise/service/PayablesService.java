package com.flowwise.service;

import com.flowwise.dto.PayableDTO;
import com.flowwise.dto.PayablesSummaryDTO;
import com.flowwise.entity.Payable;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.MerchantRepository;
import com.flowwise.repository.PayableRepository;
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
public class PayablesService {

    private final MerchantRepository merchantRepository;
    private final PayableRepository payableRepository;

    public PayablesService(MerchantRepository merchantRepository,
                          PayableRepository payableRepository) {
        this.merchantRepository = merchantRepository;
        this.payableRepository = payableRepository;
    }

    public List<PayableDTO> getPayables(Long merchantId) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }

        return payableRepository.findByMerchantIdOrderByDueDateAsc(merchantId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public PayablesSummaryDTO getPayablesSummary(Long merchantId) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }

        List<Payable> list = payableRepository.findByMerchantIdOrderByDueDateAsc(merchantId);
        OffsetDateTime now = OffsetDateTime.now();

        BigDecimal totalOutstanding = BigDecimal.ZERO;
        BigDecimal totalBillAmount = BigDecimal.ZERO;
        BigDecimal totalPaid = BigDecimal.ZERO;
        BigDecimal dueToday = BigDecimal.ZERO;
        BigDecimal due7Days = BigDecimal.ZERO;
        BigDecimal due30Days = BigDecimal.ZERO;
        BigDecimal totalOverdue = BigDecimal.ZERO;

        Map<String, BigDecimal> vendorTotals = new HashMap<>();
        int overdueCount = 0;

        List<PayableDTO> dtoList = new ArrayList<>();

        for (Payable p : list) {
            PayableDTO dto = mapToDTO(p);
            dtoList.add(dto);

            BigDecimal outstanding = p.getOutstandingAmount() != null ? p.getOutstandingAmount() : BigDecimal.ZERO;
            BigDecimal billAmt = p.getBillAmount() != null ? p.getBillAmount() : BigDecimal.ZERO;
            BigDecimal paidAmt = p.getAmountPaid() != null ? p.getAmountPaid() : BigDecimal.ZERO;

            totalOutstanding = totalOutstanding.add(outstanding);
            totalBillAmount = totalBillAmount.add(billAmt);
            totalPaid = totalPaid.add(paidAmt);

            if (outstanding.compareTo(BigDecimal.ZERO) > 0) {
                vendorTotals.put(
                        p.getVendor(),
                        vendorTotals.getOrDefault(p.getVendor(), BigDecimal.ZERO).add(outstanding)
                );

                if (p.getDueDate() != null) {
                    if (p.getDueDate().isBefore(now.truncatedTo(ChronoUnit.DAYS))) {
                        overdueCount++;
                        totalOverdue = totalOverdue.add(outstanding);
                    } else {
                        long days = ChronoUnit.DAYS.between(now.truncatedTo(ChronoUnit.DAYS), p.getDueDate().truncatedTo(ChronoUnit.DAYS));
                        if (days == 0) {
                            dueToday = dueToday.add(outstanding);
                        } else if (days <= 7) {
                            due7Days = due7Days.add(outstanding);
                        } else if (days <= 30) {
                            due30Days = due30Days.add(outstanding);
                        }
                    }
                }
            }
        }

        BigDecimal paymentCoverageRatioPct = totalBillAmount.compareTo(BigDecimal.ZERO) > 0
                ? totalPaid.multiply(new BigDecimal("100")).divide(totalBillAmount, 1, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        BigDecimal upcomingPayablePressure = dueToday.add(due7Days).add(totalOverdue);

        String largestVendor = "N/A";
        BigDecimal largestAmount = BigDecimal.ZERO;
        for (Map.Entry<String, BigDecimal> entry : vendorTotals.entrySet()) {
            if (entry.getValue().compareTo(largestAmount) > 0) {
                largestAmount = entry.getValue();
                largestVendor = entry.getKey();
            }
        }

        return new PayablesSummaryDTO(
                totalOutstanding.setScale(2, RoundingMode.HALF_UP),
                dueToday.setScale(2, RoundingMode.HALF_UP),
                due7Days.setScale(2, RoundingMode.HALF_UP),
                due30Days.setScale(2, RoundingMode.HALF_UP),
                totalOverdue.setScale(2, RoundingMode.HALF_UP),
                totalPaid.setScale(2, RoundingMode.HALF_UP),
                paymentCoverageRatioPct,
                upcomingPayablePressure.setScale(2, RoundingMode.HALF_UP),
                largestVendor,
                largestAmount.setScale(2, RoundingMode.HALF_UP),
                list.size(),
                overdueCount,
                dtoList
        );
    }

    private PayableDTO mapToDTO(Payable entity) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        OffsetDateTime now = OffsetDateTime.now();
        long daysUntilDue = 0;
        if (entity.getDueDate() != null) {
            daysUntilDue = ChronoUnit.DAYS.between(now.truncatedTo(ChronoUnit.DAYS), entity.getDueDate().truncatedTo(ChronoUnit.DAYS));
        }

        return new PayableDTO(
                entity.getId(),
                entity.getMerchantId(),
                entity.getVendor(),
                entity.getBillReference(),
                entity.getBillAmount(),
                entity.getAmountPaid(),
                entity.getOutstandingAmount(),
                entity.getBillDate() != null ? formatter.format(entity.getBillDate()) : "",
                entity.getDueDate() != null ? formatter.format(entity.getDueDate()) : "",
                entity.getCategory(),
                entity.getStatus(),
                daysUntilDue
        );
    }
}
