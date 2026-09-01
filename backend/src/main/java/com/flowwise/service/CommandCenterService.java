package com.flowwise.service;

import com.flowwise.dto.*;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.MerchantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CommandCenterService {

    private final MerchantRepository merchantRepository;
    private final BusinessHealthService healthService;
    private final CashFlowService cashFlowService;
    private final WorkingCapitalService workingCapitalService;
    private final ReceivablesService receivablesService;
    private final PayablesService payablesService;
    private final ForecastingService forecastingService;
    private final TemporalIntelligenceService temporalService;
    private final FinancialActionService actionService;

    public CommandCenterService(MerchantRepository merchantRepository,
                                 BusinessHealthService healthService,
                                 CashFlowService cashFlowService,
                                 WorkingCapitalService workingCapitalService,
                                 ReceivablesService receivablesService,
                                 PayablesService payablesService,
                                 ForecastingService forecastingService,
                                 TemporalIntelligenceService temporalService,
                                 FinancialActionService actionService) {
        this.merchantRepository = merchantRepository;
        this.healthService = healthService;
        this.cashFlowService = cashFlowService;
        this.workingCapitalService = workingCapitalService;
        this.receivablesService = receivablesService;
        this.payablesService = payablesService;
        this.forecastingService = forecastingService;
        this.temporalService = temporalService;
        this.actionService = actionService;
    }

    public CommandCenterSnapshotDTO getCommandCenterSnapshot(Long merchantId) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }

        BusinessHealthDTO health = healthService.calculateBusinessHealth(merchantId);
        CashFlowSummaryDTO cashFlow = cashFlowService.getCashFlowSummary(merchantId);
        WorkingCapitalSummaryDTO wc = workingCapitalService.getWorkingCapitalSummary(merchantId);
        ReceivablesSummaryDTO recv = receivablesService.getReceivablesSummary(merchantId);
        PayablesSummaryDTO pay = payablesService.getPayablesSummary(merchantId);
        ForecastSummaryDTO forecast = forecastingService.getForecastSummary(merchantId);
        TemporalSummaryDTO temporal = temporalService.getTemporalSummary(merchantId);
        ActionSummaryDTO actionSummary = actionService.getMerchantActions(merchantId);

        List<FinancialActionDTO> top3 = actionSummary.getActions().stream()
                .filter(a -> "OPEN".equalsIgnoreCase(a.getStatus()))
                .limit(3)
                .collect(Collectors.toList());

        String positiveSignal = !health.getPositiveSignals().isEmpty()
                ? health.getPositiveSignals().get(0)
                : "Liquid cash reserves and cash runway remain stable.";

        String riskSignal = !health.getRiskSignals().isEmpty()
                ? health.getRiskSignals().get(0)
                : "No severe liquidity alerts detected in active ledger.";

        String forecastRisk = "FEASIBLE";
        if (forecast.getProjections() != null && !forecast.getProjections().isEmpty()) {
            BigDecimal day30Cash = forecast.getProjections().get(0).getProjectedEndingCash();
            BigDecimal day90Runway = forecast.getProjections().get(forecast.getProjections().size() - 1).getProjectedRunwayMonths();
            if (day30Cash.compareTo(BigDecimal.ZERO) < 0) {
                forecastRisk = "HIGH_RISK";
            } else if (day90Runway.compareTo(new BigDecimal("3.0")) < 0) {
                forecastRisk = "CAUTION";
            }
        }

        String whatChangedSummary = "Net cash flow movement shifted by " + temporal.getNetCashChangePct() 
                + "% (" + temporal.getNetCashDirection() + ") compared to previous period (" + temporal.getPreviousMonth() + ").";

        return new CommandCenterSnapshotDTO(
                health.getHealthStatus(),
                health.getOverallScore(),
                wc.getAvailableCash(),
                cashFlow.getNetCashFlow(),
                wc.getCurrentCoverageRatio(),
                recv.getTotalOverdue(),
                pay.getUpcomingPayablePressure(),
                forecastRisk,
                top3,
                positiveSignal,
                riskSignal,
                whatChangedSummary,
                OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        );
    }
}
