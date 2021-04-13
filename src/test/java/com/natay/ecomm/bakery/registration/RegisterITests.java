package com.natay.ecomm.bakery.registration;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.natay.ecomm.bakery.testutils.ControllerITests;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;

import static com.natay.ecomm.bakery.testutils.RandomUtil.randomEmail;
import static com.natay.ecomm.bakery.testutils.RegisterTestHelper.register;
import static com.natay.ecomm.bakery.testutils.RegisterTestHelper.registerWithEmail;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author natayeung
 */
@ExtendWith(SoftAssertionsExtension.class)
public class RegisterITests extends ControllerITests {

    @InjectSoftAssertions
    private SoftAssertions softly;

    @Test
    public void sameEmailCannotBeUsedMoreThanOnce() throws IOException {
        final String email = randomEmail();
        registerWithEmail(mockMvc(), email);

        HtmlPage resultPage = registerWithEmail(mockMvc(), email);

        DomElement feedbackMessage = resultPage.getElementById("email-feedback-message");
        assertThat(feedbackMessage.getTextContent()).containsIgnoringCase("Email address already in use");
    }

    @Test
    public void cannotRegisterIfValidationFails() throws IOException {
        RegistrationDto dto = new RegistrationDto();
        dto.setEmail("harryp");
        dto.setPassword("12345");
        dto.setAddressLine1(" ");
        dto.setPostcode("AB 37");

        HtmlPage resultPage = register(mockMvc(), dto);

        softly.assertThat(resultPage.getElementById("email-feedback-message").getTextContent())
                .containsIgnoringCase("Invalid email address");
        softly.assertThat(resultPage.getElementById("password-feedback-message").getTextContent())
                .containsIgnoringCase("Password must be between 6 and 8 characters");
        softly.assertThat(resultPage.getElementById("address-line1-feedback-message").getTextContent())
                .containsIgnoringCase("Invalid address line 1");
        softly.assertThat(resultPage.getElementById("postcode-feedback-message").getTextContent())
                .containsIgnoringCase("Invalid postcode");
    }

}
