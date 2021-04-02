package com.natay.ecomm.bakery.basket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

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

    @PostMapping
    public String updateBasket(@RequestParam("added-product-id") String productId) {
        basket.addItem(productId);
        logger.info("Product {} added to basket. Item count updated to {}", productId, basket.itemCount());

        return "redirect:/";
    }

    @GetMapping
    public ModelAndView viewBasket() {

        return new ModelAndView("basket");
    }

    @Bean
    @SessionScope
    public Basket shoppingBasket() {
        return new ShoppingBasket();
    }
}
