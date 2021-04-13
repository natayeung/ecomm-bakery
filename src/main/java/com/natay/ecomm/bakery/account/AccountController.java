package com.natay.ecomm.bakery.account;

import com.natay.ecomm.bakery.MessageProperties;
import com.natay.ecomm.bakery.registration.AddressDto;
import com.natay.ecomm.bakery.security.AuthenticatedUser;
import com.natay.ecomm.bakery.security.AuthenticatedUserLookup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static com.natay.ecomm.bakery.account.AccountUpdateFeedbackDtoFactory.createAccountUpdateFeedbackDtoForValidationErrors;

/**
 * @author natayeung
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final AuthenticatedUserLookup authenticatedUserLookup;
    private final AddressService addressService;
    private final MessageProperties messageProperties;

    public AccountController(AuthenticatedUserLookup authenticatedUserLookup, AddressService addressService, MessageProperties messageProperties) {
        this.authenticatedUserLookup = authenticatedUserLookup;
        this.addressService = addressService;
        this.messageProperties = messageProperties;
    }

    @GetMapping
    public String viewAccountDetails(@RequestParam(value = "update-success", required = false) boolean updatedSuccessfully,
                                     ModelMap model) {

        authenticatedUserLookup.getAuthenticatedUser()
                .ifPresentOrElse(
                        u -> {
                            final String username = u.username();
                            logger.info("Received request to view account details for {}", username);

                            model.addAttribute("user", username);
                            populateModelWithAddressInfoIfPresent(model, username);
                        },
                        () -> logger.warn("Unable to retrieve account details: no authenticated user"));

        if (updatedSuccessfully) {
            model.addAttribute("feedbackMessage", messageProperties.getAccountUpdated());
        }

        return "account";
    }

    @PostMapping
    public String updateAccountDetails(@ModelAttribute("address") @Valid AddressDto addressDto,
                                       BindingResult bindingResult,
                                       Model model) {

        Optional<AuthenticatedUser> user = authenticatedUserLookup.getAuthenticatedUser();

        if (bindingResult.hasErrors()) {
            logger.warn("Unable to update account details {}, validation failed: {}", addressDto, bindingResult.getFieldErrors());
            user.ifPresentOrElse(
                    u -> populateModelWithFeedback(addressDto, bindingResult, model, u),
                    () -> {
                        throw new IllegalStateException("Authenticated user expected");
                    }
            );

            return "account";
        }

        user.ifPresentOrElse(
                u -> {
                    logger.info("Received request to update account details for {}: {}", u.username(), addressDto);

                    addressService.updateAddress(u.username(), addressDto);
                },
                () -> {
                    throw new IllegalStateException("Authenticated user expected");
                });

        return "redirect:/account?update-success=true";
    }

    private void populateModelWithFeedback(AddressDto addressDto, BindingResult bindingResult, Model model, AuthenticatedUser user) {
        AccountUpdateFeedbackDto feedbackDto = createAccountUpdateFeedbackDtoForValidationErrors(addressDto, bindingResult, messageProperties);
        model.addAttribute("feedback", feedbackDto);
        model.addAttribute("user", user.username());
    }

    private void populateModelWithAddressInfoIfPresent(ModelMap model, String username) {
        addressService.findAddressByEmail(username)
                .ifPresentOrElse(
                        a -> model.addAttribute("address", a),
                        () -> logger.info("No address found for user {}", username));
    }
}
