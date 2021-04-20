package com.natay.ecomm.bakery.checkout;

import com.natay.ecomm.bakery.configuration.PayPalProperties;
import com.paypal.core.PayPalEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author natayeung
 */
@Configuration
@Profile(value = "prod")
public class PayPalLiveConfiguration {

    private final PayPalProperties payPalProperties;

    public PayPalLiveConfiguration(PayPalProperties payPalProperties) {
        this.payPalProperties = payPalProperties;
    }

    @Bean
    public PayPalEnvironment getPayPalEnvironment() {
        return new PayPalEnvironment.Live(payPalProperties.getClientId(), payPalProperties.getClientSecret());
    }
}
