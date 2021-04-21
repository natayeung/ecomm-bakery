package com.natay.ecomm.bakery.checkout;

import com.natay.ecomm.bakery.checkout.payment.*;
import com.natay.ecomm.bakery.checkout.payment.paypal.PayPalCheckoutPort;
import com.natay.ecomm.bakery.checkout.payment.paypal.PayPalCheckoutRestAdapter;
import com.natay.ecomm.bakery.checkout.payment.paypal.PayPalProperties;
import com.natay.ecomm.bakery.checkout.payment.paypal.PayPalSandboxConfiguration;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.natay.ecomm.bakery.testutils.OrderDetailsFactory.createOrderDetails;

/**
 * @author natayeung
 */
@SpringBootTest(classes = {PayPalProperties.class, PayPalSandboxConfiguration.class, PayPalCheckoutRestAdapter.class})
@EnableEncryptableProperties
@ActiveProfiles("dev")
@ExtendWith(SoftAssertionsExtension.class)
public class PayPalCheckoutTests {

    @InjectSoftAssertions
    private SoftAssertions softly;

    @Autowired
    private PayPalCheckoutPort payPalCheckoutPort;

    private PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        paymentService = new PayPalPaymentService(payPalCheckoutPort);
    }

    @Test
    public void initiatesPayPalPayment() throws InitiatePaymentFailedException {
        OrderDetails orderDetails = createOrderDetails();
        InitiatePaymentRequest request = InitiatePaymentRequest.of(orderDetails);

        InitiatePaymentResponse response = paymentService.initiatePayment(request);

        softly.assertThat(response.externalOrderId()).isNotBlank();
        softly.assertThat(response.approvalLink()).isNotBlank();
    }
}
