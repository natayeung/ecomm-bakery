package com.natay.ecomm.bakery.checkout.payment;

/**
 * @author natayeung
 */
public record CapturePaymentRequest(String externalOrderId) {

    public static CapturePaymentRequest of(String externalOrderId) {
        return new CapturePaymentRequest(externalOrderId);
    }
}
