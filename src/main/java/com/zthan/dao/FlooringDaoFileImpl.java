package com.zthan.dao;

import com.zthan.dto.Order;
import org.springframework.stereotype.Component;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;


@Component
public class FlooringDaoFileImpl implements FlooringDao {

    Map<Integer, Order> orders = new LinkedHashMap<>();
    String FOLDER;
    String DELIMITER = ",";
    String ORDER_FILE;


    // set up file for regular use
    public FlooringDaoFileImpl() {
        this.FOLDER = "Orders/Orders_";
    }

    // setup file for unit test
    public FlooringDaoFileImpl(String testFolder){
        this.FOLDER = testFolder;
    }

    @Override
    public Order createOrder(Order order, String date){
        try {
            readOrderFile(date);
        } catch (PersistenceException ignored) {}
        // add order into the Map
        Order newOrder = orders.put(order.getOrderNumber(), order);
        try {
            writeOrderFile(date);
        } catch (PersistenceException ignored) {}
        return newOrder;
    }

    @Override
    public Order getOrder(int id, String date) {
        try {
            readOrderFile(date);
        } catch (PersistenceException ignored) {}
        return orders.get(id);
    }

    @Override
    public List<Order> getAllOrders(String date) {
        try {
            readOrderFile(date);
        } catch (PersistenceException e) {
            orders.clear();
        }
        return orders.values().stream().toList();
    }

    @Override
    public Order updateOrder(Order order, String date) {
        try {
            readOrderFile(date);
        } catch (PersistenceException e) {
            System.out.println("No such order or file.");
        }
        // add order into the Map
        Order updateOrder = orders.put(order.getOrderNumber(), order);
        try {
            writeOrderFile(date);
        } catch (PersistenceException ignored) {}
        return updateOrder;
    }

    @Override
    public Order removeOrder(int id, String date) throws PersistenceException {
        readOrderFile(date);
        Order removedOrder = orders.remove(id);
        writeOrderFile(date);
        return removedOrder;
    }

    // convert order object to string
    private String marshallOrder(Order order) {
        return order.getOrderNumber() + DELIMITER
                + order.getCustomerName() + DELIMITER
                + order.getStateAbbreviation() + DELIMITER
                + order.getTaxRate() + DELIMITER
                + order.getProductType() + DELIMITER
                + order.getArea() + DELIMITER
                + order.getCostPerSqFt() + DELIMITER
                + order.getLaborCostPerSqFt() + DELIMITER
                + order.getMaterialCost() + DELIMITER
                + order.getLaborCost() + DELIMITER
                + order.getTax() + DELIMITER
                + order.getTotal();
    }

    // reading from a file
    private void writeOrderFile(String date) throws PersistenceException {
        ORDER_FILE = FOLDER + date + ".txt";
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(ORDER_FILE));
        } catch (IOException e) {
            throw new PersistenceException("-_- Could not write order data.", e);
        }
        // write headline
        String headline = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total";
        out.println(headline);

        // get all orders from the Map and write each line into the file
        orders.values().stream().map(this::marshallOrder).forEach(out::println);
        out.close();
    }

    // convert string to order object
    private Order unmarshallOrder(String text) {
        String[] tokens = text.split(DELIMITER);
        int length = tokens.length;

        // collect order properties
        int number = Integer.parseInt(tokens[0]);
        String state = tokens[length - 10];
        BigDecimal rate = new BigDecimal(tokens[length - 9]);
        String type = tokens[length - 8];
        BigDecimal area = new BigDecimal(tokens[length - 7]);
        BigDecimal costSqFt = new BigDecimal(tokens[length - 6]);
        BigDecimal laborSqFt = new BigDecimal(tokens[length - 5]);
        BigDecimal material = new BigDecimal(tokens[length - 4]);
        BigDecimal labor = new BigDecimal(tokens[length - 3]);
        BigDecimal tax = new BigDecimal(tokens[length - 2]);
        BigDecimal total = new BigDecimal(tokens[length - 1]);

        // add name tokens if comma split apart
        StringBuilder name = new StringBuilder();
        for (int i = 1; i < (length - 10); i++) {
            name.append(tokens[i]).append(",");
        }
        // remove last comma
        name.deleteCharAt(name.length()-1);
        return new Order(number, name.toString(), state, rate, type, area, costSqFt, laborSqFt, material, labor, tax, total);
    }

    // writing to the file
    private void readOrderFile(String date) throws PersistenceException {
        ORDER_FILE = FOLDER + date + ".txt";
        // read file by file reader, buffered reader and scanner
        Scanner scanner;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(ORDER_FILE)));
        }
        catch (FileNotFoundException e) {
            throw new PersistenceException("No such Order file!.\n", e.getCause());
        }

        // catch exception if file is empty and skip first line of the file
        try {
            scanner.nextLine();
        }
        catch (NoSuchElementException ignored) {}

        // read each line
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            // convert string into order object
            Order order = unmarshallOrder(line);
            // keep all orders in the Map
            orders.put(order.getOrderNumber(), order);
        }
        scanner.close();
    }

    @Override
    public void backupFiles() throws PersistenceException, FileNotFoundException {
        // create files directory
        String FILE_DIRECTORY = "Orders";
        File directory = new File(FILE_DIRECTORY);
        // list all existing files of the directory
        File[] listFiles = directory.listFiles();

        Scanner scanner;
        List<String> list = new ArrayList<>();
        String input;
        String header = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot," +
                "LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total,OrderDate";

        // read each file and keep data in a list
        if (listFiles != null) {
            // declare a counter for ordering data
            int totalOrder = 1;

            for (File file : listFiles) {
                scanner = new Scanner(file);
                // get file name
                String name = file.getName();
                String[] tokens = name.split("[_.]");
                // get the date from file name
                String date = "," + tokens[1];
                // format date as MM-dd-yyyy
                date = date.substring(0, 3) + "-" + date.substring(3, 5) + "-" + date.substring(5);

                // read line by line in each file
                while (scanner.hasNextLine()) {
                    input = scanner.nextLine();

                    // skip header or read if not header
                    if (input.indexOf("OrderNumber") != 0){
                        // reorder number
                        input = totalOrder + input.substring(input.indexOf(","));
                        totalOrder++;
                        // collect data into list
                        list.add(input.concat(date));
                    }
                }
            }
        }
        // insert header back to the file
        list.add(0, header);

        // write/save all data in a backup file
        String BACKUP_FILE = "Backup/DataExport.txt";
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(BACKUP_FILE));
        } catch (IOException e) {
            throw new PersistenceException("-_- Could not write order data.", e);
        }
        // write each line from the list to the file
        list.forEach(out::println);
        out.flush();

    }
}
