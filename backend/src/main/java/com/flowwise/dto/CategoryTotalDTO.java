package com.flowwise.dto;

import java.math.BigDecimal;

public class CategoryTotalDTO {
    private String category;
    private String type; // CREDIT or DEBIT
    private BigDecimal totalAmount;
    private long count;

    public CategoryTotalDTO() {}

    public CategoryTotalDTO(String category, String type, BigDecimal totalAmount, long count) {
        this.category = category;
        this.type = type;
        this.totalAmount = totalAmount;
        this.count = count;
    }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public long getCount() { return count; }
    public void setCount(long count) { this.count = count; }
}
