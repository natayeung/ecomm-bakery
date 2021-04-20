package com.natay.ecomm.bakery.checkout;

import com.natay.ecomm.bakery.configuration.PayPalProperties;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * @author natayeung
 */
@Component
public class PayPalCheckoutRestAdapter implements PayPalCheckoutPort {

    private static final Logger logger = LoggerFactory.getLogger(PayPalCheckoutRestAdapter.class);

    private final PayPalHttpClient payPalHttpClient;
    private final PayPalProperties properties;

    public PayPalCheckoutRestAdapter(PayPalEnvironment payPalEnvironment, PayPalProperties properties) {
        this.payPalHttpClient = new PayPalHttpClient(payPalEnvironment);
        this.properties = properties;
    }

    @Override
    public OrderCreated createOrder(OrderDetails orderDetails) {
        OrdersCreateRequest request = createOrderRequest(orderDetails);
        HttpResponse<Order> response;
        try {
            response = payPalHttpClient.execute(request);
            logger.debug("Received response from PayPal {}", response);
        } catch (IOException ex) {
            throw new PayPalCheckoutException("Unable to create order: " + ex.getMessage(), ex);
        }
        Order payPalCreatedOrder = Optional.ofNullable(response)
                .map(HttpResponse::result)
                .orElseThrow(() -> {
                    throw new PayPalCheckoutException("PayPal response expected");
                });

        return orderCreated(payPalCreatedOrder);
    }

    private OrdersCreateRequest createOrderRequest(OrderDetails orderDetails) {
        ApplicationContext applicationContext = createApplicationContext();
        List<PurchaseUnitRequest> purchaseUnits = createPurchaseUnits(orderDetails);

        OrderRequest orderRequest = new OrderRequest()
                .checkoutPaymentIntent(properties.getCheckoutPaymentIntent())
                .applicationContext(applicationContext)
                .purchaseUnits(purchaseUnits);

        return new OrdersCreateRequest().requestBody(orderRequest);
    }

    private ApplicationContext createApplicationContext() {
        return new ApplicationContext()
                .brandName(properties.getBrandName())
                .returnUrl(properties.getReturnUrl())
                .cancelUrl(properties.getCancelUrl())
                .landingPage(properties.getLandingPage())
//                .shippingPreference(payPalProperties.getShippingPreference())
                .userAction(properties.getUserAction());
    }

    private List<PurchaseUnitRequest> createPurchaseUnits(OrderDetails orderDetails) {
        AmountBreakdown amountBreakdown = new AmountBreakdown().itemTotal(toMoney(orderDetails.amount()));
        String value = orderDetails.amount().toPlainString();
        AmountWithBreakdown amountWithBreakdown = new AmountWithBreakdown()
                .amountBreakdown(amountBreakdown)
                .currencyCode(properties.getCurrencyCode())
                .value(value);

        List<Item> items = orderDetails.items().stream().map(itemMapper()).collect(toList());
        return List.of(new PurchaseUnitRequest()
                .items(items)
                .amountWithBreakdown(amountWithBreakdown));
    }

    private Function<OrderDetails.Item, Item> itemMapper() {
        return i -> new Item().name(i.title())
                .quantity(String.valueOf(i.quantity()))
                .unitAmount(toMoney(i.unitPrice()));
    }

    private Money toMoney(BigDecimal amount) {
        return new Money().currencyCode(properties.getCurrencyCode()).value(amount.toPlainString());
    }

    private OrderCreated orderCreated(com.paypal.orders.Order createdOrder) {
        String orderId = createdOrder.id();
        String approvalLink = createdOrder.links().stream()
                .filter(link -> "approve".equals(link.rel())).findFirst()
                .map(LinkDescription::href)
                .orElseThrow(() -> {
                    throw new PayPalCheckoutException("No approval link returned from PayPal");
                });

        return new OrderCreated(orderId, approvalLink);
    }
}
