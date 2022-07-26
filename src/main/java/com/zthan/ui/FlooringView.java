package com.zthan.ui;

import com.zthan.dto.Order;
import com.zthan.dto.Product;
import com.zthan.dto.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class FlooringView {

    @Autowired
    private final UserIO io;

    public FlooringView(UserIO io) {
        this.io = io;
    }

    public int showMenuAndGetSelection() {
        io.print("==================================");
        io.print("        Flooring Program");
        io.print("==================================");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Export All Data");
        io.print("6. Quit");
        return io.readInt("Select from Menu", 1, 6);
    }

    public void displayAllOrders(List<Order> orders) {

        if (orders != null) {
            io.print("          ====================================================");
            io.print("                      All Orders in the list");
            io.print("          ====================================================");

            orders.stream().map(order -> String.format(
                    "%s. %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s",
                    order.getOrderNumber(),
                    order.getCustomerName(),
                    order.getStateAbbreviation(),
                    order.getTaxRate(),
                    order.getProductType(),
                    order.getArea(),
                    order.getCostPerSqFt(),
                    order.getLaborCostPerSqFt(),
                    order.getMaterialCost(),
                    order.getLaborCost(),
                    order.getTax(),
                    order.getTotal()
            )).forEachOrdered(io::print);
        }
        else {
            io.print("List is empty!");
        }
        io.readString("Hit enter to continue.");
    }

    public Order getNewOrderInfo(List<State> states, List<Product> products) {
        // collect info from user, create order and add properties to it
        Order order = new Order();
        order.setCustomerName(io.readString("Enter name"));
        order.setStateAbbreviation(getState(states));
        order.setProductType(getProduct(products));
        order.setArea(getArea());

        return order;
    }

    public Order getInfoToEdit(List<State> states, List<Product> products, Order order) {
        // get name if user want to change and set it as project property
        if (io.askQuestion("Do you want to change Name? Enter 'y' for Yes, or hit any key to skip.")) {
            String name = io.readString("Enter Name");
            order.setCustomerName(name);
        }
        // get state if user want to change and set it as project property
        if (io.askQuestion("Do you want to change State? Enter 'y' for Yes, or hit any key to skip.")) {
            String state = getState(states);
            order.setStateAbbreviation(state);
        }
        // get product if user want to change and set it as project property
        if (io.askQuestion("Do you want to change Product Type? Enter 'y' for Yes, or hit any key to skip.")) {
            String product = getProduct(products);
            order.setProductType(product);
        }
        // get area if user want to change and set it as project property
        if (io.askQuestion("Do you want to change Area? Enter 'y' for Yes, or hit any key to skip.")) {
            BigDecimal area = getArea();
            order.setArea(area);
        }
        return order;
    }

    private String getState(List<State> states) {
        AtomicInteger count = new AtomicInteger();
        io.print("          ====================================================");
        io.print("                          Available States");
        io.print("          ====================================================");

        // loop the list of states
        states.forEach(s -> {
            String info = String.format("%d. %s,    Tax: %s%%",
                    count.incrementAndGet(),
                    s.getStateName(),
                    s.getTaxRate());
            io.print(info);
        });

        // get choice
        int choice = io.readInt("\nSelect a State from the list", 1, states.size());
        // return the selected state name
        return states.get(choice - 1).getStateAbbreviation();
    }

    private String getProduct(List<Product> products) {
        AtomicInteger count = new AtomicInteger();
        io.print("      =====================================================");
        io.print("       Available Product Types and Rates for a square feet");
        io.print("      =====================================================");

        // loop list of the product
        products.forEach(p -> {
            String type = String.format("%d. %s,    Cost: $%s,    Labor_Cost: $%s",
                    count.incrementAndGet(),
                    p.getType(),
                    p.getCostPerSquareFoot(),
                    p.getLaborCostPerSquareFoot());
            io.print(type);
        });

        // get choice
        int choice = io.readInt("\nSelect a Product from the list!", 1, products.size());
        // return the selected product type
        return products.get(choice - 1).getType();
    }

    private BigDecimal getArea() {
        BigDecimal area;
        do {
            area = BigDecimal.valueOf(io.readDouble(
                    "\nEnter Area, Acceptable minimum size is 100 square feet"));
        }
        while (area.doubleValue() < 100);
        return area;
    }

    public String getDate() {
        return io.readDate();
    }

    public String getFutureDate() {
        return io.readFutureDate();
    }

    public int getOrderNumber() {
        return io.readInt("Enter order number");
    }

    public void displaySummaryOrder(Order order) {
        if (order != null) {
            io.print("      ====================================================");
            io.print("                     Summary of the Order");
            io.print("      ====================================================");
            io.print(order.toString());
        } else {
            io.print("No such a order!");
        }
    }

    public boolean continueOrExit() {
        // prompt customer whether continue or not
        String keepGoing = io.readString("Enter 'y' to place order, or Any key to exit.");
        // return boolean value
        return keepGoing.equalsIgnoreCase("y");
    }

    public void displayCreateBanner() {
        io.print("Order was created.\n");
    }

    public void displayEditBanner() {
        io.print("Order was edited.\n");
    }

    public void displayDeleteBanner() {
        io.print("Order was deleted!\n");
    }

    public void displayExportDataBanner() {
        io.print("Files have been backed up!\n");
    }
}
