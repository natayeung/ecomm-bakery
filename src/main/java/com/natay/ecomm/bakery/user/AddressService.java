package com.natay.ecomm.bakery.user;

import java.util.Optional;

public interface AddressService {

    UserAddress registerAddress(RegistrationDto dto);

    Optional<UserAddress> findAddressByEmail(String email);
}
