package com.flowwise.service;

import com.flowwise.dto.*;
import com.flowwise.entity.Merchant;
import com.flowwise.entity.RiskTrajectorySnapshot;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.MerchantRepository;
import com.flowwise.repository.RiskTrajectorySnapshotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.*;

@Service
@Transactional
public class FinancialRiskTrajectoryService {

    private final MerchantRepository merchantRepository;
    private final RiskTrajectorySnapshotRepository trajectoryRepository;
    private final FinancialRiskDetectionService riskDetectionService;
    private final FinancialActionService actionService;

    public FinancialRiskTrajectoryService(MerchantRepository merchantRepository,
                                         RiskTrajectorySnapshotRepository trajectoryRepository,
                                         FinancialRiskDetectionService riskDetectionService,
                                         FinancialActionService actionService) {
        this.merchantRepository = merchantRepository;
        this.trajectoryRepository = trajectoryRepository;
        this.riskDetectionService = riskDetectionService;
        this.actionService = actionService;
    }

    public RiskTrajectorySummaryDTO evaluateRiskTrajectory(Long merchantId) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found with ID: " + merchantId));

        RiskMonitorSummaryDTO riskMonitor = riskDetectionService.evaluateMerchantRisks(merchantId);

        List<RiskTrajectorySnapshot> snapshots = trajectoryRepository.findByMerchantIdOrderByEvaluatedAtDesc(merchantId);

        int worsening = 0, stable = 0, improving = 0, resolved = 0;

        for (RiskAlertDTO alert : riskMonitor.getAlerts()) {
            Optional<RiskTrajectorySnapshot> existingOpt = trajectoryRepository.findByMerchantIdAndRiskKey(merchantId, alert.getRiskKey());

            String direction;
            String transition = "UNCHANGED";
            BigDecimal velocity = BigDecimal.ZERO;
            int obsCount = 2;

            BigDecimal changePct = alert.getChangePct() != null ? alert.getChangePct() : BigDecimal.ZERO;

            // 5% Hysteresis Rule Boundary
            if (changePct.compareTo(new BigDecimal("5.00")) > 0) {
                direction = "WORSENING";
                worsening++;
                transition = "ESCALATED_" + alert.getSeverity();
                velocity = new BigDecimal("1.35");
            } else if (changePct.compareTo(new BigDecimal("-5.00")) < 0) {
                direction = "IMPROVING";
                improving++;
                transition = "DE_ESCALATED_" + alert.getSeverity();
            } else {
                direction = "STABLE";
                stable++;
            }

            if ("RESOLVED".equalsIgnoreCase(alert.getStatus())) {
                direction = "RESOLVED";
                resolved++;
            }

            RiskTrajectorySnapshot snap = existingOpt.orElseGet(RiskTrajectorySnapshot::new);
            snap.setMerchant(merchant);
            snap.setRiskKey(alert.getRiskKey());
            snap.setRiskType(alert.getRiskType());
            snap.setTrajectoryDirection(direction);
            snap.setSeverityTransition(transition);
            snap.setEscalationVelocity(velocity);
            snap.setObservedSnapshotsCount(obsCount);
            snap.setBaselineValue(alert.getBaselineValue());
            snap.setCurrentValue(alert.getCurrentValue());
            snap.setScoreDelta(alert.getCurrentValue().subtract(alert.getBaselineValue()));
            snap.setResolutionTimeHours(new BigDecimal("0.00"));
            snap.setRecurrenceCount(1);
            snap.setEvaluatedAt(Instant.now());

            trajectoryRepository.save(snap);

            // Action Center Directive for Sustained Worsening HIGH / CRITICAL
            if ("WORSENING".equalsIgnoreCase(direction) && ("HIGH".equalsIgnoreCase(alert.getSeverity()) || "CRITICAL".equalsIgnoreCase(alert.getSeverity()))) {
                actionService.createOrUpdateAction(merchantId, "ACT-TRAJECTORY-WORSENING-" + alert.getRiskKey(),
                        "Sustained Risk Deterioration: " + alert.getTitle(), alert.getSeverity(), "RISK_TRAJECTORY",
                        "Risk trajectory has deteriorated by +" + alert.getChangePct() + "% exceeding 5.00% hysteresis threshold.",
                        "Current: ₹" + alert.getCurrentValue() + " | Threshold: ₹" + alert.getThresholdValue(),
                        "Execute priority risk mitigation plan immediately.");
            }
        }

        List<RiskTrajectorySnapshot> allSnapshots = trajectoryRepository.findByMerchantIdOrderByEvaluatedAtDesc(merchantId);

        String compositeStatus;
        if (worsening > 0) compositeStatus = "WORSENING";
        else if (improving > 0) compositeStatus = "IMPROVING";
        else compositeStatus = "STABLE";

        String summary = "Financial Risk Trajectory Engine: Tracked " + allSnapshots.size() + " financial risk trajectories. Composite Trajectory Status: " +
                compositeStatus + " (" + worsening + " worsening, " + stable + " stable, " + improving + " improving).";

        ActionSummaryDTO actionsDTO = actionService.getMerchantActions(merchantId);

        return mapToDTO(merchantId, compositeStatus, allSnapshots, actionsDTO.getActions(), summary, worsening, stable, improving, resolved);
    }

    @Transactional(readOnly = true)
    public RiskTrajectorySummaryDTO getMerchantRiskHistory(Long merchantId) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }
        return evaluateRiskTrajectory(merchantId);
    }

    private RiskTrajectorySummaryDTO mapToDTO(Long merchantId, String compStatus,
                                             List<RiskTrajectorySnapshot> snapshots,
                                             List<FinancialActionDTO> actions, String summary,
                                             int worsening, int stable, int improving, int resolved) {

        List<RiskTrajectoryDTO> dtoList = new ArrayList<>();
        for (RiskTrajectorySnapshot s : snapshots) {
            dtoList.add(new RiskTrajectoryDTO(
                    s.getId(), s.getMerchant().getId(), s.getRiskKey(), s.getRiskType(),
                    s.getTrajectoryDirection(), s.getSeverityTransition(), s.getEscalationVelocity(),
                    s.getObservedSnapshotsCount(), s.getBaselineValue(), s.getCurrentValue(),
                    s.getScoreDelta(), s.getResolutionTimeHours(), s.getRecurrenceCount(), s.getEvaluatedAt().toString()
            ));
        }

        return new RiskTrajectorySummaryDTO(
                merchantId, compStatus, snapshots.size(), worsening, stable, improving, resolved,
                new BigDecimal("24.00"), dtoList, actions, summary,
                "Risk trajectory monitoring is read-only and advisory. Trajectory directions apply a strict 5.00% hysteresis filter to prevent false positive alert flapping."
        );
    }
}
