package com.natay.ecomm.bakery.testutils;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

import static com.natay.ecomm.bakery.testutils.HtmlFormHelper.fillInPassword;
import static com.natay.ecomm.bakery.testutils.HtmlFormHelper.fillInText;

/**
 * @author natayeung
 */
public class RegisterTestHelper {

    public static HtmlPage goToRegisterPageFrom(HtmlPage page) throws IOException {
        HtmlAnchor basketAnchor = page.getAnchorByHref("/user/register");
        return basketAnchor.click();
    }

    public static void fillInDetailsWithEmail(HtmlForm registerForm, String email) {
        fillInText(registerForm, "email", email);
        fillInPassword(registerForm, "password", "pass");
        fillInText(registerForm, "addressLine1", "12 High Street");
        fillInText(registerForm, "postcode", "PO3 0ST");
    }

    private RegisterTestHelper() {}
}
