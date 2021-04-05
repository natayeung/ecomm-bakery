package com.natay.ecomm.bakery.user;

public class RegistrationDto {

    private String email;

    private String password;

    private String addressLine1;

    private String addressLine2;

    private String postCode;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Override
    public String toString() {
        return "RegistrationDto{" +
                "email='" + email + '\'' +
                ", password='" + password.replaceAll(".", "*") + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", postCode='" + postCode + '\'' +
                '}';
    }
}
