package com.natay.ecomm.bakery.basket;

import com.natay.ecomm.bakery.catalog.Price;
import com.natay.ecomm.bakery.catalog.Product;

import static com.natay.ecomm.bakery.utils.Arguments.requireNonBlank;
import static com.natay.ecomm.bakery.utils.Arguments.requireNonNull;

/**
 * @author natayeung
 */
public class BasketItem {

    private final String productId;
    private final String itemTitle;
    private final Price itemPrice;
    private int quantity;
    private Price itemTotal;

    private BasketItem(String productId, String itemTitle, Price itemPrice) {
        this.productId = requireNonBlank(productId, "Product ID must be specified");
        this.itemTitle = requireNonBlank(itemTitle, "Item title must be specified");
        this.itemPrice = requireNonNull(itemPrice, "Item price must be specified");
        this.quantity = 0;
        this.itemTotal = Price.of(0);
    }

    public static BasketItem from(Product product) {
        requireNonNull(product, "Product must be specified");

        return new BasketItem(product.productId(), product.title(), product.price());
    }

    public void addOne() {
        quantity++;
        itemTotal = itemTotal().add(itemPrice);
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public String productId() {
        return productId;
    }

    public String title() {
        return itemTitle;
    }

    public int quantity() {
        return quantity;
    }

    public Price itemPrice() {
        return itemPrice;
    }

    public Price itemTotal() {
        return itemTotal;
    }

    @Override
    public String toString() {
        return "BasketItem{" +
                "productId='" + productId + '\'' +
                ", itemTitle='" + itemTitle + '\'' +
                ", itemPrice=" + itemPrice +
                ", quantity=" + quantity +
                ", itemTotal=" + itemTotal +
                '}';
    }
}
