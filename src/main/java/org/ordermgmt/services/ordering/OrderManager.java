package org.ordermgmt.services.ordering;

import org.ordermgmt.model.Address;
import org.ordermgmt.model.Item;
import org.ordermgmt.model.Order;
import org.ordermgmt.model.OrderStatus;

import java.util.Map;

public interface OrderManager {
    Order createOrder(Integer customerId, Map<Item, Integer> itemsInfo, Address address);

    boolean updateOrder(Integer orderId, OrderStatus orderStatus);
}
