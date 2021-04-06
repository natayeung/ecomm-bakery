package com.natay.ecomm.bakery.user;

import com.natay.ecomm.bakery.user.dto.RegistrationDto;
import com.natay.ecomm.bakery.user.dto.RegistrationFeedbackDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import static com.natay.ecomm.bakery.session.SessionAttributeLookup.getBasket;
import static com.natay.ecomm.bakery.user.dto.RegistrationFeedbackDtoFactory.createRegistrationFeedbackDtoForEmailAlreadyInUse;

/**
 * @author natayeung
 */
@Controller
@RequestMapping("/register")
public class UserRegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(UserRegistrationController.class);

    private final RegistrationService registrationService;

    public UserRegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public String showRegistrationForm(HttpSession session,
                                       ModelMap model) {

        getBasket(session).ifPresent((b) -> model.addAttribute("userBasket", b));

        return "register";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("registration") RegistrationDto registrationDto,
                               HttpSession session,
                               ModelMap model) {

        logger.info("Received request to register user {}", registrationDto);

        try {
            User registeredUser = registrationService.register(registrationDto);
            session.setAttribute("user", registeredUser);

        } catch (EmailAlreadyUsedException ex) {
            RegistrationFeedbackDto feedbackDto = createRegistrationFeedbackDtoForEmailAlreadyInUse(registrationDto);
            model.put("registrationFeedback", feedbackDto);

            return "register";
        }

        return "redirect:/";
    }
}
