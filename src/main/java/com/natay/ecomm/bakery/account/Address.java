package com.natay.ecomm.bakery.account;

import lombok.ToString;

import static com.natay.ecomm.bakery.utils.Arguments.requireNonBlank;
import static com.natay.ecomm.bakery.utils.Arguments.requireNonNull;

/**
 * @author natayeung
 */
@ToString
public class Address {

    private final String email;
    private final String addressLine1;
    private final String addressLine2;
    private final String postcode;

    private Address(Builder builder) {
        email = builder.email;
        addressLine1 = requireNonBlank(builder.addressLine1, "Address line 1 must be specified");
        addressLine2 = builder.addressLine2;
        postcode = requireNonBlank(builder.postcode, "Postcode must be specified");
    }

    public static Builder builder() {
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

    public static final class Builder {

        private String email;
        private String addressLine1;
        private String addressLine2;
        private String postcode;

        private Builder() {
        }

        public Builder from(Address address) {
            requireNonNull(address, "An address expected");
            this.email = address.email;
            this.addressLine1 = address.addressLine1;
            this.addressLine2 = address.addressLine2;
            this.postcode = address.postcode;
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

        public Address build() {
            return new Address(this);
        }
    }
}
