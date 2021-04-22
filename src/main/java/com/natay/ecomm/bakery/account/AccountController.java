package com.natay.ecomm.bakery.account;

import com.natay.ecomm.bakery.account.dto.AccountDto;
import com.natay.ecomm.bakery.account.dto.AccountUpdateFeedbackDto;
import com.natay.ecomm.bakery.basket.dto.BasketDto;
import com.natay.ecomm.bakery.basket.SessionBasket;
import com.natay.ecomm.bakery.common.MessageProperties;
import com.natay.ecomm.bakery.security.authentication.UserIdentity;
import com.natay.ecomm.bakery.security.authentication.AuthenticatedUserLookup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.natay.ecomm.bakery.account.dto.AccountDtoFactory.createAccountDto;
import static com.natay.ecomm.bakery.account.dto.AccountUpdateFeedbackDtoFactory.createAccountUpdateFeedbackDtoForValidationErrors;

/**
 * @author natayeung
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final AuthenticatedUserLookup authenticatedUserLookup;
    private final SessionBasket sessionBasket;
    private final AddressService addressService;
    private final MessageProperties messageProperties;

    public AccountController(AuthenticatedUserLookup authenticatedUserLookup,
                             SessionBasket sessionBasket,
                             AddressService addressService,
                             MessageProperties messageProperties) {
        this.authenticatedUserLookup = authenticatedUserLookup;
        this.sessionBasket = sessionBasket;
        this.addressService = addressService;
        this.messageProperties = messageProperties;
    }

    @ModelAttribute("account")
    public String addAccountToModel() {
        return authenticatedUserLookup.getAuthenticatedUser()
                .map(UserIdentity::username).orElse(null);
    }

    @ModelAttribute("basket")
    public BasketDto addBasketToModel() {
        return sessionBasket.getBasket();
    }

    @GetMapping
    public String viewAccountDetails(@RequestParam(name = "update-success", required = false) boolean updatedSuccessfully,
                                     ModelMap model) {
        UserIdentity user = authenticatedUserLookup.getAuthenticatedUser().orElseThrow(() -> {
            throw new IllegalStateException("Authenticated user expected");
        });
        logger.info("Received request to view account details for {}", user.username());

        addAccountDetailsToModelIfPresent(model, user);

        if (updatedSuccessfully) {
            model.addAttribute("feedbackMessage", messageProperties.getAccountUpdated());
        }

        return "account";
    }

    @PostMapping
    public String updateAccountDetails(@ModelAttribute("accountDetails") @Valid AccountDto accountDto,
                                       BindingResult bindingResult,
                                       Model model) {
        UserIdentity user = authenticatedUserLookup.getAuthenticatedUser().orElseThrow(() -> {
            throw new IllegalStateException("Authenticated user expected");
        });
        logger.info("Received request to update account details for {}: {}", user.username(), accountDto);

        if (bindingResult.hasErrors()) {
            logger.warn("Unable to update account details {}, validation failed: {}", accountDto, bindingResult.getFieldErrors());
            addFeedbackToModel(accountDto, bindingResult, model);
            return "account";
        }

        addressService.updateAddress(accountDto);

        return "redirect:/account?update-success=true";
    }

    private void addFeedbackToModel(AccountDto accountDto, BindingResult bindingResult, Model model) {
        AccountUpdateFeedbackDto feedbackDto = createAccountUpdateFeedbackDtoForValidationErrors(accountDto, bindingResult, messageProperties);
        model.addAttribute("feedback", feedbackDto);
    }

    private void addAccountDetailsToModelIfPresent(ModelMap model, UserIdentity user) {
        addressService.findAddressByEmail(user.username())
                .ifPresentOrElse(
                        a -> {
                            AccountDto accountDto = createAccountDto(user, a);
                            model.addAttribute("accountDetails", accountDto);
                        },
                        () -> logger.info("No address found for user {}", user.username()));
    }
}
