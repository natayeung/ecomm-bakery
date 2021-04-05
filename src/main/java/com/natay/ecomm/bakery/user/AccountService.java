package com.natay.ecomm.bakery.user;

import java.util.Optional;

public interface AccountService {

    UserAccount registerAccount(RegistrationDto dto);

    Optional<UserAccount> findAccountByEmail(String email);
}
