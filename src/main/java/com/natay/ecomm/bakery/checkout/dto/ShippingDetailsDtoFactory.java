package com.natay.ecomm.bakery.checkout.dto;

import com.natay.ecomm.bakery.account.Address;
import com.natay.ecomm.bakery.security.authentication.UserIdentity;

/**
 * @author natayeung
 */
public class ShippingDetailsDtoFactory {

    public static ShippingDetailsDto createShippingDetailsDto(UserIdentity userIdentity, Address address) {
        ShippingDetailsDto shippingDetailsDto = new ShippingDetailsDto();
        shippingDetailsDto.setShippingFirstName(userIdentity.firstName());
        shippingDetailsDto.setShippingLastName(userIdentity.lastName());
        shippingDetailsDto.setAddressLine1(address.addressLine1());
        shippingDetailsDto.setAddressLine2(address.addressLine2());
        shippingDetailsDto.setTownOrCity(address.townOrCity());
        shippingDetailsDto.setPostcode(address.postcode());
        return shippingDetailsDto;
    }

    private ShippingDetailsDtoFactory() {
    }
}
