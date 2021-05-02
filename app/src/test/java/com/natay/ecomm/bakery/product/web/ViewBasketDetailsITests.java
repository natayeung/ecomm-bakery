package com.natay.ecomm.bakery.product.web;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.natay.ecomm.bakery.testutil.web.ControllerITests;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.natay.ecomm.bakery.testutil.web.BasketTestHelper.addItemToBasket;
import static com.natay.ecomm.bakery.testutil.web.BasketTestHelper.goToBasketPageFrom;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author natayeung
 */
public class ViewBasketDetailsITests extends ControllerITests {

    @Test
    public void shouldDisplayTitleOfItemInBasket() throws IOException {
        HtmlPage catalogPage = webClient().getPage(ControllerITests.LOCALHOST);
        addItemToBasket(catalogPage, "bfc", 1);

        HtmlPage basketPage = goToBasketPageFrom(catalogPage);

        assertThatItemTitleIsDisplayed(basketPage, "bfc", "Black Forest Cake");
    }

    @Test
    public void shouldDisplayQuantityOfItemInBasket() throws IOException {
        HtmlPage catalogPage = webClient().getPage(ControllerITests.LOCALHOST);
        addItemToBasket(catalogPage, "cc", 2);

        HtmlPage basketPage = goToBasketPageFrom(catalogPage);

        assertThatItemQuantityIsDisplayed(basketPage, "cc", "2");
    }

    @Test
    public void shouldDisplayTotalPriceOfItemInBasket() throws IOException {
        HtmlPage catalogPage = webClient().getPage(ControllerITests.LOCALHOST);
        addItemToBasket(catalogPage, "rsc", 2);

        HtmlPage basketPage = goToBasketPageFrom(catalogPage);

        assertThatItemTotalIsDisplayed(basketPage, "rsc", "49.90");
    }

    private void assertThatItemTitleIsDisplayed(HtmlPage page, String productId, String expectedTitle) {
        DomElement itemTitle = page.getElementById("title-" + productId);
        assertThat(itemTitle).isNotNull();
        assertThat(itemTitle.getTextContent()).isEqualTo(expectedTitle);
    }

    private void assertThatItemQuantityIsDisplayed(HtmlPage page, String productId, String expectedQuantity) {
        DomElement itemQuantity = page.getElementById("quantity-" + productId);
        assertThat(itemQuantity).isNotNull();
        assertThat(itemQuantity.getTextContent()).isEqualTo(expectedQuantity);
    }

    private void assertThatItemTotalIsDisplayed(HtmlPage page, String productId, String expectedTotal) {
        DomElement itemQuantity = page.getElementById("total-" + productId);
        assertThat(itemQuantity).isNotNull();
        assertThat(itemQuantity.getTextContent()).isEqualTo(expectedTotal);
    }
}
