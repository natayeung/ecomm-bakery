package com.natay.ecomm.bakery.account;

/**
 * @author natayeung
 */
public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String message) {
        super(message);
    }
}
