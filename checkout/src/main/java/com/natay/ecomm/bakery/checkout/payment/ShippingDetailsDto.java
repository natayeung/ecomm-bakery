package com.natay.ecomm.bakery.checkout.payment;

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
public class ShippingDetailsDto {

    @Email
    private String contactEmail;
    @NotBlank
    private String shippingFirstName;
    @NotBlank
    private String shippingLastName;
    @NotBlank
    private String addressLine1;
    private String addressLine2;
    @NotBlank
    private String townOrCity;
    @Pattern(regexp = "^[A-Z]{1,2}\\d[A-Z\\d]? ?\\d[A-Z]{2}$")
    private String postcode;
}
