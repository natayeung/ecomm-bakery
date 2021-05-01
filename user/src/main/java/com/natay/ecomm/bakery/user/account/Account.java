package com.natay.ecomm.bakery.user.account;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import static com.natay.ecomm.bakery.common.Arguments.requireNonBlank;

/**
 * @author natayeung
 */
@Data
@Builder
@Accessors(fluent = true)
public class Account {

    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;

    private Account(String email, String password, String firstName, String lastName) {
        this.email = requireNonBlank(email, "Email cannot be blank");
        this.password = requireNonBlank(password, "Password cannot be blank");
        this.firstName = requireNonBlank(firstName, "First name cannot be blank");
        this.lastName = requireNonBlank(lastName, "Last name cannot be blank");
    }
}
