package com.natay.ecomm.bakery.checkout.order;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

import static com.natay.ecomm.bakery.common.Arguments.requireNonBlank;

/**
 * @author natayeung
 */
@Data
@Builder
@Accessors(fluent = true)
public class ShippingDetails implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String shippingFirstName;
    private final String shippingLastName;
    private final String addressLine1;
    private final String addressLine2;
    private final String townOrCity;
    private final String postcode;

    private ShippingDetails(String shippingFirstName, String shippingLastName, String addressLine1, String addressLine2, String townOrCity, String postcode) {
        this.shippingFirstName = requireNonBlank(shippingFirstName, "Shipping first name cannot be blank");
        this.shippingLastName = requireNonBlank(shippingLastName, "Shipping last name cannot be blank");
        this.addressLine1 = requireNonBlank(addressLine1, "Address line 1 cannot be blank");
        this.addressLine2 = addressLine2;
        this.townOrCity = requireNonBlank(townOrCity, "Town or city cannot be blank");
        this.postcode = requireNonBlank(postcode, "Postcode cannot be blank");
    }
}
