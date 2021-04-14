package com.natay.ecomm.bakery.basket;

import com.natay.ecomm.bakery.catalog.ProductQueryPort;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;

/**
 * @author natayeung
 */
@Component
@SessionScope
public class SessionBasket implements Serializable {

    private static final long serialVersionUID = 1L;

    private final ProductQueryPort productQueryPort;
    private Basket basket;

    public SessionBasket(ProductQueryPort productQueryPort) {
        this.productQueryPort = productQueryPort;
    }

    public Basket getBasket() {
        if (basket == null) {
            basket = new ShoppingBasket(productQueryPort);
        }
        return basket;
    }
}
