package org.ordermgmt;

import org.ordermgmt.exception.SellerNotFoundException;
import org.ordermgmt.model.*;
import org.ordermgmt.services.inventory.InventoryManager;
import org.ordermgmt.services.ordering.FlipkartOrdering;
import org.ordermgmt.services.ordering.OrderManager;
import org.ordermgmt.utils.InventoryProvider;
import org.ordermgmt.utils.ItemProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws SellerNotFoundException, InterruptedException {
        InventoryManager inventoryManager = InventoryProvider.getInventory(Seller.FLIPKART);
        inventoryManager.addItemToInventory(0, 10);
        inventoryManager.addItemToInventory(2, 12);
        inventoryManager.addItemToInventory(1, 13);

        System.out.println("Available Inventory for [id = 0]: " + inventoryManager.getAvailableInventory(0));
        System.out.println("Available Inventory for [id = 1]: " + inventoryManager.getAvailableInventory(1));


        OrderManager orderManager = new FlipkartOrdering(inventoryManager);
        int customerID = 1;
        Map<Item, Integer> itemsInfo = new HashMap<>();
        itemsInfo.put(ItemProvider.getItemsContext().get(0), 9);
        Address address = new Address("", 100000, "Bangalore", "India");

        Order order = orderManager.createOrder(customerID, itemsInfo, address);
        System.out.println(order);

        TimeUnit.SECONDS.sleep(4);

        if (orderManager.updateOrder(order.getOrderId(), OrderStatus.CONFIRMED)) {
            System.out.println(order);

            // We can see blocked and freed items in LOGS
            orderManager.updateOrder(order.getOrderId(), OrderStatus.FULFILLED);
            System.out.println(order);
        } else {
            System.out.println("Order already cancelled");
        }
    }
}