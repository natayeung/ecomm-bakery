package com.natay.ecomm.bakery.user.account;

/**
 * @author natayeung
 */
public class EmailAlreadyUsedException extends RuntimeException {

    public EmailAlreadyUsedException(String message) {
        super(message);
    }
}
