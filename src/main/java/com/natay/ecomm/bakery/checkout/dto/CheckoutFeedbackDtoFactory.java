package com.natay.ecomm.bakery.checkout.dto;

import com.natay.ecomm.bakery.common.MessageProperties;
import org.springframework.validation.BindingResult;

/**
 * @author natayeung
 */
public class CheckoutFeedbackDtoFactory {

    public static CheckoutFeedbackDto createCheckoutFeedbackDtoForValidationErrors(ShippingDetailsDto shippingDetailsDto,
                                                                                   BindingResult bindingResult,
                                                                                   MessageProperties messageProperties) {
        CheckoutFeedbackDto feedbackDto = new CheckoutFeedbackDto();
        if (bindingResult.hasFieldErrors("shippingFirstName")) {
            feedbackDto.setShippingFirstNameErrorMessage(messageProperties.getInvalidFirstName());
        } else {
            feedbackDto.setLastInputShippingFirstName(shippingDetailsDto.getShippingFirstName());
        }

        if (bindingResult.hasFieldErrors("shippingLastName")) {
            feedbackDto.setShippingLastNameErrorMessage(messageProperties.getInvalidLastName());
        } else {
            feedbackDto.setLastInputShippingLastName(shippingDetailsDto.getShippingLastName());
        }

        if (bindingResult.hasFieldErrors("addressLine1")) {
            feedbackDto.setAddressLine1ErrorMessage(messageProperties.getInvalidAddressLine1());
        } else {
            feedbackDto.setLastInputAddressLine1(shippingDetailsDto.getAddressLine1());
        }

        feedbackDto.setLastInputAddressLine2(shippingDetailsDto.getAddressLine2());

        if (bindingResult.hasFieldErrors("townOrCity")) {
            feedbackDto.setTownOrCityErrorMessage(messageProperties.getInvalidTownOrCity());
        } else {
            feedbackDto.setLastInputTownOrCity(shippingDetailsDto.getTownOrCity());
        }

        if (bindingResult.hasFieldErrors("postcode")) {
            feedbackDto.setPostcodeErrorMessage(messageProperties.getInvalidPostcode());
        } else {
            feedbackDto.setLastInputPostcode(shippingDetailsDto.getPostcode());
        }

        return feedbackDto;
    }
}
