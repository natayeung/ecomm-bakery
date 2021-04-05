package com.natay.ecomm.bakery.order;

import com.natay.ecomm.bakery.user.UserAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import static com.natay.ecomm.bakery.session.SessionAttributeLookup.getBasket;
import static com.natay.ecomm.bakery.session.SessionAttributeLookup.getUser;

/**
 * @author natayeung
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @PostMapping
    public String processOrder(@ModelAttribute("address") UserAddress address,
                               HttpSession session,
                               ModelMap model) {

        logger.info("Received request to create order, delivery address: {}", address);

        processOrder(session);

        getUser(session).ifPresent((u) -> model.addAttribute("user", u));
        session.invalidate();

        return "order-confirm";
    }

    private void processOrder(HttpSession session) {
        getBasket(session).ifPresentOrElse(
                (b) -> logger.info("Processing order from {}", b),
                () -> {
                    throw new IllegalStateException("Unable to retrieve shopping basket");
                });
    }
}
