package com.natay.ecomm.bakery.basket;

import com.natay.ecomm.bakery.catalog.Product;
import com.natay.ecomm.bakery.catalog.ProductAccessException;
import com.natay.ecomm.bakery.catalog.ProductNotFoundException;
import com.natay.ecomm.bakery.catalog.ProductQueryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.util.*;

import static com.natay.ecomm.bakery.utils.Arguments.requireNonBlank;
import static java.util.Objects.isNull;

/**
 * @author natayeung
 */
public class ShoppingBasket implements Basket {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingBasket.class);

    private final String basketRef = UUID.randomUUID().toString();
    private final Map<String, BasketItem> basketItems = new HashMap<>();
    private final ProductQueryPort productQueryPort;

    public ShoppingBasket(ProductQueryPort productQueryPort) {
        this.productQueryPort = productQueryPort;
        logger.info("Shopping basket instantiated, ref={}", basketRef);
    }

    @Override
    public void addItem(String productId)
            throws ProductAccessException, ProductNotFoundException {

        requireNonBlank(productId, "Product ID must be specified");
        Product product = findProductById(productId);

        BasketItem basketItem = getBasketItem(product);
        basketItem.addOne();

        basketItems.put(productId, basketItem);
        logger.info("Item {} added to basket, ref={}", productId, basketRef);
    }

    @Override
    public void removeItem(String productId) {
        requireNonBlank(productId, "Product ID must be specified");

        BasketItem removed = basketItems.remove(productId);
        if (isNull(removed)) {
            logger.warn("Unable to remove item {} from basket: unrecognised product ID", productId);
        } else {
            logger.info("Item {} removed from basket, ref={}", productId, basketRef);
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
        return basketItems.values().stream().map(BasketItem::itemPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public String basketRef() {
        return basketRef;
    }

    @Override
    public String toString() {
        return "ShoppingBasket{" +
                "basketRef='" + basketRef + '\'' +
                ", basketItems=" + basketItems +
                '}';
    }

    private Product findProductById(String productId)
            throws ProductNotFoundException, ProductAccessException {

        Optional<Product> product;
        try {
            product = productQueryPort.findById(productId);
        } catch (DataAccessException ex) {
            throw new ProductAccessException("Unable to retrieve product with id " + productId, ex);
        }

        if (product.isEmpty()) {
            throw new ProductNotFoundException("Product not found for id " + productId);
        }

        return product.get();
    }

    private BasketItem getBasketItem(Product product) {
        return Optional.ofNullable(basketItems.get(product.productId()))
                .orElse(BasketItem.from(product));
    }
}
