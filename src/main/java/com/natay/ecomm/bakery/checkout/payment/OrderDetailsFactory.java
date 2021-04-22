package com.natay.ecomm.bakery.checkout.payment;

import com.natay.ecomm.bakery.basket.dto.BasketDto;
import com.natay.ecomm.bakery.basket.dto.ItemDto;
import com.natay.ecomm.bakery.checkout.CustomerDetails;
import com.natay.ecomm.bakery.checkout.OrderDetails;
import com.natay.ecomm.bakery.checkout.ShippingDetails;
import com.natay.ecomm.bakery.checkout.dto.ShippingDetailsDto;
import com.natay.ecomm.bakery.security.authentication.UserIdentity;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * @author natayeung
 */
public class OrderDetailsFactory {

    public static OrderDetails createOrderDetails(InitiatePaymentRequest initiatePaymentRequest) {
        UserIdentity customer = initiatePaymentRequest.customer();
        BasketDto basket = initiatePaymentRequest.basket();
        ShippingDetailsDto shippingDetails = initiatePaymentRequest.shippingDetails();

        List<OrderDetails.Item> items = basket.getItems().stream().map(itemMapper()).collect(toList());
        return OrderDetails.builder()
                .withCustomerDetails(CustomerDetails.from(customer))
                .withShippingDetails(shippingDetailsMapper().apply(shippingDetails))
                .withItems(items)
                .withTotalPrice(basket.getTotalPrice())
                .build();
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
