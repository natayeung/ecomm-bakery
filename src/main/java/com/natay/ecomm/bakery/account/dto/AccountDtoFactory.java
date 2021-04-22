package com.natay.ecomm.bakery.account.dto;

import com.natay.ecomm.bakery.account.Address;
import com.natay.ecomm.bakery.security.authentication.UserIdentity;

/**
 * @author natayeung
 */
public class AccountDtoFactory {

    public static AccountDto createAccountDto(UserIdentity user, Address address) {
        AccountDto accountDto = new AccountDto();
        accountDto.setEmail(user.username());
        accountDto.setFirstName(user.firstName());
        accountDto.setLastName(user.lastName());
        accountDto.setAddressLine1(address.addressLine1());
        accountDto.setAddressLine2(address.addressLine2());
        accountDto.setTownOrCity(address.townOrCity());
        accountDto.setPostcode(address.postcode());

        return accountDto;
    }
}
