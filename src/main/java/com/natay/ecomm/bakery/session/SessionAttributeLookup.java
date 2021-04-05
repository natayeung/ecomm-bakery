package com.natay.ecomm.bakery.session;

import com.natay.ecomm.bakery.basket.Basket;
import com.natay.ecomm.bakery.user.User;

import javax.servlet.http.HttpSession;

import static java.util.Objects.isNull;

/**
 * @author natayeung
 */
public class SessionAttributeLookup {

    public static int getItemCount(HttpSession session) {
        Object basket = session.getAttribute("scopedTarget.shoppingBasket");
        return isNull(basket) ? 0 : ((Basket) basket).itemCount();
    }

    public static User getUser(HttpSession session) {
        Object user = session.getAttribute("user");
        return isNull(user) ? null : (User) user;
    }

    public static boolean isYetToLogin(HttpSession session) {
        Object isYetToLogin = session.getAttribute("isYetToLogin");
        return isNull(isYetToLogin) || (boolean) isYetToLogin;
    }
}
