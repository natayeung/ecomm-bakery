package com.natay.ecomm.bakery.user.account.persistence;

import com.natay.ecomm.bakery.user.account.Account;

import java.util.Optional;

/**
 * @author natayeung
 */
public interface AccountPersistencePort {

    int add(Account account);

    Optional<Account> findByEmail(String email);
}
