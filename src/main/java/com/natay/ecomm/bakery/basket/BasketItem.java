package com.natay.ecomm.bakery.basket;

import com.natay.ecomm.bakery.catalog.Product;

import java.math.BigDecimal;

import static com.natay.ecomm.bakery.utils.Arguments.requireNonBlank;
import static com.natay.ecomm.bakery.utils.Arguments.requireNonNull;

/**
 * @author natayeung
 */
public class BasketItem {

    private final String productId;
    private final String itemTitle;
    private final BigDecimal itemPrice;
    private BigDecimal itemTotal;
    private int quantity;

    private BasketItem(String productId, String itemTitle, BigDecimal itemPrice) {
        this.productId = requireNonBlank(productId, "Product ID must be specified");
        this.itemTitle = requireNonBlank(itemTitle, "Item title must be specified");
        this.itemPrice = requireNonNull(itemPrice, "Item price must be specified");
        this.quantity = 0;
        this.itemTotal = BigDecimal.ZERO;
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

    public BigDecimal itemPrice() {
        return itemPrice;
    }

    public BigDecimal itemTotal() {
        return itemTotal;
    }

    public int quantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "BasketItem{" +
                "productId='" + productId + '\'' +
                ", itemTitle='" + itemTitle + '\'' +
                ", itemPrice=" + itemPrice +
                ", itemTotal=" + itemTotal +
                ", quantity=" + quantity +
                '}';
    }
}
