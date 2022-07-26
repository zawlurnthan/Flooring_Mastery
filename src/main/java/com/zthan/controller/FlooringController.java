package com.zthan.controller;

import com.zthan.dao.PersistenceException;
import com.zthan.dao.ProductDaoFileImpl;
import com.zthan.dao.StateDaoFileImpl;
import com.zthan.dto.Order;
import com.zthan.dto.Product;
import com.zthan.dto.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.zthan.service.FlooringService;
import com.zthan.ui.FlooringView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Component
public class FlooringController {

    FlooringView view;
    FlooringService service;
    ProductDaoFileImpl pDao;
    StateDaoFileImpl sDao;

    @Autowired
    public FlooringController(FlooringView view, FlooringService service, ProductDaoFileImpl pDao, StateDaoFileImpl sDao) {
        this.view = view;
        this.service = service;
        this.pDao = pDao;
        this.sDao = sDao;
    }

    public void run() {
        boolean keepGoing = true;

        while (keepGoing) {
            int choice = getChoice();

            try {
                switch (choice) {
                    case 1 -> displayOrders();
                    case 2 -> addNewOrder();
                    case 3 -> updateOrder();
                    case 4 -> removeOrder();
                    case 5 -> exportAllData();
                    case 6 -> keepGoing = false;
                    default -> invalidInput();
                }
            } catch (PersistenceException | FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException ignored) {}
        }
        System.out.println("Existing Program...");
    }

    private int getChoice () {
        return view.showMenuAndGetSelection();
    }

    private void displayOrders() throws PersistenceException {
        // get desired date to see a list
        String date = view.getDate();
        List<Order> orders = service.getAllOrders(date);
        if (orders.isEmpty()) {
            System.out.println("No such Order!\n");
        } else {
            view.displayAllOrders(orders);
        }
    }

    // add new order
    private void addNewOrder() throws PersistenceException {
        // get future order date
        String date = view.getFutureDate();
        // get data from tax and product files
        List<State> states = sDao.getAllStates();
        List<Product> products = pDao.getAllProducts();
        // collect info from user and show available date in tax and product
        Order order = view.getNewOrderInfo(states, products);
        // check and get available order ID
        order.setOrderNumber(service.getAvailableOrderId(date));
        // calculate
        service.calculateCost(order);
        // show the summary to user
        view.displaySummaryOrder(order);
        // save if user want to continue
        if (view.continueOrExit()) {
            service.createOrder(order, date);
            view.displayCreateBanner();
        }
    }

    // update existing order
    private void updateOrder() throws PersistenceException {
        // get the date and order number of the object to update
        String date = view.getDate();
        int orderNumber = view.getOrderNumber();
        // check if order exist
        Order orderToUpdate = service.getOrder(orderNumber, date);

        if (orderToUpdate != null) {
            // get data from tax and product files
            List<State> states = sDao.getAllStates();
            List<Product> products = pDao.getAllProducts();
            // get order info
             Order order = view.getInfoToEdit(states, products, orderToUpdate);

            // calculate and display summary
            service.calculateCost(order);
            view.displaySummaryOrder(order);

            // save updated order if user want to continue
            if (view.continueOrExit()) {
                service.updateOrder(order, date);
                view.displayEditBanner();
            }
        } else {
            System.out.println("No such order exist!\n");
        }
    }

    private void removeOrder() throws PersistenceException, IOException {
        String date = view.getDate();
        int orderNumber = view.getOrderNumber();
        // check if order exist
        Order orderToDelete = service.getOrder(orderNumber, date);
        if (orderToDelete != null) {
            service.removeOrder(orderNumber, date);
            view.displayDeleteBanner();
        } else {
            System.out.println("No such order exist!\n");
        }
    }

    private void exportAllData() throws PersistenceException, FileNotFoundException {
        service.backupFiles();
        view.displayExportDataBanner();
    }

    private void invalidInput() {
        System.out.println("Invalid Input!");
    }
}
