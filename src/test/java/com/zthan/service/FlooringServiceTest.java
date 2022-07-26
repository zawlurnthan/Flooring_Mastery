package com.zthan.service;

import com.zthan.dao.*;
import com.zthan.dto.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;


class FlooringServiceTest {

    FlooringDaoStub dao;
    StateDao sDao;
    ProductDao pDao;
    FlooringService testService;
    String date;


    @BeforeEach
    void setUp() {
        dao = new FlooringDaoStub();
        pDao = new ProductDaoFileImpl();
        sDao = new StateDaoFileImpl();
        testService = new FlooringServiceFileImpl(dao, pDao, sDao);
        date = "09303000";
    }


    // teardown method erases file after each use
    @AfterEach
    void tearDown() throws IOException {
        new FileWriter("TestOrder/Orders_" + date + ".txt");
    }

    @Test
    void createOrder() {
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

        testService.createOrder(second, date);
    }

    @Test
    void getOrder() {
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
        // add order
        testService.createOrder(first, date);

        // act & assert
        Order shouldBeFirst = testService.getOrder(1, date);

        assertNotNull(shouldBeFirst, "It should be first.");
        assertEquals(first, shouldBeFirst, "It should be first.");

        Order shouldBeNull = testService.getOrder(18, date);
        assertNull(shouldBeNull, "It should be null.");
    }

    @Test
    void getAllOrders() {
        // arrange
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

        // act & assert
        assertEquals(1, testService.getAllOrders(date).size(), "Should be only one order.");
        assertTrue(testService.getAllOrders(date).contains(first), "Order should be first.");
    }

    @Test
    void removeOrder() throws PersistenceException, IOException {
        // arrange
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
        // add order
        testService.createOrder(first, date);

        // act & assert
        Order shouldBeFirst = testService.removeOrder(first.getOrderNumber(), date);

        assertNotNull(shouldBeFirst, "It should not null.");
        assertEquals(first, shouldBeFirst, "Removed order should be first.");

        Order shouldBeNull = testService.removeOrder(55, date);
        assertNull(shouldBeNull, "It should be null.");
    }
}