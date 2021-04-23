package com.natay.ecomm.bakery.catalog;

import com.natay.ecomm.bakery.catalog.persistence.ProductQueryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.natay.ecomm.bakery.utils.Arguments.requireNonNull;

/**
 * @author natayeung
 */
@Service
@Slf4j
public class ProductCatalog implements Catalog {

    private final ProductQueryPort productQueryPort;

    public ProductCatalog(ProductQueryPort productQueryPort) {
        this.productQueryPort = productQueryPort;
    }

    @Override
    public List<Product> findAllProducts() throws ProductAccessException {
        List<Product> retrievedProducts;
        try {
            retrievedProducts = productQueryPort.findAll();
            log.info("Retrieved {} products.", retrievedProducts.size());
        } catch (DataAccessException ex) {
            throw new ProductAccessException("Failed to retrieve products", ex);
        }

        return retrievedProducts;
    }

    @Override
    public List<Product> findProductByType(Product.Type productType) throws ProductAccessException {
        requireNonNull(productType, "Product type must be specified");

        List<Product> retrievedProducts;
        try {
            retrievedProducts = productQueryPort.findByType(productType);
            log.info("Retrieved {} {} products.", retrievedProducts.size(), productType);
        } catch (DataAccessException ex) {
            throw new ProductAccessException("Failed to retrieve products", ex);
        }

        return retrievedProducts;
    }
}
