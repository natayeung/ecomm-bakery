package com.natay.ecomm.bakery.product.catalog;

import java.util.List;
import java.util.Optional;

/**
 * @author natayeung
 */
public interface Catalog {

    List<Product> findAllProducts();

    List<Product> findProductByType(Product.Type productType);

    Optional<Product> findProductById(String id);
}
