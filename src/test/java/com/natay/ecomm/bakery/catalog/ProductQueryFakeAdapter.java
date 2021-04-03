package com.natay.ecomm.bakery.catalog;

import java.util.List;
import java.util.Optional;

/**
 * @author natayeung
 */
public class ProductQueryFakeAdapter implements ProductQueryPort {

    private List<Product> products;

    public void load(Product... products) {
        this.products = List.of(products);
    }

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Optional<Product> findById(String id) {
        return Optional.empty();
    }
}