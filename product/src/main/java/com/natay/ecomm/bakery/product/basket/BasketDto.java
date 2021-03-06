package com.natay.ecomm.bakery.product.basket;

import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author natayeung
 */
@Value
public class BasketDto {

    List<ItemDto> items;
    int itemCount;
    BigDecimal totalPrice;

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
