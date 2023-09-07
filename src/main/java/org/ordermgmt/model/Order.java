package org.ordermgmt.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Order {
    private static int orderIdIndex = 0;
    private int orderId;
    private OrderStatus orderStatus;
    private Date orderDate;
    private int customerId;

    private Map<Item, Integer> items;

    public Order(int customerId) {
        this.orderId = orderIdIndex++;
        this.customerId = customerId;
        this.orderDate = new Date(System.currentTimeMillis());
        this.orderStatus = OrderStatus.CREATED;
        this.items = new HashMap<>();
    }

    public static int getOrderIdIndex() {
        return orderIdIndex;
    }

    public void addItem(Item item, int quantity) {
        this.items.put(item, quantity);
    }

    public int getOrderId() {
        return orderId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public Map<Item, Integer> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderStatus=" + orderStatus +
                ", orderDate=" + orderDate +
                ", customerId=" + customerId +
                ", items=" + items +
                '}';
    }
}
