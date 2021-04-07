package com.natay.ecomm.bakery.registration;

import com.natay.ecomm.bakery.account.EmailAlreadyUsedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.natay.ecomm.bakery.session.SessionAttributeLookup.getBasket;
import static com.natay.ecomm.bakery.registration.RegistrationFeedbackDtoFactory.createRegistrationFeedbackDtoForEmailAlreadyInUse;

/**
 * @author natayeung
 */
@Controller
@RequestMapping("/register")
public class UserRegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(UserRegistrationController.class);

    private final RegistrationService registrationService;
    private final AuthenticationManager authenticationManager;

    public UserRegistrationController(RegistrationService registrationService, AuthenticationManager authenticationManager) {
        this.registrationService = registrationService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping
    public String showRegistrationForm(HttpSession session,
                                       ModelMap model) {

        getBasket(session).ifPresent((b) -> model.addAttribute("userBasket", b));

        return "register";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("registration") RegistrationDto registrationDto,
                               HttpServletRequest request,
                               ModelMap model) {

        logger.info("Received request to register user {}", registrationDto);

        try {
            registrationService.register(registrationDto);

            authenticate(request, registrationDto.getEmail(), registrationDto.getPassword());

        } catch (EmailAlreadyUsedException ex) {
            RegistrationFeedbackDto feedbackDto = createRegistrationFeedbackDtoForEmailAlreadyInUse(registrationDto);
            model.put("registrationFeedback", feedbackDto);

            return "register";
        }

        return "redirect:/";
    }

    private void authenticate(HttpServletRequest request, String username, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        authToken.setDetails(new WebAuthenticationDetails(request));

        Authentication authentication = authenticationManager.authenticate(authToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
