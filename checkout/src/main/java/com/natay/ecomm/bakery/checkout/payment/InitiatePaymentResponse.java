package com.natay.ecomm.bakery.checkout.payment;

import com.natay.ecomm.bakery.checkout.order.OrderDetails;

/**
 * @author natayeung
 */
public record InitiatePaymentResponse(String externalOrderId, OrderDetails orderDetails, String approvalLink) {

    static InitiatePaymentResponse of(String externalOrderId, OrderDetails orderDetails, String approvalLink) {
        return new InitiatePaymentResponse(externalOrderId, orderDetails, approvalLink);
    }
}
