package com.natay.ecomm.bakery.user;

import com.natay.ecomm.bakery.user.dto.RegistrationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author natayeung
 */
@Service
public class UserAccountService implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(UserAccountService.class);

    private final PasswordEncoder passwordEncoder;
    private final AccountPersistencePort persistencePort;

    public UserAccountService(PasswordEncoder passwordEncoder, AccountPersistencePort persistencePort) {
        this.passwordEncoder = passwordEncoder;
        this.persistencePort = persistencePort;
    }

    @Override
    public UserAccount registerAccount(RegistrationDto dto) throws EmailAlreadyUsedException {
        final String email = dto.getEmail();
        final String encodedPassword = passwordEncoder.encode(dto.getPassword());

        persistencePort.findByEmail(email)
                .ifPresent(account -> {
                    throw new EmailAlreadyUsedException(email + " already used");
                });

        UserAccount newUserAccount = new UserAccount(email, encodedPassword);
        persistencePort.add(newUserAccount);

        return newUserAccount;
    }

    @Override
    public Optional<UserAccount> findAccountByEmail(String email) {
        logger.info("Finding account by email {}", email);
        return persistencePort.findByEmail(email);
    }
}
