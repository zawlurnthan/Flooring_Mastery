package com.zthan.service;

import com.zthan.dao.PersistenceException;
import com.zthan.dto.Order;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


public interface FlooringService {
    Order createOrder(Order order, String date);
    Order getOrder(int id, String date);
    List<Order> getAllOrders(String date);
    Order updateOrder(Order order, String date) throws PersistenceException;
    Order removeOrder(int id, String date) throws PersistenceException, IOException;
    int getAvailableOrderId(String date) throws PersistenceException;
    void calculateCost(Order order) throws PersistenceException;
    void backupFiles() throws PersistenceException, FileNotFoundException;
}
