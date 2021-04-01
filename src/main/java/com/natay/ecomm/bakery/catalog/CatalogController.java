package com.natay.ecomm.bakery.catalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class CatalogController {

    private final Catalog catalog;

    @Autowired
    public CatalogController(Catalog catalog) {
        this.catalog = catalog;
    }

    @GetMapping
    public ModelAndView displayCatalog(Map<String, Object> model) {
        List<Product> products = catalog.findAllProducts();
        model.put("catalog", products);

        return new ModelAndView("index", model);
    }
}
