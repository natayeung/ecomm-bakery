package com.natay.ecomm.bakery.catalog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author natayeung
 */
@Service
public class ProductCatalog implements Catalog<Product> {

    private static final Logger logger = LoggerFactory.getLogger(ProductCatalog.class);

    private final ProductQueryPort productQueryPort;

    @Autowired
    public ProductCatalog(ProductQueryPort productQueryPort) {
        this.productQueryPort = productQueryPort;
    }

    @Override
    public List<Product> findAll() {
        List<Product> retrievedProducts = productQueryPort.findAll();
        logger.info("Retrieved {} products.", retrievedProducts.size());

        return retrievedProducts;
    }
}
