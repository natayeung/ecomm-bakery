package com.natay.ecomm.bakery.registration;

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
    @Size(min=6, max=10)
    private String password;
    @NotBlank
    private String addressLine1;
    private String addressLine2;
    @Pattern(regexp = "^[A-Z]{1,2}\\d[A-Z\\d]? ?\\d[A-Z]{2}$")
    private String postcode;

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
