package com.natay.ecomm.bakery.registration;

public class RegistrationFeedbackDtoFactory {

    public static RegistrationFeedbackDto createRegistrationFeedbackDtoForEmailAlreadyInUse(RegistrationDto registrationDto) {
        final RegistrationFeedbackDto feedbackDto = new RegistrationFeedbackDto();
        feedbackDto.setEmailErrorMessage("Email already in use. Please provide another one.");
        feedbackDto.setLastInputAddressLine1(registrationDto.getAddressLine1());
        feedbackDto.setLastInputAddressLine2(registrationDto.getAddressLine2());
        feedbackDto.setLastInputPostcode(registrationDto.getPostcode());

        return feedbackDto;
    }
}
