package org.ordermgmt.exception;

import org.ordermgmt.model.Seller;

public class SellerNotFoundException extends Throwable {
    public SellerNotFoundException(Seller seller) {
    }
}
