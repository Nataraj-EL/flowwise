package com.flowwise.repository;

import com.flowwise.entity.RiskTrajectorySnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RiskTrajectorySnapshotRepository extends JpaRepository<RiskTrajectorySnapshot, Long> {
    List<RiskTrajectorySnapshot> findByMerchantIdOrderByEvaluatedAtDesc(Long merchantId);
    Optional<RiskTrajectorySnapshot> findByIdAndMerchantId(Long id, Long merchantId);
    Optional<RiskTrajectorySnapshot> findByMerchantIdAndRiskKey(Long merchantId, String riskKey);
}
