package com.natay.ecomm.bakery.registration;

import com.natay.ecomm.bakery.account.AccountService;
import com.natay.ecomm.bakery.account.AddressService;
import org.springframework.stereotype.Service;

/**
 * @author natayeung
 */
@Service
public class UserRegistrationService implements RegistrationService {

    private final AccountService accountService;
    private final AddressService addressService;

    public UserRegistrationService(AccountService accountService,
                                   AddressService addressService) {
        this.accountService = accountService;
        this.addressService = addressService;
    }

    @Override
    public void register(RegistrationDto dto) {
        accountService.registerAccount(dto);
        addressService.registerAddress(dto);
    }
}
