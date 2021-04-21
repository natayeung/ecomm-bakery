package com.natay.ecomm.bakery.security;


import com.natay.ecomm.bakery.account.AccountService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author natayeung
 */
@Component
public class AuthenticatedUserLookup {

    private final AccountService accountService;

    public AuthenticatedUserLookup(AccountService accountService) {
        this.accountService = accountService;
    }

    public Optional<AuthenticatedUser> getAuthenticatedUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .flatMap(a -> getAuthenticatedUser(a.getPrincipal()));
    }

    private Optional<AuthenticatedUser> getAuthenticatedUser(Object principal) {
        AuthenticatedUser.Builder userBuilder = AuthenticatedUser.builder();
        String email = null;
        if (principal instanceof UserDetails userDetails) {
            email = userDetails.getUsername();
            populateFirstNameAndLastNameIfPresent(userBuilder, email);
        } else if (principal instanceof OAuth2User oAuth2User) {
            email = oAuth2User.getAttribute("email");
            populateFirstNameAndLastNameIfPresent(userBuilder, oAuth2User);
        }
        return Optional.ofNullable(email).map(e -> userBuilder.withUsername(e).build());
    }

    private void populateFirstNameAndLastNameIfPresent(AuthenticatedUser.Builder userBuilder, String email) {
        accountService.findAccountByEmail(email)
                .ifPresent(a -> userBuilder.withFirstName(a.firstName()).withLastName(a.lastName()));
    }

    private void populateFirstNameAndLastNameIfPresent(AuthenticatedUser.Builder userBuilder, OAuth2User oAuth2User) {
        Optional.<String>ofNullable(oAuth2User.getAttribute("name"))
                .ifPresent(n -> {
                    String[] nameParts = n.split(" ");
                    if (nameParts.length > 0)
                        userBuilder.withFirstName(nameParts[0]);
                    if (nameParts.length > 1)
                        userBuilder.withLastName(nameParts[nameParts.length - 1]);
                });
    }
}
