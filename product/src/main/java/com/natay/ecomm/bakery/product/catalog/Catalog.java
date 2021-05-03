package com.natay.ecomm.bakery.product.catalog;

import java.util.List;

/**
 * @author natayeung
 */
public interface Catalog {

    List<Product> findAllProducts() throws ProductAccessException;

    List<Product> findProductByType(Product.Type productType) throws ProductAccessException;
}
