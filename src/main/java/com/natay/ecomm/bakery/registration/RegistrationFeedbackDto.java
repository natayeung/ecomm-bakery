package com.natay.ecomm.bakery.registration;

import lombok.Data;

@Data
public class RegistrationFeedbackDto {

    private String emailErrorMessage;
    private String passwordErrorMessage;
    private String firstNameErrorMessage;
    private String lastNameErrorMessage;
    private String addressLine1ErrorMessage;
    private String townOrCityErrorMessage;
    private String postcodeErrorMessage;
    private String lastInputEmail;
    private String lastInputFirstName;
    private String lastInputLastName;
    private String lastInputAddressLine1;
    private String lastInputAddressLine2;
    private String lastInputTownOrCity;
    private String lastInputPostcode;
}
