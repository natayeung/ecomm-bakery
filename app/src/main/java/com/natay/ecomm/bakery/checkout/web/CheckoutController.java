package com.natay.ecomm.bakery.checkout.web;

import com.natay.ecomm.bakery.checkout.CheckoutFeedbackDto;
import com.natay.ecomm.bakery.checkout.order.OrderCache;
import com.natay.ecomm.bakery.checkout.order.OrderDetails;
import com.natay.ecomm.bakery.checkout.order.OrderNotFoundException;
import com.natay.ecomm.bakery.checkout.order.OrderReceivedEvent;
import com.natay.ecomm.bakery.checkout.payment.*;
import com.natay.ecomm.bakery.common.MessageProperties;
import com.natay.ecomm.bakery.product.basket.BasketDto;
import com.natay.ecomm.bakery.product.basket.SessionBasket;
import com.natay.ecomm.bakery.user.authentication.AuthenticatedUserLookup;
import com.natay.ecomm.bakery.user.authentication.UserIdentity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

import static com.natay.ecomm.bakery.checkout.CheckoutFeedbackDtoFactory.createCheckoutFeedbackDtoForValidationErrors;

/**
 * @author natayeung
 */
@Controller
@RequestMapping("/checkout")
@SessionAttributes("scopedTarget.sessionBasket")
@Slf4j
public class CheckoutController implements ApplicationEventPublisherAware {

    private final AuthenticatedUserLookup authenticatedUserLookup;
    private final SessionBasket sessionBasket;
    private final PaymentService paymentService;
    private final OrderCache orderCache;
    private final MessageProperties messageProperties;
    private ApplicationEventPublisher eventPublisher;

    public CheckoutController(AuthenticatedUserLookup authenticatedUserLookup,
                              SessionBasket sessionBasket,
                              PaymentService paymentService,
                              OrderCache orderCache,
                              MessageProperties messageProperties) {
        this.authenticatedUserLookup = authenticatedUserLookup;
        this.sessionBasket = sessionBasket;
        this.paymentService = paymentService;
        this.orderCache = orderCache;
        this.messageProperties = messageProperties;
    }

    @ModelAttribute("basket")
    public BasketDto addBasketToModel() {
        return sessionBasket.getBasket();
    }

    @ModelAttribute("account")
    public String addAccountToModel() {
        return authenticatedUserLookup.getAuthenticatedUser()
                .map(UserIdentity::username).orElse(null);
    }

    @PostMapping
    public String initiateCheckout(@ModelAttribute("shippingDetails") @Valid ShippingDetailsDto shippingDetailsDto,
                                   BindingResult bindingResult,
                                   Model model)
            throws InitiatePaymentFailedException {
        log.info("Received request to initiate checkout, delivery address: {}", shippingDetailsDto);

        UserIdentity user = authenticatedUserLookup.getAuthenticatedUser().orElseThrow(() -> {
            throw new IllegalStateException("Authenticated user expected");
        });

        if (bindingResult.hasErrors()) {
            log.warn("Unable to proceed with checkout, validation failed: {}", bindingResult.getFieldErrors());
            addFeedbackToModel(shippingDetailsDto, bindingResult, model);
            return "basket";
        }

        InitiatePaymentRequest initiatePaymentRequest = InitiatePaymentRequest.of(user, sessionBasket.getBasket(), shippingDetailsDto);
        InitiatePaymentResponse initiatePaymentResponse = paymentService.initiatePayment(initiatePaymentRequest);

        orderCache.put(initiatePaymentResponse.externalOrderId(), initiatePaymentResponse.orderDetails());

        String approvalLink = initiatePaymentResponse.approvalLink();
        log.debug("Redirecting to payment approval link {}", approvalLink);
        return "redirect:" + approvalLink;
    }

    @GetMapping("/complete")
    public String completeCheckout(@RequestParam(name = "token") String externalOrderId,
                                   SessionStatus sessionStatus)
            throws CapturePaymentFailedException {
        log.info("Received request to complete checkout, externalOrderId: {}", externalOrderId);

        CapturePaymentRequest capturePaymentRequest = CapturePaymentRequest.of(externalOrderId);
        CapturePaymentResponse capturePaymentResponse = paymentService.capturePayment(capturePaymentRequest);

        publishOrderReceivedEvent(capturePaymentResponse.externalOrderId());
        sessionStatus.setComplete();

        return "order-confirm";
    }

    @GetMapping("/cancel")
    public String cancelCheckout() {
        log.debug("Received request to cancel checkout");
        return "redirect:/basket";
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        eventPublisher = applicationEventPublisher;
    }

    private void addFeedbackToModel(ShippingDetailsDto shippingDetailsDto,
                                    BindingResult bindingResult,
                                    Model model) {
        CheckoutFeedbackDto feedbackDto = createCheckoutFeedbackDtoForValidationErrors(shippingDetailsDto, bindingResult, messageProperties);
        model.addAttribute("feedback", feedbackDto);
    }

    private void publishOrderReceivedEvent(String orderId) {
        OrderDetails orderDetails = orderCache.get(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found, orderId=" + orderId));
        OrderReceivedEvent orderReceivedEvent = new OrderReceivedEvent(this, orderDetails);
        eventPublisher.publishEvent(orderReceivedEvent);
        log.info("Published {}", orderReceivedEvent);
    }
}
