package com.natay.ecomm.bakery.account;

import com.natay.ecomm.bakery.registration.AddressDto;
import com.natay.ecomm.bakery.registration.RegistrationDto;

import java.util.Optional;

/**
 * @author natayeung
 */
public interface AddressService {

    UserAddress registerAddress(RegistrationDto registrationDto);

    Optional<UserAddress> findAddressByEmail(String email);

    void updateAddress(String email, AddressDto addressDto);
}
