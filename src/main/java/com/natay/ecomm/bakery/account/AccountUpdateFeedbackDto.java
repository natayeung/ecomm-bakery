package com.natay.ecomm.bakery.account;

import lombok.Data;

/**
 * @author natayeung
 */
@Data
public class AccountUpdateFeedbackDto {

    private String addressLine1ErrorMessage;
    private String postcodeErrorMessage;
    private String lastInputAddressLine1;
    private String lastInputAddressLine2;
    private String lastInputPostcode;
}
