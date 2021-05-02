package com.natay.ecomm.bakery.testutil;

import com.natay.ecomm.bakery.user.registration.RegistrationDto;

import static com.natay.ecomm.bakery.testutil.RandomUtil.randomEmail;

/**
 * @author natayeung
 */
public class RegistrationDtoFactory {

    private static final String PASSWORD = "jdpass";
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String ADDRESS_LINE_1 = "12 High Street";
    private static final String TOWN_OR_CITY = "London";
    private static final String POSTCODE = "E15 2GU";
    
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
