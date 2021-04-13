package com.natay.ecomm.bakery.registration;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author natayeung
 */
public class RegistrationDto {

    @NotBlank
    @Email
    private String email;
    @Size(min=6, max=10)
    private String password;
    @NotBlank
    private String addressLine1;
    private String addressLine2;
    @Pattern(regexp = "^[A-Z]{1,2}\\d[A-Z\\d]? ?\\d[A-Z]{2}$")
    private String postcode;

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

    public String getPostcode() {
        return postcode;
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

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Override
    public String toString() {
        return "RegistrationDto{" +
                "email='" + email + '\'' +
                ", password='" + password.replaceAll("\\.", "*") + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", postcode='" + postcode + '\'' +
                '}';
    }
}
