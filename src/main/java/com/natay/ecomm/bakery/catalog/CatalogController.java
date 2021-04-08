package com.natay.ecomm.bakery.catalog;

import com.natay.ecomm.bakery.security.UserCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import static com.natay.ecomm.bakery.session.SessionAttributeLookup.getBasket;

/**
 * @author natayeung
 */
@Controller
@RequestMapping("/")
public class CatalogController {

    private static final Logger logger = LoggerFactory.getLogger(CatalogController.class);

    private final Catalog catalog;

    @Autowired
    public CatalogController(Catalog catalog) {
        this.catalog = catalog;
    }

    @GetMapping
    public String displayCatalogForAllProducts(@AuthenticationPrincipal UserCredentials userCredentials,
                                               HttpSession session,
                                               ModelMap model)
            throws ProductAccessException {

        List<Product> products = catalog.findAllProducts();
        model.addAttribute("catalog", products);

        getBasket(session).ifPresent((b) -> model.addAttribute("userBasket", b));
        Optional.ofNullable(userCredentials).ifPresent(u -> model.addAttribute("user", u.getUsername()));

        return "index";
    }

    @GetMapping("/catalog/{product-type}")
    public String displayCatalogByType(@PathVariable(name = "product-type") String productType,
                                       @AuthenticationPrincipal UserCredentials userCredentials,
                                       HttpSession session,
                                       ModelMap model)
            throws ProductAccessException {

        logger.info("Received request to display catalog for {}", productType);

        List<Product> products = catalog.findProductByType(Product.Type.valueOf(productType));
        model.addAttribute("catalog", products);

        getBasket(session).ifPresent((b) -> model.addAttribute("userBasket", b));
        Optional.ofNullable(userCredentials).ifPresent(u -> model.addAttribute("user", u.getUsername()));

        return "catalog";
    }
}
