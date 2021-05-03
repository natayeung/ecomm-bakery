package com.natay.ecomm.bakery.product.web;

import com.natay.ecomm.bakery.product.basket.BasketDto;
import com.natay.ecomm.bakery.product.basket.SessionBasket;
import com.natay.ecomm.bakery.product.catalog.Catalog;
import com.natay.ecomm.bakery.product.catalog.Product;
import com.natay.ecomm.bakery.product.catalog.ProductAccessException;
import com.natay.ecomm.bakery.user.authentication.AuthenticatedUserLookup;
import com.natay.ecomm.bakery.user.authentication.UserIdentity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author natayeung
 */
@Controller
@RequestMapping("/")
@Slf4j
public class CatalogController {

    private final AuthenticatedUserLookup authenticatedUserLookup;
    private final SessionBasket sessionBasket;
    private final Catalog catalog;

    public CatalogController(AuthenticatedUserLookup authenticatedUserLookup,
                             SessionBasket sessionBasket,
                             Catalog catalog) {
        this.authenticatedUserLookup = authenticatedUserLookup;
        this.sessionBasket = sessionBasket;
        this.catalog = catalog;
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

    @GetMapping
    public String viewCatalogForAllProducts(Model model)
            throws ProductAccessException {
        List<Product> products = catalog.findAllProducts();
        model.addAttribute("catalog", products);

        return "index";
    }

    @GetMapping("/catalog/{product-type}")
    public String viewCatalogByType(@PathVariable(name = "product-type") String productType,
                                    Model model)
            throws ProductAccessException {
        log.debug("Received request to display catalog for {}", productType);

        List<Product> products = catalog.findProductByType(Product.Type.valueOf(productType));
        model.addAttribute("catalog", products);
        model.addAttribute("productType", productType);

        return "catalog";
    }
}
