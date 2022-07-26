package com.zthan.dao;

import com.zthan.dto.Product;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ProductDaoFileImpl implements ProductDao {

    Map<String, Product> product_file = new LinkedHashMap<>();
    String DELIMITER = ",";
    String PRODUCT_FILE = "Data/Products.txt";

    @Override
    public Product getProduct(String type) throws PersistenceException {
        readProductFile();
        return product_file.get(type);
    }

    @Override
    public List<Product> getAllProducts() throws PersistenceException {
        readProductFile();
        return product_file.values().stream().toList();
    }

    // convert String into product object
    private Product unmarshallProduct(String text) {
        String[] tokens = text.split(DELIMITER);
        String type = tokens[0];
        BigDecimal material = new BigDecimal(tokens[1]);
        BigDecimal labor = new BigDecimal(tokens[2]);

        return new Product(type, material, labor);
    }

    @Override
    public void readProductFile() throws PersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(PRODUCT_FILE)));
        } catch (FileNotFoundException e) {
            throw new PersistenceException("Could not load product file into memory.", e);
        }
        // skip first line, convert texts to product objects and collect it as Map
        product_file = scanner
                .tokens()
                .skip(1)
                .map(this::unmarshallProduct)
                .collect(Collectors.toMap(Product::getType, Function.identity()));
        scanner.close();
    }
}
