package com.natay.ecomm.bakery.user.authentication;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * @author natayeung
 */
@ToString
@Getter
public class AutoLoginRequestEvent extends ApplicationEvent {

    private final UsernamePasswordAuthenticationToken authenticationToken;

    public AutoLoginRequestEvent(Object source, UsernamePasswordAuthenticationToken authenticationToken) {
        super(source);
        this.authenticationToken = authenticationToken;
    }
}