package com.natay.ecomm.bakery.order;

import com.natay.ecomm.bakery.basket.Basket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static java.util.Objects.isNull;

/**
 * @author natayeung
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @PostMapping
    public String processOrder(@RequestParam("address1") String addressLine1,
                               @RequestParam("address2") String addressLine2,
                               @RequestParam("postcode") String postcode,
                               HttpSession session,
                               ModelMap model) {

        logger.info("Received request to create order, delivery address: addressLine1={}, addressLine2={}, postcode={}",
                addressLine1, addressLine2, postcode);

        processOrder(session);
        session.invalidate();
        model.addAttribute("basketItemCount", 0);

        return "order-confirm";
    }

    private void processOrder(HttpSession session) {
        Optional<Basket> basket = getBasket(session);
        basket.ifPresentOrElse(
                (b) -> logger.info("Processing order from {}", b),
                () -> {
                    throw new IllegalStateException("Unable to retrieve shopping basket");
                });
    }

    private Optional<Basket> getBasket(HttpSession session) {
        Object basket = session.getAttribute("scopedTarget.shoppingBasket");
        return isNull(basket) ? Optional.empty() : Optional.of((Basket) basket);
    }
}
