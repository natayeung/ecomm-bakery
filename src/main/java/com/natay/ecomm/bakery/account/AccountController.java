package com.natay.ecomm.bakery.account;

import com.natay.ecomm.bakery.registration.AddressDto;
import com.natay.ecomm.bakery.security.AuthenticatedUserLookup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author natayeung
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final AuthenticatedUserLookup authenticatedUserLookup;
    private final AddressService addressService;

    public AccountController(AuthenticatedUserLookup authenticatedUserLookup, AddressService addressService) {
        this.authenticatedUserLookup = authenticatedUserLookup;
        this.addressService = addressService;
    }

    @GetMapping
    public String viewAccountDetails(ModelMap model) {

        authenticatedUserLookup.getAuthenticatedUser()
                .ifPresentOrElse(
                        u -> {
                            final String username = u.username();
                            logger.info("Received request to view account {}", username);

                            model.addAttribute("user", username);
                            populateModelWithAddressInfoIfPresent(model, username);
                        },
                        () -> logger.warn("Unable to handle request to view account: no authenticated user"));

        return "account";
    }

    @PostMapping
    public String updateAccountDetails(@ModelAttribute("address") AddressDto addressDto) {

        authenticatedUserLookup.getAuthenticatedUser()
                .ifPresentOrElse(
                        u -> {
                            final String username = u.username();
                            logger.info("Received request to update account {}: {}", username, addressDto);

                            addressService.updateAddress(username, addressDto);
                        },
                        () -> logger.warn("Unable to handle request to update account: no authenticated user"));

        return "redirect:/account";
    }

    private void populateModelWithAddressInfoIfPresent(ModelMap model, String username) {
        addressService.findAddressByEmail(username)
                .ifPresentOrElse(
                        a -> model.addAttribute("address", a),
                        () -> logger.info("No address found for user {}", username));
    }
}
