package com.natay.ecomm.bakery.checkout.payment;

import com.natay.ecomm.bakery.checkout.order.OrderDetails;
import com.natay.ecomm.bakery.checkout.payment.paypal.PayPalCheckoutException;
import com.natay.ecomm.bakery.checkout.payment.paypal.PayPalCheckoutPort;
import org.springframework.stereotype.Service;

import static com.natay.ecomm.bakery.checkout.payment.OrderDetailsFactory.createOrderDetails;
import static java.util.Objects.requireNonNull;

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
        requireNonNull(request, "Initial payment request must be specified");

        try {
            OrderDetails orderDetails = createOrderDetails(request);
            OrderCreated orderCreated = payPalCheckoutPort.createOrder(orderDetails);
            return InitiatePaymentResponse.of(orderCreated.externalOrderId(), orderDetails, orderCreated.approvalLink());
        } catch (PayPalCheckoutException ex) {
            String message = "Error initiating payment with PayPal: " + ex.getMessage();
            throw new InitiatePaymentFailedException(message, ex);
        }
    }

    @Override
    public CapturePaymentResponse capturePayment(CapturePaymentRequest request) throws CapturePaymentFailedException {
        requireNonNull(request, "Capture payment request must be specified");

        try {
            OrderCaptured orderCaptured = payPalCheckoutPort.captureOrder(request.externalOrderId());
            return CapturePaymentResponse.of(orderCaptured.externalOrderId());
        } catch (PayPalCheckoutException ex) {
            String message = "Error capturing payment with PayPal: " + ex.getMessage();
            throw new CapturePaymentFailedException(message, ex);
        }
    }
}
