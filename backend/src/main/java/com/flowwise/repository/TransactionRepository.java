package com.flowwise.repository;

import com.flowwise.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByMerchantIdOrderByTransactionDateDesc(Long merchantId);

    boolean existsBySourceCaptureId(Long sourceCaptureId);

    java.util.Optional<Transaction> findBySourceCaptureId(Long sourceCaptureId);

    @Query("SELECT t FROM Transaction t WHERE t.merchant.id = :merchantId " +
           "AND (:type IS NULL OR LOWER(t.type) = LOWER(:type)) " +
           "AND (:category IS NULL OR LOWER(t.category) = LOWER(:category)) " +
           "AND (:search IS NULL OR LOWER(t.counterparty) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "     OR LOWER(t.description) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "     OR LOWER(t.transactionReference) LIKE LOWER(CONCAT('%', :search, '%'))) " +
           "ORDER BY t.transactionDate DESC")
    List<Transaction> findFilteredTransactions(
            @Param("merchantId") Long merchantId,
            @Param("type") String type,
            @Param("category") String category,
            @Param("search") String search
    );
}
