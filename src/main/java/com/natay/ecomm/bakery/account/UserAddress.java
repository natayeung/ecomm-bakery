package com.natay.ecomm.bakery.account;

import static com.natay.ecomm.bakery.utils.Arguments.requireNonNull;

/**
 * @author natayeung
 */
public class UserAddress {

    private final String email;
    private final String addressLine1;
    private final String addressLine2;
    private final String postcode;

    private UserAddress(Builder builder) {
        email = builder.email;
        addressLine1 = builder.addressLine1;
        addressLine2 = builder.addressLine2;
        postcode = builder.postcode;
    }

    public static Builder create() {
        return new Builder();
    }

    public String email() {
        return email;
    }

    public String addressLine1() {
        return addressLine1;
    }

    public String addressLine2() {
        return addressLine2;
    }

    public String postcode() {
        return postcode;
    }

    @Override
    public String toString() {
        return "UserAddress{" +
                "email='" + email + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", postcode='" + postcode + '\'' +
                '}';
    }

    public static final class Builder {
        private String email;
        private String addressLine1;
        private String addressLine2;
        private String postcode;

        private Builder() {
        }

        public Builder from(UserAddress userAddress) {
            requireNonNull(userAddress, "An address expected");

            this.email = userAddress.email;
            this.addressLine1 = userAddress.addressLine1;
            this.addressLine2 = userAddress.addressLine2;
            this.postcode = userAddress.postcode;

            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
            return this;
        }

        public Builder withAddressLine2(String addressLine2) {
            this.addressLine2 = addressLine2;
            return this;
        }

        public Builder withPostcode(String postcode) {
            this.postcode = postcode;
            return this;
        }

        public UserAddress build() {
            return new UserAddress(this);
        }
    }
}
