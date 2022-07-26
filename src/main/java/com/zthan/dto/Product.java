package com.zthan.dto;

import java.math.BigDecimal;

public class Product {
    private String type;
    private BigDecimal CostPerSquareFoot;
    private BigDecimal LaborCostPerSquareFoot;

    public Product() {
    }

    public Product(String type, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot) {
        this.type = type;
        CostPerSquareFoot = costPerSquareFoot;
        LaborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getCostPerSquareFoot() {
        return CostPerSquareFoot;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        CostPerSquareFoot = costPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return LaborCostPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
        LaborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;

        if (!getType().equals(product.getType())) return false;
        if (!getCostPerSquareFoot().equals(product.getCostPerSquareFoot())) return false;
        return getLaborCostPerSquareFoot().equals(product.getLaborCostPerSquareFoot());
    }

    @Override
    public int hashCode() {
        int result = getType().hashCode();
        result = 31 * result + getCostPerSquareFoot().hashCode();
        result = 31 * result + getLaborCostPerSquareFoot().hashCode();
        return result;
    }
}
