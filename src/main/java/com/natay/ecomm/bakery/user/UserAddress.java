package com.natay.ecomm.bakery.user;

public class UserAddress {

    private String addressLine1;
    private String addressLine2;
    private String postCode;

    public UserAddress(String addressLine1, String addressLine2, String postCode) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.postCode = postCode;
    }

    public String addressLine1() {
        return addressLine1;
    }

    public String addressLine2() {
        return addressLine2;
    }

    public String postCode() {
        return postCode;
    }
}
