package com.natay.ecomm.bakery.user;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.natay.ecomm.bakery.testutils.ControllerITests;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.natay.ecomm.bakery.testutils.RandomUtil.randomEmail;
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
        assertThat(feedbackMessage.getTextContent()).containsIgnoringCase("Email already in use");
    }
}
