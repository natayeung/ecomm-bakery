package com.natay.ecomm.bakery.account.dto;

import lombok.Data;

/**
 * @author natayeung
 */
@Data
public class AccountUpdateFeedbackDto {

    private String firstNameErrorMessage;
    private String lastNameErrorMessage;
    private String addressLine1ErrorMessage;
    private String townOrCityErrorMessage;
    private String postcodeErrorMessage;
    private String lastInputFirstName;
    private String lastInputLastName;
    private String lastInputAddressLine1;
    private String lastInputAddressLine2;
    private String lastInputTownOrCity;
    private String lastInputPostcode;
}
