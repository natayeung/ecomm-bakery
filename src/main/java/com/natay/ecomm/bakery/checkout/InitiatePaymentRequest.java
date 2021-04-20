package com.natay.ecomm.bakery.checkout;

import java.util.UUID;

/**
 * @author natayeung
 */
public class InitiatePaymentRequest {

    private final String requestId;
    private final OrderDetails orderDetails;

    private InitiatePaymentRequest(OrderDetails orderDetails) {
        this.requestId = UUID.randomUUID().toString();
        this.orderDetails = orderDetails;
    }

    public static InitiatePaymentRequest with(OrderDetails orderDetails) {
        return new InitiatePaymentRequest(orderDetails);
    }

    public String getRequestId() {
        return requestId;
    }

    public OrderDetails getOrder() {
        return orderDetails;
    }
}
