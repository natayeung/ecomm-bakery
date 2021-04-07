package com.natay.ecomm.bakery.account;

import com.natay.ecomm.bakery.registration.RegistrationDto;

import java.util.Optional;

/**
 * @author natayeung
 */
public interface AddressService {

    UserAddress registerAddress(RegistrationDto dto);

    Optional<UserAddress> findAddressByEmail(String email);
}
