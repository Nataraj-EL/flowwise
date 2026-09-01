package com.flowwise.service;

import com.flowwise.dto.CashFlowSummaryDTO;
import com.flowwise.dto.MerchantDetailDTO;
import com.flowwise.dto.PayablesSummaryDTO;
import com.flowwise.dto.ReceivablesSummaryDTO;
import com.flowwise.dto.WorkingCapitalSummaryDTO;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.MerchantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class WorkingCapitalService {

    private final MerchantRepository merchantRepository;
    private final MerchantService merchantService;
    private final CashFlowService cashFlowService;
    private final ReceivablesService receivablesService;
    private final PayablesService payablesService;

    public WorkingCapitalService(MerchantRepository merchantRepository,
                                 MerchantService merchantService,
                                 CashFlowService cashFlowService,
                                 ReceivablesService receivablesService,
                                 PayablesService payablesService) {
        this.merchantRepository = merchantRepository;
        this.merchantService = merchantService;
        this.cashFlowService = cashFlowService;
        this.receivablesService = receivablesService;
        this.payablesService = payablesService;
    }

    public WorkingCapitalSummaryDTO getWorkingCapitalSummary(Long merchantId) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }

        MerchantDetailDTO merchantDetail = merchantService.getMerchantDetail(merchantId);
        CashFlowSummaryDTO cashFlow = cashFlowService.getCashFlowSummary(merchantId);
        ReceivablesSummaryDTO recv = receivablesService.getReceivablesSummary(merchantId);
        PayablesSummaryDTO pay = payablesService.getPayablesSummary(merchantId);

        BigDecimal availableCash = merchantDetail.getTotalAvailableCash() != null ? merchantDetail.getTotalAvailableCash() : BigDecimal.ZERO;
        BigDecimal receivablesOutstanding = recv.getTotalOutstanding() != null ? recv.getTotalOutstanding() : BigDecimal.ZERO;
        BigDecimal payablesOutstanding = pay.getTotalOutstanding() != null ? pay.getTotalOutstanding() : BigDecimal.ZERO;

        BigDecimal netWorkingCapital = availableCash.add(receivablesOutstanding).subtract(payablesOutstanding);

        BigDecimal workingCapitalGap = payablesOutstanding.subtract(receivablesOutstanding);
        if (workingCapitalGap.compareTo(BigDecimal.ZERO) < 0) {
            workingCapitalGap = BigDecimal.ZERO;
        }

        BigDecimal currentCoverageRatio;
        if (payablesOutstanding.compareTo(BigDecimal.ZERO) > 0) {
            currentCoverageRatio = availableCash.add(receivablesOutstanding)
                    .divide(payablesOutstanding, 2, RoundingMode.HALF_UP);
        } else {
            currentCoverageRatio = new BigDecimal("99.99");
        }

        BigDecimal upcomingPressure = pay.getUpcomingPayablePressure() != null ? pay.getUpcomingPayablePressure() : BigDecimal.ZERO;
        BigDecimal nearTermCollection = recv.getEstimatedNearTermCollection() != null ? recv.getEstimatedNearTermCollection() : BigDecimal.ZERO;

        BigDecimal nearTermCoverageRatio;
        if (upcomingPressure.compareTo(BigDecimal.ZERO) > 0) {
            nearTermCoverageRatio = availableCash.add(nearTermCollection)
                    .divide(upcomingPressure, 2, RoundingMode.HALF_UP);
        } else {
            nearTermCoverageRatio = new BigDecimal("99.99");
        }

        String riskStatus;
        if (nearTermCoverageRatio.compareTo(new BigDecimal("1.50")) >= 0) {
            riskStatus = "OPTIMAL";
        } else if (nearTermCoverageRatio.compareTo(new BigDecimal("1.00")) >= 0) {
            riskStatus = "MODERATE";
        } else {
            riskStatus = "HIGH_RISK";
        }

        List<String> pressureDrivers = new ArrayList<>();
        if (pay.getTotalOverdue() != null && pay.getTotalOverdue().compareTo(BigDecimal.ZERO) > 0) {
            pressureDrivers.add("Overdue Vendor Obligations: ₹" + pay.getTotalOverdue());
        }
        if (upcomingPressure.compareTo(BigDecimal.ZERO) > 0) {
            pressureDrivers.add("Short-Term Payment Demand (7-Day + Overdue): ₹" + upcomingPressure);
        }
        if (recv.getTotalOverdue() != null && recv.getTotalOverdue().compareTo(BigDecimal.ZERO) > 0) {
            pressureDrivers.add("Stuck Receivables Past Due: ₹" + recv.getTotalOverdue());
        }
        if (recv.getConcentrationRatioPct() != null && recv.getConcentrationRatioPct().compareTo(new BigDecimal("40.0")) > 0) {
            pressureDrivers.add("Debtor Concentration Risk: " + recv.getConcentrationRatioPct() + "% with " + recv.getLargestOutstandingCounterparty());
        }
        if (pressureDrivers.isEmpty()) {
            pressureDrivers.add("Working capital position is balanced with zero overdue liabilities.");
        }

        String summaryExplanation = "Net working capital stands at ₹" + netWorkingCapital + " with a liquid current coverage ratio of " 
                + currentCoverageRatio + "x and near-term coverage ratio of " + nearTermCoverageRatio + "x (Risk Status: " + riskStatus + ").";

        return new WorkingCapitalSummaryDTO(
                netWorkingCapital.setScale(2, RoundingMode.HALF_UP),
                availableCash.setScale(2, RoundingMode.HALF_UP),
                receivablesOutstanding.setScale(2, RoundingMode.HALF_UP),
                payablesOutstanding.setScale(2, RoundingMode.HALF_UP),
                workingCapitalGap.setScale(2, RoundingMode.HALF_UP),
                currentCoverageRatio,
                nearTermCoverageRatio,
                riskStatus,
                nearTermCollection.setScale(2, RoundingMode.HALF_UP),
                upcomingPressure.setScale(2, RoundingMode.HALF_UP),
                pressureDrivers,
                summaryExplanation
        );
    }
}
