package com.natay.ecomm.bakery.user.account;

import com.natay.ecomm.bakery.user.registration.RegistrationDto;

import java.util.Optional;

/**
 * @author natayeung
 */
public interface AccountService {

    void registerAccount(RegistrationDto dto) throws EmailAlreadyUsedException;

    Optional<Account> findAccountByEmail(String email);
}
