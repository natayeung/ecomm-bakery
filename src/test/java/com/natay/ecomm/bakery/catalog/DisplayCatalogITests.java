package com.natay.ecomm.bakery.catalog;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.natay.ecomm.bakery.ControllerITests;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author natayeung
 */
public class DisplayCatalogITests extends ControllerITests {

    @Test
    public void shouldDisplayTitleOfProduct() throws IOException {
        HtmlPage page = webClient().getPage(LOCALHOST);

        assertThatTitleOfProductTitleIsDisplayed(page, "title-bfc", "Black Forest Cake");
        assertThatTitleOfProductTitleIsDisplayed(page, "title-cc", "Carrot Cake");
        assertThatTitleOfProductTitleIsDisplayed(page, "title-rsc", "Rainbow Sprinkles Cake");
    }

    @Test
    public void shouldDisplayPriceOfProduct() throws IOException {
        HtmlPage page = webClient().getPage(LOCALHOST);

        assertThatPriceOfProductIsDisplayed(page, "price-bfc", "£ 27.95");
        assertThatPriceOfProductIsDisplayed(page, "price-cc", "£ 21.95");
        assertThatPriceOfProductIsDisplayed(page, "price-rsc", "£ 24.95");
    }

    private void assertThatTitleOfProductTitleIsDisplayed(HtmlPage page, String titleElementId, String expectedTitle) {
        DomElement productTitle = page.getElementById(titleElementId);
        assertThat(productTitle).isNotNull();
        assertThat(productTitle.getTextContent()).isEqualTo(expectedTitle);
    }

    private void assertThatPriceOfProductIsDisplayed(HtmlPage page, String priceElementId, String expectedPrice) {
        DomElement productPrice = page.getElementById(priceElementId);
        assertThat(productPrice).isNotNull();
        assertThat(productPrice.getTextContent()).isEqualTo(expectedPrice);
    }
}