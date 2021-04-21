package com.natay.ecomm.bakery.testutils;

import com.natay.ecomm.bakery.checkout.OrderDetails;
import com.natay.ecomm.bakery.checkout.ShippingDetails;

import java.math.BigDecimal;
import java.util.List;

import static com.natay.ecomm.bakery.testutils.TestConstants.*;

/**
 * @author natayeung
 */
public class OrderDetailsFactory {

    public static OrderDetails createOrderDetails() {
        ShippingDetails shippingDetails = ShippingDetails.builder()
                .withShippingFirstName(FIRST_NAME)
                .withShippingLastName(LAST_NAME)
                .withAddressLine1(ADDRESS_LINE_1)
                .withTownOrCity(TOWN_OR_CITY)
                .withPostcode(POSTCODE)
                .build();
        List<OrderDetails.Item> items = List.of(OrderDetails.Item.of("Black Forest Cake", 2, BigDecimal.valueOf(22.75)));
        BigDecimal totalPrice = BigDecimal.valueOf(45.50);

        return OrderDetails.builder().withItems(items).withShippingDetails(shippingDetails).withTotalPrice(totalPrice).build();
    }
}
