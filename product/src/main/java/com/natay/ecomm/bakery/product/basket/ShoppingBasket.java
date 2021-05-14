package com.natay.ecomm.bakery.product.basket;

import com.natay.ecomm.bakery.product.catalog.Catalog;
import com.natay.ecomm.bakery.product.catalog.Product;
import com.natay.ecomm.bakery.product.catalog.ProductAccessException;
import com.natay.ecomm.bakery.product.catalog.ProductNotFoundException;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

import static com.natay.ecomm.bakery.common.Arguments.requireNonBlank;
import static java.math.BigDecimal.ZERO;
import static java.util.Objects.isNull;

/**
 * @author natayeung
 */
@ToString
@Slf4j
public class ShoppingBasket implements Basket, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String basketRef = UUID.randomUUID().toString();
    private final Map<String, BasketItem> basketItems = new HashMap<>();
    private final Catalog catalog;

    public ShoppingBasket(Catalog catalog) {
        this.catalog = catalog;
        log.info("Shopping basket instantiated, ref={}", basketRef);
    }

    @Override
    public void addItem(String productId)
            throws ProductAccessException, ProductNotFoundException {
        requireNonBlank(productId, "Product ID must be specified");

        Product product = findProductById(productId);
        BasketItem basketItem = getBasketItem(product);
        basketItem.addOne();

        basketItems.put(productId, basketItem);
        log.info("Item {} added to basket {}, totalPrice={}", productId, basketRef, totalPrice());
    }

    @Override
    public void removeItem(String productId) {
        requireNonBlank(productId, "Product ID must be specified");

        BasketItem removed = basketItems.remove(productId);
        if (isNull(removed)) {
            log.warn("Unable to remove item {} from basket: unrecognised product ID", productId);
        } else {
            log.info("Item {} removed from basket {}, totalPrice={}", productId, basketRef, totalPrice());
        }
    }

    @Override
    public List<BasketItem> items() {
        return List.copyOf(basketItems.values());
    }

    @Override
    public int itemCount() {
        return basketItems.values().stream().map(BasketItem::quantity).reduce(0, Integer::sum);
    }

    @Override
    public BigDecimal totalPrice() {
        return basketItems.values().stream()
                .map(i -> i.itemPrice().multiply(BigDecimal.valueOf(i.quantity())))
                .reduce(ZERO, BigDecimal::add);
    }

    @Override
    public String basketRef() {
        return basketRef;
    }

    private Product findProductById(String productId)
            throws ProductNotFoundException, ProductAccessException {

        return catalog.findProductById(productId)
                .orElseThrow(() -> {
                    throw new ProductNotFoundException("Product not found for ID " + productId);
                });
    }

    private BasketItem getBasketItem(Product product) {
        return Optional.ofNullable(basketItems.get(product.id()))
                .orElse(BasketItem.from(product));
    }
}
