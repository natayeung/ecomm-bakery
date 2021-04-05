package com.natay.ecomm.bakery.catalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static com.natay.ecomm.bakery.session.SessionAttributeLookup.*;
import static java.util.Objects.isNull;

/**
 * @author natayeung
 */
@Controller
@RequestMapping("/")
@SessionAttributes("catalog")
public class CatalogController {

    private final Catalog<Product> productCatalog;

    @Autowired
    public CatalogController(Catalog<Product> productCatalog) {
        this.productCatalog = productCatalog;
    }

    @GetMapping
    public String displayCatalog(HttpSession session,
                                 ModelMap model)
            throws ProductAccessException {

        if (isCatalogNotPopulated(model)) {
            List<Product> products = productCatalog.findAll();
            model.addAttribute("catalog", products);
        }

        getBasket(session).ifPresent((b) -> model.addAttribute("userBasket", b));
        getUser(session).ifPresent((u) -> model.addAttribute("user", u));

        return "index";
    }

    @ModelAttribute("catalog")
    public List<Product> catalog() {
        return new ArrayList<>();
    }

    private boolean isCatalogNotPopulated(ModelMap model) {
        Object catalog = model.getAttribute("catalog");
        return isNull(catalog) || ((List<?>) catalog).isEmpty();
    }
}
