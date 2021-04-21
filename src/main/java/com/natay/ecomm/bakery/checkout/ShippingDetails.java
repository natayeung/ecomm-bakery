package com.natay.ecomm.bakery.checkout;

import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author natayeung
 */
@ToString
public class ShippingDetails implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String shippingFirstName;
    private final String shippingLastName;
    private final String addressLine1;
    private final String addressLine2;
    private final String townOrCity;
    private final String postcode;

    private ShippingDetails(Builder builder) {
        shippingFirstName = builder.shippingFirstName;
        shippingLastName = builder.shippingLastName;
        addressLine1 = builder.addressLine1;
        addressLine2 = builder.addressLine2;
        townOrCity = builder.townOrCity;
        postcode = builder.postcode;
    }

    public String shippingFirstName() {
        return shippingFirstName;
    }

    public String shippingLastName() {
        return shippingLastName;
    }

    public String addressLine1() {
        return addressLine1;
    }

    public String addressLine2() {
        return addressLine2;
    }

    public String townOrCity() {
        return townOrCity;
    }

    public String postcode() {
        return postcode;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String shippingFirstName;
        private String shippingLastName;
        private String addressLine1;
        private String addressLine2;
        private String townOrCity;
        private String postcode;

        private Builder() {
        }

        public Builder withShippingFirstName(String shippingFirstName) {
            this.shippingFirstName = shippingFirstName;
            return this;
        }

        public Builder withShippingLastName(String shippingLastName) {
            this.shippingLastName = shippingLastName;
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

        public Builder withTownOrCity(String townOrCity) {
            this.townOrCity = townOrCity;
            return this;
        }

        public Builder withPostcode(String postcode) {
            this.postcode = postcode;
            return this;
        }

        public ShippingDetails build() {
            return new ShippingDetails(this);
        }
    }
}
