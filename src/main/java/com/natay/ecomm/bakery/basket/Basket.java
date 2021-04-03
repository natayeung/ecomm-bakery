package com.natay.ecomm.bakery.basket;

import com.natay.ecomm.bakery.catalog.Price;

import java.util.List;

/**
 * @author natayeung
 */
public interface Basket {

    void addItem(String productId);

    List<BasketItem> items();

    int itemCount();

    Price totalPrice();
}
