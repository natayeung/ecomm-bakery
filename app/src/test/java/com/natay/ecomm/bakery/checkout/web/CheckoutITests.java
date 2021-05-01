package com.natay.ecomm.bakery.checkout.web;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.natay.ecomm.bakery.testutil.ControllerITests;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.natay.ecomm.bakery.testutil.BasketTestHelper.addItemToBasket;
import static com.natay.ecomm.bakery.testutil.BasketTestHelper.goToBasketPageFrom;
import static com.natay.ecomm.bakery.testutil.HtmlFormHelper.fillInText;
import static com.natay.ecomm.bakery.testutil.LoginTestHelper.loginWithEmailAndPassword;
import static com.natay.ecomm.bakery.testutil.RandomUtil.randomEmail;
import static com.natay.ecomm.bakery.testutil.RandomUtil.randomPassword;
import static com.natay.ecomm.bakery.testutil.RegisterTestHelper.registerWithEmailAndPassword;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author natayeung
 */
public class CheckoutITests extends ControllerITests {

    @Test
    public void redirectedToPayPalWhenCheckoutIsInitiated() throws IOException {
        final String email = randomEmail();
        final String password = randomPassword();
        registerWithEmailAndPassword(mockMvc(), email, password);
        HtmlPage catalogPage = loginWithEmailAndPassword(webClient(), email, password);
        addItemToBasket(catalogPage, "bfc", 2);
        goToBasketPageFrom(catalogPage);
        webClient().waitForBackgroundJavaScript(5000);

        initiateCheckout();
        webClient().waitForBackgroundJavaScript(5000);

        HtmlPage resultPage = (HtmlPage) webClient().getCurrentWindow().getEnclosedPage();
        assertThat(resultPage.getBaseURL())
                .hasHost("www.sandbox.paypal.com")
                .hasPath("/checkoutnow")
                .hasParameter("token");
    }

    private HtmlPage initiateCheckout() throws IOException {
        HtmlPage basketPage = (HtmlPage) webClient().getCurrentWindow().getEnclosedPage();
        HtmlForm checkoutForm = basketPage.getFormByName("form-checkout");
        fillInText(checkoutForm, "shippingFirstName", "John");
        fillInText(checkoutForm, "shippingLastName", "Doe");

        HtmlInput checkoutButton = checkoutForm.getInputByName("initiate-checkout");
        return checkoutButton.click();
    }
}
