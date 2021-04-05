package com.natay.ecomm.bakery.user;

import com.natay.ecomm.bakery.user.dto.RegistrationDto;
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
    public User register(RegistrationDto dto) {
        UserAccount registeredAccount = accountService.registerAccount(dto);
        UserAddress registeredAddress = addressService.registerAddress(dto);

        return User.with(registeredAccount, registeredAddress);
    }
}
