package com.natay.ecomm.bakery.user;

import java.util.Optional;

/**
 * @author natayeung
 */
public interface AddressPersistencePort {

    int add(UserAddress userAddress);

    Optional<UserAddress> findByEmail(String email);
}
