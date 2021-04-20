package com.natay.ecomm.bakery.checkout;

/**
 * @author natayeung
 */
public interface PaymentService {

    InitiatePaymentResponse initiatePayment(InitiatePaymentRequest request);
}
