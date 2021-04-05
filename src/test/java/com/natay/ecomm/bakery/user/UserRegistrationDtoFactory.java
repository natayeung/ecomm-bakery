package com.natay.ecomm.bakery.user;

public class UserRegistrationDtoFactory {

    private static final String email = "harryp@gmail.com";
    private static final String password = "harrypass";
    private static final String addressLine1 = "1 High Street";
    private static final String postcode = "PO1 2ST";

    public static RegistrationDto createUserRegistrationDtoWithEmailAndPassword(String email, String password) {
        final RegistrationDto dto = new RegistrationDto();
        dto.setEmail(email);
        dto.setPassword(password);
        dto.setAddressLine1(addressLine1);
        dto.setPostcode(postcode);
        return dto;
    }

    public static RegistrationDto createUserRegistrationDtoWithAddressDetails(String addressLine1, String addressLine2, String postcode) {
        final RegistrationDto dto = new RegistrationDto();
        dto.setEmail(email);
        dto.setPassword(password);
        dto.setAddressLine1(addressLine1);
        dto.setAddressLine2(addressLine2);
        dto.setPostcode(postcode);
        return dto;
    }

    private UserRegistrationDtoFactory() {}
}
