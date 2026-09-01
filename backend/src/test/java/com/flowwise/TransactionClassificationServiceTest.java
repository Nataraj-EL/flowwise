package com.flowwise;

import com.flowwise.service.TransactionClassificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionClassificationServiceTest {

    private TransactionClassificationService classificationService;

    @BeforeEach
    void setUp() {
        classificationService = new TransactionClassificationService();
    }

    @Test
    void testClassifyPayroll() {
        String category = classificationService.classifyTransaction("Monthly Employee Salary Disbursement", "Monthly Staff Payroll", "DEBIT");
        assertEquals("PAYROLL", category);
    }

    @Test
    void testClassifyTax() {
        String category = classificationService.classifyTransaction("GST3B Tax Filing Settlement", "GST Department", "DEBIT");
        assertEquals("TAX", category);
    }

    @Test
    void testClassifyRent() {
        String category = classificationService.classifyTransaction("Commercial Lease Premises Rent", "Tech Park Realty", "DEBIT");
        assertEquals("RENT", category);
    }

    @Test
    void testClassifyUtilities() {
        String category = classificationService.classifyTransaction("Electricity and Utility Bill", "Power Corp", "DEBIT");
        assertEquals("UTILITIES", category);
    }

    @Test
    void testClassifySales() {
        String category = classificationService.classifyTransaction("UPI QR Retail Collection", "Customer", "CREDIT");
        assertEquals("SALES", category);
    }
}
