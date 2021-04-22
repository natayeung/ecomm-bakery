package com.natay.ecomm.bakery.checkout;

import com.natay.ecomm.bakery.security.authentication.UserIdentity;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author natayeung
 */
@ToString
public class CustomerDetails implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String email;
    private final String firstName;
    private final String lastName;

    private CustomerDetails(Builder builder) {
        email = builder.email;
        firstName = builder.firstName;
        lastName = builder.lastName;
    }

    public static CustomerDetails from(UserIdentity userIdentity) {
        return CustomerDetails.builder()
                .withEmail(userIdentity.email())
                .withFirstName(userIdentity.firstName())
                .withLastName(userIdentity.lastName())
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String email;
        private String firstName;
        private String lastName;

        private Builder() {
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public CustomerDetails build() {
            return new CustomerDetails(this);
        }
    }
}
