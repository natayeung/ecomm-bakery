package com.natay.ecomm.bakery.checkout.payment.paypal;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author natayeung
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix="paypal")
@Getter
@Setter
public class PayPalProperties {

    private String clientId;
    private String clientSecret;
    private String brandName;
    private String currencyCode;
    private String countryCode;
    private String returnUrl;
    private String cancelUrl;
    private String landingPage;
    private String shippingPreference;
    private String userAction;
    private String checkoutPaymentIntent;
}
