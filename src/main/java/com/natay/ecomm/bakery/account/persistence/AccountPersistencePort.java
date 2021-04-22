package com.natay.ecomm.bakery.account.persistence;

import com.natay.ecomm.bakery.account.Account;

import java.util.Optional;

/**
 * @author natayeung
 */
public interface AccountPersistencePort {

    int add(Account account);

    Optional<Account> findByEmail(String email);
}
