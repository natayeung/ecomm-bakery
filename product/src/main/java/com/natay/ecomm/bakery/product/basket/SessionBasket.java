package com.natay.ecomm.bakery.product.basket;

import com.natay.ecomm.bakery.product.catalog.ProductAccessException;
import com.natay.ecomm.bakery.product.catalog.ProductNotFoundException;
import com.natay.ecomm.bakery.product.catalog.persistence.ProductQueryPort;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * @author natayeung
 */
@Component
@SessionScope
public class SessionBasket implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Basket basket;

    public SessionBasket(ProductQueryPort productQueryPort) {
        this.basket = new ShoppingBasket(productQueryPort);
    }

    public String getBasketRef() {
        return basket.basketRef();
    }

    public BasketDto getBasket() {
        List<ItemDto> items = basket.items().stream().map(itemMapper()).collect(toList());
        return new BasketDto(items, basket.itemCount(), basket.totalPrice());
    }

    public void addItem(String productId) throws ProductAccessException, ProductNotFoundException {
        basket.addItem(productId);
    }

    public void removeItem(String productId) {
        basket.removeItem(productId);
    }

    private Function<BasketItem, ItemDto> itemMapper() {
        return i -> ItemDto.builder()
                .productId(i.productId())
                .itemTitle(i.itemTitle())
                .itemPrice(i.itemPrice())
                .itemTotal(i.itemTotal())
                .quantity(i.quantity())
                .build();
    }
}
