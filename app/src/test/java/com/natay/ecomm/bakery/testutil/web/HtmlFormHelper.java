package com.natay.ecomm.bakery.testutil.web;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

/**
 * @author natayeung
 */
public class HtmlFormHelper {

    public static void fillInText(HtmlForm form, String inputName, String inputValue) {
        HtmlTextInput textInput = form.getInputByName(inputName);
        textInput.setValueAttribute(inputValue);
    }

    public static void fillInPassword(HtmlForm form, String inputName, String inputValue) {
        HtmlPasswordInput passwordInput = form.getInputByName(inputName);
        passwordInput.setValueAttribute(inputValue);
    }

    private HtmlFormHelper() {}
}
