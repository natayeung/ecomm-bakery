package com.natay.ecomm.bakery.catalog;

import java.util.List;

/**
 * @author natayeung
 */
public interface Catalog {

    List<Product> findAllProducts() throws ProductAccessException;

    List<Product> findProductByType(Product.Type productType) throws ProductAccessException;
}
