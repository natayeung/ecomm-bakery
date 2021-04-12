package com.natay.ecomm.bakery.basket;

import com.natay.ecomm.bakery.account.AddressService;
import com.natay.ecomm.bakery.catalog.ProductAccessException;
import com.natay.ecomm.bakery.catalog.ProductNotFoundException;
import com.natay.ecomm.bakery.catalog.ProductQueryPort;
import com.natay.ecomm.bakery.security.AuthenticatedUserLookup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpSession;

import static com.natay.ecomm.bakery.session.SessionAttributeLookup.getBasket;

/**
 * @author natayeung
 */
@Controller
@RequestMapping("/basket")
public class BasketController {

    private static final Logger logger = LoggerFactory.getLogger(BasketController.class);

    private final AuthenticatedUserLookup authenticatedUserLookup;
    private final AddressService addressService;
    private final Basket basket;

    public BasketController(AuthenticatedUserLookup authenticatedUserLookup,
                            AddressService addressService,
                            Basket basket) {
        this.authenticatedUserLookup = authenticatedUserLookup;
        this.addressService = addressService;
        this.basket = basket;
    }

    @PostMapping("/add")
    public String addItemToBasket(@RequestParam("item-to-add") String productId)
            throws ProductAccessException, ProductNotFoundException {

        logger.info("Received request to add item {} to basket {}", productId, basket.basketRef());

        basket.addItem(productId);

        logger.info("Item count: {}", basket.itemCount());

        return "redirect:/";
    }

    @PostMapping("/delete")
    public String removeItemFromBasket(@RequestParam("item-to-remove") String productId) {

        logger.info("Received request to remove item {} from basket {}", productId, basket.basketRef());

        basket.removeItem(productId);

        logger.info("Item count: {}", basket.itemCount());

        return "redirect:/basket";
    }

    @GetMapping
    public String viewBasket(HttpSession session,
                             Model model) {

        logger.info("Received request to view basket {}", basket.basketRef());

        getBasket(session).ifPresent((b) -> model.addAttribute("userBasket", b));
        populateModelWithUserAndAddressInfoIfPresent(model);

        return "basket";
    }

    @Bean
    @SessionScope
    public Basket shoppingBasket(ProductQueryPort productQueryPort) {
        return new ShoppingBasket(productQueryPort);
    }

    private void populateModelWithUserAndAddressInfoIfPresent(Model model) {
        authenticatedUserLookup.getAuthenticatedUser()
                .ifPresent(
                        u -> {
                            model.addAttribute("user", u.username());

                            addressService.findAddressByEmail(u.username())
                                    .ifPresent(a -> model.addAttribute("address", a));
                        });
    }
}
