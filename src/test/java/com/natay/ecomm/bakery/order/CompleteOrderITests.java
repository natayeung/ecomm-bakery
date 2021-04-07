package com.natay.ecomm.bakery.order;

import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
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
public class CompleteOrderITests extends ControllerITests {

    @Test
    public void confirmationIsDisplayedWhenOrderIsComplete() throws IOException {
        final String email = randomEmail();
        final String password = randomPassword();
        registerWithEmailAndPassword(mockMvc(), email, password);
        HtmlPage catalogPage = loginWithEmailAndPassword(webClient(), email, password);
        addItemToBasket(catalogPage, "bfc", 2);
        goToBasketPageFrom(catalogPage);
        webClient().waitForBackgroundJavaScript(5000);

        HtmlPage resultPage = submitOrder();

        HtmlElement pageBody = resultPage.getBody();
        assertThat(pageBody.getTextContent()).containsIgnoringCase("Your order is now complete");
    }

    private HtmlPage submitOrder() throws IOException {
        HtmlPage basketPage = (HtmlPage) webClient().getCurrentWindow().getEnclosedPage();
        HtmlForm addressForm = basketPage.getFormByName("form-delivery-address");

        HtmlButton submitButton = addressForm.getButtonByName("complete-order");
        return submitButton.click();
    }
}
