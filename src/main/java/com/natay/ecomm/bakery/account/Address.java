package com.natay.ecomm.bakery.account;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import static com.natay.ecomm.bakery.utils.Arguments.requireNonBlank;

/**
 * @author natayeung
 */
@Data
@Builder
@Accessors(fluent = true)
public class Address {

    private final String email;
    private final String addressLine1;
    private final String addressLine2;
    private final String townOrCity;
    private final String postcode;

    private Address(String email, String addressLine1, String addressLine2, String townOrCity, String postcode) {
        this.email = requireNonBlank(email, "Email cannot be blank");
        this.addressLine1 = requireNonBlank(addressLine1, "Address line 1 cannot be blank");
        this.addressLine2 = addressLine2;
        this.townOrCity = requireNonBlank(townOrCity, "Town or city cannot be blank");
        this.postcode = requireNonBlank(postcode, "Postcode cannot be blank");
    }
}
