package com.natay.ecomm.bakery.user;

public class UserAddress {

    private String addressLine1;
    private String addressLine2;
    private String postcode;

    public UserAddress(String addressLine1, String addressLine2, String postcode) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.postcode = postcode;
    }

    public String addressLine1() {
        return addressLine1;
    }

    public String addressLine2() {
        return addressLine2;
    }

    public String postcode() {
        return postcode;
    }

    @Override
    public String toString() {
        return "UserAddress{" +
                "addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", postcode='" + postcode + '\'' +
                '}';
    }
}
