package com.natay.ecomm.bakery.catalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Catalog {

    private final ProductQueryPort productQueryPort;

    @Autowired
    public Catalog(ProductQueryPort productQueryPort) {
        this.productQueryPort = productQueryPort;
    }

    public List<Product> findAllProducts() {
        return productQueryPort.findAll();
    }
}
