package com.natay.ecomm.bakery.basket;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.natay.ecomm.bakery.ControllerITests;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.isNull;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author natayeung
 */
public class UpdateBasketItemCountITests extends ControllerITests {

    @Test
    public void shouldShowBasketItemCountWhenOneItemIsAddedToBasket() throws IOException {
        HtmlPage page = webClient().getPage(LOCALHOST);
        HtmlButton button = buttonFromFormAt(0, page);

        HtmlPage resultPage = button.click();

        String basketItemCount = extractBasketItemCount(resultPage);
        assertThat(basketItemCount).isEqualTo("1");
    }

    @Test
    public void shouldShowBasketItemCountWhenTwoSameItemsAreAddedToBasket() throws IOException {
        HtmlPage page = webClient().getPage(LOCALHOST);
        HtmlButton button = buttonFromFormAt(0, page);

        button.click();
        HtmlPage resultPage = button.click();

        String basketItemCount = extractBasketItemCount(resultPage);
        assertThat(basketItemCount).isEqualTo("2");
    }

    @Test
    public void shouldShowBasketItemCountWhenTwoDifferentItemsAreAddedToBasket() throws IOException {
        HtmlPage page = webClient().getPage(LOCALHOST);
        HtmlButton firstButton = buttonFromFormAt(0, page);
        HtmlButton secondButton = buttonFromFormAt(1, page);

        firstButton.click();
        HtmlPage resultPage = secondButton.click();

        String basketItemCount = extractBasketItemCount(resultPage);
        assertThat(basketItemCount).isEqualTo("2");
    }

    private HtmlButton buttonFromFormAt(int index, HtmlPage page) {
        List<HtmlForm> forms = page.getForms();
        HtmlForm form = forms.get(index);

        return form.getButtonByName("added-product-id");
    }

    private String extractBasketItemCount(HtmlPage resultPage) {
        DomElement basketItemCount = resultPage.getElementById("basket-item-count");
        return isNull(basketItemCount) ? "" : basketItemCount.getTextContent();
    }
}
