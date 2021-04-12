package com.natay.ecomm.bakery.security;

import static com.natay.ecomm.bakery.utils.Arguments.requireNonBlank;

public class AuthenticatedUser {

    private final String username;

    private AuthenticatedUser(String username) {
        this.username = requireNonBlank(username, "Username must be specified");
    }

    public static AuthenticatedUser of(String username) {
        return new AuthenticatedUser(username);
    }

    public String username() {
        return username;
    }
}
