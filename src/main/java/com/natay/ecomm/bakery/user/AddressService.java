package com.natay.ecomm.bakery.user;

import com.natay.ecomm.bakery.user.dto.RegistrationDto;

import java.util.Optional;

/**
 * @author natayeung
 */
public interface AddressService {

    UserAddress registerAddress(RegistrationDto dto);

    Optional<UserAddress> findAddressByEmail(String email);
}
