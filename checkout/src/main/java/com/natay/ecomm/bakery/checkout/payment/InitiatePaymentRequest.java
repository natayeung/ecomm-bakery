package com.natay.ecomm.bakery.checkout.payment;

import com.natay.ecomm.bakery.product.basket.BasketDto;

/**
 * @author natayeung
 */
public record InitiatePaymentRequest(BasketDto basket, ShippingDetailsDto shippingDetails) {

    public static InitiatePaymentRequest of(BasketDto basket, ShippingDetailsDto shippingDetails) {
        return new InitiatePaymentRequest(basket, shippingDetails);
    }
}
