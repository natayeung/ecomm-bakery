package com.natay.ecomm.bakery.checkout;

/**
 * @author natayeung
 */
public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String message) {
        super(message);
    }
}
