package com.natay.ecomm.bakery.catalog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Catalog {

    private static final Logger logger = LoggerFactory.getLogger(Catalog.class);

    private final ProductQueryPort productQueryPort;

    @Autowired
    public Catalog(ProductQueryPort productQueryPort) {
        this.productQueryPort = productQueryPort;
    }

    public List<Product> findAllProducts() {
        List<Product> retrievedProducts = productQueryPort.findAll();
        logger.info("Retrieved {} products", retrievedProducts.size());

        return retrievedProducts;
    }
}
