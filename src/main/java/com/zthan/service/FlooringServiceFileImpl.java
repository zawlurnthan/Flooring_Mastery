package com.zthan.service;

import com.zthan.dao.*;
import com.zthan.dto.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


@Component
public class FlooringServiceFileImpl implements FlooringService {

    FlooringDao dao;
    ProductDao pDao;
    StateDao sDoa;

    @Autowired
    public FlooringServiceFileImpl(FlooringDao dao, ProductDao pDao, StateDao sDoa) {
        this.dao = dao;
        this.pDao = pDao;
        this.sDoa = sDoa;
    }

    @Override
    public Order createOrder(Order order, String date){
        return dao.createOrder(order, date);
    }

    @Override
    public Order getOrder(int id, String date) {
        return dao.getOrder(id, date);
    }

    @Override
    public List<Order> getAllOrders(String date) {
        return dao.getAllOrders(date);
    }

    @Override
    public Order updateOrder(Order order, String date) throws PersistenceException {
        return dao.updateOrder(order, date);
    }

    @Override
    public Order removeOrder(int id, String date) throws PersistenceException, IOException {
        return dao.removeOrder(id, date);
    }

    @Override
    public int getAvailableOrderId(String date) {
        return dao.getAllOrders(date)
                .stream()
                .map(Order::getOrderNumber)
                .mapToInt(x -> x)
                .max()
                .orElse(0)
                + 1;
    }

    @Override
    public void calculateCost(Order order) throws PersistenceException {
        // get input data
        String state = order.getStateAbbreviation();
        String product = order.getProductType();
        BigDecimal area = order.getArea();

        // get data from product and tax files
        BigDecimal costPerSqFt = pDao.getProduct(product).getCostPerSquareFoot();
        BigDecimal laborCostPerSqFt = pDao.getProduct(product).getLaborCostPerSquareFoot();
        BigDecimal taxRate = sDoa.getState(state).getTaxRate();


        // calculate the rest of parts
        BigDecimal materialCost = area.multiply(costPerSqFt).setScale(2, RoundingMode.HALF_UP);
        BigDecimal laborCost = area.multiply(laborCostPerSqFt).setScale(2, RoundingMode.HALF_UP);

        BigDecimal cost = materialCost.add(laborCost).setScale(2, RoundingMode.HALF_UP);
        BigDecimal taxPercentage = taxRate.divide(new BigDecimal("100"), RoundingMode.HALF_UP);

        BigDecimal tax = cost.multiply(taxPercentage).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = materialCost.add(laborCost).add(tax).setScale(2, RoundingMode.HALF_UP);

        // set all data as order properties
        order.setTaxRate(taxRate);
        order.setCostPerSqFt(costPerSqFt);
        order.setLaborCostPerSqFt(laborCostPerSqFt);
        order.setMaterialCost(materialCost);
        order.setLaborCost(laborCost);
        order.setTax(tax);
        order.setTotal(total);
    }

    @Override
    public void backupFiles() throws PersistenceException, FileNotFoundException {
        dao.backupFiles();
    }
}
