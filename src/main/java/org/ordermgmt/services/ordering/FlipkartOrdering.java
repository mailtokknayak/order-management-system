package org.ordermgmt.services.ordering;

import org.ordermgmt.model.Address;
import org.ordermgmt.model.Item;
import org.ordermgmt.model.Order;
import org.ordermgmt.model.OrderStatus;
import org.ordermgmt.services.inventory.InventoryManager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FlipkartOrdering implements OrderManager {
    private final InventoryManager inventoryManager;
    private final Map<Integer, Order> orderIndex;

    public FlipkartOrdering(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
        orderIndex = new HashMap<>();
    }

    @Override
    public Order createOrder(Integer customerId, Map<Item, Integer> itemsInfo, Address address) {
        for (Map.Entry<Item, Integer> entry : itemsInfo.entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();

            int availableItemCount = inventoryManager.getAvailableInventory(item.getItemId());
            if (availableItemCount < quantity) {
                // Throw items not in stock error for Item: item.getItemID()
                return null;
            }
        }

        Order order = new Order(customerId);
        Map<Integer, Integer> blockItemIdList = new HashMap<>();
        for (Map.Entry<Item, Integer> entry : itemsInfo.entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();
            order.addItem(item, quantity);
            blockItemIdList.put(item.getItemId(), quantity);
        }
        inventoryManager.blockInventoryItems(blockItemIdList);
        orderIndex.put(order.getOrderId(), order);

        // This part was added after the interview just as demonstration of one of the ways
        // that we can achieve the optional requirement of automatic order cancellation
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.schedule(() -> {
            if (order.getOrderStatus() == OrderStatus.CREATED) {
                cancelOrder(order);
            }
            System.out.println(order);
        }, 3, TimeUnit.SECONDS);
        scheduledExecutorService.shutdown();
        return order;
    }

    @Override
    public boolean updateOrder(Integer orderId, OrderStatus orderStatus) {
        if (!orderIndex.containsKey(orderId)) {
            // Throw order not found
            return false;
        }

        Order order = orderIndex.get(orderId);
        if (order.getOrderStatus() == OrderStatus.CANCELLED) {
            return false;
        }
        switch (orderStatus) {
            case CANCELLED:
                cancelOrder(order);
                return true;
            case FULFILLED:
                order.setOrderStatus(OrderStatus.FULFILLED);
                freeInventoryForOrder(order);
                return true;
            case CONFIRMED:
                order.setOrderStatus(OrderStatus.CONFIRMED);
                return true;
        }
        return false;
    }

    private void freeInventoryForOrder(Order order) {
        Map<Integer, Integer> freeItemIdList = new HashMap<>();
        order.getItems().forEach((item, count) -> freeItemIdList.put(item.getItemId(), count));
        inventoryManager.freeInventoryItems(freeItemIdList);
    }

    private void unblockInventoryForOrder(Order order) {
        Map<Integer, Integer> unblockItemIdList = new HashMap<>();
        order.getItems().forEach((item, count) -> unblockItemIdList.put(item.getItemId(), count));
        inventoryManager.unblockInventoryItems(unblockItemIdList);
    }

    private void cancelOrder(Order order) {
        order.setOrderStatus(OrderStatus.CANCELLED);
        unblockInventoryForOrder(order);
    }
}
