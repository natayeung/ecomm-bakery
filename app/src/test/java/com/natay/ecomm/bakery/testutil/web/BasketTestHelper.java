package com.natay.ecomm.bakery.testutil.web;

import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;

import static java.util.Objects.isNull;

/**
 * @author natayeung
 */
public class BasketTestHelper {

    public static void addItemToBasket(HtmlPage page, String productId, int quantity) throws IOException {
        HtmlButton addButton = findAddButtonForProduct(productId, page);

        for (int i = 0; i < quantity; i++) {
            addButton.click();
        }
    }

    public static HtmlPage removeItemFromBasket(HtmlPage page, String productId) throws IOException {
        HtmlButton removeButton = findRemoveButtonForProduct(productId, page);
        return removeButton.click();
    }

    public static HtmlButton findAddButtonForProduct(String productId, HtmlPage page) {
        HtmlForm form = page.getFormByName("form-add-item-" + productId);
        return form.getButtonByName("item-to-add");
    }

    public static HtmlButton findRemoveButtonForProduct(String productId, HtmlPage page) {
        HtmlForm form = page.getFormByName("form-remove-item-" + productId);
        return form.getButtonByName("item-to-remove");
    }

    public static HtmlPage goToBasketPageFrom(HtmlPage page) throws IOException {
        HtmlAnchor basketAnchor = page.getAnchorByHref("/basket");
        return basketAnchor.click();
    }

    public static String extractBasketItemCountFromPage(HtmlPage page) {
        DomElement basketItemCount = page.getElementById("basket-item-count");
        return isNull(basketItemCount) ? "" : basketItemCount.getTextContent();
    }

    private BasketTestHelper() {
    }
}
