package com.natay.ecomm.bakery.checkout.payment.paypal;

import com.paypal.core.PayPalEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author natayeung
 */
@Configuration
@Profile(value = {"test", "dev"})
public class PayPalSandboxConfiguration {

    private final PayPalProperties payPalProperties;

    public PayPalSandboxConfiguration(PayPalProperties payPalProperties) {
        this.payPalProperties = payPalProperties;
    }

    @Bean
    public PayPalEnvironment getPayPalEnvironment() {
        return new PayPalEnvironment.Sandbox(payPalProperties.getClientId(), payPalProperties.getClientSecret());
    }
}
