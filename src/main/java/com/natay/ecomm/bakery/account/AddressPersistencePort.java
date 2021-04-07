package com.natay.ecomm.bakery.account;

import java.util.Optional;

/**
 * @author natayeung
 */
public interface AddressPersistencePort {

    int add(UserAddress userAddress);

    Optional<UserAddress> findByEmail(String email);
}
