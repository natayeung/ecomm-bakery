package com.natay.ecomm.bakery.security.authentication;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import static com.natay.ecomm.bakery.utils.Arguments.requireNonBlank;

/**
 * @author natayeung
 */
@Data
@Builder
@Accessors(fluent = true)
public class UserIdentity {
    private final String email;
    private final String firstName;
    private final String lastName;

    private UserIdentity(String email, String firstName, String lastName) {
        this.email = requireNonBlank(email, "Email cannot be blank");
        this.firstName = requireNonBlank(firstName, "First name cannot be blank");
        this.lastName = requireNonBlank(lastName, "Last name cannot be blank");
    }

    public String username() {
        return email;
    }
}
