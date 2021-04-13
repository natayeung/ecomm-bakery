package com.natay.ecomm.bakery.security;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Objects.isNull;

/**
 * @author natayeung
 */
@Component
public class AuthenticatedUserLookup {

    public Optional<AuthenticatedUser> getAuthenticatedUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .flatMap(a -> getAuthenticatedUser(a.getPrincipal()));
    }

    private Optional<AuthenticatedUser> getAuthenticatedUser(Object principal) {
        String email = null;
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else if (principal instanceof OAuth2User) {
            email = ((OAuth2User) principal).getAttribute("email");
        }

        return isNull(email) ? Optional.empty() : Optional.of(AuthenticatedUser.of(email));
    }
}
