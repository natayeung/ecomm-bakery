package com.natay.ecomm.bakery.user.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author natayeung
 */
@Component
@Slf4j
class AutoLoginHandler implements ApplicationListener<AutoLoginRequestEvent> {

    private final AuthenticationManager authenticationManager;

    public AutoLoginHandler(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void onApplicationEvent(AutoLoginRequestEvent autoLoginRequestEvent) {
        UsernamePasswordAuthenticationToken authenticationToken = autoLoginRequestEvent.getAuthenticationToken();
        log.info("Received request to log {} in", authenticationToken.getPrincipal());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
