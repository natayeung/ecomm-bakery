package com.natay.ecomm.bakery.registration;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.natay.ecomm.bakery.registration.dto.RegistrationDto;
import com.natay.ecomm.bakery.testutils.ControllerITests;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.natay.ecomm.bakery.testutils.RegistrationDtoFactory.createRegistrationDto;
import static com.natay.ecomm.bakery.testutils.RandomUtil.randomEmail;
import static com.natay.ecomm.bakery.testutils.RegisterTestHelper.register;
import static com.natay.ecomm.bakery.testutils.RegisterTestHelper.registerWithEmail;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author natayeung
 */
public class RegisterITests extends ControllerITests {

    @Test
    public void sameEmailCannotBeUsedMoreThanOnce() throws IOException {
        final String email = randomEmail();
        registerWithEmail(mockMvc(), email);

        HtmlPage resultPage = registerWithEmail(mockMvc(), email);

        DomElement feedbackMessage = resultPage.getElementById("email-feedback-message");
        assertThat(feedbackMessage.getTextContent()).containsIgnoringCase("Email address already in use");
    }

    @Test
    public void cannotRegisterIfFirstNameIsInvalid() throws IOException {
        RegistrationDto registrationDto = createRegistrationDto();
        registrationDto.setFirstName(" ");

        HtmlPage resultPage = register(mockMvc(), registrationDto);

        assertThat(resultPage.getElementById("first-name-feedback-message").getTextContent())
                .containsIgnoringCase("Invalid first name");
    }

    @Test
    public void cannotRegisterIfLastNameIsInvalid() throws IOException {
        RegistrationDto registrationDto = createRegistrationDto();
        registrationDto.setLastName(" ");

        HtmlPage resultPage = register(mockMvc(), registrationDto);

        assertThat(resultPage.getElementById("last-name-feedback-message").getTextContent())
                .containsIgnoringCase("Invalid last name");
    }

    @Test
    public void cannotRegisterIfEmailIsInvalid() throws IOException {
        RegistrationDto registrationDto = createRegistrationDto();
        registrationDto.setEmail("john.doe");

        HtmlPage resultPage = register(mockMvc(), registrationDto);

        assertThat(resultPage.getElementById("email-feedback-message").getTextContent())
                .containsIgnoringCase("Invalid email address");
    }

    @Test
    public void cannotRegisterIfPasswordIsInvalid() throws IOException {
        RegistrationDto registrationDto = createRegistrationDto();
        registrationDto.setPassword("12345");

        HtmlPage resultPage = register(mockMvc(), registrationDto);

        assertThat(resultPage.getElementById("password-feedback-message").getTextContent())
                .containsIgnoringCase("Password must be between 6 and 8 characters");
    }

    @Test
    public void cannotRegisterIfAddressLine1IsInvalid() throws IOException {
        RegistrationDto registrationDto = createRegistrationDto();
        registrationDto.setAddressLine1(" ");

        HtmlPage resultPage = register(mockMvc(), registrationDto);

        assertThat(resultPage.getElementById("address-line1-feedback-message").getTextContent())
                .containsIgnoringCase("Invalid address line 1");
    }

    @Test
    public void cannotRegisterIfTownOrCityIsInvalid() throws IOException {
        RegistrationDto registrationDto = createRegistrationDto();
        registrationDto.setTownOrCity(" ");

        HtmlPage resultPage = register(mockMvc(), registrationDto);

        assertThat(resultPage.getElementById("town-or-city-feedback-message").getTextContent())
                .containsIgnoringCase("Invalid town or city");
    }

    @Test
    public void cannotRegisterIfPostcodeIsInvalid() throws IOException {
        RegistrationDto registrationDto = createRegistrationDto();
        registrationDto.setPostcode("E15");

        HtmlPage resultPage = register(mockMvc(), registrationDto);

        assertThat(resultPage.getElementById("postcode-feedback-message").getTextContent())
                .containsIgnoringCase("Invalid postcode");
    }

}
