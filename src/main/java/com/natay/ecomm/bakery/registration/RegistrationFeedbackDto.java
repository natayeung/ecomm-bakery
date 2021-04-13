package com.natay.ecomm.bakery.registration;

public class RegistrationFeedbackDto {

    private String emailErrorMessage;

    private String passwordErrorMessage;

    private String addressLine1ErrorMessage;

    private String postcodeErrorMessage;

    private String lastInputEmail;

    private String lastInputAddressLine1;

    private String lastInputAddressLine2;

    private String lastInputPostcode;

    public String getEmailErrorMessage() {
        return emailErrorMessage;
    }

    public String getPasswordErrorMessage() {
        return passwordErrorMessage;
    }

    public String getAddressLine1ErrorMessage() {
        return addressLine1ErrorMessage;
    }

    public String getPostcodeErrorMessage() {
        return postcodeErrorMessage;
    }

    public String getLastInputEmail() {
        return lastInputEmail;
    }

    public String getLastInputAddressLine1() {
        return lastInputAddressLine1;
    }

    public String getLastInputAddressLine2() {
        return lastInputAddressLine2;
    }

    public String getLastInputPostcode() {
        return lastInputPostcode;
    }

    public void setEmailErrorMessage(String emailErrorMessage) {
        this.emailErrorMessage = emailErrorMessage;
    }

    public void setPasswordErrorMessage(String passwordErrorMessage) {
        this.passwordErrorMessage = passwordErrorMessage;
    }

    public void setAddressLine1ErrorMessage(String addressLine1ErrorMessage) {
        this.addressLine1ErrorMessage = addressLine1ErrorMessage;
    }

    public void setPostcodeErrorMessage(String postcodeErrorMessage) {
        this.postcodeErrorMessage = postcodeErrorMessage;
    }

    public void setLastInputEmail(String lastInputEmail) {
        this.lastInputEmail = lastInputEmail;
    }

    public void setLastInputAddressLine1(String lastInputAddressLine1) {
        this.lastInputAddressLine1 = lastInputAddressLine1;
    }

    public void setLastInputAddressLine2(String lastInputAddressLine2) {
        this.lastInputAddressLine2 = lastInputAddressLine2;
    }

    public void setLastInputPostcode(String lastInputPostcode) {
        this.lastInputPostcode = lastInputPostcode;
    }
}
