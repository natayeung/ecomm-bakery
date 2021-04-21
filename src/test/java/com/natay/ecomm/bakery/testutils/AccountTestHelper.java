package com.natay.ecomm.bakery.testutils;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

/**
 * @author natayeung
 */
public class AccountTestHelper {

    public static HtmlPage goToAccountPageFrom(HtmlPage page) throws IOException {
        HtmlAnchor accountAnchor = page.getAnchorByHref("/account");
        return accountAnchor.click();
    }

    public static void clickUpdateButton(HtmlForm accountForm) throws IOException {
        HtmlButton updateButton = accountForm.getButtonByName("update-account");
        updateButton.click();
    }

    private AccountTestHelper() {}
}
