package com.natay.ecomm.bakery.user.web;

import com.natay.ecomm.bakery.common.MessageProperties;
import com.natay.ecomm.bakery.user.account.EmailAlreadyUsedException;
import com.natay.ecomm.bakery.user.authentication.AutoLoginRequestEvent;
import com.natay.ecomm.bakery.user.registration.RegistrationDto;
import com.natay.ecomm.bakery.user.registration.RegistrationFeedbackDto;
import com.natay.ecomm.bakery.user.registration.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

import static com.natay.ecomm.bakery.user.registration.RegistrationFeedbackDtoFactory.createRegistrationFeedbackDtoForEmailAlreadyInUse;
import static com.natay.ecomm.bakery.user.registration.RegistrationFeedbackDtoFactory.createRegistrationFeedbackDtoForValidationErrors;

/**
 * @author natayeung
 */
@Controller
@RequestMapping("/register")
@Slf4j
public class UserRegistrationController implements ApplicationEventPublisherAware {

    private final RegistrationService registrationService;
    private final MessageProperties messageProperties;
    private ApplicationEventPublisher eventPublisher;

    public UserRegistrationController(RegistrationService registrationService,
                                      MessageProperties messageProperties) {
        this.registrationService = registrationService;
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
        log.info("Received request to register user {}", registrationDto);

        if (bindingResult.hasErrors()) {
            log.warn("Unable to register user, validation failed: {}", bindingResult.getFieldErrors());
            RegistrationFeedbackDto feedbackDto = createRegistrationFeedbackDtoForValidationErrors(registrationDto, bindingResult, messageProperties);
            model.addAttribute("feedback", feedbackDto);
            return "register";
        }

        try {
            registrationService.register(registrationDto);
            tryAutoLogin(request, registrationDto.getEmail(), registrationDto.getPassword());
        } catch (EmailAlreadyUsedException ex) {
            RegistrationFeedbackDto feedbackDto = createRegistrationFeedbackDtoForEmailAlreadyInUse(registrationDto, messageProperties);
            model.addAttribute("feedback", feedbackDto);
            return "register";
        }
        return "redirect:/";
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        eventPublisher = applicationEventPublisher;
    }

    private void tryAutoLogin(HttpServletRequest request, String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        authenticationToken.setDetails(new WebAuthenticationDetails(request));

        AutoLoginRequestEvent autoLoginRequestEvent = new AutoLoginRequestEvent(this, authenticationToken);
        eventPublisher.publishEvent(autoLoginRequestEvent);
    }
}
