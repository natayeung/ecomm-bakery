package com.natay.ecomm.bakery.product.basket;

import com.natay.ecomm.bakery.product.catalog.ProductAccessException;
import com.natay.ecomm.bakery.product.catalog.ProductNotFoundException;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author natayeung
 */
public interface Basket {

    void addItem(String productId) throws ProductAccessException, ProductNotFoundException;

    void removeItem(String productId);

    List<BasketItem> items();

    int itemCount();

    BigDecimal totalPrice();

    String basketRef();
}
