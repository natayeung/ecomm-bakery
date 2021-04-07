package com.natay.ecomm.bakery.account;

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
    public UserAddress registerAddress(RegistrationDto dto) {
        final String email = dto.getEmail();
        final String addressLine1 = dto.getAddressLine1();
        final String addressLine2 = dto.getAddressLine2();
        final String postcode = dto.getPostcode();

        UserAddress userAddress = UserAddress.create()
                .withEmail(email)
                .withAddressLine1(addressLine1)
                .withAddressLine2(addressLine2)
                .withPostcode(postcode)
                .build();

        persistencePort.add(userAddress);

        return userAddress;
    }

    @Override
    public Optional<UserAddress> findAddressByEmail(String email) {
        return persistencePort.findByEmail(email);
    }
}
