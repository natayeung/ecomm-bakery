package com.natay.ecomm.bakery.account;

import com.natay.ecomm.bakery.registration.AddressDto;
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
                .withPostcode(registrationDto.getPostcode().toUpperCase())
                .build();

        persistencePort.add(address);
    }

    @Override
    public void updateAddress(String email, AddressDto addressDto) {
        requireNonNull(addressDto, "Address dto must be specified");

        Address address = Address.builder()
                .withEmail(email)
                .withAddressLine1(addressDto.getAddressLine1())
                .withAddressLine2(addressDto.getAddressLine2())
                .withPostcode(addressDto.getPostcode().toUpperCase())
                .build();

        persistencePort.findByEmail(email)
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
