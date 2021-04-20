package com.natay.ecomm.bakery.basket;

import lombok.Value;

import java.math.BigDecimal;

/**
 * @author natayeung
 */
@Value
public class ItemDto {

    String productId;
    String itemTitle;
    BigDecimal itemPrice;
    BigDecimal itemTotal;
    int quantity;
}
