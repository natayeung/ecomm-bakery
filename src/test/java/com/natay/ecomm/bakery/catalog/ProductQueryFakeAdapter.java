package com.natay.ecomm.bakery.catalog;

import java.util.List;

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
}