package com.natay.ecomm.bakery.account;

import com.natay.ecomm.bakery.registration.AccountDto;
import com.natay.ecomm.bakery.registration.RegistrationDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.natay.ecomm.bakery.utils.Arguments.requireNonBlank;
import static com.natay.ecomm.bakery.utils.Arguments.requireNonNull;

/**
 * @author natayeung
 */
@Service
public class UserAddressService implements AddressService {

    private final AddressPersistencePort persistencePort;

    public UserAddressService(AddressPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public void registerAddress(RegistrationDto registrationDto) {
        requireNonNull(registrationDto, "Registration dto must be specified");

        Address address = Address.builder()
                .withEmail(registrationDto.getEmail())
                .withAddressLine1(registrationDto.getAddressLine1())
                .withAddressLine2(registrationDto.getAddressLine2())
                .withTownOrCity(registrationDto.getTownOrCity())
                .withPostcode(registrationDto.getPostcode().toUpperCase())
                .build();

        persistencePort.add(address);
    }

    @Override
    public void updateAddress(AccountDto accountDto) {
        requireNonNull(accountDto, "Account dto must be specified");

        Address address = Address.builder()
                .withEmail(accountDto.getEmail())
                .withAddressLine1(accountDto.getAddressLine1())
                .withAddressLine2(accountDto.getAddressLine2())
                .withTownOrCity(accountDto.getTownOrCity())
                .withPostcode(accountDto.getPostcode().toUpperCase())
                .build();

        persistencePort.findByEmail(address.email())
                .ifPresentOrElse(
                        (a) -> persistencePort.update(address),
                        () -> persistencePort.add(address));
    }

    @Override
    public Optional<Address> findAddressByEmail(String email) {
        requireNonBlank(email, "Email cannot be blank");

        return persistencePort.findByEmail(email);
    }
}
