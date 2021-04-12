package com.natay.ecomm.bakery.catalog;

import com.natay.ecomm.bakery.security.AuthenticatedUserLookup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.natay.ecomm.bakery.session.SessionAttributeLookup.getBasket;

/**
 * @author natayeung
 */
@Controller
@RequestMapping("/")
public class CatalogController {

    private static final Logger logger = LoggerFactory.getLogger(CatalogController.class);

    private final AuthenticatedUserLookup authenticatedUserLookup;
    private final Catalog catalog;

    public CatalogController(AuthenticatedUserLookup authenticatedUserLookup, Catalog catalog) {
        this.authenticatedUserLookup = authenticatedUserLookup;
        this.catalog = catalog;
    }

    @GetMapping
    public String displayCatalogForAllProducts(HttpSession session,
                                     ModelMap model)
            throws ProductAccessException {

        List<Product> products = catalog.findAllProducts();
        model.addAttribute("catalog", products);

        populateModelWithUserAndBasketInfoIfPresent(session, model);

        return "index";
    }

    @GetMapping("/catalog/{product-type}")
    public String displayCatalogByType(@PathVariable(name = "product-type") String productType,
                                       HttpSession session,
                                       ModelMap model)
            throws ProductAccessException {

        logger.info("Received request to display catalog for {}", productType);

        List<Product> products = catalog.findProductByType(Product.Type.valueOf(productType));
        model.addAttribute("catalog", products);

        populateModelWithUserAndBasketInfoIfPresent(session, model);

        return "catalog";
    }

    private void populateModelWithUserAndBasketInfoIfPresent(HttpSession session, ModelMap model) {
        authenticatedUserLookup.getAuthenticatedUser()
                .ifPresent(user -> model.addAttribute("user", user.username()));

        getBasket(session).ifPresent((b) -> model.addAttribute("userBasket", b));
    }
}
