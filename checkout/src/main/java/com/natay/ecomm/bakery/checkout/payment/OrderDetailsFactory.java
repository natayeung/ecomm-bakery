package com.natay.ecomm.bakery.checkout.payment;

import com.natay.ecomm.bakery.checkout.order.OrderDetails;
import com.natay.ecomm.bakery.checkout.order.ShippingDetails;
import com.natay.ecomm.bakery.product.basket.BasketDto;
import com.natay.ecomm.bakery.product.basket.ItemDto;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * @author natayeung
 */
public class OrderDetailsFactory {

    public static OrderDetails createOrderDetails(InitiatePaymentRequest initiatePaymentRequest) {
        BasketDto basket = initiatePaymentRequest.basket();
        ShippingDetailsDto shippingDetails = initiatePaymentRequest.shippingDetails();

        List<OrderDetails.Item> items = basket.getItems().stream().map(itemMapper()).collect(toList());
        return OrderDetails.builder()
                .shippingDetails(shippingDetailsMapper().apply(shippingDetails))
                .items(items)
                .totalPrice(basket.getTotalPrice())
                .build();
    }

    private static Function<ShippingDetailsDto, ShippingDetails> shippingDetailsMapper() {
        return d -> ShippingDetails.builder()
                .contactEmail(d.getContactEmail())
                .addressLine1(d.getAddressLine1())
                .addressLine2(d.getAddressLine2())
                .townOrCity(d.getTownOrCity())
                .postcode(d.getPostcode())
                .shippingFirstName(d.getShippingFirstName())
                .shippingLastName(d.getShippingLastName())
                .build();
    }

    private static Function<ItemDto, OrderDetails.Item> itemMapper() {
        return i -> OrderDetails.Item.of(i.getItemTitle(), i.getQuantity(), i.getItemPrice());
    }
}
