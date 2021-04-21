package com.natay.ecomm.bakery.account;

import java.util.Optional;

/**
 * @author natayeung
 */
public interface AccountPersistencePort {

    int add(Account account);

    Optional<Account> findByEmail(String email);
}
