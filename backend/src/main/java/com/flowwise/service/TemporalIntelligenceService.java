package com.flowwise.service;

import com.flowwise.dto.CategoryMovementDTO;
import com.flowwise.dto.MonthlyCashFlowDTO;
import com.flowwise.dto.TemporalSummaryDTO;
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
public class TemporalIntelligenceService {

    private final MerchantRepository merchantRepository;
    private final TransactionRepository transactionRepository;
    private final CashFlowService cashFlowService;

    public TemporalIntelligenceService(MerchantRepository merchantRepository,
                                       TransactionRepository transactionRepository,
                                       CashFlowService cashFlowService) {
        this.merchantRepository = merchantRepository;
        this.transactionRepository = transactionRepository;
        this.cashFlowService = cashFlowService;
    }

    public TemporalSummaryDTO getTemporalSummary(Long merchantId) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }

        List<MonthlyCashFlowDTO> monthlySeries = cashFlowService.getMonthlyCashFlows(merchantId);
        // Filter out months with zero transactions if any
        List<MonthlyCashFlowDTO> activeMonths = monthlySeries.stream()
                .filter(m -> m.getInflow().compareTo(BigDecimal.ZERO) > 0 || m.getOutflow().compareTo(BigDecimal.ZERO) > 0)
                .collect(Collectors.toList());

        if (activeMonths.size() < 2) {
            // Baseline default for single month / limited history
            MonthlyCashFlowDTO current = activeMonths.isEmpty() ? new MonthlyCashFlowDTO("Current", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO) : activeMonths.get(0);
            return new TemporalSummaryDTO(
                    current.getMonth(),
                    "N/A",
                    current.getInflow(),
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    "FLAT",
                    current.getOutflow(),
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    "FLAT",
                    current.getNetCashFlow(),
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    "FLAT",
                    Collections.emptyList(),
                    Collections.singletonList("Insufficient historical ledger periods to calculate period-over-period trends."),
                    true,
                    activeMonths.size()
            );
        }

        MonthlyCashFlowDTO current = activeMonths.get(activeMonths.size() - 1);
        MonthlyCashFlowDTO previous = activeMonths.get(activeMonths.size() - 2);

        BigDecimal inflowPct = calculatePctChange(current.getInflow(), previous.getInflow());
        BigDecimal outflowPct = calculatePctChange(current.getOutflow(), previous.getOutflow());
        BigDecimal netCashPct = calculatePctChange(current.getNetCashFlow(), previous.getNetCashFlow());

        String inflowDir = getDirection(inflowPct);
        String outflowDir = getDirection(outflowPct);
        String netCashDir = getDirection(netCashPct);

        // Category Movements Calculation
        List<CategoryMovementDTO> movements = calculateCategoryMovements(merchantId, current.getMonth(), previous.getMonth());

        // Anomaly Detection
        List<String> anomalies = new ArrayList<>();
        if (outflowPct.compareTo(new BigDecimal("20.0")) > 0) {
            anomalies.add("Outflow Surge: Monthly business outflows increased by " + outflowPct + "% compared to previous month.");
        }
        for (CategoryMovementDTO mov : movements) {
            if ("INCREASED".equals(mov.getDirection()) && mov.getChangePct().compareTo(new BigDecimal("25.0")) >= 0) {
                anomalies.add("Category Spike: " + mov.getCategory() + " expenditure rose by " + mov.getChangePct() + "% (+₹" + mov.getChangeAmount() + ").");
            }
        }
        if (anomalies.isEmpty()) {
            anomalies.add("No critical financial anomalies detected across current ledger period.");
        }

        return new TemporalSummaryDTO(
                current.getMonth(),
                previous.getMonth(),
                current.getInflow(),
                previous.getInflow(),
                inflowPct,
                inflowDir,
                current.getOutflow(),
                previous.getOutflow(),
                outflowPct,
                outflowDir,
                current.getNetCashFlow(),
                previous.getNetCashFlow(),
                netCashPct,
                netCashDir,
                movements,
                anomalies,
                false,
                activeMonths.size()
        );
    }

    public List<CategoryMovementDTO> calculateCategoryMovements(Long merchantId, String currentMonthName, String previousMonthName) {
        List<Transaction> transactions = transactionRepository.findByMerchantIdOrderByTransactionDateDesc(merchantId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM").withZone(ZoneId.of("UTC"));

        Map<String, BigDecimal> currentCatSpend = new HashMap<>();
        Map<String, BigDecimal> previousCatSpend = new HashMap<>();

        for (Transaction t : transactions) {
            if ("DEBIT".equalsIgnoreCase(t.getType())) {
                String mName = formatter.format(t.getTransactionDate());
                String cat = t.getCategory() != null ? t.getCategory() : "OTHER";

                if (mName.equalsIgnoreCase(currentMonthName)) {
                    currentCatSpend.put(cat, currentCatSpend.getOrDefault(cat, BigDecimal.ZERO).add(t.getAmount()));
                } else if (mName.equalsIgnoreCase(previousMonthName)) {
                    previousCatSpend.put(cat, previousCatSpend.getOrDefault(cat, BigDecimal.ZERO).add(t.getAmount()));
                }
            }
        }

        Set<String> allCats = new HashSet<>();
        allCats.addAll(currentCatSpend.keySet());
        allCats.addAll(previousCatSpend.keySet());

        List<CategoryMovementDTO> list = new ArrayList<>();
        for (String cat : allCats) {
            BigDecimal curAmt = currentCatSpend.getOrDefault(cat, BigDecimal.ZERO);
            BigDecimal prevAmt = previousCatSpend.getOrDefault(cat, BigDecimal.ZERO);
            BigDecimal diff = curAmt.subtract(prevAmt);
            BigDecimal pct = calculatePctChange(curAmt, prevAmt);

            String dir = "STABLE";
            if (diff.compareTo(BigDecimal.ZERO) > 0) {
                dir = "INCREASED";
            } else if (diff.compareTo(BigDecimal.ZERO) < 0) {
                dir = "DECREASED";
            }

            list.add(new CategoryMovementDTO(cat, curAmt, prevAmt, diff.abs(), pct, dir));
        }

        list.sort((a, b) -> b.getChangeAmount().compareTo(a.getChangeAmount()));
        return list;
    }

    private BigDecimal calculatePctChange(BigDecimal current, BigDecimal previous) {
        if (previous == null || previous.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO.setScale(1, RoundingMode.HALF_UP);
        }
        return current.subtract(previous)
                .divide(previous.abs(), 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"))
                .setScale(1, RoundingMode.HALF_UP);
    }

    private String getDirection(BigDecimal pct) {
        if (pct.compareTo(new BigDecimal("1.0")) > 0) return "UP";
        if (pct.compareTo(new BigDecimal("-1.0")) < 0) return "DOWN";
        return "FLAT";
    }
}
