package com.natay.ecomm.bakery.user;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserAccountService implements AccountService {

    private final Map<String, UserAccount> userAccounts = new HashMap<>();

    @Override
    public UserAccount registerAccount(RegistrationDto dto) {
        final String email = dto.getEmail();
        final String password = dto.getPassword();

        UserAccount newUserAccount = new UserAccount(email, password);
        userAccounts.put(email, newUserAccount);

        return newUserAccount;
    }

    @Override
    public Optional<UserAccount> findAccountByEmail(String email) {
        return Optional.ofNullable(userAccounts.get(email));
    }
}
