package com.natay.ecomm.bakery.registration;

import com.natay.ecomm.bakery.account.AccountService;
import com.natay.ecomm.bakery.account.AddressService;
import com.natay.ecomm.bakery.registration.dto.RegistrationDto;
import org.springframework.stereotype.Service;

import static com.natay.ecomm.bakery.utils.Arguments.requireNonNull;

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
    public void register(RegistrationDto registrationDto) {
        requireNonNull(registrationDto, "Registration dto must be specified");

        accountService.registerAccount(registrationDto);
        addressService.registerAddress(registrationDto);
    }
}
