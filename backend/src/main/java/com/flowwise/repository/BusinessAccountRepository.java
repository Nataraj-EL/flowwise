package com.flowwise.repository;

import com.flowwise.entity.BusinessAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessAccountRepository extends JpaRepository<BusinessAccount, Long> {
    List<BusinessAccount> findByMerchantId(Long merchantId);
}
