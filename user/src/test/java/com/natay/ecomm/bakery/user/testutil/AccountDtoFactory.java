package com.natay.ecomm.bakery.user.testutil;

import com.natay.ecomm.bakery.user.account.AccountDto;

/**
 * @author natayeung
 */
public class AccountDtoFactory {

    public static AccountDto createAccountDtoWithAddress(String email, String addressLine1, String addressLine2, String townOrCity, String postcode) {
        return new AccountDto()
                .setEmail(email)
                .setAddressLine1(addressLine1)
                .setAddressLine2(addressLine2)
                .setTownOrCity(townOrCity)
                .setPostcode(postcode);
    }

    private AccountDtoFactory() {
    }
}
