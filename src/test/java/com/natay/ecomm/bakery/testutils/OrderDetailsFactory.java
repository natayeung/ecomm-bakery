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

    private OrderDetailsFactory() {
    }
}
