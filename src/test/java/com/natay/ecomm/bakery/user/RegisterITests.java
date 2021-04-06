package com.natay.ecomm.bakery.user;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.natay.ecomm.bakery.testutils.ControllerITests;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.natay.ecomm.bakery.testutils.RegisterTestHelper.fillInDetailsWithEmail;
import static com.natay.ecomm.bakery.testutils.RegisterTestHelper.goToRegisterPageFrom;
import static com.natay.ecomm.bakery.testutils.RandomUtil.randomEmail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder.mockMvcSetup;

/**
 * @author natayeung
 */
public class RegisterITests extends ControllerITests {

    @Test
    public void sameEmailCannotBeUsedMoreThanOnce() throws IOException {
        final String email = randomEmail();
        registerWithEmail(email);

        HtmlPage resultPage = registerWithEmail(email);

        DomElement feedbackMessage = resultPage.getElementById("email-feedback-message");
        assertThat(feedbackMessage.getTextContent()).containsIgnoringCase("Email already in use");
    }

    private HtmlPage registerWithEmail(String email) throws IOException {
        try (WebClient webClient = mockMvcSetup(mockMvc()).build()) {

            HtmlPage homePage = webClient.getPage(LOCALHOST);
            HtmlPage registerPage = goToRegisterPageFrom(homePage);
            HtmlForm registerForm = registerPage.getFormByName("form-register");

            fillInDetailsWithEmail(registerForm, email);

            HtmlButton registerButton = registerForm.getButtonByName("register");
            return registerButton.click();
        }
    }
}
