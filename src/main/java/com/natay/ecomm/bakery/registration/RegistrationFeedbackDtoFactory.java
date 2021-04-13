package com.natay.ecomm.bakery.registration;

import com.natay.ecomm.bakery.MessageProperties;
import org.springframework.validation.BindingResult;

/**
 * @author natayeung
 */
class RegistrationFeedbackDtoFactory {

    static RegistrationFeedbackDto createRegistrationFeedbackDtoForEmailAlreadyInUse(RegistrationDto registrationDto,
                                                                                     MessageProperties messageProperties) {
        RegistrationFeedbackDto feedbackDto = new RegistrationFeedbackDto();
        feedbackDto.setEmailErrorMessage(messageProperties.getEmailInUse());
        feedbackDto.setLastInputAddressLine1(registrationDto.getAddressLine1());
        feedbackDto.setLastInputAddressLine2(registrationDto.getAddressLine2());
        feedbackDto.setLastInputPostcode(registrationDto.getPostcode());

        return feedbackDto;
    }

    static RegistrationFeedbackDto createRegistrationFeedbackDtoForValidationErrors(RegistrationDto registrationDto,
                                                                                    BindingResult bindingResult,
                                                                                    MessageProperties messageProperties) {
        RegistrationFeedbackDto feedbackDto = new RegistrationFeedbackDto();
        if (bindingResult.hasFieldErrors("email")) {
            feedbackDto.setEmailErrorMessage(messageProperties.getInvalidEmail());
        } else {
            feedbackDto.setLastInputEmail(registrationDto.getEmail());
        }

        if (bindingResult.hasFieldErrors("password")) {
            feedbackDto.setPasswordErrorMessage(messageProperties.getInvalidPassword());
        }

        if (bindingResult.hasFieldErrors("addressLine1")) {
            feedbackDto.setAddressLine1ErrorMessage(messageProperties.getInvalidAddressLine1());
        } else {
            feedbackDto.setLastInputAddressLine1(registrationDto.getAddressLine1());
        }

        feedbackDto.setLastInputAddressLine2(registrationDto.getAddressLine2());

        if (bindingResult.hasFieldErrors("postcode")) {
            feedbackDto.setPostcodeErrorMessage(messageProperties.getInvalidPostcode());
        } else {
            feedbackDto.setLastInputPostcode(registrationDto.getPostcode());
        }

        return feedbackDto;
    }
}
