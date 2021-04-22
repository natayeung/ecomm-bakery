package com.natay.ecomm.bakery.account;

import com.natay.ecomm.bakery.registration.dto.RegistrationDto;

import java.util.Optional;

/**
 * @author natayeung
 */
public interface AccountService {

    void registerAccount(RegistrationDto dto) throws EmailAlreadyUsedException;

    Optional<Account> findAccountByEmail(String email);
}
