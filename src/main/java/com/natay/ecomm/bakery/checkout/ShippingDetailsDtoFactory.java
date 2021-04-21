package com.natay.ecomm.bakery.checkout;

import com.natay.ecomm.bakery.account.Address;
import com.natay.ecomm.bakery.security.authentication.AuthenticatedUser;

/**
 * @author natayeung
 */
public class ShippingDetailsDtoFactory {

    public static ShippingDetailsDto createShippingDetailsDto(AuthenticatedUser authenticatedUser, Address address) {
        ShippingDetailsDto shippingDetailsDto = new ShippingDetailsDto();
        shippingDetailsDto.setShippingFirstName(authenticatedUser.firstname());
        shippingDetailsDto.setShippingLastName(authenticatedUser.lastname());
        shippingDetailsDto.setAddressLine1(address.addressLine1());
        shippingDetailsDto.setAddressLine2(address.addressLine2());
        shippingDetailsDto.setTownOrCity(address.townOrCity());
        shippingDetailsDto.setPostcode(address.postcode());
        return shippingDetailsDto;
    }

    private ShippingDetailsDtoFactory() {
    }
}
