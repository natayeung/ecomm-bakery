package com.natay.ecomm.bakery.account;

import com.natay.ecomm.bakery.registration.RegistrationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.natay.ecomm.bakery.utils.Arguments.requireNonBlank;
import static com.natay.ecomm.bakery.utils.Arguments.requireNonNull;

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
    public void registerAccount(RegistrationDto registrationDto)
            throws EmailAlreadyUsedException {
        requireNonNull(registrationDto, "Registration dto must be specified");

        final String email = registrationDto.getEmail();
        final String encodedPassword = passwordEncoder.encode(registrationDto.getPassword());
        persistencePort.findByEmail(email)
                .ifPresent(account -> {
                    throw new EmailAlreadyUsedException(email + " already used");
                });

        Account newAccount = Account.builder()
                .withEmail(email)
                .withPassword(encodedPassword)
                .withFirstName(registrationDto.getFirstName())
                .withLastName(registrationDto.getLastName())
                .build();
        persistencePort.add(newAccount);
    }

    @Override
    public Optional<Account> findAccountByEmail(String email) {
        requireNonBlank(email, "Email cannot be blank");

        logger.info("Finding account by email {}", email);
        return persistencePort.findByEmail(email);
    }
}
