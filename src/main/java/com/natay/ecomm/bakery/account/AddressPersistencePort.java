package com.natay.ecomm.bakery.account;

import java.util.Optional;

/**
 * @author natayeung
 */
public interface AddressPersistencePort {

    int add(Address address);

    void update(Address address);

    Optional<Address> findByEmail(String email);
}
