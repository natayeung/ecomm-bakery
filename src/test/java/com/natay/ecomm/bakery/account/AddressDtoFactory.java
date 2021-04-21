package com.natay.ecomm.bakery.account;

import com.natay.ecomm.bakery.registration.AddressDto;

/**
 * @author natayeung
 */
public class AddressDtoFactory {

    public static AddressDto createAddressDto(String addressLine1, String addressLine2, String postcode) {
        AddressDto addressDto = new AddressDto();
        addressDto.setAddressLine1(addressLine1);
        addressDto.setAddressLine2(addressLine2);
        addressDto.setPostcode(postcode);

        return addressDto;
    }

    public static AddressDto createAddressDto(String addressLine1, String postcode) {
        return createAddressDto(addressLine1, null, postcode);
    }

    private AddressDtoFactory() {
    }
}
