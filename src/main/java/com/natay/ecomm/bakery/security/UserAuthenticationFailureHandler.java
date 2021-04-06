package com.natay.ecomm.bakery.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class UserAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest servletRequest,
                                        HttpServletResponse servletResponse,
                                        AuthenticationException exception)
            throws IOException {

        String exceptionName = exception.getClass().getSimpleName();
        String exceptionMessage = exception.getMessage();

        logger.info("Authentication failed, {}: {}", exceptionName, exceptionMessage);

        servletResponse.sendRedirect("/login/error?cause=" + exceptionName);
    }
}
