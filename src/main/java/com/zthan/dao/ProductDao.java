package com.zthan.dao;

import com.zthan.dto.Product;

import java.util.List;

public interface ProductDao {
    Product getProduct(String type) throws PersistenceException;
    List<Product> getAllProducts() throws PersistenceException;
    void readProductFile() throws PersistenceException;
}
