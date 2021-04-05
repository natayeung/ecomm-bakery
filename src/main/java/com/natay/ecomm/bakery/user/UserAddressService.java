package com.natay.ecomm.bakery.user;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserAddressService implements AddressService {

    private final Map<String, UserAddress> userAddresses = new HashMap<>();

    @Override
    public UserAddress registerAddress(RegistrationDto dto) {
        final String email = dto.getEmail();
        final String addressLine1 = dto.getAddressLine1();
        final String addressLine2 = dto.getAddressLine2();
        final String postCode = dto.getPostCode();

        UserAddress userAddress = new UserAddress(addressLine1, addressLine2, postCode);
        userAddresses.put(email, userAddress);
        return userAddress;
    }

    @Override
    public Optional<UserAddress> findAddressByEmail(String email) {
        return Optional.ofNullable(userAddresses.get(email));
    }
}
