package com.natay.ecomm.bakery.checkout;

import com.natay.ecomm.bakery.account.AccountUpdateFeedbackDto;
import com.natay.ecomm.bakery.basket.BasketDto;
import com.natay.ecomm.bakery.basket.SessionBasket;
import com.natay.ecomm.bakery.configuration.MessageProperties;
import com.natay.ecomm.bakery.registration.AddressDto;
import com.natay.ecomm.bakery.security.AuthenticatedUser;
import com.natay.ecomm.bakery.security.AuthenticatedUserLookup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

import static com.natay.ecomm.bakery.account.AccountUpdateFeedbackDtoFactory.createAccountUpdateFeedbackDtoForValidationErrors;
import static com.natay.ecomm.bakery.checkout.InitiatePaymentRequestFactory.createInitiatePaymentRequest;

/**
 * @author natayeung
 */
@Controller
@RequestMapping("/checkout")
@SessionAttributes("scopedTarget.sessionBasket")
public class CheckoutController {

    private static final Logger logger = LoggerFactory.getLogger(CheckoutController.class);

    private final AuthenticatedUserLookup authenticatedUserLookup;
    private final SessionBasket sessionBasket;
    private final PaymentService paymentService;
    private final MessageProperties messageProperties;

    public CheckoutController(AuthenticatedUserLookup authenticatedUserLookup,
                              SessionBasket sessionBasket,
                              PaymentService paymentService,
                              MessageProperties messageProperties) {
        this.authenticatedUserLookup = authenticatedUserLookup;
        this.sessionBasket = sessionBasket;
        this.paymentService = paymentService;
        this.messageProperties = messageProperties;
    }

    @ModelAttribute("basket")
    public BasketDto addBasketToModel() {
        return sessionBasket.getBasket();
    }

    @ModelAttribute("account")
    public String addAccountToModel() {
        return authenticatedUserLookup.getAuthenticatedUser()
                .map(AuthenticatedUser::username).orElse(null);
    }

    @PostMapping
    public String initiateCheckout(@ModelAttribute("address") @Valid AddressDto addressDto,
                                   BindingResult bindingResult,
                                   Model model) {
        logger.info("Received request to initiate checkout, delivery address: {}", addressDto);

        AuthenticatedUser user = authenticatedUserLookup.getAuthenticatedUser().orElseThrow(() -> {
            throw new IllegalStateException("Authenticated user expected");
        });

        if (bindingResult.hasErrors()) {
            logger.warn("Unable to proceed with checkout, validation failed: {}", bindingResult.getFieldErrors());
            addFeedbackToModel(addressDto, bindingResult, model);
            return "basket";
        }

        InitiatePaymentRequest initiatePaymentRequest = createInitiatePaymentRequest(sessionBasket.getBasket());
        InitiatePaymentResponse initiatePaymentResponse = paymentService.initiatePayment(initiatePaymentRequest);
        if (initiatePaymentResponse.isFailure()) {
            logger.warn("Unable to initiate payment: {}", initiatePaymentResponse.getFailureReason());
            return "basket";
        }

        String approvalLink = initiatePaymentResponse.getApprovalLink();
        logger.debug("Payment approval link {}", approvalLink);
        return "redirect:" + approvalLink;
    }

    @GetMapping("complete")
    public String completeCheckout(SessionStatus sessionStatus) {
        // TODO capture payment
        sessionStatus.setComplete();
        return "order-confirm";
    }

    private void addFeedbackToModel(AddressDto addressDto, BindingResult bindingResult, Model model) {
        AccountUpdateFeedbackDto feedbackDto = createAccountUpdateFeedbackDtoForValidationErrors(addressDto, bindingResult, messageProperties);
        model.addAttribute("feedback", feedbackDto);
    }
}
