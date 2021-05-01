package com.natay.ecomm.bakery.user.account;

import com.natay.ecomm.bakery.user.account.persistence.AccountPersistencePort;
import com.natay.ecomm.bakery.user.registration.RegistrationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.natay.ecomm.bakery.common.Arguments.requireNonBlank;
import static com.natay.ecomm.bakery.common.Arguments.requireNonNull;

/**
 * @author natayeung
 */
@Service
@Slf4j
public class UserAccountService implements AccountService {

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
                .email(email)
                .password(encodedPassword)
                .firstName(registrationDto.getFirstName())
                .lastName(registrationDto.getLastName())
                .build();
        persistencePort.add(newAccount);
    }

    @Override
    public Optional<Account> findAccountByEmail(String email) {
        requireNonBlank(email, "Email cannot be blank");

        log.info("Finding account by email {}", email);
        return persistencePort.findByEmail(email);
    }
}
