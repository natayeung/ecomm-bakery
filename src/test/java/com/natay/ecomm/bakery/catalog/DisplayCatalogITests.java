package com.natay.ecomm.bakery.catalog;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.natay.ecomm.bakery.ControllerITests;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;

/**
 * @author natayeung
 */
public class DisplayCatalogITests extends ControllerITests {

    @Test
    public void shouldDisplayTitleOfProduct() throws Exception {
        HtmlPage htmlPage = webClient().getPage(LOCALHOST);

        List<DomElement> titles = htmlPage.getElementsById("title");
        assertThat(titles)
                .extracting(DomElement::getTextContent)
                .contains("Black Forest Cake", atIndex(0))
                .contains("Carrot Cake", atIndex(1))
                .contains("Rainbow Sprinkles Cake", atIndex(2));
    }

    @Test
    public void shouldDisplayPriceOfProduct() throws IOException {
        HtmlPage htmlPage = webClient().getPage(LOCALHOST);

        List<DomElement> prices = htmlPage.getElementsById("price");
        assertThat(prices)
                .extracting(DomElement::getTextContent)
                .contains("£ 27.95", atIndex(0))
                .contains("£ 21.95", atIndex(1))
                .contains("£ 24.95", atIndex(2));
    }
}