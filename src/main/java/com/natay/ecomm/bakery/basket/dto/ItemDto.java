package com.natay.ecomm.bakery.basket.dto;

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
