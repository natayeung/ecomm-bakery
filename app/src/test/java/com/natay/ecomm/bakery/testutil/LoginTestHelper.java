package com.natay.ecomm.bakery.testutil;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

import static com.natay.ecomm.bakery.testutil.ControllerITests.LOCALHOST;
import static com.natay.ecomm.bakery.testutil.HtmlFormHelper.fillInPassword;
import static com.natay.ecomm.bakery.testutil.HtmlFormHelper.fillInText;

/**
 * @author natayeung
 */
public class LoginTestHelper {

    public static HtmlPage loginWithEmailAndPassword(WebClient webClient, String email, String password) throws IOException {
        HtmlPage homePage = webClient.getPage(LOCALHOST);
        HtmlPage loginPage = goToLoginPageFrom(homePage);
        HtmlForm loginForm = loginPage.getFormByName("form-login");

        fillInLoginDetails(loginForm, email, password);

        HtmlButton loginButton = loginForm.getButtonByName("login");
        return loginButton.click();
    }

    private static HtmlPage goToLoginPageFrom(HtmlPage page) throws IOException {
        HtmlAnchor basketAnchor = page.getAnchorByHref("/login");
        return basketAnchor.click();
    }

    private static void fillInLoginDetails(HtmlForm loginFOrm, String email, String password) {
        fillInText(loginFOrm, "email", email);
        fillInPassword(loginFOrm, "password", password);
    }
}
