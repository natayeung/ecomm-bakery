package com.natay.ecomm.bakery.user.dto;

public class RegistrationFeedbackDto {

    private String emailErrorMessage;

    private String lastInputAddressLine1;

    private String lastInputAddressLine2;

    private String lastInputPostcode;

    public String getEmailErrorMessage() {
        return emailErrorMessage;
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
