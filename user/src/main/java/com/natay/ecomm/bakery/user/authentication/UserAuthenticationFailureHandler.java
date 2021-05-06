package com.natay.ecomm.bakery.user.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author natayeung
 */
@Component
@Slf4j
class UserAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest servletRequest,
                                        HttpServletResponse servletResponse,
                                        AuthenticationException exception)
            throws IOException {

        String exceptionName = exception.getClass().getSimpleName();
        String exceptionMessage = exception.getMessage();

        log.info("Authentication failed, {}: {}", exceptionName, exceptionMessage);

        servletResponse.sendRedirect("/login/error?cause=" + exceptionName);
    }
}
