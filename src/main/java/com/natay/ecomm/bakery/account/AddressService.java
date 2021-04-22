package com.natay.ecomm.bakery.account;

import com.natay.ecomm.bakery.account.dto.AccountDto;
import com.natay.ecomm.bakery.registration.dto.RegistrationDto;

import java.util.Optional;

/**
 * @author natayeung
 */
public interface AddressService {

    void registerAddress(RegistrationDto registrationDto);

    void updateAddress(AccountDto accountDto);

    Optional<Address> findAddressByEmail(String email);
}
