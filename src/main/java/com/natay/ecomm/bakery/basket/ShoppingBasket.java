package com.natay.ecomm.bakery.basket;

import com.natay.ecomm.bakery.catalog.Price;
import com.natay.ecomm.bakery.catalog.Product;
import com.natay.ecomm.bakery.catalog.ProductQueryPort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.natay.ecomm.bakery.utils.Arguments.requireNonBlank;

/**
 * @author natayeung
 */
public class ShoppingBasket implements Basket {

    private final Map<String, BasketItem> basketItems = new HashMap<>();
    private final ProductQueryPort productQueryPort;

    public ShoppingBasket(ProductQueryPort productQueryPort) {
        this.productQueryPort = productQueryPort;
    }

    @Override
    public void addItem(String productId) {
        requireNonBlank(productId, "Product ID must be specified");
        Product product = findProductById(productId);

        BasketItem basketItem = getBasketItem(product);
        basketItem.addOne();

        basketItems.put(productId, basketItem);
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
    public Price totalPrice() {
        return basketItems.values().stream().map(BasketItem::itemPrice).reduce(Price.of(0), Price::add);
    }

    private Product findProductById(String productId) {
        Optional<Product> product = productQueryPort.findById(productId);
        if (product.isEmpty()) {
            throw new IllegalArgumentException("Product ID " + productId + " is not recognised");
        }
        return product.get();
    }

    private BasketItem getBasketItem(Product product) {
        return basketItems.containsKey(product.productId())
                ? basketItems.get(product.productId())
                : BasketItem.from(product);
    }
}
