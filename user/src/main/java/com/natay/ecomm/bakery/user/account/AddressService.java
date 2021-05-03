package com.natay.ecomm.bakery.user.account;

import com.natay.ecomm.bakery.user.registration.RegistrationDto;

import java.util.Optional;

/**
 * @author natayeung
 */
public interface AddressService {

    void registerAddress(RegistrationDto registrationDto);

    void updateAddress(AccountDto accountDto);

    Optional<Address> findAddressByEmail(String email);
}
