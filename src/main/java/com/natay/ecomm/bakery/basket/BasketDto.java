package com.natay.ecomm.bakery.basket;

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
}
