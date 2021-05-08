package com.natay.ecomm.bakery.checkout.testutil;

import com.natay.ecomm.bakery.checkout.order.OrderDetails;
import com.natay.ecomm.bakery.checkout.order.ShippingDetails;
import com.natay.ecomm.bakery.checkout.payment.ShippingDetailsDto;
import com.natay.ecomm.bakery.product.basket.BasketDto;
import com.natay.ecomm.bakery.product.basket.ItemDto;

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
    private static final String PRODUCT_ID = "bfc";
    private static final String PRODUCT_TITLE = "Black Forest Cake";
    private static final int QUANTITY = 2;
    private static final int ITEM_COUNT = 2;
    private static final BigDecimal ITEM_PRICE = BigDecimal.valueOf(22.75);
    private static final BigDecimal ITEM_TOTAL = BigDecimal.valueOf(45.50);
    private static final BigDecimal TOTAL_PRICE = BigDecimal.valueOf(45.50);

    public static BasketDto createBasketDetails() {
        List<ItemDto> items = List.of(
                ItemDto.builder().productId(PRODUCT_ID).itemTitle(PRODUCT_TITLE)
                        .itemPrice(ITEM_PRICE).itemTotal(ITEM_TOTAL).quantity(QUANTITY).build());
        return new BasketDto(items, ITEM_COUNT, TOTAL_PRICE);
    }

    public static ShippingDetailsDto createShippingDetails() {
        return new ShippingDetailsDto()
                .setContactEmail(randomEmail())
                .setShippingFirstName(FIRST_NAME)
                .setShippingLastName(LAST_NAME)
                .setAddressLine1(ADDRESS_LINE_1)
                .setTownOrCity(TOWN_OR_CITY)
                .setPostcode(POSTCODE);
    }

    public static OrderDetails createOrderDetails() {
        ShippingDetails shippingDetails = ShippingDetails.builder()
                .contactEmail(randomEmail())
                .shippingFirstName(FIRST_NAME)
                .shippingLastName(LAST_NAME)
                .addressLine1(ADDRESS_LINE_1)
                .townOrCity(TOWN_OR_CITY)
                .postcode(POSTCODE)
                .build();
        return OrderDetails.builder()
                .shippingDetails(shippingDetails)
                .items(List.of(OrderDetails.Item.of(PRODUCT_TITLE, QUANTITY, ITEM_PRICE)))
                .totalPrice(TOTAL_PRICE)
                .build();
    }

    public static String randomOrderId() {
        return random(8, true, true);
    }

    private static String randomEmail() {
        return random(8, true, true) + "@gmail.com";
    }

    private OrderDetailsFactory() {
    }
}
