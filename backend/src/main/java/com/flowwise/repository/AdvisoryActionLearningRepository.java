package com.flowwise.repository;

import com.flowwise.entity.AdvisoryActionLearning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdvisoryActionLearningRepository extends JpaRepository<AdvisoryActionLearning, Long> {
    List<AdvisoryActionLearning> findByMerchantIdOrderByEvaluatedAtDesc(Long merchantId);
    Optional<AdvisoryActionLearning> findByMerchantIdAndActionTypeAndContextType(Long merchantId, String actionType, String contextType);
    Optional<AdvisoryActionLearning> findByMerchantIdAndActionType(Long merchantId, String actionType);
}
