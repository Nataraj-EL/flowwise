package com.flowwise.service;

import com.flowwise.dto.CashFlowSummaryDTO;
import com.flowwise.dto.MerchantDetailDTO;
import com.flowwise.dto.MonthlyCashFlowDTO;
import com.flowwise.entity.Transaction;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.MerchantRepository;
import com.flowwise.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CashFlowService {

    private final TransactionRepository transactionRepository;
    private final MerchantRepository merchantRepository;
    private final MerchantService merchantService;

    public CashFlowService(TransactionRepository transactionRepository, 
                           MerchantRepository merchantRepository,
                           MerchantService merchantService) {
        this.transactionRepository = transactionRepository;
        this.merchantRepository = merchantRepository;
        this.merchantService = merchantService;
    }

    public CashFlowSummaryDTO getCashFlowSummary(Long merchantId) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }

        List<Transaction> transactions = transactionRepository.findByMerchantIdOrderByTransactionDateDesc(merchantId);
        MerchantDetailDTO merchantDetail = merchantService.getMerchantDetail(merchantId);
        BigDecimal availableCash = merchantDetail.getTotalAvailableCash();

        BigDecimal totalInflows = BigDecimal.ZERO;
        BigDecimal totalOutflows = BigDecimal.ZERO;
        BigDecimal operatingInflows = BigDecimal.ZERO;
        BigDecimal operatingOutflows = BigDecimal.ZERO;
        BigDecimal recurringExpenses = BigDecimal.ZERO;
        BigDecimal payablePressure = BigDecimal.ZERO;

        Set<String> monthKeys = new HashSet<>();
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMM yyyy").withZone(ZoneId.of("UTC"));

        for (Transaction t : transactions) {
            BigDecimal amt = t.getAmount();
            String cat = t.getCategory() != null ? t.getCategory().toUpperCase() : "";
            monthKeys.add(monthFormatter.format(t.getTransactionDate()));

            if ("CREDIT".equalsIgnoreCase(t.getType())) {
                totalInflows = totalInflows.add(amt);
                if ("SALES".equals(cat)) {
                    operatingInflows = operatingInflows.add(amt);
                }
            } else if ("DEBIT".equalsIgnoreCase(t.getType())) {
                totalOutflows = totalOutflows.add(amt);
                if (Arrays.asList("OPERATIONS", "INVENTORY", "PAYROLL", "UTILITIES", "RENT", "TAX").contains(cat)) {
                    operatingOutflows = operatingOutflows.add(amt);
                }
                if (Arrays.asList("RENT", "PAYROLL", "UTILITIES").contains(cat)) {
                    recurringExpenses = recurringExpenses.add(amt);
                }
                if ("PENDING".equalsIgnoreCase(t.getStatus())) {
                    payablePressure = payablePressure.add(amt);
                }
            }
        }

        BigDecimal netCashFlow = totalInflows.subtract(totalOutflows);
        int monthsCount = Math.max(1, monthKeys.size());

        BigDecimal averageMonthlyOutflow = totalOutflows.divide(BigDecimal.valueOf(monthsCount), 2, RoundingMode.HALF_UP);
        BigDecimal burnRate = averageMonthlyOutflow;

        // Cash Runway Calculation with Zero/Negative Division Safety
        BigDecimal cashRunwayMonths;
        if (availableCash == null || availableCash.compareTo(BigDecimal.ZERO) <= 0 || burnRate.compareTo(BigDecimal.ZERO) <= 0) {
            cashRunwayMonths = BigDecimal.ZERO.setScale(1, RoundingMode.HALF_UP);
        } else {
            cashRunwayMonths = availableCash.divide(burnRate, 1, RoundingMode.HALF_UP);
        }

        String liquidityStatus = "OPTIMAL";
        if (cashRunwayMonths.compareTo(new BigDecimal("2.0")) < 0) {
            liquidityStatus = "CRITICAL";
        } else if (cashRunwayMonths.compareTo(new BigDecimal("4.0")) < 0) {
            liquidityStatus = "MODERATE";
        }

        return new CashFlowSummaryDTO(
                totalInflows,
                totalOutflows,
                netCashFlow,
                operatingInflows,
                operatingOutflows,
                averageMonthlyOutflow,
                burnRate,
                cashRunwayMonths,
                recurringExpenses,
                payablePressure,
                liquidityStatus
        );
    }

    public List<MonthlyCashFlowDTO> getMonthlyCashFlows(Long merchantId) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }

        List<Transaction> transactions = transactionRepository.findByMerchantIdOrderByTransactionDateDesc(merchantId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM").withZone(ZoneId.of("UTC"));

        // Pre-initialize 6-month historical baseline
        Map<String, MonthlyCashFlowDTO> map = new LinkedHashMap<>();
        String[] defaultMonths = {"Apr", "May", "Jun", "Jul", "Aug", "Sep"};
        for (String m : defaultMonths) {
            map.put(m, new MonthlyCashFlowDTO(m, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));
        }

        for (Transaction t : transactions) {
            String monthName = formatter.format(t.getTransactionDate());
            MonthlyCashFlowDTO dto = map.computeIfAbsent(monthName, k -> new MonthlyCashFlowDTO(k, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));

            if ("CREDIT".equalsIgnoreCase(t.getType())) {
                dto.setInflow(dto.getInflow().add(t.getAmount()));
            } else if ("DEBIT".equalsIgnoreCase(t.getType())) {
                dto.setOutflow(dto.getOutflow().add(t.getAmount()));
            }
            dto.setNetCashFlow(dto.getInflow().subtract(dto.getOutflow()));
        }

        return new ArrayList<>(map.values());
    }
}
