package com.natay.ecomm.bakery.testutils;

import com.natay.ecomm.bakery.account.AccountDto;

/**
 * @author natayeung
 */
public class AccountDtoFactory {

    public static AccountDto createAccountDtoWithAddress(String email, String addressLine1, String addressLine2, String townOrCity, String postcode) {
        AccountDto accountDto = new AccountDto();
        accountDto.setEmail(email);
        accountDto.setAddressLine1(addressLine1);
        accountDto.setAddressLine2(addressLine2);
        accountDto.setTownOrCity(townOrCity);
        accountDto.setPostcode(postcode);
        return accountDto;
    }

    private AccountDtoFactory() {
    }
}
