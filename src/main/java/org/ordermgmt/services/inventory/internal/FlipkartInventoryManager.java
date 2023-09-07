package org.ordermgmt.services.inventory.internal;

import org.ordermgmt.model.Item;
import org.ordermgmt.utils.ItemProvider;

import java.util.HashMap;
import java.util.Map;

public class FlipkartInventoryManager extends InternalInventoryManager {
    Map<Integer, Item> inventoryIndex;

    public FlipkartInventoryManager() {
        inventoryIndex = new HashMap<>();
    }

    @Override
    public void addItemToInventory(int itemId, int quantity) {
        if (!ItemProvider.getItemsContext().containsKey(itemId)) {
            // Throw item not found error
            return;
        }

        if (inventoryIndex.containsKey(itemId)) {
            inventoryIndex.get(itemId).incrementAvailableItemCount(quantity);
        } else {
            Item item = ItemProvider.getItemsContext().get(itemId);
            item.setAvailableItemCount(quantity);
            inventoryIndex.put(itemId, item);
        }
    }

    @Override
    public int getAvailableInventory(int itemId) {
        if (!inventoryIndex.containsKey(itemId)) {
            return 0;
        }
        return inventoryIndex.get(itemId).getAvailableItemCount();
    }

    @Override
    public void unblockInventoryItems(Map<Integer, Integer> itemIds) {
        itemIds.forEach((itemId, count) -> inventoryIndex.get(itemId).unblockItem(count));
    }

    @Override
    public void blockInventoryItems(Map<Integer, Integer> itemIds) {
        itemIds.forEach((itemId, count) -> inventoryIndex.get(itemId).blockItem(count));
    }

    @Override
    public void freeInventoryItems(Map<Integer, Integer> itemIds) {
        itemIds.forEach((itemId, count) -> inventoryIndex.get(itemId).freeItem(count));
    }
}
