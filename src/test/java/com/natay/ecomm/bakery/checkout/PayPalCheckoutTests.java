package com.natay.ecomm.bakery.checkout;

import com.natay.ecomm.bakery.account.Address;
import com.natay.ecomm.bakery.configuration.PayPalProperties;
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

import java.math.BigDecimal;
import java.util.List;

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
    public void initiatesPayPalPayment() {
        Address address = Address.builder().withAddressLine1("123 Middle Road").withPostcode("PO1 2ST").build();
        List<OrderDetails.Item> items = List.of(OrderDetails.Item.of("Black Forest Cake", 2, BigDecimal.valueOf(22.75)));
        BigDecimal amount = BigDecimal.valueOf(45.50);
        OrderDetails orderDetails = OrderDetails.builder().withItems(items).withShippingAddress(address).withAmount(amount).build();
        InitiatePaymentRequest request = InitiatePaymentRequest.with(orderDetails);

        InitiatePaymentResponse response = paymentService.initiatePayment(request);

        softly.assertThat(response.isSuccess()).isTrue();
        softly.assertThat(response.getOrderRef()).isNotBlank();
        softly.assertThat(response.getApprovalLink()).isNotBlank();
    }
}
