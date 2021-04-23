package com.natay.ecomm.bakery.basket;

import com.natay.ecomm.bakery.catalog.Product;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

import static com.natay.ecomm.bakery.utils.Arguments.requireNonBlank;
import static com.natay.ecomm.bakery.utils.Arguments.requireNonNull;

/**
 * @author natayeung
 */
@ToString
@Getter
@Accessors(fluent = true)
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

        return new BasketItem(product.id(), product.title(), product.price());
    }

    public void addOne() {
        quantity++;
        itemTotal = itemTotal().add(itemPrice);
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }
}
