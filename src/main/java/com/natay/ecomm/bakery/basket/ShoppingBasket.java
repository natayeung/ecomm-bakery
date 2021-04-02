package com.natay.ecomm.bakery.basket;

import java.util.HashMap;
import java.util.Map;

/**
 * @author natayeung
 */
public class ShoppingBasket implements Basket {

    private final Map<String, Integer> quantityByProductId = new HashMap<>();
    private int itemCount;

    public ShoppingBasket() {
        itemCount = 0;
    }

    @Override
    public void addItem(String productId) {
        if (quantityByProductId.containsKey(productId)) {
            quantityByProductId.put(productId, quantityByProductId.get(productId) + 1);
        } else {
            quantityByProductId.put(productId, 1);
        }

        itemCount++;
    }

    @Override
    public int itemCount() {
        return itemCount;
    }

    @Override
    public String toString() {
        return "Basket{" +
                "quantityByProductId=" + quantityByProductId +
                ", itemCount=" + itemCount +
                '}';
    }
}
