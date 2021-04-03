package com.natay.ecomm.bakery.catalog;

import java.util.List;
import java.util.Optional;

/**
 * @author natayeung
 */
public interface ProductQueryPort {

    List<Product> findAll();

    Optional<Product> findById(String id);
}
