package com.natay.ecomm.bakery.checkout;

import lombok.Data;

/**
 * @author natayeung
 */
@Data
public class CheckoutFeedbackDto {

    private String contactEmailErrorMessage;
    private String shippingFirstNameErrorMessage;
    private String shippingLastNameErrorMessage;
    private String addressLine1ErrorMessage;
    private String townOrCityErrorMessage;
    private String postcodeErrorMessage;
    private String lastInputContactEmail;
    private String lastInputShippingFirstName;
    private String lastInputShippingLastName;
    private String lastInputAddressLine1;
    private String lastInputAddressLine2;
    private String lastInputTownOrCity;
    private String lastInputPostcode;
}
