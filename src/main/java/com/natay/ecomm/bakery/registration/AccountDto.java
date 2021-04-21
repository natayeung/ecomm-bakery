package com.natay.ecomm.bakery.registration;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author natayeung
 */
@Data
public class AccountDto {

    @NotBlank
    private String email;
    @NotBlank
    private String addressLine1;
    private String addressLine2;
    @NotBlank
    private String townOrCity;
    @Pattern(regexp = "^[A-Za-z]{1,2}\\d[A-Za-z\\d]? ?\\d[A-Za-z]{2}$")
    private String postcode;
}
