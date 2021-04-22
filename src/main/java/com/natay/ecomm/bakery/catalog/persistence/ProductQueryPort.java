package com.natay.ecomm.bakery.catalog.persistence;

import com.natay.ecomm.bakery.catalog.Product;

import java.util.List;
import java.util.Optional;

/**
 * @author natayeung
 */
public interface ProductQueryPort {

    List<Product> findAll();

    List<Product> findByType(Product.Type type);

    Optional<Product> findById(String id);
}
