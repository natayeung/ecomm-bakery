package com.natay.ecomm.bakery.checkout.payment;

import com.natay.ecomm.bakery.basket.dto.BasketDto;
import com.natay.ecomm.bakery.checkout.dto.ShippingDetailsDto;
import com.natay.ecomm.bakery.security.authentication.UserIdentity;

/**
 * @author natayeung
 */
public record InitiatePaymentRequest(UserIdentity customer, BasketDto basket, ShippingDetailsDto shippingDetails) {

    public static InitiatePaymentRequest of(UserIdentity customer, BasketDto basket, ShippingDetailsDto shippingDetails) {
        return new InitiatePaymentRequest(customer, basket, shippingDetails);
    }
}
