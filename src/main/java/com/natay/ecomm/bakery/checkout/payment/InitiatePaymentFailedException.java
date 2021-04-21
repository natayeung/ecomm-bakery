package com.natay.ecomm.bakery.checkout.payment;

/**
 * @author natayeung
 */
public class InitiatePaymentFailedException extends Exception {

    public InitiatePaymentFailedException(String message) {
        super(message);
    }

    public InitiatePaymentFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
