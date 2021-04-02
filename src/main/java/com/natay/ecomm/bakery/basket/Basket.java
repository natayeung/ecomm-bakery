package com.natay.ecomm.bakery.basket;

import java.util.HashMap;
import java.util.Map;

public class Basket {

    private final Map<String, Integer> quantityByProductId = new HashMap<>();
    private int itemCount;

    public Basket() {
        itemCount = 0;
    }

    public void addItem(String productId) {
        if (quantityByProductId.containsKey(productId)) {
            quantityByProductId.put(productId, quantityByProductId.get(productId) + 1);
        } else {
            quantityByProductId.put(productId, 1);
        }

        itemCount++;
    }

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
