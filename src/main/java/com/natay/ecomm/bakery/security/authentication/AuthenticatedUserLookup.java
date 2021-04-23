package com.natay.ecomm.bakery.security.authentication;


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

    public Optional<UserIdentity> getAuthenticatedUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .flatMap(a -> getAuthenticatedUser(a.getPrincipal()));
    }

    private Optional<UserIdentity> getAuthenticatedUser(Object principal) {
        UserIdentity.UserIdentityBuilder userBuilder = UserIdentity.builder();
        String email = null;
        if (principal instanceof UserDetails userDetails) {
            email = userDetails.getUsername();
            populateFirstNameAndLastNameIfPresent(userBuilder, email);
        } else if (principal instanceof OAuth2User oAuth2User) {
            email = oAuth2User.getAttribute("email");
            populateFirstNameAndLastNameIfPresent(userBuilder, oAuth2User);
        }
        return Optional.ofNullable(email).map(e -> userBuilder.email(e).build());
    }

    private void populateFirstNameAndLastNameIfPresent(UserIdentity.UserIdentityBuilder userBuilder, String email) {
        accountService.findAccountByEmail(email)
                .ifPresent(a -> userBuilder.firstName(a.firstName()).lastName(a.lastName()));
    }

    private void populateFirstNameAndLastNameIfPresent(UserIdentity.UserIdentityBuilder userBuilder, OAuth2User oAuth2User) {
        Optional.<String>ofNullable(oAuth2User.getAttribute("name"))
                .ifPresent(n -> {
                    String[] nameParts = n.split(" ");
                    if (nameParts.length > 0)
                        userBuilder.firstName(nameParts[0]);
                    if (nameParts.length > 1)
                        userBuilder.lastName(nameParts[nameParts.length - 1]);
                });
    }
}
