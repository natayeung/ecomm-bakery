package com.natay.ecomm.bakery.product.web;

import com.natay.ecomm.bakery.checkout.payment.ShippingDetailsDto;
import com.natay.ecomm.bakery.product.basket.BasketDto;
import com.natay.ecomm.bakery.product.basket.SessionBasket;
import com.natay.ecomm.bakery.product.catalog.ProductAccessException;
import com.natay.ecomm.bakery.product.catalog.ProductNotFoundException;
import com.natay.ecomm.bakery.user.account.AddressService;
import com.natay.ecomm.bakery.user.authentication.AuthenticatedUserLookup;
import com.natay.ecomm.bakery.user.authentication.UserIdentity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.function.Consumer;

import static com.natay.ecomm.bakery.checkout.payment.ShippingDetailsDtoFactory.createShippingDetailsDto;

/**
 * @author natayeung
 */
@Controller
@RequestMapping("/basket")
@Slf4j
public class BasketController {

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
        log.info("Received request to add item {} to basket {}", productId, sessionBasket.getBasketRef());

        sessionBasket.addItem(productId);
        log.info("Item count: {}", sessionBasket.getBasket().getItemCount());

        String redirectUri = Optional.ofNullable(productType).map(type -> "/catalog/" + type).orElse("/");
        return "redirect:" + redirectUri;
    }

    @PostMapping("/delete")
    public String removeItemFromBasket(@RequestParam(name = "item-to-remove") String productId) {
        log.info("Received request to remove item {} from basket {}", productId, sessionBasket.getBasketRef());

        sessionBasket.removeItem(productId);
        log.info("Item count: {}", sessionBasket.getBasket().getItemCount());

        return "redirect:/basket";
    }

    @GetMapping
    public String viewBasket(Model model) {
        log.info("Received request to view basket {}", sessionBasket.getBasketRef());

        addShippingDetailsToModelIfPresent(model);

        return "basket";
    }

    private void addShippingDetailsToModelIfPresent(Model model) {
        authenticatedUserLookup.getAuthenticatedUser()
                .ifPresent(addShippingDetails(model));
    }

    private Consumer<UserIdentity> addShippingDetails(Model model) {
        return user -> addressService.findAddressByEmail(user.username())
                .ifPresentOrElse(
                        address -> {
                            ShippingDetailsDto dto = createShippingDetailsDto(user, address);
                            model.addAttribute("shippingDetails", dto);
                        },
                        () -> {
                            ShippingDetailsDto dto = createShippingDetailsDto(user);
                            model.addAttribute("shippingDetails", dto);
                        });
    }
}
