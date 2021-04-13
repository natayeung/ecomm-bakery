package com.natay.ecomm.bakery.order;

import com.natay.ecomm.bakery.MessageProperties;
import com.natay.ecomm.bakery.account.AccountUpdateFeedbackDto;
import com.natay.ecomm.bakery.registration.AddressDto;
import com.natay.ecomm.bakery.security.AuthenticatedUser;
import com.natay.ecomm.bakery.security.AuthenticatedUserLookup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

import static com.natay.ecomm.bakery.account.AccountUpdateFeedbackDtoFactory.createAccountUpdateFeedbackDtoForValidationErrors;
import static com.natay.ecomm.bakery.session.SessionAttributeLookup.getBasket;

/**
 * @author natayeung
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final AuthenticatedUserLookup authenticatedUserLookup;
    private final MessageProperties messageProperties;

    public OrderController(AuthenticatedUserLookup authenticatedUserLookup, MessageProperties messageProperties) {
        this.authenticatedUserLookup = authenticatedUserLookup;
        this.messageProperties = messageProperties;
    }

    @PostMapping
    public String processOrder(@ModelAttribute("address") @Valid AddressDto addressDto,
                               BindingResult bindingResult,
                               HttpSession session,
                               Model model) {

        logger.info("Received request to process order, delivery address: {}", addressDto);

        Optional<AuthenticatedUser> user = authenticatedUserLookup.getAuthenticatedUser();

        if (bindingResult.hasErrors()) {
            logger.warn("Unable to update account details {}, validation failed: {}", addressDto, bindingResult.getFieldErrors());
            user.ifPresentOrElse(
                    u -> populateModelWithFeedback(addressDto, bindingResult, model, u),
                    () -> {
                        throw new IllegalStateException("Authenticated user expected");
                    }
            );
            getBasket(session).ifPresent((b) -> model.addAttribute("userBasket", b));

            return "basket";
        }

        user.ifPresentOrElse(
                u -> {
                    model.addAttribute("user", u.username());
                    processOrder(session);
                    session.invalidate();

                },
                () -> {
                    throw new IllegalStateException("Authenticated user expected");
                });

        return "order-confirm";
    }

    private void populateModelWithFeedback(AddressDto addressDto, BindingResult bindingResult, Model model, AuthenticatedUser user) {
        AccountUpdateFeedbackDto feedbackDto = createAccountUpdateFeedbackDtoForValidationErrors(addressDto, bindingResult, messageProperties);
        model.addAttribute("feedback", feedbackDto);
        model.addAttribute("user", user.username());
    }

    private void processOrder(HttpSession session) {
        getBasket(session).ifPresentOrElse(
                (b) -> logger.info("Processing order from {}", b),
                () -> {
                    throw new IllegalStateException("Unable to retrieve shopping basket");
                });
    }
}
