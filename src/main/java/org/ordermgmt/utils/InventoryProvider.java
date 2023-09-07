package org.ordermgmt.utils;

import org.ordermgmt.exception.SellerNotFoundException;
import org.ordermgmt.model.Seller;
import org.ordermgmt.services.inventory.InventoryManager;
import org.ordermgmt.services.inventory.external.CoolCompanyInventoryManager;
import org.ordermgmt.services.inventory.internal.FlipkartInventoryManager;

public class InventoryProvider {
    public static InventoryManager getInventory(Seller seller) throws SellerNotFoundException {
        switch (seller) {
            case FLIPKART:
                return new FlipkartInventoryManager();
            case EXTERNAL_COOL_COMPANY:
                return new CoolCompanyInventoryManager();
        }
        throw new SellerNotFoundException(seller);
    }
}
