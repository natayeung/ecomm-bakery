package com.natay.ecomm.bakery.checkout.payment;

import java.util.UUID;

/**
 * @author natayeung
 */
public record CapturePaymentRequest(String requestId, String externalOrderId) {

    public static CapturePaymentRequest of(String externalOrderId) {
        return new CapturePaymentRequest(UUID.randomUUID().toString(), externalOrderId);
    }
}
