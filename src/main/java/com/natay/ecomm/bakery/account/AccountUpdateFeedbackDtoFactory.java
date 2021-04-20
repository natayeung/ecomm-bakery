package com.natay.ecomm.bakery.account;

import com.natay.ecomm.bakery.configuration.MessageProperties;
import com.natay.ecomm.bakery.registration.AddressDto;
import org.springframework.validation.BindingResult;

/**
 * @author natayeung
 */
public class AccountUpdateFeedbackDtoFactory {

    public static AccountUpdateFeedbackDto createAccountUpdateFeedbackDtoForValidationErrors(AddressDto addressDto,
                                                                                             BindingResult bindingResult,
                                                                                             MessageProperties messageProperties) {
        AccountUpdateFeedbackDto feedbackDto = new AccountUpdateFeedbackDto();
        if (bindingResult.hasFieldErrors("addressLine1")) {
            feedbackDto.setAddressLine1ErrorMessage(messageProperties.getInvalidAddressLine1());
        } else {
            feedbackDto.setLastInputAddressLine1(addressDto.getAddressLine1());
        }

        feedbackDto.setLastInputAddressLine2(addressDto.getAddressLine2());

        if (bindingResult.hasFieldErrors("postcode")) {
            feedbackDto.setPostcodeErrorMessage(messageProperties.getInvalidPostcode());
        } else {
            feedbackDto.setLastInputPostcode(addressDto.getPostcode());
        }

        return feedbackDto;
    }

    private AccountUpdateFeedbackDtoFactory() {}
}
