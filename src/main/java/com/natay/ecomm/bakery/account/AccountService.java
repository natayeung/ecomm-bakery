package com.natay.ecomm.bakery.account;

import com.natay.ecomm.bakery.registration.RegistrationDto;

import java.util.Optional;

/**
 * @author natayeung
 */
public interface AccountService {

    UserAccount registerAccount(RegistrationDto dto) throws EmailAlreadyUsedException;

    Optional<UserAccount> findAccountByEmail(String email);
}
