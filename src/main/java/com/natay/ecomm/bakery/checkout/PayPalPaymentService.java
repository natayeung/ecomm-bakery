package com.natay.ecomm.bakery.checkout;

import org.springframework.stereotype.Service;


/**
 * @author natayeung
 */
@Service
public class PayPalPaymentService implements PaymentService {

    private final PayPalCheckoutPort payPalCheckoutPort;

    public PayPalPaymentService(PayPalCheckoutPort payPalCheckoutPort) {
        this.payPalCheckoutPort = payPalCheckoutPort;
    }

    @Override
    public InitiatePaymentResponse initiatePayment(InitiatePaymentRequest request) {
        try {
            OrderCreated orderCreated = payPalCheckoutPort.createOrder(request.getOrder());
            return InitiatePaymentResponse.success(orderCreated.orderId(), orderCreated.approvalLink());
        } catch (PayPalCheckoutException ex) {
            return InitiatePaymentResponse.failure("Error initiating payment with PayPal: " + ex.getMessage());
        }
    }
}
