package com.natay.ecomm.bakery.account;

/**
 * @author natayeung
 */
public class EmailAlreadyUsedException extends RuntimeException {

    public EmailAlreadyUsedException(String message) {
        super(message);
    }
}
