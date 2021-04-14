package com.natay.ecomm.bakery.order;

import com.natay.ecomm.bakery.MessageProperties;
import com.natay.ecomm.bakery.account.AccountUpdateFeedbackDto;
import com.natay.ecomm.bakery.basket.Basket;
import com.natay.ecomm.bakery.basket.SessionBasket;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.Optional;

import static com.natay.ecomm.bakery.account.AccountUpdateFeedbackDtoFactory.createAccountUpdateFeedbackDtoForValidationErrors;

/**
 * @author natayeung
 */
@Controller
@RequestMapping("/order")
@SessionAttributes("scopedTarget.sessionBasket")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final AuthenticatedUserLookup authenticatedUserLookup;
    private final SessionBasket sessionBasket;
    private final MessageProperties messageProperties;

    public OrderController(AuthenticatedUserLookup authenticatedUserLookup,
                           SessionBasket sessionBasket,
                           MessageProperties messageProperties) {
        this.authenticatedUserLookup = authenticatedUserLookup;
        this.sessionBasket = sessionBasket;
        this.messageProperties = messageProperties;
    }

    @ModelAttribute("basket")
    public Basket addBasketToModel() {
        return sessionBasket.getBasket();
    }

    @ModelAttribute("account")
    public String addAccountToModel() {
        return authenticatedUserLookup.getAuthenticatedUser()
                .flatMap(u -> Optional.of(u.username())).orElse(null);
    }

    @PostMapping
    public String processOrder(@ModelAttribute("address") @Valid AddressDto addressDto,
                               BindingResult bindingResult,
                               SessionStatus sessionStatus,
                               Model model) {
        logger.info("Received request to process order, delivery address: {}", addressDto);

        Optional<AuthenticatedUser> user = authenticatedUserLookup.getAuthenticatedUser();
        if (bindingResult.hasErrors()) {
            logger.warn("Unable to update account details {}, validation failed: {}", addressDto, bindingResult.getFieldErrors());
            user.ifPresentOrElse(
                    u -> addFeedbackToModel(addressDto, bindingResult, model, u),
                    () -> {
                        throw new IllegalStateException("Authenticated user expected");
                    }
            );
            return "basket";
        }

        user.ifPresentOrElse(
                u -> {
                    processOrder(addressDto);
                    sessionStatus.setComplete();
                },
                () -> {
                    throw new IllegalStateException("Authenticated user expected");
                });
        return "order-confirm";
    }

    private void addFeedbackToModel(AddressDto addressDto, BindingResult bindingResult, Model model, AuthenticatedUser user) {
        AccountUpdateFeedbackDto feedbackDto = createAccountUpdateFeedbackDtoForValidationErrors(addressDto, bindingResult, messageProperties);
        model.addAttribute("feedback", feedbackDto);
    }

    private void processOrder(AddressDto addressDto) {
        logger.info("Processing order from: {}, delivery address: {}", sessionBasket.getBasket(), addressDto);
    }
}
