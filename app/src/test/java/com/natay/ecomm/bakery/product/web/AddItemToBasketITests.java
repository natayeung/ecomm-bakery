package com.natay.ecomm.bakery.product.web;

import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.natay.ecomm.bakery.testutil.BasketTestHelper;
import com.natay.ecomm.bakery.testutil.ControllerITests;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.natay.ecomm.bakery.testutil.BasketTestHelper.findAddButtonForProduct;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author natayeung
 */
public class AddItemToBasketITests extends ControllerITests {

    @Test
    public void shouldUpdateBasketItemCountWhenOneItemIsAdded() throws IOException {
        HtmlPage page = webClient().getPage(ControllerITests.LOCALHOST);
        HtmlButton button = findAddButtonForProduct("bfc", page);

        HtmlPage resultPage = button.click();

        String basketItemCount = BasketTestHelper.extractBasketItemCountFromPage(resultPage);
        assertThat(basketItemCount).isEqualTo("1");
    }

    @Test
    public void shouldUpdateBasketItemCountWhenTwoSameItemsAreAdded() throws IOException {
        HtmlPage page = webClient().getPage(ControllerITests.LOCALHOST);
        HtmlButton button = findAddButtonForProduct("bfc", page);

        button.click();
        HtmlPage resultPage = button.click();

        String basketItemCount = BasketTestHelper.extractBasketItemCountFromPage(resultPage);
        assertThat(basketItemCount).isEqualTo("2");
    }

    @Test
    public void shouldUpdateBasketItemCountWhenTwoDifferentItemsAreAdded() throws IOException {
        HtmlPage page = webClient().getPage(ControllerITests.LOCALHOST);
        HtmlButton firstButton = findAddButtonForProduct("bfc", page);
        HtmlButton secondButton = findAddButtonForProduct("rsc", page);

        firstButton.click();
        HtmlPage resultPage = secondButton.click();

        String basketItemCount = BasketTestHelper.extractBasketItemCountFromPage(resultPage);
        assertThat(basketItemCount).isEqualTo("2");
    }
}
