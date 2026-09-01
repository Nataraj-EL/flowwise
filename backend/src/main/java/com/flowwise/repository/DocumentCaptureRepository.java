package com.flowwise.repository;

import com.flowwise.entity.DocumentCapture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentCaptureRepository extends JpaRepository<DocumentCapture, Long> {
    List<DocumentCapture> findByMerchantIdOrderByCapturedAtDesc(Long merchantId);
}
