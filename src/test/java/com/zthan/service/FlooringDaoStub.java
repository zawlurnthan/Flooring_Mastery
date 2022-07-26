package com.zthan.service;

import com.zthan.dao.FlooringDao;
import com.zthan.dto.Order;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class FlooringDaoStub implements FlooringDao {

    public Order onlyOrder;

    public FlooringDaoStub() {
        // create order
        onlyOrder = new Order();
        onlyOrder.setOrderNumber(1);
        onlyOrder.setCustomerName("Doctor Joe");
        onlyOrder.setStateAbbreviation("TX");
        onlyOrder.setTaxRate(new BigDecimal("4.45"));
        onlyOrder.setProductType("Wood");
        onlyOrder.setArea(new BigDecimal("101"));
        onlyOrder.setCostPerSqFt(new BigDecimal("5.15"));
        onlyOrder.setLaborCostPerSqFt(new BigDecimal("4.75"));
        onlyOrder.setMaterialCost(new BigDecimal("520.15"));
        onlyOrder.setLaborCost(new BigDecimal("479.74"));
        onlyOrder.setTax(new BigDecimal("44.50"));
        onlyOrder.setTotal(new BigDecimal("1044.12"));
    }

    @Override
    public Order createOrder(Order order, String date){
        return order.getOrderNumber() == onlyOrder.getOrderNumber() ? onlyOrder : null;
    }

    @Override
    public Order getOrder(int id, String date) {
        return id == onlyOrder.getOrderNumber() ? onlyOrder : null;
    }

    @Override
    public List<Order> getAllOrders(String date) {
        List<Order> allOrders = new ArrayList<>();
        allOrders.add(onlyOrder);
        return allOrders;
    }

    @Override
    public Order updateOrder(Order order, String date) {
        return order.getOrderNumber() == onlyOrder.getOrderNumber() ? onlyOrder : null;
    }

    @Override
    public Order removeOrder(int id, String date) {
        return id == onlyOrder.getOrderNumber() ? onlyOrder : null;
    }

    @Override
    public void backupFiles() {
    }
}
