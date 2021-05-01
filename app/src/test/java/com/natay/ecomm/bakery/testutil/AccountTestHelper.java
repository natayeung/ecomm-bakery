package com.natay.ecomm.bakery.testutil;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

import static com.natay.ecomm.bakery.testutil.HtmlFormHelper.fillInText;

/**
 * @author natayeung
 */
public class AccountTestHelper {

    public static HtmlPage goToAccountPageFrom(HtmlPage page) throws IOException {
        HtmlAnchor accountAnchor = page.getAnchorByHref("/account");
        return accountAnchor.click();
    }

    public static void updateField(HtmlPage accountPage, String inputName, String newValue) throws IOException {
        HtmlForm accountForm = accountPage.getFormByName("form-account");
        fillInText(accountForm, inputName, newValue);
        clickUpdateButton(accountForm);
    }

    public static void clickUpdateButton(HtmlForm accountForm) throws IOException {
        HtmlButton updateButton = accountForm.getButtonByName("update-account");
        updateButton.click();
    }

    private AccountTestHelper() {
    }
}
