package com.natay.ecomm.bakery.basket;

import com.natay.ecomm.bakery.catalog.ProductAccessException;
import com.natay.ecomm.bakery.catalog.ProductNotFoundException;
import com.natay.ecomm.bakery.catalog.ProductQueryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpSession;

import static com.natay.ecomm.bakery.session.SessionAttributeLookup.getUser;
import static com.natay.ecomm.bakery.session.SessionAttributeLookup.isYetToLogin;

/**
 * @author natayeung
 */
@Controller
@RequestMapping("/basket")
public class BasketController {

    private static final Logger logger = LoggerFactory.getLogger(BasketController.class);

    private final Basket basket;

    public BasketController(Basket basket) {
        this.basket = basket;
    }

    @PostMapping("/add")
    public String addItemToBasket(@RequestParam("item-to-add") String productId)
            throws ProductAccessException, ProductNotFoundException {

        logger.info("Received request to add item {} to basket.", productId);

        basket.addItem(productId);

        logger.info("Item count: {}", basket.itemCount());

        return "redirect:/";
    }

    @PostMapping("/delete")
    public String removeItemFromBasket(@RequestParam("item-to-remove") String productId,
                                       HttpSession session,
                                       ModelMap model) {
        logger.info("Received request to remove item {} from basket.", productId);

        basket.removeItem(productId);
        populateModel(session, model);

        logger.info("Item count: {}", basket.itemCount());

        return "basket";
    }

    @GetMapping
    public String viewBasket(HttpSession session,
                             ModelMap model) {
        populateModel(session, model);

        return "basket";
    }

    @Bean
    @SessionScope
    public Basket shoppingBasket(ProductQueryPort productQueryPort) {
        return new ShoppingBasket(productQueryPort);
    }

    private void populateModel(HttpSession session, ModelMap model) {
        model.addAttribute("basketItems", basket.items());
        model.addAttribute("basketTotalPrice", basket.totalPrice());
        model.addAttribute("basketItemCount", basket.itemCount());

        model.addAttribute("isYetToLogin", isYetToLogin(session));
        model.addAttribute("user", getUser(session));
    }
}
