package com.natay.ecomm.bakery.user;

import java.util.Optional;

/**
 * @author natayeung
 */
public interface AccountPersistencePort {

    int add(UserAccount userAccount);

    Optional<UserAccount> findByEmail(String email);
}
