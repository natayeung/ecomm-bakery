package com.natay.ecomm.bakery.testutils;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.natay.ecomm.bakery.registration.dto.RegistrationDto;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.Optional;

import static com.natay.ecomm.bakery.testutils.ControllerITests.LOCALHOST;
import static com.natay.ecomm.bakery.testutils.HtmlFormHelper.fillInPassword;
import static com.natay.ecomm.bakery.testutils.HtmlFormHelper.fillInText;
import static org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder.mockMvcSetup;

/**
 * @author natayeung
 */
public class RegisterTestHelper {

    private static final String password = "johndpass";
    private static final String firstName = "John";
    private static final String lastName = "Doe";
    private static final String addressLine1 = "12 High Street";
    private static final String townOrCity = "London";
    private static final String postcode = "E15 2GU";

    public static HtmlPage registerWithEmailPasswordAndPostcode(MockMvc mockMvc, String email, String password, String postcode) throws IOException {
        try (WebClient webClient = mockMvcSetup(mockMvc).build()) {

            HtmlForm registerForm = getRegisterForm(webClient);
            fillInRegistrationDetails(registerForm, email, password, postcode);

            HtmlButton registerButton = registerForm.getButtonByName("register");
            return registerButton.click();
        }
    }

    public static HtmlPage registerWithEmailAndPassword(MockMvc mockMvc, String email, String password) throws IOException {
        try (WebClient webClient = mockMvcSetup(mockMvc).build()) {

            HtmlForm registerForm = getRegisterForm(webClient);
            fillInRegistrationDetails(registerForm, email, password);

            HtmlButton registerButton = registerForm.getButtonByName("register");
            return registerButton.click();
        }
    }

    public static HtmlPage register(MockMvc mockMvc, RegistrationDto dto) throws IOException {
        try (WebClient webClient = mockMvcSetup(mockMvc).build()) {

            HtmlForm registerForm = getRegisterForm(webClient);
            fillInText(registerForm, "firstName", dto.getFirstName());
            fillInText(registerForm, "lastName", dto.getLastName());
            fillInText(registerForm, "email", dto.getEmail());
            fillInPassword(registerForm, "password", dto.getPassword());
            fillInText(registerForm, "addressLine1", dto.getAddressLine1());
            fillInText(registerForm, "addressLine2", Optional.ofNullable(dto.getAddressLine2()).orElse(""));
            fillInText(registerForm, "townOrCity", dto.getTownOrCity());
            fillInText(registerForm, "postcode", dto.getPostcode());

            HtmlButton registerButton = registerForm.getButtonByName("register");
            return registerButton.click();
        }
    }

    public static HtmlPage registerWithEmail(MockMvc mockMvc, String email) throws IOException {
        return registerWithEmailAndPassword(mockMvc, email, password);
    }

    private static HtmlPage goToRegisterPageFrom(HtmlPage page) throws IOException {
        HtmlAnchor basketAnchor = page.getAnchorByHref("/register");
        return basketAnchor.click();
    }

    private static HtmlForm getRegisterForm(WebClient webClient) throws IOException {
        HtmlPage homePage = webClient.getPage(LOCALHOST);
        HtmlPage registerPage = goToRegisterPageFrom(homePage);
        return registerPage.getFormByName("form-register");
    }

    private static void fillInRegistrationDetails(HtmlForm registerForm, String email, String password, String postcode) {

        fillInText(registerForm, "firstName", firstName);
        fillInText(registerForm, "lastName", lastName);
        fillInText(registerForm, "email", email);
        fillInPassword(registerForm, "password", password);
        fillInText(registerForm, "addressLine1", addressLine1);
        fillInText(registerForm, "townOrCity", townOrCity);
        fillInText(registerForm, "postcode", postcode);
    }

    private static void fillInRegistrationDetails(HtmlForm registerForm, String email, String password) {
        fillInRegistrationDetails(registerForm, email, password, postcode);
    }

    private RegisterTestHelper() {
    }
}
