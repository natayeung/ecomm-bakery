package com.natay.ecomm.bakery.checkout.payment;

/**
 * @author natayeung
 */
public record CapturePaymentResponse(String externalOrderId) {

    public static CapturePaymentResponse of(String externalOrderId) {
        return new CapturePaymentResponse(externalOrderId);
    }
}
