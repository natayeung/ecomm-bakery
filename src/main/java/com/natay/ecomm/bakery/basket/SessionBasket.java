package com.natay.ecomm.bakery.basket;

import com.natay.ecomm.bakery.basket.dto.BasketDto;
import com.natay.ecomm.bakery.basket.dto.ItemDto;
import com.natay.ecomm.bakery.catalog.ProductAccessException;
import com.natay.ecomm.bakery.catalog.ProductNotFoundException;
import com.natay.ecomm.bakery.catalog.persistence.ProductQueryPort;
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
        return new BasketDto(items, basket.itemCount(), basket.totalPrice(), items.isEmpty());
    }

    public void addItem(String productId) throws ProductAccessException, ProductNotFoundException {
        basket.addItem(productId);
    }

    public void removeItem(String productId) {
        basket.removeItem(productId);
    }

    private Function<BasketItem, ItemDto> itemMapper() {
        return i -> new ItemDto(i.productId(), i.title(), i.itemPrice(), i.itemTotal(), i.quantity());
    }
}
