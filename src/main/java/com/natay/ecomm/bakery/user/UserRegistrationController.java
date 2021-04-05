package com.natay.ecomm.bakery.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.natay.ecomm.bakery.session.SessionAttributeLookup.getItemCount;

@Controller
@RequestMapping("/user/register")
public class UserRegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(UserRegistrationController.class);

    private final RegistrationService registrationService;

    public UserRegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public String showRegistrationForm(HttpSession session,
                                       ModelMap model) {

        model.addAttribute("basketItemCount", getItemCount(session));
        model.addAttribute("registration", new RegistrationDto());

        return "register";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("registration") RegistrationDto registrationDto,
                               HttpSession session,
                               ModelMap model) {
        logger.info("Received request to register user {}", registrationDto);

        User registeredUser = registrationService.register(registrationDto);

        session.setAttribute("user", registeredUser);
        session.setAttribute("yetToLogin", false);

        return "redirect:/";
    }
}
