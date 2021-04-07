package com.natay.ecomm.bakery.account;

import com.natay.ecomm.bakery.registration.AddressDto;
import com.natay.ecomm.bakery.security.UserCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * @author natayeung
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final AddressService addressService;

    public AccountController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public String viewAccountDetails(@AuthenticationPrincipal UserCredentials userCredentials,
                                     ModelMap model) {


        Optional.ofNullable(userCredentials)
                .ifPresentOrElse(
                        u -> {
                            final String username = u.getUsername();
                            logger.info("Received request to view account {}", username);

                            model.addAttribute("user", username);
                            addressService.findAddressByEmail(username)
                                    .ifPresentOrElse(a -> model.addAttribute("address", a),
                                            () -> logger.warn("No address found for user {}", username));
                        },
                        () -> logger.warn("Unable to handle request to view account, userCredentials not available"));

        return "account";
    }

    @PostMapping
    public String updateAccountDetails(@ModelAttribute("address") AddressDto addressDto,
                                       @AuthenticationPrincipal UserCredentials userCredentials) {

        Optional.ofNullable(userCredentials)
                .ifPresentOrElse(
                        u -> {
                            final String username = u.getUsername();
                            logger.info("Received request to update account {}: {}", username, addressDto);

                            addressService.updateAddress(username, addressDto);
                        },
                        () -> logger.warn("Unable to handle request to update account, userCredentials not available"));

        return "redirect:/account";
    }
}
