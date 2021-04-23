package com.natay.ecomm.bakery.checkout.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author natayeung
 */
@Data
public class ShippingDetailsDto {

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