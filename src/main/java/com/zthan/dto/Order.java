package com.zthan.dto;

import java.math.BigDecimal;

public class Order {
    private int orderNumber = 0;
    private String customerName;
    private String stateAbbreviation;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal area;
    private BigDecimal costPerSqFt;
    private BigDecimal laborCostPerSqFt;
    private BigDecimal MaterialCost;
    private BigDecimal LaborCost;
    private BigDecimal tax;
    private BigDecimal total;

    public Order() {
    }

    public Order(int orderNumber, String customerName, String stateAbbreviation, BigDecimal taxRate,
                 String productType, BigDecimal area, BigDecimal costPerSqFt,
                 BigDecimal laborCostPerSqFt, BigDecimal materialCost, BigDecimal laborCost,
                 BigDecimal tax, BigDecimal total) {

        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.stateAbbreviation = stateAbbreviation;
        this.taxRate = taxRate;
        this.productType = productType;
        this.area = area;
        this.costPerSqFt = costPerSqFt;
        this.laborCostPerSqFt = laborCostPerSqFt;
        MaterialCost = materialCost;
        LaborCost = laborCost;
        this.tax = tax;
        this.total = total;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStateAbbreviation() {
        return stateAbbreviation;
    }

    public void setStateAbbreviation(String stateAbbreviation) {
        this.stateAbbreviation = stateAbbreviation;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getCostPerSqFt() {
        return costPerSqFt;
    }

    public void setCostPerSqFt(BigDecimal costPerSqFt) {
        this.costPerSqFt = costPerSqFt;
    }

    public BigDecimal getLaborCostPerSqFt() {
        return laborCostPerSqFt;
    }

    public void setLaborCostPerSqFt(BigDecimal laborCostPerSqFt) {
        this.laborCostPerSqFt = laborCostPerSqFt;
    }

    public BigDecimal getMaterialCost() {
        return MaterialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        MaterialCost = materialCost;
    }

    public BigDecimal getLaborCost() {
        return LaborCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        LaborCost = laborCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;

        if (getOrderNumber() != order.getOrderNumber()) return false;
        if (!getCustomerName().equals(order.getCustomerName())) return false;
        if (!getStateAbbreviation().equals(order.getStateAbbreviation())) return false;
        if (!getTaxRate().equals(order.getTaxRate())) return false;
        if (!getProductType().equals(order.getProductType())) return false;
        if (!getArea().equals(order.getArea())) return false;
        if (!getCostPerSqFt().equals(order.getCostPerSqFt())) return false;
        if (!getLaborCostPerSqFt().equals(order.getLaborCostPerSqFt())) return false;
        if (!getMaterialCost().equals(order.getMaterialCost())) return false;
        if (!getLaborCost().equals(order.getLaborCost())) return false;
        if (!getTax().equals(order.getTax())) return false;
        return getTotal().equals(order.getTotal());
    }

    @Override
    public int hashCode() {
        int result = getOrderNumber();
        result = 31 * result + getCustomerName().hashCode();
        result = 31 * result + getStateAbbreviation().hashCode();
        result = 31 * result + getTaxRate().hashCode();
        result = 31 * result + getProductType().hashCode();
        result = 31 * result + getArea().hashCode();
        result = 31 * result + getCostPerSqFt().hashCode();
        result = 31 * result + getLaborCostPerSqFt().hashCode();
        result = 31 * result + getMaterialCost().hashCode();
        result = 31 * result + getLaborCost().hashCode();
        result = 31 * result + getTax().hashCode();
        result = 31 * result + getTotal().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "orderNumber: " + orderNumber +
                "\ncustomerName: " + customerName +
                "\nstate: " + stateAbbreviation +
                "\ntaxRate: " + taxRate +
                "\nproductType: " + productType +
                "\narea: " + area +
                "\ncostPerSquareFoot: " + costPerSqFt +
                "\nlaborCostPerSquareFoot: " + laborCostPerSqFt +
                "\nMaterialCost: " + MaterialCost +
                "\nLaborCost: " + LaborCost +
                "\ntax: " + tax +
                "\ntotal: " + total + "\n";
    }
}
