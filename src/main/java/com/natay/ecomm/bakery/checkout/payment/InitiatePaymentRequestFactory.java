package com.natay.ecomm.bakery.checkout.payment;

import com.natay.ecomm.bakery.basket.BasketDto;
import com.natay.ecomm.bakery.basket.ItemDto;
import com.natay.ecomm.bakery.checkout.OrderDetails;
import com.natay.ecomm.bakery.checkout.ShippingDetails;
import com.natay.ecomm.bakery.checkout.ShippingDetailsDto;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * @author natayeung
 */
public class InitiatePaymentRequestFactory {

    public static InitiatePaymentRequest createInitiatePaymentRequest(BasketDto basketDto, ShippingDetailsDto shippingDetailsDto) {
        List<OrderDetails.Item> items = basketDto.getItems().stream().map(itemMapper()).collect(toList());
        OrderDetails orderDetails = OrderDetails.builder()
                .withShippingDetails(shippingDetailsMapper().apply(shippingDetailsDto))
                .withItems(items)
                .withTotalPrice(basketDto.getTotalPrice())
                .build();

        return InitiatePaymentRequest.of(orderDetails);
    }

    private static Function<ShippingDetailsDto, ShippingDetails> shippingDetailsMapper() {
        return d -> ShippingDetails.builder().withAddressLine1(d.getAddressLine1())
                .withAddressLine2(d.getAddressLine2())
                .withTownOrCity(d.getTownOrCity())
                .withPostcode(d.getPostcode())
                .withShippingFirstName(d.getShippingFirstName())
                .withShippingLastName(d.getShippingLastName())
                .build();
    }

    private static Function<ItemDto, OrderDetails.Item> itemMapper() {
        return i -> OrderDetails.Item.of(i.getItemTitle(), i.getQuantity(), i.getItemPrice());
    }
}
