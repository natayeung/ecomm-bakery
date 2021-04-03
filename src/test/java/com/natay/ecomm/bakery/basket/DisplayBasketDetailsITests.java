package com.natay.ecomm.bakery.basket;

import com.gargoylesoftware.htmlunit.html.*;
import com.natay.ecomm.bakery.ControllerITests;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static com.natay.ecomm.bakery.utils.Arguments.requireArgument;
import static org.assertj.core.api.Assertions.assertThat;

public class DisplayBasketDetailsITests extends ControllerITests {

    @Test
    public void shouldDisplayTitleOfItemInBasket() throws IOException {
        final int quantity = 1;
        final int productIndex = 0;
        HtmlPage catalogPage = webClient().getPage(LOCALHOST);
        addItems(catalogPage, productIndex, quantity);

        HtmlAnchor basketAnchor = catalogPage.getAnchorByHref("/basket");
        HtmlPage basketPage = basketAnchor.click();

        assertThatItemTitleIsDisplayed(basketPage, "title-bfc", "Black Forest Cake");
    }

    @Test
    public void shouldDisplayQuantityOfItemInBasket() throws IOException {
        final int quantity = 2;
        final int productIndex = 1;
        HtmlPage catalogPage = webClient().getPage(LOCALHOST);
        addItems(catalogPage, productIndex, quantity);

        HtmlAnchor basketAnchor = catalogPage.getAnchorByHref("/basket");
        HtmlPage basketPage = basketAnchor.click();

        assertThatItemQuantityIsDisplayed(basketPage, "quantity-cc", "2");
    }

    @Test
    public void shouldDisplayTotalPriceOfItemInBasket() throws IOException {
        final int quantity = 2;
        final int productIndex = 2;
        HtmlPage catalogPage = webClient().getPage(LOCALHOST);
        addItems(catalogPage, productIndex, quantity);

        HtmlAnchor basketAnchor = catalogPage.getAnchorByHref("/basket");
        HtmlPage basketPage = basketAnchor.click();

        assertThatItemTotalIsDisplayed(basketPage, "total-rsc", "49.90");
    }

    private void addItems(HtmlPage page, int productIndex, int quantity) throws IOException {
        List<HtmlForm> forms = page.getForms();
        requireArgument(productIndex < forms.size(), "Product index out of bound");

        HtmlForm form = forms.get(productIndex);
        HtmlButton addButton = form.getButtonByName("added-product-id");

        for (int i = 0; i < quantity; i++) {
           addButton.click();
        }
    }

    private void assertThatItemTitleIsDisplayed(HtmlPage page, String itemTitleElementId, String expectedTitle) {
        DomElement itemTitle = page.getElementById(itemTitleElementId);
        assertThat(itemTitle).isNotNull();
        assertThat(itemTitle.getTextContent()).isEqualTo(expectedTitle);
    }

    private void assertThatItemQuantityIsDisplayed(HtmlPage page, String itemQuantityElementId, String expectedQuantity) {
        DomElement itemQuantity = page.getElementById(itemQuantityElementId);
        assertThat(itemQuantity).isNotNull();
        assertThat(itemQuantity.getTextContent()).isEqualTo(expectedQuantity);
    }

    private void assertThatItemTotalIsDisplayed(HtmlPage page, String itemTotalElementId, String expectedTotal) {
        DomElement itemQuantity = page.getElementById(itemTotalElementId);
        assertThat(itemQuantity).isNotNull();
        assertThat(itemQuantity.getTextContent()).isEqualTo(expectedTotal);
    }
}
