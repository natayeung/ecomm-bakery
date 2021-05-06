package com.natay.ecomm.bakery.checkout.payment;

import com.natay.ecomm.bakery.user.account.Address;
import com.natay.ecomm.bakery.user.authentication.UserIdentity;

/**
 * @author natayeung
 */
public class ShippingDetailsDtoFactory {

    public static ShippingDetailsDto createShippingDetailsDto(UserIdentity user, Address address) {
        return new ShippingDetailsDto()
                .setShippingFirstName(user.firstName())
                .setShippingLastName(user.lastName())
                .setAddressLine1(address.addressLine1())
                .setAddressLine2(address.addressLine2())
                .setTownOrCity(address.townOrCity())
                .setPostcode(address.postcode());
    }

    public static ShippingDetailsDto createShippingDetailsDto(UserIdentity user) {
        return new ShippingDetailsDto()
                .setShippingFirstName(user.firstName())
                .setShippingLastName(user.lastName());
    }

    private ShippingDetailsDtoFactory() {
    }
}
