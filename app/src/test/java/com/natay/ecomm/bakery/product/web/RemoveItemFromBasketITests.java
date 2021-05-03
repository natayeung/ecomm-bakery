package com.natay.ecomm.bakery.product.web;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.natay.ecomm.bakery.testutil.web.ControllerITests;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.natay.ecomm.bakery.testutil.web.BasketTestHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author natayeung
 */
public class RemoveItemFromBasketITests extends ControllerITests {

    @Test
    public void shouldUpdateBasketItemCountWhenAnItemWithQuantityEqualToOneIsRemoved() throws IOException {
        HtmlPage catalogPage = webClient().getPage(ControllerITests.LOCALHOST);
        addItemToBasket(catalogPage, "bfc", 2);
        addItemToBasket(catalogPage, "cc", 1);

        HtmlPage basketPage = goToBasketPageFrom(catalogPage);
        HtmlPage updatedBasketPage = removeItemFromBasket(basketPage, "cc");

        String basketItemCount = extractBasketItemCountFromPage(updatedBasketPage);
        assertThat(basketItemCount).isEqualTo("2");
    }

    @Test
    public void shouldUpdateBasketItemCountWhenAnItemWithQuantityGreaterThanOneIsRemoved() throws IOException {
        HtmlPage catalogPage = webClient().getPage(ControllerITests.LOCALHOST);
        addItemToBasket(catalogPage, "bfc", 2);
        addItemToBasket(catalogPage, "cc", 1);

        HtmlPage basketPage = goToBasketPageFrom(catalogPage);
        HtmlPage updatedBasketPage = removeItemFromBasket(basketPage, "bfc");

        String basketItemCount = extractBasketItemCountFromPage(updatedBasketPage);
        assertThat(basketItemCount).isEqualTo("1");
    }

    @Test
    public void shouldUpdateBasketItemCountWhenAllItemsAreRemoved() throws IOException {
        HtmlPage catalogPage = webClient().getPage(ControllerITests.LOCALHOST);
        addItemToBasket(catalogPage, "bfc", 1);
        addItemToBasket(catalogPage, "cc", 1);

        HtmlPage basketPage = goToBasketPageFrom(catalogPage);
        removeItemFromBasket(basketPage, "bfc");
        HtmlPage updatedBasketPage = removeItemFromBasket(basketPage, "cc");

        String basketItemCount = extractBasketItemCountFromPage(updatedBasketPage);
        assertThat(basketItemCount).isEqualTo("0");
    }
}
