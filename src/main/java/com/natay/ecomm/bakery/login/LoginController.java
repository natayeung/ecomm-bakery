package com.natay.ecomm.bakery.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/error")
    public String showLoginFormWithError(@RequestParam("cause") String exceptionName,
                                         ModelMap model) {

        logger.info("Login failed with {}", exceptionName);

        if (BadCredentialsException.class.getSimpleName().equals(exceptionName)) {
            model.put("feedback", "We didn't recognise your details. Please check your email and password.");
        } else {
            model.put("feedback", "Login failed. Please try again.");
        }

        return "login";
    }
}
