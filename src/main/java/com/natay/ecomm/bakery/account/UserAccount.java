package com.natay.ecomm.bakery.account;

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
