package com.natay.ecomm.bakery.checkout;

/**
 * @author natayeung
 */
public class PayPalCheckoutException extends RuntimeException {

    public PayPalCheckoutException(String message) {
        super(message);
    }

    public PayPalCheckoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
