package com.zthan.dao;

import com.zthan.dto.Order;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


public interface FlooringDao {
    Order createOrder(Order order, String date);
    Order getOrder(int id, String date);
    List<Order> getAllOrders(String date);
    Order updateOrder(Order order, String date) throws PersistenceException;
    Order removeOrder(int id, String date) throws PersistenceException, IOException;
    void backupFiles() throws PersistenceException, FileNotFoundException;
}
