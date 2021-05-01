package com.natay.ecomm.bakery.user.testutil;

import com.natay.ecomm.bakery.user.registration.RegistrationDto;

import static com.natay.ecomm.bakery.testutil.RandomUtil.randomEmail;

/**
 * @author natayeung
 */
public class RegistrationDtoFactory {

    private static final String password = "jdpass";
    private static final String firstName = "John";
    private static final String lastName = "Doe";
    private static final String addressLine1 = "12 High Street";
    private static final String townOrCity = "London";
    private static final String postcode = "E15 2GU";

    public static RegistrationDto createRegistrationDtoWithEmail(String email) {
        return createRegistrationDtoWithEmailAndPassword(email, password);
    }

    public static RegistrationDto createRegistrationDtoWithEmailAndPassword(String email,
                                                                            String password) {
        RegistrationDto dto = new RegistrationDto();
        dto.setEmail(email);
        dto.setPassword(password);
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setAddressLine1(addressLine1);
        dto.setTownOrCity(townOrCity);
        dto.setPostcode(postcode);
        return dto;
    }

    public static RegistrationDto createRegistrationDtoWithAddressDetails(String addressLine1,
                                                                          String addressLine2,
                                                                          String townOrCity,
                                                                          String postcode) {
        RegistrationDto dto = new RegistrationDto();
        dto.setEmail(randomEmail());
        dto.setPassword(password);
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setAddressLine1(addressLine1);
        dto.setAddressLine2(addressLine2);
        dto.setTownOrCity(townOrCity);
        dto.setPostcode(postcode);
        return dto;
    }

    private RegistrationDtoFactory() {
    }
}
