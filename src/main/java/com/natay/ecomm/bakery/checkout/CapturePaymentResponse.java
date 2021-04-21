package com.natay.ecomm.bakery.checkout;

/**
 * @author natayeung
 */
public record CapturePaymentResponse(String requestId, String externalOrderId) {

    public static CapturePaymentResponse of(String requestId, String externalOrderId) {
        return new CapturePaymentResponse(requestId, externalOrderId);
    }
}
