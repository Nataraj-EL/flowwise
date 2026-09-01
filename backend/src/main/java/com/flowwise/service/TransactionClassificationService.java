package com.flowwise.service;

import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class TransactionClassificationService {

    /**
     * Deterministic classification rule engine mapping transaction metadata into standardized categories:
     * SALES, INVENTORY, OPERATIONS, PAYROLL, TAX, UTILITIES, RENT, TRANSFER, REFUND, OTHER.
     * Isolated design allowing an AI classifier to extend/augment in future sprints.
     */
    public String classifyTransaction(String description, String counterparty, String type) {
        if (description == null) description = "";
        if (counterparty == null) counterparty = "";
        if (type == null) type = "";

        String text = (description + " " + counterparty).toLowerCase(Locale.ROOT);
        String upperType = type.toUpperCase(Locale.ROOT);

        if (text.contains("salary") || text.contains("payroll") || text.contains("stipend") || text.contains("wages")) {
            return "PAYROLL";
        }
        if (text.contains("gst") || text.contains("tax") || text.contains("tds") || text.contains("filing")) {
            return "TAX";
        }
        if (text.contains("rent") || text.contains("lease") || text.contains("realty") || text.contains("premise")) {
            return "RENT";
        }
        if (text.contains("power") || text.contains("electricity") || text.contains("utility") || text.contains("water") || text.contains("broadband")) {
            return "UTILITIES";
        }
        if (text.contains("refund") || text.contains("return")) {
            return "REFUND";
        }
        if (text.contains("transfer") || text.contains("reserve") || text.contains("internal")) {
            return "TRANSFER";
        }
        if (text.contains("inventory") || text.contains("packaging") || text.contains("purchase") || text.contains("supplier") || text.contains("goods")) {
            return "INVENTORY";
        }
        if (text.contains("logistics") || text.contains("freight") || text.contains("courier") || text.contains("overhead") || text.contains("maintenance")) {
            return "OPERATIONS";
        }

        if ("CREDIT".equals(upperType)) {
            return "SALES";
        }

        return "OTHER";
    }
}
