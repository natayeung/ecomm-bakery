package com.natay.ecomm.bakery.checkout.payment.paypal;

import com.natay.ecomm.bakery.checkout.OrderDetails;
import com.natay.ecomm.bakery.checkout.payment.OrderCaptured;
import com.natay.ecomm.bakery.checkout.payment.OrderCreated;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

import static com.natay.ecomm.bakery.checkout.payment.paypal.OrdersCreateRequestFactory.createOrdersCreateRequest;

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
        OrdersCreateRequest request = createOrdersCreateRequest(orderDetails, properties);
        HttpResponse<Order> response;
        try {
            response = payPalHttpClient.execute(request);
            logger.debug("Received response from PayPal {}", response);
        } catch (IOException ex) {
            throw new PayPalCheckoutException("Unable to create order: " + ex.getMessage(), ex);
        }

        Order payPalOrder = extractOrder(response, "CREATED");

        return orderCreated(payPalOrder);
    }

    @Override
    public OrderCaptured captureOrder(String orderId) {
        OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);
        HttpResponse<Order> response;
        try {
            response = payPalHttpClient.execute(request);
            logger.debug("Received response from PayPal {}", response);
        } catch (IOException ex) {
            throw new PayPalCheckoutException("Unable to capture order: " + ex.getMessage(), ex);
        }

        Order payPalOrder = extractOrder(response, "COMPLETED");

        return new OrderCaptured(payPalOrder.id());
    }

    private Order extractOrder(HttpResponse<Order> response, String expectedStatus) {
        Order order = Optional.ofNullable(response)
                .map(HttpResponse::result)
                .orElseThrow(() -> {
                    throw new PayPalCheckoutException("PayPal response expected");
                });

        if (!expectedStatus.equals(order.status())) {
            throw new PayPalCheckoutException("Expecting order status " + expectedStatus + " but was " + order.status());
        }

        return order;
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
