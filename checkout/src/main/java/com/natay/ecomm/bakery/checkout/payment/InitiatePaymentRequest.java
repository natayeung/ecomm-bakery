package com.natay.ecomm.bakery.checkout.payment;

import com.natay.ecomm.bakery.product.basket.BasketDto;
import com.natay.ecomm.bakery.user.authentication.UserIdentity;

/**
 * @author natayeung
 */
public record InitiatePaymentRequest(UserIdentity customer, BasketDto basket, ShippingDetailsDto shippingDetails) {

    public static InitiatePaymentRequest of(UserIdentity customer, BasketDto basket, ShippingDetailsDto shippingDetails) {
        return new InitiatePaymentRequest(customer, basket, shippingDetails);
    }
}
