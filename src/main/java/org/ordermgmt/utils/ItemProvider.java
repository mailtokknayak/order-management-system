package org.ordermgmt.utils;

import org.ordermgmt.model.Item;

import java.util.HashMap;
import java.util.Map;

public class ItemProvider {
    private static final Map<Integer, Item> items = new HashMap<>();

    public static Map<Integer, Item> getItemsContext() {
        if (items.isEmpty()) {
            mockItemData();
        }
        return items;
    }

    private static void mockItemData() {
        Item item;
        item = new Item("Camera", 100);
        items.put(item.getItemId(), item);

        item = new Item("Bottle", 20);
        items.put(item.getItemId(), item);

        item = new Item("Book", 10);
        items.put(item.getItemId(), item);

        item = new Item("Drone", 250);
        items.put(item.getItemId(), item);
    }
}

