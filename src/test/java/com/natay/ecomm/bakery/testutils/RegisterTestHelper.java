package com.natay.ecomm.bakery.testutils;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static com.natay.ecomm.bakery.testutils.ControllerITests.LOCALHOST;
import static com.natay.ecomm.bakery.testutils.HtmlFormHelper.fillInPassword;
import static com.natay.ecomm.bakery.testutils.HtmlFormHelper.fillInText;
import static org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder.mockMvcSetup;

/**
 * @author natayeung
 */
public class RegisterTestHelper {

    public static HtmlPage registerWithEmailAndPassword(MockMvc mockMvc, String email, String password) throws IOException {
        try (WebClient webClient = mockMvcSetup(mockMvc).build()) {

            HtmlPage homePage = webClient.getPage(LOCALHOST);
            HtmlPage registerPage = goToRegisterPageFrom(homePage);
            HtmlForm registerForm = registerPage.getFormByName("form-register");

            fillInRegistrationDetails(registerForm, email, password);

            HtmlButton registerButton = registerForm.getButtonByName("register");
            return registerButton.click();
        }
    }

    public static HtmlPage registerWithEmail(MockMvc mockMvc, String email) throws IOException {
        return registerWithEmailAndPassword(mockMvc, email, "pwd123");
    }

    private static HtmlPage goToRegisterPageFrom(HtmlPage page) throws IOException {
        HtmlAnchor basketAnchor = page.getAnchorByHref("/register");
        return basketAnchor.click();
    }

    private static void fillInRegistrationDetails(HtmlForm registerForm, String email, String password) {
        fillInText(registerForm, "email", email);
        fillInPassword(registerForm, "password", password);
        fillInText(registerForm, "addressLine1", "12 High Street");
        fillInText(registerForm, "postcode", "PO3 0ST");
    }

    private RegisterTestHelper() {}
}