package com.natay.ecomm.bakery.user.testutil;

import com.natay.ecomm.bakery.user.registration.RegistrationDto;

import static org.apache.commons.lang3.RandomStringUtils.random;

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
        return new RegistrationDto()
                .setEmail(email)
                .setPassword(password)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setAddressLine1(addressLine1)
                .setTownOrCity(townOrCity)
                .setPostcode(postcode);
    }

    public static RegistrationDto createRegistrationDtoWithAddressDetails(String addressLine1,
                                                                          String addressLine2,
                                                                          String townOrCity,
                                                                          String postcode) {
        return new RegistrationDto()
                .setEmail(randomEmail())
                .setPassword(password)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setAddressLine1(addressLine1)
                .setAddressLine2(addressLine2)
                .setTownOrCity(townOrCity)
                .setPostcode(postcode);
    }

    private static String randomEmail() {
        return random(8, true, true) + "@gmail.com";
    }

    private RegistrationDtoFactory() {
    }
}
