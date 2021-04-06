package com.natay.ecomm.bakery.basket;

import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.natay.ecomm.bakery.testutils.ControllerITests;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.natay.ecomm.bakery.testutils.BasketTestHelper.extractBasketItemCountFromPage;
import static com.natay.ecomm.bakery.testutils.BasketTestHelper.findAddButtonForProduct;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author natayeung
 */
public class AddItemToBasketITests extends ControllerITests {

    @Test
    public void shouldUpdateBasketItemCountWhenOneItemIsAdded() throws IOException {
        HtmlPage page = webClient().getPage(LOCALHOST);
        HtmlButton button = findAddButtonForProduct("bfc", page);

        HtmlPage resultPage = button.click();

        String basketItemCount = extractBasketItemCountFromPage(resultPage);
        assertThat(basketItemCount).isEqualTo("1");
    }

    @Test
    public void shouldUpdateBasketItemCountWhenTwoSameItemsAreAdded() throws IOException {
        HtmlPage page = webClient().getPage(LOCALHOST);
        HtmlButton button = findAddButtonForProduct("bfc", page);

        button.click();
        HtmlPage resultPage = button.click();

        String basketItemCount = extractBasketItemCountFromPage(resultPage);
        assertThat(basketItemCount).isEqualTo("2");
    }

    @Test
    public void shouldUpdateBasketItemCountWhenTwoDifferentItemsAreAdded() throws IOException {
        HtmlPage page = webClient().getPage(LOCALHOST);
        HtmlButton firstButton = findAddButtonForProduct("bfc", page);
        HtmlButton secondButton = findAddButtonForProduct("rsc", page);

        firstButton.click();
        HtmlPage resultPage = secondButton.click();

        String basketItemCount = extractBasketItemCountFromPage(resultPage);
        assertThat(basketItemCount).isEqualTo("2");
    }
}
