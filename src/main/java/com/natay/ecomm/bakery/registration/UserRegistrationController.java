package com.natay.ecomm.bakery.registration;

import com.natay.ecomm.bakery.configuration.MessageProperties;
import com.natay.ecomm.bakery.account.EmailAlreadyUsedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.natay.ecomm.bakery.registration.RegistrationFeedbackDtoFactory.createRegistrationFeedbackDtoForEmailAlreadyInUse;
import static com.natay.ecomm.bakery.registration.RegistrationFeedbackDtoFactory.createRegistrationFeedbackDtoForValidationErrors;

/**
 * @author natayeung
 */
@Controller
@RequestMapping("/register")
public class UserRegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(UserRegistrationController.class);

    private final RegistrationService registrationService;
    private final AuthenticationManager authenticationManager;
    private final MessageProperties messageProperties;

    public UserRegistrationController(RegistrationService registrationService,
                                      AuthenticationManager authenticationManager,
                                      MessageProperties messageProperties) {
        this.registrationService = registrationService;
        this.authenticationManager = authenticationManager;
        this.messageProperties = messageProperties;
    }

    @GetMapping
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("registration") @Valid RegistrationDto registrationDto,
                               BindingResult bindingResult,
                               HttpServletRequest request,
                               Model model) {
        logger.info("Received request to register user {}", registrationDto);

        if (bindingResult.hasErrors()) {
            logger.warn("Unable to register user, validation failed: {}", bindingResult.getFieldErrors());
            RegistrationFeedbackDto feedbackDto = createRegistrationFeedbackDtoForValidationErrors(registrationDto, bindingResult, messageProperties);
            model.addAttribute("feedback", feedbackDto);
            return "register";
        }

        try {
            registrationService.register(registrationDto);
            autoLogin(request, registrationDto.getEmail(), registrationDto.getPassword());
        } catch (EmailAlreadyUsedException ex) {
            RegistrationFeedbackDto feedbackDto = createRegistrationFeedbackDtoForEmailAlreadyInUse(registrationDto, messageProperties);
            model.addAttribute("feedback", feedbackDto);
            return "register";
        }
        return "redirect:/";
    }

    private void autoLogin(HttpServletRequest request, String username, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        authToken.setDetails(new WebAuthenticationDetails(request));

        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
