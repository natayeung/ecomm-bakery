package com.natay.ecomm.bakery.checkout;

import com.natay.ecomm.bakery.basket.BasketDto;
import com.natay.ecomm.bakery.basket.SessionBasket;
import com.natay.ecomm.bakery.checkout.payment.*;
import com.natay.ecomm.bakery.configuration.MessageProperties;
import com.natay.ecomm.bakery.security.authentication.AuthenticatedUser;
import com.natay.ecomm.bakery.security.authentication.AuthenticatedUserLookup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

import static com.natay.ecomm.bakery.checkout.CheckoutFeedbackDtoFactory.createCheckoutFeedbackDtoForValidationErrors;
import static com.natay.ecomm.bakery.checkout.payment.InitiatePaymentRequestFactory.createInitiatePaymentRequest;

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
    public String initiateCheckout(@ModelAttribute("shippingDetails") @Valid ShippingDetailsDto shippingDetailsDto,
                                   BindingResult bindingResult,
                                   Model model)
            throws InitiatePaymentFailedException {
        logger.info("Received request to initiate checkout, delivery address: {}", shippingDetailsDto);

        authenticatedUserLookup.getAuthenticatedUser().orElseThrow(() -> {
            throw new IllegalStateException("Authenticated user expected");
        });

        if (bindingResult.hasErrors()) {
            logger.warn("Unable to proceed with checkout, validation failed: {}", bindingResult.getFieldErrors());
            addFeedbackToModel(shippingDetailsDto, bindingResult, model);
            return "basket";
        }

        InitiatePaymentRequest initiatePaymentRequest = createInitiatePaymentRequest(sessionBasket.getBasket(), shippingDetailsDto);
        InitiatePaymentResponse initiatePaymentResponse = paymentService.initiatePayment(initiatePaymentRequest);

        sessionBasket.addShippingDetails(shippingDetailsDto);

        String approvalLink = initiatePaymentResponse.approvalLink();
        logger.debug("Redirecting to payment approval link {}", approvalLink);
        return "redirect:" + approvalLink;
    }

    @GetMapping("/complete")
    public String completeCheckout(@RequestParam(name = "token") String externalOrderId,
                                   SessionStatus sessionStatus)
            throws CapturePaymentFailedException {
        logger.info("Received request to complete checkout, externalOrderId: {}", externalOrderId);

        CapturePaymentRequest capturePaymentRequest = CapturePaymentRequest.of(externalOrderId);
        CapturePaymentResponse capturePaymentResponse = paymentService.capturePayment(capturePaymentRequest);

        processOrder(capturePaymentResponse);
        sessionStatus.setComplete();

        return "order-confirm";
    }

    @GetMapping("/cancel")
    public String cancelCheckout() {
        logger.debug("Received request to cancel checkout");
        return "redirect:/basket";
    }

    private void addFeedbackToModel(ShippingDetailsDto shippingDetailsDto,
                                    BindingResult bindingResult,
                                    Model model) {
        CheckoutFeedbackDto feedbackDto = createCheckoutFeedbackDtoForValidationErrors(shippingDetailsDto, bindingResult, messageProperties);
        model.addAttribute("feedback", feedbackDto);
    }

    private void processOrder(CapturePaymentResponse capturePaymentResponse) {
        String orderId = capturePaymentResponse.externalOrderId();
        ShippingDetailsDto shippingDetails = sessionBasket.getShippingDetails().orElseThrow(() -> {
            throw new IllegalStateException("Shipping details expected");
        });
        BasketDto basket = sessionBasket.getBasket();
        logger.info("orderId={}, shippingDetails={}, basket={}", orderId, shippingDetails, basket);
    }
}
