package com.natay.ecomm.bakery.checkout;

import com.natay.ecomm.bakery.security.authentication.UserIdentity;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author natayeung
 */
@Data
@Builder
@Accessors(fluent = true)
public class CustomerDetails implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String email;
    private final String firstName;
    private final String lastName;

    public static CustomerDetails from(UserIdentity userIdentity) {
        return CustomerDetails.builder()
                .email(userIdentity.email())
                .firstName(userIdentity.firstName())
                .lastName(userIdentity.lastName())
                .build();
    }
}
