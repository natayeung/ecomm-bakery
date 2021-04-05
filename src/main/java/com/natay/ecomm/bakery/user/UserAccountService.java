package com.natay.ecomm.bakery.user;

import com.natay.ecomm.bakery.user.dto.RegistrationDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author natayeung
 */
@Service
public class UserAccountService implements AccountService {

    private final AccountPersistencePort persistencePort;

    public UserAccountService(AccountPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public UserAccount registerAccount(RegistrationDto dto) throws EmailAlreadyUsedException {
        final String email = dto.getEmail();
        final String password = dto.getPassword();

        persistencePort.findByEmail(email)
                .ifPresent(account -> {
                    throw new EmailAlreadyUsedException(email + " already used");
                });

        UserAccount newUserAccount = new UserAccount(email, password);
        persistencePort.add(newUserAccount);

        return newUserAccount;
    }

    @Override
    public Optional<UserAccount> findAccountByEmail(String email) {
        return persistencePort.findByEmail(email);
    }
}
