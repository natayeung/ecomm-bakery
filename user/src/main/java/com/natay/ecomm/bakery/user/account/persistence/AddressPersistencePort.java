package com.natay.ecomm.bakery.user.account.persistence;

import com.natay.ecomm.bakery.user.account.Address;

import java.util.Optional;

/**
 * @author natayeung
 */
public interface AddressPersistencePort {

    int add(Address address);

    void update(Address address);

    Optional<Address> findByEmail(String email);
}
