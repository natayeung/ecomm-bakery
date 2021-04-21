package com.natay.ecomm.bakery.checkout.payment;

/**
 * @author natayeung
 */
public record InitiatePaymentResponse(String requestId, String externalOrderId, String approvalLink) {

    static InitiatePaymentResponse of(String requestId, String externalOrderId, String approvalLink) {
        return new InitiatePaymentResponse(requestId, externalOrderId, approvalLink);
    }
}
