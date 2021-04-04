package com.natay.ecomm.bakery.order;

import com.gargoylesoftware.htmlunit.html.*;
import com.natay.ecomm.bakery.ControllerITests;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.natay.ecomm.bakery.basket.TestHelper.addItemToBasket;
import static com.natay.ecomm.bakery.basket.TestHelper.goToBasketPageFrom;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author natayeung
 */
public class CompleteOrderITests extends ControllerITests {

    @Test
    public void confirmationIsDisplayedWhenOrderIsComplete() throws IOException {
        HtmlPage catalogPage = webClient().getPage(LOCALHOST);
        addItemToBasket(catalogPage, "bfc", 2);
        HtmlPage basketPage = goToBasketPageFrom(catalogPage);
        HtmlForm addressForm = basketPage.getFormByName("form-delivery-address");

        fillInDeliveryAddress(addressForm);
        HtmlPage resultPage = completeOrder(addressForm);

        HtmlElement pageBody = resultPage.getBody();
        assertThat(pageBody.getTextContent()).containsIgnoringCase("Your order is now complete");
    }

    private void fillInDeliveryAddress(HtmlForm addressForm) {
        HtmlTextInput address1 = addressForm.getInputByName("address1");
        HtmlTextInput postcode = addressForm.getInputByName("postcode");
        address1.setValueAttribute("12 High Street");
        postcode.setValueAttribute("PO3 0ST");
    }

    private HtmlPage completeOrder(HtmlForm addressForm) throws IOException {
        HtmlButton submitButton = addressForm.getButtonByName("complete-order");
        return submitButton.click();
    }
}
