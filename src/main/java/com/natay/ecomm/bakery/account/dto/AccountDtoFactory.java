package com.natay.ecomm.bakery.account.dto;

import com.natay.ecomm.bakery.account.Address;
import com.natay.ecomm.bakery.security.authentication.UserIdentity;

/**
 * @author natayeung
 */
public class AccountDtoFactory {

    public static AccountDto createAccountDto(UserIdentity user, Address address) {
        return new AccountDto()
                .setEmail(user.username())
                .setFirstName(user.firstName())
                .setLastName(user.lastName())
                .setAddressLine1(address.addressLine1())
                .setAddressLine2(address.addressLine2())
                .setTownOrCity(address.townOrCity())
                .setPostcode(address.postcode());
    }
}
