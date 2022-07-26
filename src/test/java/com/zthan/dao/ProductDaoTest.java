package com.zthan.dao;

import com.zthan.dto.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoTest {

    ProductDao pDao;
    Product wood, tile;

    @BeforeEach
    void setUp() {
        pDao = new ProductDaoFileImpl();

        wood = new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75"));
        tile = new Product("Tile", new BigDecimal("3.50"), new BigDecimal("4.15"));
    }

    @Test
    void getProduct() throws PersistenceException {
        Product retrievedWood = pDao.getProduct("Wood");
        Product retrievedTile = pDao.getProduct("Tile");

        assertEquals(wood.getType(), retrievedWood.getType(), "Check Name similarity!");
        assertEquals(wood.getCostPerSquareFoot(), retrievedWood.getCostPerSquareFoot(), "Check Cost");
        assertEquals(wood.getLaborCostPerSquareFoot(), retrievedWood.getLaborCostPerSquareFoot(), "Check Labor cost");

        assertEquals(tile.getType(), retrievedTile.getType(), "Check Name similarity!");
        assertEquals(tile.getCostPerSquareFoot(), retrievedTile.getCostPerSquareFoot(), "Check Cost");
        assertEquals(tile.getLaborCostPerSquareFoot(), retrievedTile.getLaborCostPerSquareFoot(), "Check Labor cost");
    }

    @Test
    void getAllProducts() throws PersistenceException {
        List<Product> products = pDao.getAllProducts();

        assertNotNull(products, "Check list is empty or not.");
        assertEquals(4, products.size(), "Check number of list");
        assertTrue(products.contains(wood), "Check if it has wood");
        assertTrue(products.contains(tile), "Check if it has tile");
    }

}