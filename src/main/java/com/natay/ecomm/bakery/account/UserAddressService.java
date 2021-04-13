package com.natay.ecomm.bakery.account;

import com.natay.ecomm.bakery.registration.AddressDto;
import com.natay.ecomm.bakery.registration.RegistrationDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        final String email = registrationDto.getEmail();
        final String addressLine1 = registrationDto.getAddressLine1();
        final String addressLine2 = registrationDto.getAddressLine2();
        final String postcode = registrationDto.getPostcode();

        UserAddress userAddress = UserAddress.create()
                .withEmail(email)
                .withAddressLine1(addressLine1)
                .withAddressLine2(addressLine2)
                .withPostcode(postcode)
                .build();

        persistencePort.add(userAddress);
    }

    @Override
    public void updateAddress(String email, AddressDto addressDto) {
        UserAddress address = UserAddress.create()
                .withEmail(email)
                .withAddressLine1(addressDto.getAddressLine1())
                .withAddressLine2(addressDto.getAddressLine2())
                .withPostcode(addressDto.getPostcode())
                .build();

        persistencePort.findByEmail(email)
                .ifPresentOrElse(
                        (a) -> persistencePort.update(address),
                        () -> persistencePort.add(address));
    }

    @Override
    public Optional<UserAddress> findAddressByEmail(String email) {
        return persistencePort.findByEmail(email);
    }
}
