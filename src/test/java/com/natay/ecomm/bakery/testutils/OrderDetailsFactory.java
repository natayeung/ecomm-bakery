package com.natay.ecomm.bakery.testutils;

import com.natay.ecomm.bakery.basket.dto.BasketDto;
import com.natay.ecomm.bakery.basket.dto.ItemDto;
import com.natay.ecomm.bakery.checkout.dto.ShippingDetailsDto;
import com.natay.ecomm.bakery.security.authentication.UserIdentity;

import java.math.BigDecimal;
import java.util.List;

import static com.natay.ecomm.bakery.testutils.RandomUtil.randomEmail;
import static com.natay.ecomm.bakery.testutils.TestConstants.*;

/**
 * @author natayeung
 */
public class OrderDetailsFactory {

    public static UserIdentity createCustomerDetails() {
        return UserIdentity.builder()
                .withUsername(randomEmail())
                .withFirstName(FIRST_NAME)
                .withLastName(LAST_NAME)
                .build();
    }

    public static BasketDto createBasketDetails() {
        List<ItemDto> items = List.of(new ItemDto("bfc", "Black Forest Cake",
                BigDecimal.valueOf(22.75), BigDecimal.valueOf(45.50), 2));
        return new BasketDto(items, 2, BigDecimal.valueOf(45.50), false);
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

    private OrderDetailsFactory() {
    }
}
