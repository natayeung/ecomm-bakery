package com.natay.ecomm.bakery.checkout.payment;

/**
 * @author natayeung
 */
public interface PaymentService {

    InitiatePaymentResponse initiatePayment(InitiatePaymentRequest request) throws InitiatePaymentFailedException;

    CapturePaymentResponse capturePayment(CapturePaymentRequest request) throws CapturePaymentFailedException;
}
