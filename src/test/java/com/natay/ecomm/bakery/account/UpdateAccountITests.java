package com.natay.ecomm.bakery.account;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.natay.ecomm.bakery.testutils.ControllerITests;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.natay.ecomm.bakery.account.AccountTestHelper.clickUpdateButton;
import static com.natay.ecomm.bakery.account.AccountTestHelper.goToAccountPageFrom;
import static com.natay.ecomm.bakery.testutils.BasketTestHelper.goToBasketPageFrom;
import static com.natay.ecomm.bakery.testutils.HtmlFormHelper.fillInText;
import static com.natay.ecomm.bakery.testutils.LoginTestHelper.loginWithEmailAndPassword;
import static com.natay.ecomm.bakery.testutils.RandomUtil.randomEmail;
import static com.natay.ecomm.bakery.testutils.RandomUtil.randomPassword;
import static com.natay.ecomm.bakery.testutils.RegisterTestHelper.registerWithEmailPasswordAndPostcode;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author natayeung
 */
public class UpdateAccountITests extends ControllerITests {

    @Test
    public void canUpdateAccountAddressIfValidationPasses() throws IOException {
        final String email = randomEmail();
        final String password = randomPassword();
        final String postcode = "E15 1GU";
        final String newPostcode = "E15 2GU";
        registerWithEmailPasswordAndPostcode(mockMvc(), email, password, postcode);
        HtmlPage homePage = loginWithEmailAndPassword(webClient(), email, password);

        HtmlPage accountPage = goToAccountPageFrom(homePage);
        HtmlForm accountForm = accountPage.getFormByName("form-account");
        fillInText(accountForm, "postcode", newPostcode);
        clickUpdateButton(accountForm);
        webClient().waitForBackgroundJavaScript(5000);

        HtmlPage resultPage = (HtmlPage) webClient().getCurrentWindow().getEnclosedPage();
        HtmlPage basketPage = goToBasketPageFrom(resultPage);

        DomElement postcodeField = basketPage.getElementById("postcode");
        assertThat(postcodeField.getAttribute("value")).isEqualTo(newPostcode);
    }

    @Test
    public void cannotUpdateAccountIfPostcodeValidationFails() throws IOException {
        final String email = randomEmail();
        final String password = randomPassword();
        final String oldPostcode = "E15 2GU";
        final String newPostcode = "E14";
        registerWithEmailPasswordAndPostcode(mockMvc(), email, password, oldPostcode);
        HtmlPage homePage = loginWithEmailAndPassword(webClient(), email, password);

        HtmlPage accountPage = goToAccountPageFrom(homePage);
        HtmlForm accountForm = accountPage.getFormByName("form-account");
        fillInText(accountForm, "postcode", newPostcode);
        clickUpdateButton(accountForm);
        webClient().waitForBackgroundJavaScript(5000);

        HtmlPage resultPage = (HtmlPage) webClient().getCurrentWindow().getEnclosedPage();
        DomElement postcodeFeedbackMessage = resultPage.getElementById("postcode-feedback-message");
        assertThat(postcodeFeedbackMessage.getTextContent()).containsIgnoringCase("Invalid postcode");

        HtmlPage basketPage = goToBasketPageFrom(resultPage);
        DomElement postcodeField = basketPage.getElementById("postcode");
        assertThat(postcodeField.getAttribute("value")).isEqualTo(oldPostcode);
    }
}
