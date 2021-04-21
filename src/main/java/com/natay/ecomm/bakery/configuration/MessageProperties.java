package com.natay.ecomm.bakery.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix="message")
@Getter
@Setter
public class MessageProperties {

    private String badCredentials;
    private String loginFailed;
    private String emailInUse;
    private String invalidEmail;
    private String invalidFirstName;
    private String invalidLastName;
    private String invalidAddressLine1;
    private String invalidTownOrCity;
    private String invalidPostcode;
    private String invalidPassword;
    private String accountUpdated;
}
