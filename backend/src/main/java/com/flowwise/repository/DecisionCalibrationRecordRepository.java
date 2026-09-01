package com.flowwise.repository;

import com.flowwise.entity.DecisionCalibrationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DecisionCalibrationRecordRepository extends JpaRepository<DecisionCalibrationRecord, Long> {
    List<DecisionCalibrationRecord> findByMerchantIdOrderByEvaluatedAtDesc(Long merchantId);
    Optional<DecisionCalibrationRecord> findByIdAndMerchantId(Long id, Long merchantId);
    Optional<DecisionCalibrationRecord> findByMerchantIdAndCalibrationKey(Long merchantId, String calibrationKey);
}
