package com.natay.ecomm.bakery.common.web;

import com.natay.ecomm.bakery.checkout.order.OrderNotFoundException;
import com.natay.ecomm.bakery.checkout.payment.CapturePaymentFailedException;
import com.natay.ecomm.bakery.checkout.payment.InitiatePaymentFailedException;
import com.natay.ecomm.bakery.product.catalog.ProductAccessException;
import com.natay.ecomm.bakery.product.catalog.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author natayeung
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductAccessException.class)
    public String handleProductAccessException(ProductAccessException ex) {
        log.warn("Failed to access products: {}", ex.getMessage(), ex);
        return "error";
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public String handleProductNotFoundException(ProductNotFoundException ex) {
        log.warn("Failed to find product: {}", ex.getMessage(), ex);
        return "error";
    }

    @ExceptionHandler(InitiatePaymentFailedException.class)
    public String handleInitiatePaymentFailedException(InitiatePaymentFailedException ex) {
        log.warn("Failed to initiate checkout: {}", ex.getMessage(), ex);
        return "redirect:/error";
    }

    @ExceptionHandler(CapturePaymentFailedException.class)
    public String handleCapturePaymentFailedException(CapturePaymentFailedException ex) {
        log.warn("Failed to complete checkout: {}", ex.getMessage(), ex);
        return "redirect:/error";
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public String handleOrderNotFoundException(OrderNotFoundException ex) {
        log.warn("Failed to process order: {}", ex.getMessage(), ex);
        return "redirect:/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex) {
        log.error("Unexpected error", ex);
        return "error";
    }
}
