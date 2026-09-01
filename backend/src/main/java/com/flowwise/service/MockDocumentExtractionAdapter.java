package com.flowwise.service;

import com.flowwise.entity.DocumentCapture;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;

@Component
public class MockDocumentExtractionAdapter implements DocumentExtractionAdapter {

    @Override
    public DocumentExtractionResult extract(DocumentCapture capture) {
        String docType = capture.getDocumentType() != null ? capture.getDocumentType().toUpperCase() : "RECEIPT";
        
        BigDecimal amount = capture.getExtractedAmount();
        String vendor = capture.getExtractedVendor();
        String category = capture.getExtractedCategory();
        BigDecimal tax = BigDecimal.ZERO;
        String reference = "REF-" + (1000 + (capture.getId() != null ? capture.getId() : 1));

        if ("INVOICE".equals(docType)) {
            if (amount == null) amount = new BigDecimal("45000.00");
            if (vendor == null) vendor = "Apex Wholesale Distributors [DEMO]";
            if (category == null) category = "INVENTORY";
            tax = amount.multiply(new BigDecimal("0.18")).setScale(2, RoundingMode.HALF_UP);
            reference = "INV-9920";
        } else if ("EXPENSE".equals(docType)) {
            if (amount == null) amount = new BigDecimal("1200.00");
            if (vendor == null) vendor = "Fuel & Express Transport [DEMO]";
            if (category == null) category = "LOGISTICS";
            tax = amount.multiply(new BigDecimal("0.18")).setScale(2, RoundingMode.HALF_UP);
            reference = "EXP-1044";
        } else { // RECEIPT
            if (amount == null) amount = new BigDecimal("2450.00");
            if (vendor == null) vendor = "Metro Commercial Supplies [DEMO]";
            if (category == null) category = "OPERATIONS";
            tax = amount.multiply(new BigDecimal("0.18")).setScale(2, RoundingMode.HALF_UP);
            reference = "REC-8841";
        }

        return new DocumentExtractionResult(
                amount,
                vendor,
                category,
                tax,
                reference,
                OffsetDateTime.now()
        );
    }
}
