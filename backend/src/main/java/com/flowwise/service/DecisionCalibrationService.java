package com.flowwise.service;

import com.flowwise.dto.*;
import com.flowwise.entity.DecisionCalibrationRecord;
import com.flowwise.entity.Merchant;
import com.flowwise.entity.OptionCalibrationFactor;
import com.flowwise.exception.ResourceNotFoundException;
import com.flowwise.repository.DecisionCalibrationRecordRepository;
import com.flowwise.repository.MerchantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.*;

@Service
@Transactional
public class DecisionCalibrationService {

    private final MerchantRepository merchantRepository;
    private final DecisionCalibrationRecordRepository recordRepository;
    private final FinancialDecisionService decisionService;

    public DecisionCalibrationService(MerchantRepository merchantRepository,
                                     DecisionCalibrationRecordRepository recordRepository,
                                     FinancialDecisionService decisionService) {
        this.merchantRepository = merchantRepository;
        this.recordRepository = recordRepository;
        this.decisionService = decisionService;
    }

    public DecisionCalibrationDTO evaluateCalibration(Long merchantId) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found with ID: " + merchantId));

        List<FinancialDecisionDTO> decisions = decisionService.getMerchantDecisions(merchantId);

        int totalEvaluated = 0;
        int successful = 0;

        Map<String, int[]> optionStats = new HashMap<>(); // key -> [total, positive, negative]
        List<String> optionKeys = List.of("COLLECT_RECEIVABLES", "PAY_NOW", "BUILD_RESERVE", "DEFER", "REDUCE_EXPENSE");
        for (String k : optionKeys) {
            optionStats.put(k, new int[]{0, 0, 0});
        }

        for (FinancialDecisionDTO d : decisions) {
            if ("COMPLETED".equalsIgnoreCase(d.getDecisionStatus())) {
                totalEvaluated++;
                String type = d.getDecisionType() != null ? d.getDecisionType() : "COLLECT_RECEIVABLES";
                int[] stats = optionStats.computeIfAbsent(type, k -> new int[]{0, 0, 0});
                stats[0]++;

                if ("POSITIVE".equalsIgnoreCase(d.getOutcomeStatus())) {
                    successful++;
                    stats[1]++;
                } else if ("NEGATIVE".equalsIgnoreCase(d.getOutcomeStatus())) {
                    stats[2]++;
                }
            }
        }

