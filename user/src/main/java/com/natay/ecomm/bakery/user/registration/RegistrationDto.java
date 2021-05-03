package com.natay.ecomm.bakery.user.registration;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author natayeung
 */
@Data
public class RegistrationDto {

    @NotBlank
    @Email
    private String email;
    @Size(min = 6, max = 10)
    private String password;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String addressLine1;
    private String addressLine2;
    @NotBlank
    private String townOrCity;
    @Pattern(regexp = "^[A-Z]{1,2}\\d[A-Z\\d]? ?\\d[A-Z]{2}$")
    private String postcode;

    @Override
    public String toString() {
        return "RegistrationDto{" +
                "email='" + email + '\'' +
                ", password='" + password.replaceAll("\\.", "*") + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", townOrCity='" + townOrCity + '\'' +
                ", postcode='" + postcode + '\'' +
                '}';
    }
}
