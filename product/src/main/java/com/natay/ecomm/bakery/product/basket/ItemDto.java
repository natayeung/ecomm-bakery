package com.natay.ecomm.bakery.product.basket;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

/**
 * @author natayeung
 */
@Value
@Builder
public class ItemDto {

    String productId;
    String itemTitle;
    BigDecimal itemPrice;
    BigDecimal itemTotal;
    int quantity;
}
