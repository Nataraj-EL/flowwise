package com.flowwise.repository;

import com.flowwise.entity.Receivable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceivableRepository extends JpaRepository<Receivable, Long> {
    List<Receivable> findByMerchantIdOrderByDueDateAsc(Long merchantId);
}
