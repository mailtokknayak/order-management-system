package org.ordermgmt.model;

public class Item {
    // Preferably a database auto increment key
    private static int itemIdIndex = 0;
    private Integer itemId;
    private String name;
    private int availableItemCount;
    private double price;
    private int blockedItemCount;

    public Item(String name, int availableItemCount, double price) {
        this.itemId = itemIdIndex++;
        this.name = name;
        this.availableItemCount = availableItemCount;
        this.price = price;
        this.blockedItemCount = 0;
    }

    public Item(String name, double price) {
        this(name, 0, price);
    }

    public static int getItemIdIndex() {
        return itemIdIndex;
    }

    public Integer getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public int getAvailableItemCount() {
        return availableItemCount;
    }

    public void setAvailableItemCount(int availableItemCount) {
        this.availableItemCount = availableItemCount;
    }

    public double getPrice() {
        return price;
    }

    public void incrementAvailableItemCount(int additionalCount) {
        this.availableItemCount = this.availableItemCount + additionalCount;
    }

    public void blockItem(int itemCount) {
        if (itemCount > availableItemCount) {
            // throw items not in stock error
            return;
        }
        this.blockedItemCount += itemCount;
        this.availableItemCount -= itemCount;
    }

    public void unblockItem(int itemCount) {
        this.blockedItemCount -= itemCount;
        this.blockedItemCount = Math.max(0, this.blockedItemCount);
        this.availableItemCount += itemCount;
    }

    public void freeItem(int itemCount) {
        this.blockedItemCount -= itemCount;
        this.blockedItemCount = Math.max(0, this.blockedItemCount);
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", name='" + name + '\'' +
                ", availableItemCount=" + availableItemCount +
                ", price=" + price +
                ", blockedItemCount=" + blockedItemCount +
                '}';
    }
}
