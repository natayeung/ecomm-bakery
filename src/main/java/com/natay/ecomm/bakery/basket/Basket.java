package com.natay.ecomm.bakery.basket;

import com.natay.ecomm.bakery.catalog.Price;
import com.natay.ecomm.bakery.catalog.ProductAccessException;
import com.natay.ecomm.bakery.catalog.ProductNotFoundException;

import java.util.List;

/**
 * @author natayeung
 */
public interface Basket {

    void addItem(String productId) throws ProductAccessException, ProductNotFoundException;

    void removeItem(String productId);

    List<BasketItem> items();

    int itemCount();

    Price totalPrice();

    String basketRef();
}
