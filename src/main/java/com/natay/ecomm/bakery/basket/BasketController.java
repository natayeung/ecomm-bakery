package com.natay.ecomm.bakery.basket;

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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Collections;

import static java.util.Objects.isNull;

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
    public ModelAndView viewBasket(HttpSession session, ModelMap model) {
        Object basket = session.getAttribute("scopedTarget.shoppingBasket");

        model.addAttribute("basketItems",
                isNull(basket) ? Collections.emptyList() : ((Basket) basket).items());
        model.addAttribute("basketTotalPrice",
                isNull(basket) ? 0 : ((Basket) basket).totalPrice());
        model.addAttribute("basketItemCount",
                isNull(basket) ? 0 : ((Basket) basket).itemCount());

        return new ModelAndView("basket");
    }

    @Bean
    @SessionScope
    public Basket shoppingBasket(ProductQueryPort productQueryPort) {
        return new ShoppingBasket(productQueryPort);
    }
}
