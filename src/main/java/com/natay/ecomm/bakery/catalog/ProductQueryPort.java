package com.natay.ecomm.bakery.catalog;

import java.util.List;

/**
 * @author natayeung
 */
public interface ProductQueryPort {

    List<Product> findAll();
}
