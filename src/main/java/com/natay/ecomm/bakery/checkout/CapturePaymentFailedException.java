package com.natay.ecomm.bakery.checkout;

/**
 * @author natayeung
 */
public class CapturePaymentFailedException extends Exception {

    public CapturePaymentFailedException(String message) {
        super(message);
    }

    public CapturePaymentFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
