package com.natay.ecomm.bakery.security.authentication;

import com.natay.ecomm.bakery.common.MessageProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author natayeung
 */
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

    private final MessageProperties messageProperties;

    public LoginController(MessageProperties messageProperties) {
        this.messageProperties = messageProperties;
    }

    @GetMapping
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/error")
    public String showLoginFormWithError(@RequestParam(name = "cause") String exceptionName,
                                         ModelMap model) {

        log.info("Login failed with {}", exceptionName);

        if (BadCredentialsException.class.getSimpleName().equals(exceptionName)) {
            model.put("feedbackMessage", messageProperties.getBadCredentials());
        } else {
            model.put("feedbackMessage", messageProperties.getLoginFailed());
        }

        return "login";
    }
}
