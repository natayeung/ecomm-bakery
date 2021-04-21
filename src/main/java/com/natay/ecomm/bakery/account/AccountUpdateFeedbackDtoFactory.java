package com.natay.ecomm.bakery.account;

import com.natay.ecomm.bakery.configuration.MessageProperties;
import org.springframework.validation.BindingResult;

/**
 * @author natayeung
 */
public class AccountUpdateFeedbackDtoFactory {

    public static AccountUpdateFeedbackDto createAccountUpdateFeedbackDtoForValidationErrors(AccountDto accountDto,
                                                                                             BindingResult bindingResult,
                                                                                             MessageProperties messageProperties) {
        AccountUpdateFeedbackDto feedbackDto = new AccountUpdateFeedbackDto();

        if (bindingResult.hasFieldErrors("addressLine1")) {
            feedbackDto.setAddressLine1ErrorMessage(messageProperties.getInvalidAddressLine1());
        } else {
            feedbackDto.setLastInputAddressLine1(accountDto.getAddressLine1());
        }

        feedbackDto.setLastInputAddressLine2(accountDto.getAddressLine2());

        if (bindingResult.hasFieldErrors("townOrCity")) {
            feedbackDto.setTownOrCityErrorMessage(messageProperties.getInvalidTownOrCity());
        } else {
            feedbackDto.setLastInputTownOrCity(accountDto.getTownOrCity());
        }

        if (bindingResult.hasFieldErrors("postcode")) {
            feedbackDto.setPostcodeErrorMessage(messageProperties.getInvalidPostcode());
        } else {
            feedbackDto.setLastInputPostcode(accountDto.getPostcode());
        }

        return feedbackDto;
    }

    private AccountUpdateFeedbackDtoFactory() {
    }
}
