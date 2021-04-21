package com.natay.ecomm.bakery.account;

/**
 * @author natayeung
 */
public class Account {

    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;

    private Account(Builder builder) {
        email = builder.email;
        password = builder.password;
        firstName = builder.firstName;
        lastName = builder.lastName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String email() {
        return email;
    }

    public String password() {
        return password;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public static final class Builder {
        private String email;
        private String password;
        private String firstName;
        private String lastName;

        private Builder() {
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
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

        public Account build() {
            return new Account(this);
        }
    }
}
