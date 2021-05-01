package com.natay.ecomm.bakery.checkout.payment.paypal;

import com.natay.ecomm.bakery.checkout.order.OrderDetails;
import com.natay.ecomm.bakery.checkout.order.ShippingDetails;
import com.paypal.orders.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * @author natayeung
 */
class OrdersCreateRequestFactory {

    static OrdersCreateRequest createOrdersCreateRequest(OrderDetails orderDetails, PayPalProperties properties) {
        String checkoutPaymentIntent = properties.getCheckoutPaymentIntent();
        ApplicationContext applicationContext = createApplicationContext(properties);
        List<PurchaseUnitRequest> purchaseUnits = createPurchaseUnits(orderDetails, properties);

        OrderRequest orderRequest = new OrderRequest()
                .checkoutPaymentIntent(checkoutPaymentIntent)
                .applicationContext(applicationContext)
                .purchaseUnits(purchaseUnits);

        return new OrdersCreateRequest().requestBody(orderRequest);
    }

    private static List<PurchaseUnitRequest> createPurchaseUnits(OrderDetails orderDetails, PayPalProperties properties) {
        ShippingDetail shippingDetail = shippingDetailsMapper().apply(orderDetails.shippingDetails(), properties);
        List<Item> items = itemsMapper().apply(orderDetails.items(), properties);
        AmountWithBreakdown amountWithBreakdown = amountMapper().apply(orderDetails.totalPrice(), properties);

        return List.of(new PurchaseUnitRequest()
                .shippingDetail(shippingDetail)
                .items(items)
                .amountWithBreakdown(amountWithBreakdown));
    }

    private static ApplicationContext createApplicationContext(PayPalProperties properties) {
        return new ApplicationContext()
                .brandName(properties.getBrandName())
                .returnUrl(properties.getReturnUrl())
                .cancelUrl(properties.getCancelUrl())
                .landingPage(properties.getLandingPage())
                .shippingPreference(properties.getShippingPreference())
                .userAction(properties.getUserAction());
    }

    private static BiFunction<ShippingDetails, PayPalProperties, ShippingDetail> shippingDetailsMapper() {
        return (details, properties) -> new ShippingDetail()
                .name(nameMapper().apply(details))
                .addressPortable(addressMapper().apply(details, properties));
    }

    private static BiFunction<List<OrderDetails.Item>, PayPalProperties, List<Item>> itemsMapper() {
        return (items, properties) -> items.stream()
                .map(i -> new Item().name(i.title())
                        .quantity(String.valueOf(i.quantity()))
                        .unitAmount(toMoney().apply(i.unitPrice(), properties))
                ).collect(toList());
    }

    private static BiFunction<BigDecimal, PayPalProperties, AmountWithBreakdown> amountMapper() {
        return (amount, properties) -> new AmountWithBreakdown()
                .amountBreakdown(new AmountBreakdown().itemTotal(toMoney().apply(amount, properties)))
                .currencyCode(properties.getCurrencyCode())
                .value(amount.toPlainString());
    }

    private static BiFunction<BigDecimal, PayPalProperties, Money> toMoney() {
        return (amount, properties) -> new Money().currencyCode(properties.getCurrencyCode()).value(amount.toPlainString());
    }

    private static Function<ShippingDetails, Name> nameMapper() {
        return details -> new Name()
                .fullName(details.shippingFirstName() + " " + details.shippingLastName());
    }

    private static BiFunction<ShippingDetails, PayPalProperties, AddressPortable> addressMapper() {
        return (details, properties) -> new AddressPortable().addressLine1(details.addressLine1())
                .addressLine2(details.addressLine2())
                .adminArea2(details.townOrCity())
                .postalCode(details.postcode())
                .countryCode(properties.getCountryCode());
    }

    private OrdersCreateRequestFactory() {
    }
}
