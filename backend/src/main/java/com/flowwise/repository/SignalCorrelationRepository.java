package com.flowwise.repository;

import com.flowwise.entity.SignalCorrelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SignalCorrelationRepository extends JpaRepository<SignalCorrelation, Long> {
    List<SignalCorrelation> findByMerchantIdOrderByEvaluatedAtDesc(Long merchantId);
    Optional<SignalCorrelation> findByIdAndMerchantId(Long id, Long merchantId);
    Optional<SignalCorrelation> findByMerchantIdAndCorrelationKey(Long merchantId, String correlationKey);
}
