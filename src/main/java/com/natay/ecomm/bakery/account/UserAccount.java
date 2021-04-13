package com.natay.ecomm.bakery.account;

/**
 * @author natayeung
 */
public class UserAccount {

    private final String email;
    private final String password;

    public UserAccount(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String email() {
        return email;
    }

    public String password() {
        return password;
    }
}
