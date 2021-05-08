package com.natay.ecomm.bakery.user.account;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author natayeung
 */
@Data
@Accessors(chain = true)
public class AccountDto {

    @Email
    private String email;
    private String firstName;
    private String lastName;
    @NotBlank
    private String addressLine1;
    private String addressLine2;
    @NotBlank
    private String townOrCity;
    @Pattern(regexp = "^[A-Za-z]{1,2}\\d[A-Za-z\\d]? ?\\d[A-Za-z]{2}$")
    private String postcode;
}
