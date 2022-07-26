package com.zthan.dao;

import com.zthan.dto.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;



class FlooringDaoTest {

    FlooringDao testDao;
    String date;


    @BeforeEach
    void setUp() {
        date = "08283000";
        testDao = new FlooringDaoFileImpl("TestOrder/Orders_");
    }

    // teardown method erases file after each use
    @AfterEach
    void tearDown() throws IOException {
        String filePath = "TestOrder/Orders_" + date + ".txt";
        new FileWriter(filePath);
    }


    @Test
    void testCreateAndGetOrder() {
        // create order
        Order order = new Order();
        order.setOrderNumber(1);
        order.setCustomerName("Doctor Joe");
        order.setStateAbbreviation("TX");
        order.setTaxRate(new BigDecimal("4.45"));
        order.setProductType("Wood");
        order.setArea(new BigDecimal("101"));
        order.setCostPerSqFt(new BigDecimal("5.15"));
        order.setLaborCostPerSqFt(new BigDecimal("4.75"));
        order.setMaterialCost(new BigDecimal("520.15"));
        order.setLaborCost(new BigDecimal("479.74"));
        order.setTax(new BigDecimal("44.50"));
        order.setTotal(new BigDecimal("1044.12"));

        // add Order into a file through Dao
        testDao.createOrder(order, date);
        Order retrievedOrder = testDao.getOrder(order.getOrderNumber(), date);

        // orderNumber is auto increment, and it is associate with a Map which is not able to access from here (testDao)
        // For this reason, orderNumber won't be properly worked.
        assertEquals(order.getOrderNumber(), retrievedOrder.getOrderNumber(), "Check Order Id");
        assertEquals(order.getCustomerName(), retrievedOrder.getCustomerName(), "Chek Name");
        assertEquals(order.getStateAbbreviation(), retrievedOrder.getStateAbbreviation(), "Check State");
        assertEquals(order.getProductType(), retrievedOrder.getProductType(), "Check Product type");
        assertEquals(order.getMaterialCost(), retrievedOrder.getMaterialCost(), "Check Material Cost");
        assertEquals(order.getLaborCost(), retrievedOrder.getLaborCost(), "Check Labor Cost");
        assertEquals(order.getArea(), retrievedOrder.getArea(), "Check Area");
        assertEquals(order.getTax(), retrievedOrder.getTax(), "Check Tax");
        assertEquals(order.getTotal(), retrievedOrder.getTotal(), "Check Total");
    }


    @Test
    void testGetAllOrders() {
        // create first order
        Order first = new Order();
        first.setOrderNumber(1);
        first.setCustomerName("Doctor Joe");
        first.setStateAbbreviation("TX");
        first.setTaxRate(new BigDecimal("4.45"));
        first.setProductType("Wood");
        first.setArea(new BigDecimal("101"));
        first.setCostPerSqFt(new BigDecimal("5.15"));
        first.setLaborCostPerSqFt(new BigDecimal("4.75"));
        first.setMaterialCost(new BigDecimal("520.15"));
        first.setLaborCost(new BigDecimal("479.74"));
        first.setTax(new BigDecimal("44.50"));
        first.setTotal(new BigDecimal("1044.12"));

        // create second order
        Order second = new Order();
        second.setOrderNumber(2);
        second.setCustomerName("John Well");
        second.setStateAbbreviation("WA");
        second.setTaxRate(new BigDecimal("9.25"));
        second.setProductType("Carpet");
        second.setArea(new BigDecimal("110"));
        second.setCostPerSqFt(new BigDecimal("2.25"));
        second.setLaborCostPerSqFt(new BigDecimal("2.10"));
        second.setMaterialCost(new BigDecimal("247.50"));
        second.setLaborCost(new BigDecimal("231"));
        second.setTax(new BigDecimal("44.26"));
        second.setTotal(new BigDecimal("522.76"));

        // create both order through Dao
        testDao.createOrder(first, date);
        testDao.createOrder(second, date);

        // retrieved all order
        List<Order> allOrders = testDao.getAllOrders(date);

        assertNotNull(allOrders, "Check the list is empty or not");
        assertEquals(2, allOrders.size(), "Check the number of orders.");

        assertTrue(testDao.getAllOrders(date).contains(first), "Check if there is first Order.");
        assertTrue(testDao.getAllOrders(date).contains(second), "Check if there is Second Order.");
    }

    @Test
    void testRemoveOrder() throws PersistenceException, IOException {
        // create first order
        Order first = new Order();
        first.setOrderNumber(1);
        first.setCustomerName("Doctor Joe");
        first.setStateAbbreviation("TX");
        first.setTaxRate(new BigDecimal("4.45"));
        first.setProductType("Wood");
        first.setArea(new BigDecimal("101"));
        first.setCostPerSqFt(new BigDecimal("5.15"));
        first.setLaborCostPerSqFt(new BigDecimal("4.75"));
        first.setMaterialCost(new BigDecimal("520.15"));
        first.setLaborCost(new BigDecimal("479.74"));
        first.setTax(new BigDecimal("44.50"));
        first.setTotal(new BigDecimal("1044.12"));

        // create second order
        Order second = new Order();
        second.setOrderNumber(2);
        second.setCustomerName("John Well");
        second.setStateAbbreviation("WA");
        second.setTaxRate(new BigDecimal("9.25"));
        second.setProductType("Carpet");
        second.setArea(new BigDecimal("110"));
        second.setCostPerSqFt(new BigDecimal("2.25"));
        second.setLaborCostPerSqFt(new BigDecimal("2.10"));
        second.setMaterialCost(new BigDecimal("247.50"));
        second.setLaborCost(new BigDecimal("231"));
        second.setTax(new BigDecimal("44.26"));
        second.setTotal(new BigDecimal("522.76"));

        // create both order through Dao
        testDao.createOrder(first, date);
        testDao.createOrder(second, date);

        // remove first Order
        Order removedFirst = testDao.removeOrder(first.getOrderNumber(), date);
        // check first was removed
        assertEquals(removedFirst, first, "First order was removed.");

        // get all items
        List<Order> allOrders = testDao.getAllOrders(date);

        // check the size
        assertNotNull(allOrders, "Check the list is empty or not.");
        assertEquals(1, allOrders.size(), "Size of the list should be only one");

        assertFalse(allOrders.contains(first), "First Order should not include in the list.");
        assertTrue(allOrders.contains(second), "Second Order should include in the list.");

        // remove second order
        Order removedSecond = testDao.removeOrder(second.getOrderNumber(), date);
        // check if second was removed
        assertEquals(removedSecond, second, "Second order should be removed.");

        // retrieve all orders
        allOrders = testDao.getAllOrders(date);
        // check if list is empty
        assertTrue(allOrders.isEmpty(), "List should be empty.");

        // try to get both orders by using their old ids'
        Order retrievedFirst = testDao.getOrder(first.getOrderNumber(), date);
        Order retrievedSecond = testDao.getOrder(second.getOrderNumber(), date);

        assertNull(retrievedFirst, "Should be null");
        assertNull(retrievedSecond, "Should be null");
    }
}