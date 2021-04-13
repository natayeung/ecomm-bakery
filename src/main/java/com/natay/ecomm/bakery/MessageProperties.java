package com.natay.ecomm.bakery;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix="message")
public class MessageProperties {

    private String badCredentials;
    private String loginFailed;
    private String emailInUse;
    private String invalidEmail;
    private String invalidAddressLine1;
    private String invalidPostcode;
    private String invalidPassword;

    public String getBadCredentials() {
        return badCredentials;
    }

    public String getLoginFailed() {
        return loginFailed;
    }

    public String getEmailInUse() {
        return emailInUse;
    }

    public String getInvalidEmail() {
        return invalidEmail;
    }

    public String getInvalidAddressLine1() {
        return invalidAddressLine1;
    }

    public String getInvalidPostcode() {
        return invalidPostcode;
    }

    public String getInvalidPassword() {
        return invalidPassword;
    }

    public void setBadCredentials(String badCredentials) {
        this.badCredentials = badCredentials;
    }

    public void setLoginFailed(String loginFailed) {
        this.loginFailed = loginFailed;
    }

    public void setEmailInUse(String emailInUse) {
        this.emailInUse = emailInUse;
    }

    public void setInvalidEmail(String invalidEmail) {
        this.invalidEmail = invalidEmail;
    }

    public void setInvalidAddressLine1(String invalidAddressLine1) {
        this.invalidAddressLine1 = invalidAddressLine1;
    }

    public void setInvalidPostcode(String invalidPostcode) {
        this.invalidPostcode = invalidPostcode;
    }

    public void setInvalidPassword(String invalidPassword) {
        this.invalidPassword = invalidPassword;
    }
}