        BigDecimal successRate = totalEvaluated > 0
                ? BigDecimal.valueOf(successful).multiply(new BigDecimal("100")).divide(BigDecimal.valueOf(totalEvaluated), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        String confidenceLevel;
        if (totalEvaluated >= 5) {
            confidenceLevel = "HIGH";
        } else if (totalEvaluated >= 3) {
            confidenceLevel = "MODERATE";
        } else if (totalEvaluated >= 1) {
            confidenceLevel = "LIMITED";
        } else {
            confidenceLevel = "INSUFFICIENT_DATA";
        }

        List<OptionPerformanceDTO> performanceDTOs = new ArrayList<>();
        for (String key : optionKeys) {
            int[] st = optionStats.get(key);
            int sample = st[0];
            int pos = st[1];
            int neg = st[2];

            BigDecimal optSuccess = sample > 0
                    ? BigDecimal.valueOf(pos).multiply(new BigDecimal("100")).divide(BigDecimal.valueOf(sample), 2, RoundingMode.HALF_UP)
                    : BigDecimal.ZERO;

            // Bounded Multiplier Logic (0.80 to 1.20), minimum 3 samples
            BigDecimal mult = new BigDecimal("1.00");
            if (sample >= 3) {
                BigDecimal dev = optSuccess.subtract(new BigDecimal("50.00")).multiply(new BigDecimal("0.004"));
                mult = BigDecimal.ONE.add(dev).min(new BigDecimal("1.20")).max(new BigDecimal("0.80")).setScale(2, RoundingMode.HALF_UP);
            }

            String accuracyStatus;
            if (sample == 0) {
                accuracyStatus = "UNCALIBRATED";
            } else if (optSuccess.compareTo(new BigDecimal("70.00")) >= 0) {
                accuracyStatus = "ACCURATE";
            } else if (optSuccess.compareTo(new BigDecimal("40.00")) < 0) {
                accuracyStatus = "OVERESTIMATED";
            } else {
                accuracyStatus = "UNDERESTIMATED";
            }

            performanceDTOs.add(new OptionPerformanceDTO(
                    null, key, sample, pos, neg, optSuccess, mult, BigDecimal.ZERO, accuracyStatus
            ));
        }

        String summary = "Decision Outcome Calibration Engine: Evaluated " + totalEvaluated + " completed merchant decisions with " +
                successRate + "% success rate. Confidence Level: " + confidenceLevel + ".";

        // Idempotent Persistence
        Optional<DecisionCalibrationRecord> existing = recordRepository.findByMerchantIdAndCalibrationKey(merchantId, "CURRENT_CALIBRATION_BASELINE");
        DecisionCalibrationRecord rec = existing.orElseGet(DecisionCalibrationRecord::new);
        rec.setMerchant(merchant);
        rec.setCalibrationKey("CURRENT_CALIBRATION_BASELINE");
        rec.setTotalEvaluatedDecisions(totalEvaluated);
        rec.setSuccessfulDecisions(successful);
        rec.setOverallSuccessRatePct(successRate);
        rec.setConfidenceLevel(confidenceLevel);
        rec.setDataCompletenessPct(new BigDecimal("100.00"));
        rec.setSummaryInsight(summary);
        rec.setEvaluatedAt(Instant.now());

        rec.getOptionFactors().clear();
        for (OptionPerformanceDTO dto : performanceDTOs) {
            OptionCalibrationFactor factor = new OptionCalibrationFactor(
                    rec, dto.getOptionKey(), dto.getTotalSampleCount(), dto.getPositiveOutcomeCount(),
                    dto.getNegativeOutcomeCount(), dto.getSuccessRatePct(), dto.getCalibrationMultiplier(),
                    dto.getAvgCashImpactVariance(), dto.getAccuracyStatus()
            );
            rec.getOptionFactors().add(factor);
        }

        DecisionCalibrationRecord saved = recordRepository.save(rec);

        return mapToDTO(saved, decisions);
    }

    @Transactional(readOnly = true)
    public DecisionCalibrationDTO getMerchantCalibration(Long merchantId) {
        if (!merchantRepository.existsById(merchantId)) {
            throw new ResourceNotFoundException("Merchant not found with ID: " + merchantId);
        }
        return evaluateCalibration(merchantId);
    }

    private DecisionCalibrationDTO mapToDTO(DecisionCalibrationRecord r, List<FinancialDecisionDTO> decisions) {
        List<OptionPerformanceDTO> optionDTOs = new ArrayList<>();
        for (OptionCalibrationFactor f : r.getOptionFactors()) {
            optionDTOs.add(new OptionPerformanceDTO(
                    f.getId(), f.getOptionKey(), f.getTotalSampleCount(), f.getPositiveOutcomeCount(),
                    f.getNegativeOutcomeCount(), f.getSuccessRatePct(), f.getCalibrationMultiplier(),
                    f.getAvgCashImpactVariance(), f.getAccuracyStatus()
            ));
        }

        return new DecisionCalibrationDTO(
                r.getId(),
                r.getMerchant().getId(),
                r.getCalibrationKey(),
                r.getTotalEvaluatedDecisions(),
                r.getSuccessfulDecisions(),
                r.getOverallSuccessRatePct(),
                r.getConfidenceLevel(),
                r.getDataCompletenessPct(),
                r.getSummaryInsight(),
                r.getEvaluatedAt().toString(),
                optionDTOs,
                decisions,
                "Decision outcome calibration is read-only and advisory. Calibration factors tune future decision scoring models without mutating historical records."
        );
    }
}
