package com.natay.ecomm.bakery.catalog;

import com.natay.ecomm.bakery.basket.BasketDto;
import com.natay.ecomm.bakery.basket.SessionBasket;
import com.natay.ecomm.bakery.security.authentication.AuthenticatedUser;
import com.natay.ecomm.bakery.security.authentication.AuthenticatedUserLookup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CatalogController {

    private static final Logger logger = LoggerFactory.getLogger(CatalogController.class);

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
                .map(AuthenticatedUser::username).orElse(null);
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
        logger.debug("Received request to display catalog for {}", productType);

        List<Product> products = catalog.findProductByType(Product.Type.valueOf(productType));
        model.addAttribute("catalog", products);

        return "catalog";
    }
}
