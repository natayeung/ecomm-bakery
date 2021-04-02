package com.natay.ecomm.bakery.catalog;

import com.natay.ecomm.bakery.basket.Basket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
@SessionAttributes({"basket", "catalog"})
public class CatalogController {

    private final Catalog catalog;

    @Autowired
    public CatalogController(Catalog catalog) {
        this.catalog = catalog;
    }

    @GetMapping
    public String displayCatalog(ModelMap model) {
        if (isCatalogNotPopulated(model)) {
            List<Product> products = catalog.findAllProducts();
            model.addAttribute("catalog", products);
        }

        return "index";
    }

    @ModelAttribute("basket")
    public Basket basket() {
        return new Basket();
    }

    @ModelAttribute("catalog")
    public List<Product> catalog() {
        return new ArrayList<>();
    }

    private boolean isCatalogNotPopulated(ModelMap model) {
        return !model.containsAttribute("catalog")
                || ((List<?>) model.getAttribute("catalog")).isEmpty();
    }
}
