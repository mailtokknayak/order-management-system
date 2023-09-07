package org.ordermgmt.services.inventory;

import java.util.Map;

public interface InventoryManager {
    void addItemToInventory(int itemId, int quantity);

    int getAvailableInventory(int itemId);

    void unblockInventoryItems(Map<Integer, Integer> itemIds);

    void blockInventoryItems(Map<Integer, Integer> itemIds);

    void freeInventoryItems(Map<Integer, Integer> itemIds);
}

