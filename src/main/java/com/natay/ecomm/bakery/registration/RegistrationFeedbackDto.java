package com.natay.ecomm.bakery.registration;

import lombok.Data;

@Data
public class RegistrationFeedbackDto {

    private String emailErrorMessage;
    private String passwordErrorMessage;
    private String addressLine1ErrorMessage;
    private String postcodeErrorMessage;
    private String lastInputEmail;
    private String lastInputAddressLine1;
    private String lastInputAddressLine2;
    private String lastInputPostcode;
}
