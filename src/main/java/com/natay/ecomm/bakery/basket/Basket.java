package com.natay.ecomm.bakery.basket;

/**
 * @author natayeung
 */
public interface Basket {

    void addItem(String productId);

    int itemCount();
}
