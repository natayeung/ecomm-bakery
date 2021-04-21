package com.natay.ecomm.bakery.account;

import com.natay.ecomm.bakery.security.authentication.AuthenticatedUser;

/**
 * @author natayeung
 */
class AccountDtoFactory {

    static AccountDto createAccountDto(AuthenticatedUser user, Address address) {
        AccountDto accountDto = new AccountDto();
        accountDto.setEmail(user.username());
        accountDto.setFirstName(user.firstname());
        accountDto.setLastName(user.lastname());
        accountDto.setAddressLine1(address.addressLine1());
        accountDto.setAddressLine2(address.addressLine2());
        accountDto.setTownOrCity(address.townOrCity());
        accountDto.setPostcode(address.postcode());

        return accountDto;
    }
}
