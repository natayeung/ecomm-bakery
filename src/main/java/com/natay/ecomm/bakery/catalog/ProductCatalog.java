package com.natay.ecomm.bakery.catalog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author natayeung
 */
@Service
public class ProductCatalog implements Catalog {

    private static final Logger logger = LoggerFactory.getLogger(ProductCatalog.class);

    private final ProductQueryPort productQueryPort;

    public ProductCatalog(ProductQueryPort productQueryPort) {
        this.productQueryPort = productQueryPort;
    }

    @Override
    public List<Product> findAllProducts() throws ProductAccessException {
        final List<Product> retrievedProducts;
        try {
            retrievedProducts = productQueryPort.findAll();
            logger.info("Retrieved {} products.", retrievedProducts.size());
        } catch (DataAccessException ex) {
            throw new ProductAccessException("Failed to retrieve products", ex);
        }

        return retrievedProducts;
    }

    @Override
    public List<Product> findProductByType(Product.Type productType) throws ProductAccessException {
        final List<Product> retrievedProducts;
        try {
            retrievedProducts = productQueryPort.findByType(productType);
            logger.info("Retrieved {} {} products.", retrievedProducts.size(), productType);
        } catch (DataAccessException ex) {
            throw new ProductAccessException("Failed to retrieve products", ex);
        }

        return retrievedProducts;
    }
}
