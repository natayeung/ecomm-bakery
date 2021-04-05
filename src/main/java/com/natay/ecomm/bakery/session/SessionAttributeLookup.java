package com.natay.ecomm.bakery.session;

import com.natay.ecomm.bakery.basket.Basket;
import com.natay.ecomm.bakery.user.User;

import javax.servlet.http.HttpSession;

import java.util.Optional;

import static java.util.Objects.isNull;

/**
 * @author natayeung
 */
public class SessionAttributeLookup {

    public static Optional<Basket> getBasket(HttpSession session) {
        Object basket = session.getAttribute("scopedTarget.shoppingBasket");
        return isNull(basket) ? Optional.empty() : Optional.of((Basket) basket);
    }

    public static Optional<User> getUser(HttpSession session) {
        Object user = session.getAttribute("user");
        return isNull(user) ? Optional.empty() : Optional.of((User) user);
    }
}
