package com.natay.ecomm.bakery.checkout;

import java.util.UUID;

/**
 * @author natayeung
 */
public record InitiatePaymentRequest(String requestId, OrderDetails orderDetails) {

    public static InitiatePaymentRequest of(OrderDetails orderDetails) {
        return new InitiatePaymentRequest(UUID.randomUUID().toString(), orderDetails);
    }
}
