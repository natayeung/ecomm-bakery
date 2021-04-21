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
    public InitiatePaymentResponse initiatePayment(InitiatePaymentRequest request) throws InitiatePaymentFailedException {
        String requestId = request.requestId();
        try {
            OrderCreated orderCreated = payPalCheckoutPort.createOrder(request.orderDetails());
            return InitiatePaymentResponse.of(requestId, orderCreated.externalOrderId(), orderCreated.approvalLink());
        } catch (PayPalCheckoutException ex) {
            String message = "Error initiating payment with PayPal: " + ex.getMessage() + " , requestId=" + requestId;
            throw new InitiatePaymentFailedException(message, ex);
        }
    }

    @Override
    public CapturePaymentResponse capturePayment(CapturePaymentRequest request) throws CapturePaymentFailedException {
        String requestId = request.requestId();
        try {
            OrderCaptured orderCaptured = payPalCheckoutPort.captureOrder(request.externalOrderId());
            return CapturePaymentResponse.of(requestId, orderCaptured.externalOrderId());
        } catch (PayPalCheckoutException ex) {
            String message = "Error capturing payment with PayPal: " + ex.getMessage() + " , requestId=" + requestId;
            throw new CapturePaymentFailedException(message, ex);
        }
    }
}
