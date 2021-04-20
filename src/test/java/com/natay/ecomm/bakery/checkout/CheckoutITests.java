package com.natay.ecomm.bakery.checkout;

import com.gargoylesoftware.htmlunit.html.*;
import com.natay.ecomm.bakery.testutils.ControllerITests;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.natay.ecomm.bakery.testutils.BasketTestHelper.addItemToBasket;
import static com.natay.ecomm.bakery.testutils.BasketTestHelper.goToBasketPageFrom;
import static com.natay.ecomm.bakery.testutils.LoginTestHelper.loginWithEmailAndPassword;
import static com.natay.ecomm.bakery.testutils.RandomUtil.randomEmail;
import static com.natay.ecomm.bakery.testutils.RandomUtil.randomPassword;
import static com.natay.ecomm.bakery.testutils.RegisterTestHelper.registerWithEmailAndPassword;
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
        HtmlForm addressForm = basketPage.getFormByName("form-checkout");

        HtmlInput checkoutButton = addressForm.getInputByName("initiate-checkout");
        return checkoutButton.click();
    }
}
