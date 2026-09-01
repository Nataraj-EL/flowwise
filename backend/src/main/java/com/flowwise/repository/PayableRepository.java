package com.flowwise.repository;

import com.flowwise.entity.Payable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayableRepository extends JpaRepository<Payable, Long> {
    List<Payable> findByMerchantIdOrderByDueDateAsc(Long merchantId);
}
