package com.natay.ecomm.bakery.error;

import com.natay.ecomm.bakery.catalog.ProductAccessException;
import com.natay.ecomm.bakery.catalog.ProductNotFoundException;
import com.natay.ecomm.bakery.checkout.payment.CapturePaymentFailedException;
import com.natay.ecomm.bakery.checkout.payment.InitiatePaymentFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author natayeung
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ProductAccessException.class)
    public String handleProductAccessException(ProductAccessException ex) {
        logger.warn("Failed to access products: {}", ex.getMessage(), ex);
        return "error";
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public String handleProductNotFoundException(ProductNotFoundException ex) {
        logger.warn("Failed to find product: {}", ex.getMessage(), ex);
        return "error";
    }

    @ExceptionHandler(InitiatePaymentFailedException.class)
    public String handleInitiatePaymentFailedException(InitiatePaymentFailedException ex) {
        logger.warn("Failed to initiate checkout: {}", ex.getMessage(), ex);
        return "redirect:/error";
    }

    @ExceptionHandler(CapturePaymentFailedException.class)
    public String handleCapturePaymentFailedException(CapturePaymentFailedException ex) {
        logger.warn("Failed to complete checkout: {}", ex.getMessage(), ex);
        return "redirect:/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex) {
        logger.error("Unexpected error", ex);
        return "error";
    }
}
