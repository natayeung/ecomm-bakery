package com.natay.ecomm.bakery.checkout.testutil;

import com.natay.ecomm.bakery.checkout.payment.ShippingDetailsDto;
import com.natay.ecomm.bakery.product.basket.BasketDto;
import com.natay.ecomm.bakery.product.basket.ItemDto;
import com.natay.ecomm.bakery.user.authentication.UserIdentity;

import java.math.BigDecimal;
import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.random;

/**
 * @author natayeung
 */
public class OrderDetailsFactory {

    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String ADDRESS_LINE_1 = "12 High Street";
    private static final String TOWN_OR_CITY = "London";
    private static final String POSTCODE = "E15 2GU";

    public static UserIdentity createCustomerDetails() {
        return UserIdentity.builder()
                .email(randomEmail())
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();
    }

    public static BasketDto createBasketDetails() {
        List<ItemDto> items = List.of(
                ItemDto.builder().productId("bfc").itemTitle("Black Forest Cake")
                        .itemPrice(BigDecimal.valueOf(22.75)).itemTotal(BigDecimal.valueOf(45.50)).quantity(2).build());
        return new BasketDto(items, 2, BigDecimal.valueOf(45.50));
    }

    public static ShippingDetailsDto createShippingDetails() {
        ShippingDetailsDto shippingDetails = new ShippingDetailsDto();
        shippingDetails.setShippingFirstName(FIRST_NAME);
        shippingDetails.setShippingLastName(LAST_NAME);
        shippingDetails.setAddressLine1(ADDRESS_LINE_1);
        shippingDetails.setTownOrCity(TOWN_OR_CITY);
        shippingDetails.setPostcode(POSTCODE);
        return shippingDetails;
    }

    private static String randomEmail() {
        return random(8, true, true) + "@gmail.com";
    }

    private OrderDetailsFactory() {
    }
}
