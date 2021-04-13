package com.natay.ecomm.bakery.session;

import com.natay.ecomm.bakery.basket.Basket;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * @author natayeung
 */
public class SessionAttributeLookup {

    public static Optional<Basket> getBasket(HttpSession session) {
        return Optional.ofNullable(session.getAttribute("scopedTarget.shoppingBasket"))
                .flatMap(b -> Optional.of((Basket) b));
    }
}
