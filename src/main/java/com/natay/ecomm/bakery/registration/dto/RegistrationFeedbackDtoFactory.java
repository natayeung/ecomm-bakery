package com.natay.ecomm.bakery.registration.dto;

import com.natay.ecomm.bakery.common.MessageProperties;
import org.springframework.validation.BindingResult;

/**
 * @author natayeung
 */
public class RegistrationFeedbackDtoFactory {

    public static RegistrationFeedbackDto createRegistrationFeedbackDtoForEmailAlreadyInUse(RegistrationDto registrationDto,
                                                                                            MessageProperties messageProperties) {
        RegistrationFeedbackDto feedbackDto = new RegistrationFeedbackDto();
        feedbackDto.setEmailErrorMessage(messageProperties.getEmailInUse());
        feedbackDto.setLastInputAddressLine1(registrationDto.getAddressLine1());
        feedbackDto.setLastInputAddressLine2(registrationDto.getAddressLine2());
        feedbackDto.setLastInputTownOrCity(registrationDto.getTownOrCity());
        feedbackDto.setLastInputPostcode(registrationDto.getPostcode());

        return feedbackDto;
    }

    public static RegistrationFeedbackDto createRegistrationFeedbackDtoForValidationErrors(RegistrationDto registrationDto,
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

        if (bindingResult.hasFieldErrors("firstName")) {
            feedbackDto.setFirstNameErrorMessage(messageProperties.getInvalidFirstName());
        } else {
            feedbackDto.setLastInputFirstName(registrationDto.getFirstName());
        }

        if (bindingResult.hasFieldErrors("lastName")) {
            feedbackDto.setLastNameErrorMessage(messageProperties.getInvalidLastName());
        } else {
            feedbackDto.setLastInputLastName(registrationDto.getLastName());
        }

        if (bindingResult.hasFieldErrors("addressLine1")) {
            feedbackDto.setAddressLine1ErrorMessage(messageProperties.getInvalidAddressLine1());
        } else {
            feedbackDto.setLastInputAddressLine1(registrationDto.getAddressLine1());
        }

        feedbackDto.setLastInputAddressLine2(registrationDto.getAddressLine2());

        if (bindingResult.hasFieldErrors("townOrCity")) {
            feedbackDto.setTownOrCityErrorMessage(messageProperties.getInvalidTownOrCity());
        } else {
            feedbackDto.setLastInputTownOrCity(registrationDto.getTownOrCity());
        }

        if (bindingResult.hasFieldErrors("postcode")) {
            feedbackDto.setPostcodeErrorMessage(messageProperties.getInvalidPostcode());
        } else {
            feedbackDto.setLastInputPostcode(registrationDto.getPostcode());
        }

        return feedbackDto;
    }
}
