package com.natay.ecomm.bakery.basket;

import com.natay.ecomm.bakery.account.AddressService;
import com.natay.ecomm.bakery.basket.dto.BasketDto;
import com.natay.ecomm.bakery.catalog.ProductAccessException;
import com.natay.ecomm.bakery.catalog.ProductNotFoundException;
import com.natay.ecomm.bakery.checkout.dto.ShippingDetailsDto;
import com.natay.ecomm.bakery.security.authentication.UserIdentity;
import com.natay.ecomm.bakery.security.authentication.AuthenticatedUserLookup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.natay.ecomm.bakery.checkout.dto.ShippingDetailsDtoFactory.createShippingDetailsDto;

/**
 * @author natayeung
 */
@Controller
@RequestMapping("/basket")
public class BasketController {

    private static final Logger logger = LoggerFactory.getLogger(BasketController.class);

    private final AuthenticatedUserLookup authenticatedUserLookup;
    private final SessionBasket sessionBasket;
    private final AddressService addressService;

    public BasketController(AuthenticatedUserLookup authenticatedUserLookup,
                            SessionBasket sessionBasket,
                            AddressService addressService) {
        this.authenticatedUserLookup = authenticatedUserLookup;
        this.sessionBasket = sessionBasket;
        this.addressService = addressService;
    }

    @ModelAttribute("account")
    public String addAccountToModel() {
        return authenticatedUserLookup.getAuthenticatedUser()
                .map(UserIdentity::username).orElse(null);
    }

    @ModelAttribute("basket")
    public BasketDto addBasketToModel() {
        return sessionBasket.getBasket();
    }

    @PostMapping("/add")
    public String addItemToBasket(@RequestParam(name = "item-to-add") String productId,
                                  @RequestParam(name = "product-type", required = false) String productType)
            throws ProductAccessException, ProductNotFoundException {
        logger.info("Received request to add item {} to basket {}", productId, sessionBasket.getBasketRef());

        sessionBasket.addItem(productId);
        logger.info("Item count: {}", sessionBasket.getBasket().getItemCount());

        String redirectUri = Optional.ofNullable(productType).map(type -> "/catalog/" + type).orElse("/");
        return "redirect:" + redirectUri;
    }

    @PostMapping("/delete")
    public String removeItemFromBasket(@RequestParam(name = "item-to-remove") String productId) {
        logger.info("Received request to remove item {} from basket {}", productId, sessionBasket.getBasketRef());

        sessionBasket.removeItem(productId);
        logger.info("Item count: {}", sessionBasket.getBasket().getItemCount());

        return "redirect:/basket";
    }

    @GetMapping
    public String viewBasket(Model model) {
        logger.info("Received request to view basket {}", sessionBasket.getBasketRef());

        addShippingDetailsToModelIfPresent(model);

        return "basket";
    }

    private void addShippingDetailsToModelIfPresent(Model model) {
        authenticatedUserLookup.getAuthenticatedUser()
                .ifPresent(user -> addressService.findAddressByEmail(user.username())
                        .ifPresent(address -> {
                            ShippingDetailsDto dto = createShippingDetailsDto(user, address);
                            model.addAttribute("shippingDetails", dto);
                        }));
    }
}
