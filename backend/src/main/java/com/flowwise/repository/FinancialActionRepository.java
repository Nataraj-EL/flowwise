package com.flowwise.repository;

import com.flowwise.entity.FinancialAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinancialActionRepository extends JpaRepository<FinancialAction, Long> {
    List<FinancialAction> findByMerchantIdOrderByCreatedAtDesc(Long merchantId);
    Optional<FinancialAction> findByMerchantIdAndActionKey(Long merchantId, String actionKey);
}
