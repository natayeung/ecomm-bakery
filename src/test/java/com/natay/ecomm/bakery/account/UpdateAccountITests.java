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
    public void canUpdateAccountAddress() throws IOException {
        final String email = randomEmail();
        final String password = randomPassword();
        final String postcode = "PO7 5ET";
        final String newPostcode = "PO5 7ET";
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
}
