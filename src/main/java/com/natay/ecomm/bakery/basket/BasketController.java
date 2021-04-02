package com.natay.ecomm.bakery.basket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/basket")
@SessionAttributes("basket")
public class BasketController {

    @PostMapping
    public ModelAndView updateBasket(@RequestParam("added-product-id") String productId,
                                     @ModelAttribute("basket") Basket basket) {
        basket.addItem(productId);

        return new ModelAndView("redirect:/");
    }
}
