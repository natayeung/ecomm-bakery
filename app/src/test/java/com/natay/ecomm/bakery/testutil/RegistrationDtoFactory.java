package com.natay.ecomm.bakery.testutil;

import com.natay.ecomm.bakery.user.registration.RegistrationDto;

import static com.natay.ecomm.bakery.testutil.RandomUtil.randomEmail;
import static com.natay.ecomm.bakery.testutil.TestConstants.*;

/**
 * @author natayeung
 */
public class RegistrationDtoFactory {

    public static RegistrationDto createRegistrationDto() {
        RegistrationDto dto = new RegistrationDto();
        dto.setEmail(randomEmail());
        dto.setPassword(PASSWORD);
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);
        dto.setAddressLine1(ADDRESS_LINE_1);
        dto.setTownOrCity(TOWN_OR_CITY);
        dto.setPostcode(POSTCODE);
        return dto;
    }

    private RegistrationDtoFactory() {
    }
}
