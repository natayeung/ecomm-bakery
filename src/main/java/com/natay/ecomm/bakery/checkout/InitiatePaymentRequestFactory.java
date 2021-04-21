package com.natay.ecomm.bakery.checkout;

import com.natay.ecomm.bakery.basket.BasketDto;
import com.natay.ecomm.bakery.basket.ItemDto;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * @author natayeung
 */
public class InitiatePaymentRequestFactory {

    public static InitiatePaymentRequest createInitiatePaymentRequest(BasketDto basketDto) {
        List<OrderDetails.Item> items = basketDto.getItems().stream().map(itemMapper()).collect(toList());
        OrderDetails orderDetails = OrderDetails.builder()
                .withAmount(basketDto.getTotalPrice())
                .withItems(items)
                .build();

        return InitiatePaymentRequest.of(orderDetails);
    }

    private static Function<ItemDto, OrderDetails.Item> itemMapper() {
        return i -> OrderDetails.Item.of(i.getItemTitle(), i.getQuantity(), i.getItemPrice());
    }
}
