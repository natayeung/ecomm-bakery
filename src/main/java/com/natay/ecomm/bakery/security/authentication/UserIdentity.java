package com.natay.ecomm.bakery.security.authentication;

import static com.natay.ecomm.bakery.utils.Arguments.requireNonBlank;

/**
 * @author natayeung
 */
public class UserIdentity {

    private final String email;
    private final String firstName;
    private final String lastName;

    private UserIdentity(Builder builder) {
        email = requireNonBlank(builder.email, "Email must be specified");
        firstName = builder.firstName;
        lastName = builder.lastName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String username() {
        return email;
    }

    public String email() {
        return email;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public static final class Builder {
        private String email;
        private String firstName;
        private String lastName;

        private Builder() {
        }

        public Builder withUsername(String username) {
            this.email = username;
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

        public UserIdentity build() {
            return new UserIdentity(this);
        }
    }
}
