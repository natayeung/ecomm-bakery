package com.natay.ecomm.bakery.user;

import com.natay.ecomm.bakery.user.dto.RegistrationDto;

import java.util.Optional;

/**
 * @author natayeung
 */
public interface AccountService {

    UserAccount registerAccount(RegistrationDto dto) throws EmailAlreadyUsedException;

    Optional<UserAccount> findAccountByEmail(String email);
}
