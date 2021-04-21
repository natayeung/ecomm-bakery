package com.natay.ecomm.bakery.security;

import static com.natay.ecomm.bakery.utils.Arguments.requireNonBlank;

/**
 * @author natayeung
 */
public class AuthenticatedUser {

    private final String username;
    private final String firstName;
    private final String lastName;

    private AuthenticatedUser(Builder builder) {
        username = requireNonBlank(builder.username, "Username must be specified");
        firstName = builder.firstName;
        lastName = builder.lastName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String username() {
        return username;
    }

    public String firstname() {
        return firstName;
    }

    public String lastname() {
        return lastName;
    }

    public static final class Builder {
        private String username;
        private String firstName;
        private String lastName;

        private Builder() {
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public AuthenticatedUser build() {
            return new AuthenticatedUser(this);
        }
    }
}
