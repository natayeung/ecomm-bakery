package com.natay.ecomm.bakery.registration;

import static com.natay.ecomm.bakery.testutils.RandomUtil.randomEmail;

/**
 * @author natayeung
 */
public class RegistrationDtoFactory {

    private static final String password = "harrypass";
    private static final String addressLine1 = "1 High Street";
    private static final String postcode = "PO1 2ST";

    public static RegistrationDto createRegistrationDtoWithEmailAndPassword(String email,
                                                                            String password) {
        RegistrationDto dto = new RegistrationDto();
        dto.setEmail(email);
        dto.setPassword(password);
        dto.setAddressLine1(addressLine1);
        dto.setPostcode(postcode);
        return dto;
    }

    public static RegistrationDto createRegistrationDtoWithAddressDetails(String addressLine1,
                                                                          String addressLine2,
                                                                          String postcode) {
        RegistrationDto dto = new RegistrationDto();
        dto.setEmail(randomEmail());
        dto.setPassword(password);
        dto.setAddressLine1(addressLine1);
        dto.setAddressLine2(addressLine2);
        dto.setPostcode(postcode);
        return dto;
    }

    public static RegistrationDto createRegistrationDtoWithEmail(String email) {
        RegistrationDto dto = new RegistrationDto();
        dto.setEmail(email);
        dto.setPassword(password);
        dto.setAddressLine1(addressLine1);
        dto.setPostcode(postcode);
        return dto;
    }

    private RegistrationDtoFactory() {
    }
}
