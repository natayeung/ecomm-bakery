package com.natay.ecomm.bakery.checkout.payment;

import com.natay.ecomm.bakery.checkout.OrderDetails;

import java.util.UUID;

/**
 * @author natayeung
 */
public record InitiatePaymentRequest(String requestId, OrderDetails orderDetails) {

    public static InitiatePaymentRequest of(OrderDetails orderDetails) {
        return new InitiatePaymentRequest(UUID.randomUUID().toString(), orderDetails);
    }
}
